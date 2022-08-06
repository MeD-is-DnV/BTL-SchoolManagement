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
import com.javaweb.dao.ResultDAO;
import com.javaweb.dao.StudentDAO;

@WebServlet(name = "ResultController", urlPatterns = { "/new-result", "/create-new-result", "/edit-result",
		"/update-result", "/redirect-edit-result", "/result-details" })
public class ResultController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String action, studentID, classID, startDay, endDate;

	public ResultController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/new-result":
			showCreateView(request, response);
			break;

		case "/redirect-edit-result":
			redirectPage(request, response);
			break;

		case "/edit-result":
			showEditView(request, response);
			break;

		case "/result-details":
			resultDetails(request, response);
			break;
		}

		request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		action = request.getServletPath();

		switch (action) {
		case "/create-new-result":
			createNewResult(request, response);
			break;

		case "/update-result":
			updateResult(request, response);
			break;
		}
	}

	// hien thi trang them moi ket qua thi
	private void showCreateView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		classID = request.getParameter("class-id");

		try {
			request.setAttribute("classID", classID);
			request.setAttribute("studentListByClassID", ResultDAO.getStudentListByClassID(classID));
			request.setAttribute("listOfSelectedSubjects", ClassDAO.getSubjectsListOfClass(classID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Tạo kết quả thi");
		request.setAttribute("viewContent", "/WEB-INF/views/result/create-result.jsp");
	}

	// hien thi trang chuyen huong khi co lenh edit ket qua thi
	private void redirectPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		studentID = request.getParameter("student-id");

		try {
			classID = StudentDAO.getDetails(studentID).get("classID");
			request.setAttribute("classID", classID);
			request.setAttribute("getTime", ResultDAO.getTime(studentID));
			request.setAttribute("studentID", studentID);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Điều hướng");
		request.setAttribute("viewContent", "/WEB-INF/views/result/redirect-edit-result.jsp");
	}

	// hien thi trang chinh sua ket qua thi
	private void showEditView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		studentID = request.getParameter("student-id");
		String startDay = request.getParameter("start-day");
		String endDate = request.getParameter("end-date");

		try {
			request.setAttribute("studentInfo", StudentDAO.getDetails(studentID));
			request.setAttribute("resultDetails", ResultDAO.getResultDetails(studentID, startDay, endDate));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Sửa kết quả thi");
		request.setAttribute("viewContent", "/WEB-INF/views/result/edit-result.jsp");
	}

	// hien thi trang chi tiet ket qua thi
	private void resultDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			classID = request.getParameter("class-id");

			// lay danh sach student_id theo class_id
			List<String> stdIDList = ResultDAO.getStudentIDByClassID(classID);

			// lay danh sach subject_id theo class_id
			List<String> subjectIDList = ResultDAO.getSubjectIDByClassID(classID);

			// lay danh sach thoi gian thi (ngay bat dau va ngay ket thuc) theo student_id
			// va subject_id
			List<HashMap<String, String>> resultTimeList = ResultDAO.getResultTimeList(stdIDList.get(0),
					subjectIDList.get(0));
			int numberOfExams = resultTimeList.size();

			// custom du lieu tra ra view
			List<HashMap<String, String>> resultDetails = ResultDAO.getPoint(classID);
			List<HashMap<String, String>> customInfo = new ArrayList<>();
			int count = resultDetails.size();
			for (int i = 0; i < count - 1; i++) {
				HashMap<String, String> temp = new HashMap<>();

				temp.put("studentID", resultDetails.get(i).get("studentID"));
				temp.put("studentName", resultDetails.get(i).get("studentName"));
				temp.put("cardID", resultDetails.get(i).get("cardID"));
				temp.put("dob", resultDetails.get(i).get("dob"));
				temp.put(resultDetails.get(i).get("subjectName"), resultDetails.get(i).get("point"));
				temp.put(resultDetails.get(i).get("startDay"), resultDetails.get(i).get("startDay"));
				temp.put("startDayForURL", resultDetails.get(i).get("startDayForURL"));
				temp.put("endDateForURL", resultDetails.get(i).get("endDateForURL"));

				for (int j = i + 1; j < count; j++) {
					if (resultDetails.get(i).get("cardID").equalsIgnoreCase(resultDetails.get(j).get("cardID"))
							&& resultDetails.get(i).get("startDay")
									.equalsIgnoreCase(resultDetails.get(j).get("startDay"))) {
						temp.put(resultDetails.get(j).get("subjectName"), resultDetails.get(j).get("point"));

						resultDetails.remove(j);
						count--;
						j--;
					}
				}

				customInfo.add(temp);
			}

			request.setAttribute("classID", classID);
			request.setAttribute("className", StudentDAO.getClassNameByID(classID));
			request.setAttribute("numberOfExams", numberOfExams);
			request.setAttribute("resultTimeList", resultTimeList);
			request.setAttribute("resultDetails", customInfo);
			request.setAttribute("listOfSubjects", ClassDAO.getSubjectsListOfClass(classID));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi không lấy được dữ liệu!");
		}

		request.setAttribute("viewTitle", "Chi tiết kết quả thi");
		request.setAttribute("viewContent", "/WEB-INF/views/result/result-details.jsp");
	}

	// xu ly chuc nang them moi ket qua thi
	private void createNewResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			startDay = request.getParameter("startDay");
			endDate = request.getParameter("endDate");
			classID = request.getParameter("classID");
			String status = request.getParameter("status");

			// lay danh sach student_id
			List<String> studentsIDList = new ArrayList<String>();
			List<HashMap<String, String>> studentList = ResultDAO.getStudentListByClassID(classID);
			int totalNumberOfStudents = studentList.size();

			for (int i = 0; i < totalNumberOfStudents; i++) {
				studentsIDList.add(studentList.get(i).get("studentID"));
			}

			// danh sach luu subject_id (K) va point (V) tuong ung
			HashMap<String, String> pointBySubject = new HashMap<String, String>();

			int getNumberOfSubjects = 0;
			List<String> subjectsIDList = new ArrayList<String>();

			try {
				subjectsIDList = ClassDAO.getSubjectsIDListByClassID(classID);

				getNumberOfSubjects = subjectsIDList.size();

				for (int j = 0; j < totalNumberOfStudents; j++) {
					for (int i = 0; i < getNumberOfSubjects; i++) {
						pointBySubject.put(subjectsIDList.get(i) + "-" + studentsIDList.get(j),
								request.getParameter("point-" + studentsIDList.get(j) + "-" + subjectsIDList.get(i)));
					}
				}
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Lỗi không lấy được dữ liệu!");
			}

			HttpSession session = request.getSession();

			if (ResultDAO.isError(startDay, endDate, pointBySubject)
					|| !ResultDAO.correctPointFormat(pointBySubject)) {
				session.setAttribute("ERROR_MSG",
						"Thông tin chưa đầy đủ hoặc điểm sai định dạng hoặc thời gian thi không hợp lý. Vui lòng kiểm tra lại!");

				request.setAttribute("classID", classID);
				request.setAttribute("studentListByClassID", ResultDAO.getStudentListByClassID(classID));
				request.setAttribute("listOfSelectedSubjects", ClassDAO.getSubjectsListOfClass(classID));
				request.setAttribute("viewTitle", "Tạo kết quả thi");
				request.setAttribute("viewContent", "/WEB-INF/views/result/create-result.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				for (int j = 0; j < totalNumberOfStudents; j++) {
					HashMap<Integer, String> data = new HashMap<>();
					data.put(1, studentsIDList.get(j));

					for (int i = 0; i < getNumberOfSubjects; i++) {
						data.put(2, subjectsIDList.get(i));
						data.put(3, pointBySubject.get(subjectsIDList.get(i) + "-" + studentsIDList.get(j)));
						data.put(4, startDay);
						data.put(5, endDate);
						data.put(6, status);
						data.put(7, Common.generateRandomString(5));

						ResultDAO.Add(data);
					}
				}

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc tạo kết quả thi!");
				response.sendRedirect("student-list?class-id=" + classID);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi tạo kết quả thi!");
		}
	}

	// xu ly chuc nang chinh sua ket qua thi
	private void updateResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lay danh sach result_id dua vao student_id, start_day, end_date
			startDay = request.getParameter("startDay");
			endDate = request.getParameter("endDate");
			studentID = request.getParameter("studentID");
			classID = request.getParameter("classID");

			// khoi tao danh sach chua result_id
			List<String> resultIDList = new ArrayList<String>();

			HashMap<String, String> pointBySubject = new HashMap<>();
			HashMap<String, String> pointStatus = new HashMap<>();

			List<HashMap<String, String>> temp = ResultDAO.getResultDetails(studentID, startDay, endDate);
			int sizeOfTemp = temp.size();

			for (int i = 0; i < sizeOfTemp; i++) {
				String getResultID = temp.get(i).get("resultID");

				resultIDList.add(getResultID);

				pointBySubject.put(getResultID, request.getParameter("point-" + getResultID));

				pointStatus.put(getResultID, request.getParameter("status-" + getResultID));
			}

			HttpSession session = request.getSession();

			if (ResultDAO.isError(startDay, endDate, pointBySubject)
					|| !ResultDAO.correctPointFormat(pointBySubject)) {
				session.setAttribute("ERROR_MSG", "Vui lòng nhập đầy đủ thông tin và đúng định dạng điểm các môn!");

				request.setAttribute("studentInfo", StudentDAO.getDetails(studentID));
				request.setAttribute("resultDetails", ResultDAO.getResultDetails(studentID, startDay, endDate));
				request.setAttribute("viewTitle", "Sửa kết quả thi");
				request.setAttribute("viewContent", "/WEB-INF/views/result/edit-result.jsp");
				request.getRequestDispatcher("/WEB-INF/views/shared/layout.jsp").forward(request, response);
			} else {
				for (int i = 0; i < sizeOfTemp; i++) {
					HashMap<Integer, String> data = new HashMap<>();

					data.put(1, pointBySubject.get(resultIDList.get(i)));
					data.put(2, pointStatus.get(resultIDList.get(i)));
					data.put(3, resultIDList.get(i));

					ResultDAO.Update(data);
				}

				session.setAttribute("SUCCESS_MSG", "Đã hoàn tất việc sửa kết quả thi!");
				response.sendRedirect("student-list?class-id=" + classID);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Lỗi sửa kết quả thi sinh viên!");
		}
	}
}
