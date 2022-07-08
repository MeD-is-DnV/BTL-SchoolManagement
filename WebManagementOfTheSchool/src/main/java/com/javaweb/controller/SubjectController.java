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

import com.javaweb.dao.Common;
import com.javaweb.dao.ResultDAO;
import com.javaweb.dao.SubjectDAO;

@WebServlet(name = "SubjectController", urlPatterns = { "/new-subject", "/edit-subject", "/list-of-subjects",
		"/create-new-subject", "/update-subject" })
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int currentPage, pageSize, pageTotal;
	private static String action, orderBy, subjectID, subjectName, status;

	public SubjectController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/new-subject":
			showCreateView(request, response);
			break;

		case "/edit-subject":
			showEditView(request, response);
			break;

		case "/list-of-subjects":
			showListOfSubjectsView(request, response);
			break;
		}

		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/create-new-subject":
			createNewSubject(request, response);
			break;

		case "/update-subject":
			updateSubject(request, response);
			break;
		}
	}

	// hien thi trang them moi mon hoc
	private void showCreateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("subjectIDRandom", Common.generateRandomString(10));
		request.setAttribute("viewTitle", "Thêm môn học");
		request.setAttribute("viewContent", "/WEB-INF/views/subject/create.jsp");
	}

	// hien thi trang chinh sua mon hoc
	private void showEditView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String getSubjectID = request.getParameter("subject-id");
		try {
			request.setAttribute("subjectInfo", SubjectDAO.getDetails(getSubjectID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}
		request.setAttribute("viewTitle", "Sửa môn học");
		request.setAttribute("viewContent", "/WEB-INF/views/subject/edit.jsp");
	}

	// hien thi danh sach mon hoc
	private void showListOfSubjectsView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String keyword = request.getParameter("keyword");

			sortAndGetListByPage(request, response);

			// neu khong co keyword tim kiem thi load danh sach mon hoc binh thuong
			// neu co keyword thi load danh sach theo keyword do
			if (keyword == null) {
				// tinh tong so mon hoc hien co
				int numberOfSubjects = SubjectDAO.getAll().size();

				// tinh tong so trang
				pageTotal = (numberOfSubjects % pageSize == 0) ? (numberOfSubjects / pageSize)
						: ((numberOfSubjects / pageSize) + 1);

				request.setAttribute("listOfSubjects",
						SubjectDAO.getSubjectByName(currentPage, pageSize, orderBy, null));
				request.setAttribute("countOfSubject", numberOfSubjects);
			} else {
				// toan bo danh sach mon hoc tim duoc
				List<HashMap<String, String>> subjectsListByName = SubjectDAO.getSubjectByName(0, 0, null, keyword);

				// danh sach mon hoc theo tung phan trang
				List<HashMap<String, String>> subjectsListByNameAndPage = SubjectDAO.getSubjectByName(currentPage,
						pageSize, orderBy, keyword);

				// tinh so luong mon hoc tim duoc
				int numberOfThreadsFound = subjectsListByName.size();

				pageTotal = (numberOfThreadsFound % pageSize == 0) ? (numberOfThreadsFound / pageSize)
						: ((numberOfThreadsFound / pageSize) + 1);

				request.setAttribute("keyword", keyword);
				request.setAttribute("subjectListByName", subjectsListByNameAndPage);
				request.setAttribute("numberOfThreadsFound", numberOfThreadsFound);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("pageTotal", pageTotal);
		request.setAttribute("viewTitle", "Danh sách môn học");
		request.setAttribute("viewContent", "/WEB-INF/views/subject/list.jsp");
	}

	// xu ly them moi mon hoc
	private void createNewSubject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			subjectID = request.getParameter("subjectID");
			subjectName = request.getParameter("subjectName");
			status = request.getParameter("status");

			HttpSession session = request.getSession();

			if (SubjectDAO.isError(subjectID, subjectName)) {
				session.setAttribute("ERROR_MSG", "Tên môn học bị trùng hoặc vẫn còn trống!");

				request.setAttribute("subjectIDRandom", Common.generateRandomString(10));
				request.setAttribute("viewTitle", "Thêm môn học");
				request.setAttribute("viewContent", "/WEB-INF/views/subject/create.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				HashMap<Integer, String> data = new HashMap<>();
				data.put(1, subjectID);
				data.put(2, subjectName);
				data.put(3, status);

				SubjectDAO.Add(data);

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc thêm mới !");
				response.sendRedirect("list-of-subjects");
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi thêm mới: " + e.getMessage());
		}
	}

	// xu ly chinh sua thong tin mon hoc
	private void updateSubject(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			subjectID = request.getParameter("subjectID");
			String copySubjectID = subjectID;
			subjectName = request.getParameter("subjectName");
			status = request.getParameter("status");

			HttpSession session = request.getSession();

			// neu ten mon thay doi
			// thi kiem tra xem ten mon thay the co hop le khong?
			if (SubjectDAO.isError(subjectID, subjectName)) {
				session.setAttribute("ERROR_MSG", "Tên môn học bị trùng hoặc bị trống!");

				request.setAttribute("subjectInfo", SubjectDAO.getDetails(copySubjectID));
				request.setAttribute("viewTitle", "Sửa môn học");
				request.setAttribute("viewContent", "/WEB-INF/views/subject/edit.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				HashMap<Integer, String> data = new HashMap<>();
				data.put(1, subjectName);
				data.put(2, status);
				data.put(3, subjectID);

				SubjectDAO.Update(data);

				// sua trang thai mon thi (subject_not_active) cua bang result theo subject_id
				HashMap<Integer, String> temp = new HashMap<>();

				// neu mon hoc bi huy thi mon thi bi huy (subject_not_active = 1)
				// nguoc lai (subject_not_active = 0)
				if (Integer.parseInt(status) == 0) {
					temp.put(1, "1");
				} else {
					temp.put(1, "0");
				}
				temp.put(2, subjectID);
				ResultDAO.cancelTheSubject(temp);

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc sửa !");
				response.sendRedirect("list-of-subjects");
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi sửa dữ liệu: " + e.getMessage());
		}
	}

	// sap xep va phan trang
	private void sortAndGetListByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lay tieu chi sap xep
			String getSortParam = request.getParameter("sort-param");

			// mac dinh load danh sach tang dan theo ten mon hoc (A-Z)
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

			// so mon hoc tren 1 trang
			pageSize = 5;

			// lay trang hien tai
			currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

			request.setAttribute("currentPage", currentPage);
		} catch (Exception e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}
	}
}
