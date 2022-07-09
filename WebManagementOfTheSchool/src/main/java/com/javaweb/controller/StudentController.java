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
import com.javaweb.dao.StudentDAO;

@WebServlet(name = "StudentController", urlPatterns = { "/student-list", "/new-student", "/create-new-student",
		"/student-details", "/edit-student", "/update-student", "/all-students" })
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String action, classID, studentID, studentName, dob, gender, cardID, email, phoneNumber, status,
			address, orderBy, keyword, searchByType;
	private static int pageSize, currentPage, pageTotal, numberOfStudents, numberOfThreadsFound;

	public StudentController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/all-students":
			allStudents(request, response);
			break;

		case "/student-list":
			studentListByClass(request, response);
			break;

		case "/new-student":
			showCreateView(request, response);
			break;

		case "/student-details":
			studentDetails(request, response);
			break;

		case "/edit-student":
			showEditView(request, response);
			break;
		}

		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/create-new-student":
			createNewStudent(request, response);
			break;

		case "/update-student":
			updateStudent(request, response);
			break;
		}
	}

	private void redirectPage(HttpServletRequest request, HttpServletResponse response, String errorMess,
			String classID, String studentID, String viewTitle, String viewContent)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("ERROR_MSG", errorMess);

		request.setAttribute("classID", classID);
		request.setAttribute("studentIDRandom", Common.generateRandomString(10));
		try {
			request.setAttribute("studentInfo", StudentDAO.getDetails(studentID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}
		request.setAttribute("viewTitle", viewTitle);
		request.setAttribute("viewContent", viewContent);
		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
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

			// so luong sinh vien tren 1 trang
			pageSize = 5;

			// lay trang hien tai
			currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

			request.setAttribute("currentPage", currentPage);
		} catch (Exception e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}
	}

	// hien thi trang danh sach tat ca sinh vien
	private void allStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lay tu khoa tim kiem (ten sinh vien)
			keyword = request.getParameter("keyword");

			// sap xep theo ten sinh vien
			sortByStudentName(request, response);

			searchByType = "find-all-students";

			if (keyword == null) {
				// lay tong so luong sinh vien cua tat ca cac lop
				numberOfStudents = StudentDAO.getAll().size();

				// tinh tong so trang
				pageTotal = (numberOfStudents % pageSize == 0) ? (numberOfStudents / pageSize)
						: ((numberOfStudents / pageSize) + 1);

				request.setAttribute("numberOfStudents", numberOfStudents);
				request.setAttribute("allStudents",
						StudentDAO.getStudentsByNameAndClass(currentPage, pageSize, orderBy, null, null, searchByType));
			} else {
				// lay toan bo danh sach sinh vien tim duoc theo ten
				List<HashMap<String, String>> allStudentsByName = StudentDAO.getStudentsByNameAndClass(0, 0, null,
						keyword, null, searchByType);

				// tinh so luong sinh vien tim duoc theo ten
				numberOfThreadsFound = allStudentsByName.size();

				// lay danh sach sinh vien tim duoc theo tung phan trang
				List<HashMap<String, String>> allStudentsByNameAndPage = StudentDAO
						.getStudentsByNameAndClass(currentPage, pageSize, orderBy, keyword, null, searchByType);

				pageTotal = (numberOfThreadsFound % pageSize == 0) ? (numberOfThreadsFound / pageSize)
						: ((numberOfThreadsFound / pageSize) + 1);

				request.setAttribute("keyword", keyword);
				request.setAttribute("allStudentsByName", allStudentsByNameAndPage);
				request.setAttribute("numberOfThreadsFound", numberOfThreadsFound);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("pageTotal", pageTotal);
		request.setAttribute("viewTitle", "Danh sách sinh viên");
		request.setAttribute("viewContent", "/WEB-INF/views/student/all-students.jsp");
	}

	// hien thi danh sach sinh vien theo tung lop
	private void studentListByClass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			classID = request.getParameter("class-id");
			// lay tu khoa tim kiem (ten sinh vien)
			keyword = request.getParameter("keyword");

			// sap xep theo ten sinh vien
			sortByStudentName(request, response);

			searchByType = "student-list-by-class";

			if (keyword == null) {
				// lay tong so luong sinh vien cua lop
				numberOfStudents = StudentDAO.getStudentListByClassID(classID).size();

				// tinh tong so trang
				pageTotal = (numberOfStudents % pageSize == 0) ? (numberOfStudents / pageSize)
						: ((numberOfStudents / pageSize) + 1);

				request.setAttribute("numberOfStudents", numberOfStudents);
				request.setAttribute("numberOfActiveStudents", ClassDAO.getNumberOfStudents(classID, 1));
				request.setAttribute("studentListByClassID", StudentDAO.getStudentsByNameAndClass(currentPage, pageSize,
						orderBy, null, classID, searchByType));
			} else {
				// lay toan bo danh sach sinh vien tim duoc theo ten va ma lop
				List<HashMap<String, String>> studentListByName = StudentDAO.getStudentsByNameAndClass(0, 0, null,
						keyword, classID, searchByType);

				// tinh so luong sinh vien tim duoc theo ten va ma lop
				numberOfThreadsFound = studentListByName.size();

				// lay danh sach sinh vien tim duoc theo tung phan trang
				List<HashMap<String, String>> studentListByNameAndPage = StudentDAO
						.getStudentsByNameAndClass(currentPage, pageSize, orderBy, keyword, classID, searchByType);

				pageTotal = (numberOfThreadsFound % pageSize == 0) ? (numberOfThreadsFound / pageSize)
						: ((numberOfThreadsFound / pageSize) + 1);

				request.setAttribute("keyword", keyword);
				request.setAttribute("studentListByName", studentListByNameAndPage);
				request.setAttribute("numberOfThreadsFound", numberOfThreadsFound);
			}

			request.setAttribute("classID", classID);
			request.setAttribute("className", StudentDAO.getClassNameByID(classID));
			request.setAttribute("pageTotal", pageTotal);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Danh sách sinh viên");
		request.setAttribute("viewContent", "/WEB-INF/views/student/student-list.jsp");
	}

	// hien thi trang them moi sinh vien
	private void showCreateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		classID = request.getParameter("class-id");

		request.setAttribute("classID", classID);
		request.setAttribute("studentIDRandom", Common.generateRandomString(10));
		request.setAttribute("viewTitle", "Thêm sinh viên");
		request.setAttribute("viewContent", "/WEB-INF/views/student/create-new-student.jsp");
	}

	// hien thi chi tiet thong tin sinh vien
	private void studentDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		studentID = request.getParameter("student-id");

		try {
			request.setAttribute("studentInfo", StudentDAO.getDetails(studentID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Thông tin sinh viên");
		request.setAttribute("viewContent", "/WEB-INF/views/student/student-details.jsp");
	}

	// hien thi trang chinh sua thong tin sinh vien
	private void showEditView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		studentID = request.getParameter("student-id");

		try {
			request.setAttribute("studentInfo", StudentDAO.getDetails(studentID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Sửa thông tin sinh viên");
		request.setAttribute("viewContent", "/WEB-INF/views/student/edit-student.jsp");
	}

	// xu ly chuc nang them moi sinh vien
	private void createNewStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			studentID = request.getParameter("studentID");
			studentName = request.getParameter("studentName");
			dob = request.getParameter("dob");
			cardID = request.getParameter("cardID");
			gender = request.getParameter("gender");
			email = request.getParameter("email");
			phoneNumber = request.getParameter("phoneNumber");
			address = request.getParameter("address");
			classID = request.getParameter("classID");
			status = request.getParameter("status");

			HttpSession session = request.getSession();

			// kiem tra du lieu co day du khong?
			if (StudentDAO.isEmpty(studentName, dob, cardID, phoneNumber, email)) {
				redirectPage(request, response, "Vui lòng nhập đầy đủ thông tin!", classID, studentID, "Thêm sinh viên",
						"/WEB-INF/views/student/create-new-student.jsp");
			} else {
				if (!StudentDAO.correctCardIDFormat(cardID, studentID)) {
					redirectPage(request, response, "Sai định dạng số căn cước công dân hoặc đã bị trùng!", classID,
							studentID, "Thêm sinh viên", "/WEB-INF/views/student/create-new-student.jsp");
				} else if (!StudentDAO.correctPhoneNumberFormat(phoneNumber, studentID)) {
					redirectPage(request, response, "Sai định dạng số điện thoại hoặc đã bị trùng!", classID, studentID,
							"Thêm sinh viên", "/WEB-INF/views/student/create-new-student.jsp");
				} else if (!StudentDAO.correctEmailFormat(email, studentID)) {
					redirectPage(request, response, "Sai định dạng Email hoặc đã bị trùng!", classID, studentID,
							"Thêm sinh viên", "/WEB-INF/views/student/create-new-student.jsp");
				} else {
					HashMap<Integer, String> data = new HashMap<>();
					data.put(1, studentID);
					data.put(2, studentName);
					data.put(3, dob);
					data.put(4, cardID);
					data.put(5, gender);
					data.put(6, email);
					data.put(7, phoneNumber);
					data.put(8, address);
					data.put(9, classID);
					data.put(10, status);

					StudentDAO.Add(data);

					session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc thêm mới!");
					response.sendRedirect("student-list?class-id=" + classID);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi thêm mới sinh viên!");
		}
	}

	// xu ly chuc nang sua thong tin sinh vien
	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			studentID = request.getParameter("studentID");
			studentName = request.getParameter("studentName");
			dob = request.getParameter("dob");
			cardID = request.getParameter("cardID");
			gender = request.getParameter("gender");
			email = request.getParameter("email");
			phoneNumber = request.getParameter("phoneNumber");
			address = request.getParameter("address");
			classID = request.getParameter("classID");
			status = request.getParameter("status");

			HttpSession session = request.getSession();

			// kiem tra du lieu co day du khong?
			if (StudentDAO.isEmpty(studentName, dob, cardID, phoneNumber, email)) {
				redirectPage(request, response, "Vui lòng nhập đầy đủ thông tin!", classID, studentID,
						"Sửa thông tin sinh viên", "/WEB-INF/views/student/edit-student.jsp");
			} else {
				if (!StudentDAO.correctCardIDFormat(cardID, studentID)) {
					redirectPage(request, response, "Sai định dạng số căn cước công dân hoặc đã bị trùng!", classID,
							studentID, "Sửa thông tin sinh viên", "/WEB-INF/views/student/edit-student.jsp");
				} else if (!StudentDAO.correctPhoneNumberFormat(phoneNumber, studentID)) {
					redirectPage(request, response, "Sai định dạng số điện thoại hoặc đã bị trùng!", classID, studentID,
							"Sửa thông tin sinh viên", "/WEB-INF/views/student/edit-student.jsp");
				} else if (!StudentDAO.correctEmailFormat(email, studentID)) {
					redirectPage(request, response, "Sai định dạng Email hoặc đã bị trùng!", classID, studentID,
							"Sửa thông tin sinh viên", "/WEB-INF/views/student/edit-student.jsp");
				} else {
					HashMap<Integer, String> data = new HashMap<>();
					data.put(1, studentName);
					data.put(2, dob);
					data.put(3, cardID);
					data.put(4, gender);
					data.put(5, email);
					data.put(6, phoneNumber);
					data.put(7, address);
					data.put(8, classID);
					data.put(9, status);
					data.put(10, studentID);

					StudentDAO.Update(data);

					session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc sửa thông tin!");
					response.sendRedirect("student-list?class-id=" + classID);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi sửa thông tin sinh viên!");
		}
	}
}
