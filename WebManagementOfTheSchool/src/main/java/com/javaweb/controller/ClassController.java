package com.javaweb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaweb.dao.ClassDAO;
import com.javaweb.dao.Common;

@WebServlet(name = "ClassController", urlPatterns = { "/class-list", "/new-class", "/create-new-class", "/edit-class",
		"/update-class", "/class-details" })
public class ClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String action, getClassID, orderBy, getClassName, getStatus;
	private static String listOfSubjectsID[];
	private static int pageSize, currentPage, pageTotal;

	public ClassController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/class-list":
			showClassList(request, response);
			break;

		case "/new-class":
			showCreateView(request, response);
			break;

		case "/edit-class":
			showEditView(request, response);
			break;

		case "/class-details":
			showClassDetails(request, response);
			break;
		}

		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/create-new-class":
			createNewClass(request, response);
			break;

		case "/update-class":
			updateClass(request, response);
			break;
		}
	}

	// hien thi danh sach lop hoc
	private void showClassList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String keyword = request.getParameter("keyword");

			// sap xep theo ten lop hoc
			sortByStudentName(request, response);

			if (keyword == null) {
				// tinh tong so lop hoc hien co
				int numberOfClasses = ClassDAO.getAll().size();

				// tinh tong so trang
				pageTotal = (numberOfClasses % pageSize == 0) ? (numberOfClasses / pageSize)
						: ((numberOfClasses / pageSize) + 1);

				// custom thong tin lop hoc de tra ra view
				List<HashMap<String, String>> classList = ClassDAO.getClassListByNameAndPage(currentPage, pageSize,
						orderBy, null);

				request.setAttribute("classList", classList);
				request.setAttribute("numberOfClasses", numberOfClasses);
			} else {
				// danh sach toan bo lop hoc tim duoc
				List<HashMap<String, String>> classListByName = ClassDAO.getClassListByNameAndPage(0, 0, null, keyword);

				// tong so luong lop hoc tim duoc
				int numberOfThreadsFound = classListByName.size();

				// danh sach lop hoc theo ten cua tung phan trang
				List<HashMap<String, String>> classListByNameAndPage = ClassDAO.getClassListByNameAndPage(currentPage,
						pageSize, orderBy, keyword);

				pageTotal = (numberOfThreadsFound % pageSize == 0) ? (numberOfThreadsFound / pageSize)
						: ((numberOfThreadsFound / pageSize) + 1);

				request.setAttribute("keyword", keyword);
				request.setAttribute("classListByNameAndPage", classListByNameAndPage);
				request.setAttribute("numberOfThreadsFound", numberOfThreadsFound);
			}

			request.setAttribute("pageTotal", pageTotal);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Danh sách lớp học");
		request.setAttribute("viewContent", "/WEB-INF/views/class/class-list.jsp");
	}

	// hien thi trang them moi lop hoc
	private void showCreateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setAttribute("classIDRandom", Common.generateRandomString(10));
			request.setAttribute("numberOfSubjects", ClassDAO.getActiveSubjects().size());
			request.setAttribute("activeSubjectList", ClassDAO.getActiveSubjects());
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu lớp học!");
		}

		request.setAttribute("viewTitle", "Thêm lớp học");
		request.setAttribute("viewContent", "/WEB-INF/views/class/create-new-class.jsp");
	}

	// hien thi trang chinh sua thong tin lop hoc
	private void showEditView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getClassID = request.getParameter("class-id");

		try {
			request.setAttribute("classInfo", ClassDAO.getDetails(getClassID));
			request.setAttribute("activeSubjectList", ClassDAO.getActiveSubjects());
			request.setAttribute("subjectsListByClassID", ClassDAO.getSubjectsIDListByClassID(getClassID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Sửa lớp học");
		request.setAttribute("viewContent", "/WEB-INF/views/class/edit-class.jsp");
	}

	// hien thi trang chi tiet lop hoc
	private void showClassDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getClassID = request.getParameter("class-id");

		try {
			HashMap<String, String> classInfo = ClassDAO.getDetails(getClassID);
			
			classInfo.put("numberOfActiveStudents", String.valueOf(ClassDAO.getNumberOfStudents(getClassID, 1)));
			classInfo.put("numberOfInactiveStudents", String.valueOf(ClassDAO.getNumberOfStudents(getClassID, 0)));
			
			request.setAttribute("classInfo", classInfo);
			request.setAttribute("listOfSelectedSubjects", ClassDAO.getSubjectsListOfClass(getClassID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Thông tin lớp học");
		request.setAttribute("viewContent", "/WEB-INF/views/class/class-details.jsp");
	}

	// xu ly them moi lop hoc
	private void createNewClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getClassName = request.getParameter("className");
			getClassID = request.getParameter("classID");
			getStatus = request.getParameter("status");
			listOfSubjectsID = request.getParameterValues("subjectID");

			HttpSession session = request.getSession();

			if (ClassDAO.isError(getClassID, getClassName) || listOfSubjectsID == null) {
				session.setAttribute("ERROR_MSG",
						"Vui lòng nhập đầy đủ thông tin và lưu ý tên lớp học không được phép trùng lặp!");

				request.setAttribute("classIDRandom", Common.generateRandomString(10));
				request.setAttribute("numberOfSubjects", ClassDAO.getActiveSubjects().size());
				request.setAttribute("activeSubjectList", ClassDAO.getActiveSubjects());
				request.setAttribute("viewTitle", "Thêm lớp học");
				request.setAttribute("viewContent", "/WEB-INF/views/class/create-new-class.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				HashMap<Integer, String> data = new HashMap<>();
				data.put(1, getClassID);
				data.put(2, getClassName);
				data.put(3, getStatus);

				ClassDAO.Add(data, listOfSubjectsID);

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc thêm mới!");
				response.sendRedirect("class-list");
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi thêm mới lớp học!");
		}
	}

	// xu ly viec chinh sua thong tin lop hoc
	private void updateClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			getClassName = request.getParameter("className");
			getClassID = request.getParameter("classID");
			String copyClassID = getClassID;
			getStatus = request.getParameter("status");
			listOfSubjectsID = request.getParameterValues("subjectID");

			HttpSession session = request.getSession();

			if (ClassDAO.isError(getClassID, getClassName) || listOfSubjectsID == null) {
				session.setAttribute("ERROR_MSG",
						"Bạn đã bỏ trống thông tin nào đó hoặc tên lớp mới nhập đã trùng lặp!");

				request.setAttribute("classInfo", ClassDAO.getDetails(copyClassID));
				request.setAttribute("activeSubjectList", ClassDAO.getActiveSubjects());
				request.setAttribute("subjectsListByClassID", ClassDAO.getSubjectsIDListByClassID(getClassID));
				request.setAttribute("viewTitle", "Sửa lớp học");
				request.setAttribute("viewContent", "/WEB-INF/views/class/edit-class.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				HashMap<Integer, String> data = new HashMap<>();
				data.put(1, getClassName);
				data.put(2, getStatus);
				data.put(3, getClassID);

				ClassDAO.Update(data, listOfSubjectsID);

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc sửa thông tin lớp học!");
				response.sendRedirect("class-list");
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi sửa thông tin lớp học!");
		}
	}

	private void sortByStudentName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lay tieu chi sap xep
			String getSortParam = request.getParameter("sort-param");

			// mac dinh load danh sach tang dan theo ten (A-Z)
			orderBy = "name-asc";

			// du lieu mac dinh cho sort-param o url
			String sortParam = "name-desc";

			// du lieu cho phan trang
			String currentSort = getSortParam;

			if (currentSort == null) {
				currentSort = "name-asc";
			} else {
				if (getSortParam.equalsIgnoreCase("name-asc")) {
					orderBy = "name-asc";
				} else {
					orderBy = "name-desc";
				}

				sortParam = getSortParam.equalsIgnoreCase("name-asc") ? "name-desc" : "name-asc";
			}

			request.setAttribute("currentSort", currentSort);
			request.setAttribute("sortParam", sortParam);

			// so luong lop hoc tren 1 trang
			pageSize = 5;

			// lay trang hien tai
			currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

			request.setAttribute("currentPage", currentPage);
		} catch (Exception e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}
	}
}
