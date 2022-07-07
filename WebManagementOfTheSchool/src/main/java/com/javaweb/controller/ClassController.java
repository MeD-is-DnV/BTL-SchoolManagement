package com.javaweb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.javaweb.dao.SubjectDAO;

@WebServlet(name = "ClassController", urlPatterns = { "/class-list", "/new-class", "/create-new-class", "/edit-class",
		"/update-class", "/class-details", "/find-classes" })
public class ClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String action, getClassID;

	public ClassController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/class-list":
			try {
				// lay tieu chi sap xep
				String getSortParam = request.getParameter("sort-param");
				
				// mac dinh load danh sach tang dan theo ten (A-Z)
				String orderBy = "name-asc";
				
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
				
				// so lop hoc tren 1 trang
				int pageSize = 5;

				// lay trang hien tai
				int currentPage = request.getParameter("page") == null ? 1
						: Integer.parseInt(request.getParameter("page"));

				// tinh tong so lop hoc hien co
				int numberOfClasses = ClassDAO.getAll().size();

				// tinh tong so trang
				int pageTotal = (numberOfClasses % pageSize == 0) ? (numberOfClasses / pageSize)
						: ((numberOfClasses / pageSize) + 1);

				// custom thong tin lop hoc de tra ra view
				List<HashMap<String, String>> classList = ClassDAO.getClassListByPage(currentPage, pageSize, orderBy);
				int count = classList.size();
				for (int i = 0; i < count; i++) {
					classList.get(i).put("numberOfStudents",
							String.valueOf(ClassDAO.getNumberOfStudents(classList.get(i).get("classID"))));
				}

				request.setAttribute("currentPage", currentPage);
				request.setAttribute("pageTotal", pageTotal);
				request.setAttribute("classList", classList);
				request.setAttribute("numberOfClasses", numberOfClasses);
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Lỗi không lấy được dữ liệu!");
			}

			request.setAttribute("viewTitle", "Danh sách lớp học");
			request.setAttribute("viewContent", "/WEB-INF/views/class/class-list.jsp");
			break;

		case "/new-class":
			request.setAttribute("classIDRandom", Common.generateRandomString(10));

			try {
				request.setAttribute("numberOfSubjects", ClassDAO.getActiveSubjects().size());
				request.setAttribute("activeSubjectList", ClassDAO.getActiveSubjects());
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Lỗi không lấy được dữ liệu lớp học!");
			}

			request.setAttribute("viewTitle", "Thêm lớp học");
			request.setAttribute("viewContent", "/WEB-INF/views/class/create-new-class.jsp");
			break;

		case "/edit-class":
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
			break;

		case "/class-details":
			getClassID = request.getParameter("class-id");

			try {
				request.setAttribute("classInfo", ClassDAO.getDetails(getClassID));
				request.setAttribute("listOfSelectedSubjects", ClassDAO.getSubjectsListOfClass(getClassID));
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Lỗi không lấy được dữ liệu!");
			}

			request.setAttribute("viewTitle", "Thông tin lớp học");
			request.setAttribute("viewContent", "/WEB-INF/views/class/class-details.jsp");
			break;

		case "/find-classes":
			try {
				String keyword = request.getParameter("keyword");

				request.setAttribute("classListByName", ClassDAO.getClassListByName(keyword));
				request.setAttribute("numberOfThreadsFound", ClassDAO.getClassListByName(keyword).size());
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Lỗi không tìm thấy dữ liệu: " + e.getMessage());
			}
			request.setAttribute("viewTitle", "Danh sách lớp học");
			request.setAttribute("viewContent", "/WEB-INF/views/class/class-list.jsp");

			break;
		}

		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/create-new-class":
			try {
				String getClassName = request.getParameter("className");
				String getClassID = request.getParameter("classID");
				String getStatus = request.getParameter("status");
				String listOfSubjectsID[] = request.getParameterValues("subjectID");

				HttpSession session = request.getSession();

				if (SubjectDAO.isError(getClassID, getClassName) || listOfSubjectsID == null) {
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

			break;

		case "/update-class":
			try {
				String getClassName = request.getParameter("className");
				String getClassID = request.getParameter("classID");
				String copyClassID = getClassID;
				String getStatus = request.getParameter("status");
				String listOfSubjectsID[] = request.getParameterValues("subjectID");

				HttpSession session = request.getSession();

				if (SubjectDAO.isError(getClassID, getClassName) || listOfSubjectsID == null) {
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

			break;
		}
	}

}
