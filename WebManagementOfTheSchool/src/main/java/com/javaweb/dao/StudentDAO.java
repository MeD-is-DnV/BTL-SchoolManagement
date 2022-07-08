package com.javaweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class StudentDAO {
	private static Pattern regex;
	private static java.util.regex.Matcher matcher;

	// lấy toàn bộ danh sách sinh viên
	public static List<HashMap<String, String>> getAll() throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT * FROM student";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("name"));
			getRow.put("dob", rs.getDate("dob").toString());
			getRow.put("cardID", rs.getNString("card_id"));
			getRow.put("gender", String.valueOf(rs.getInt("gender")));
			getRow.put("email", rs.getNString("email"));
			getRow.put("phoneNumber", rs.getNString("phone_number"));
			getRow.put("address", rs.getNString("address"));
			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("status", String.valueOf(rs.getInt("status")));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay danh sach sinh vien theo class_id
	public static List<HashMap<String, String>> getStudentListByClassID(String classID)
			throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT * FROM student WHERE `class_id` = '" + classID + "'";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("name"));
			getRow.put("dob", rs.getDate("dob").toString());
			getRow.put("cardID", rs.getNString("card_id"));
			getRow.put("gender", String.valueOf(rs.getInt("gender")));
			getRow.put("email", rs.getNString("email"));
			getRow.put("phoneNumber", rs.getNString("phone_number"));
			getRow.put("address", rs.getNString("address"));
			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("status", String.valueOf(rs.getInt("status")));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay ten lop theo class_id
	public static String getClassNameByID(String classID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT name FROM class WHERE `class_id` = '" + classID + "'";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		HashMap<String, String> getRow = new HashMap<>();

		while (rs.next()) {
			getRow.put("className", rs.getNString("name"));
			break;
		}

		DB.close();

		return getRow.get("className");
	}

	// kiem tra du lieu co day du khong?
	public static boolean isEmpty(String studentName, String dob, String cardID, String phoneNumber, String email) {
		if (studentName.length() == 0 || dob.length() == 0 || phoneNumber.length() == 0 || cardID.length() == 0
				|| email.length() == 0) {
			return true;
		}

		return false;
	}

	// kiem tra so dien thoai co dung dinh dang hoac co bi trung hay khong?
	public static boolean correctPhoneNumberFormat(String phoneNumber, String studentID)
			throws ClassNotFoundException, SQLException {
		regex = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
		matcher = regex.matcher(phoneNumber);

		String getOldPhoneNumber = getDetails(studentID).get("phoneNumber");

		if (getOldPhoneNumber == null || !getOldPhoneNumber.equalsIgnoreCase(phoneNumber)) {
			if (!matcher.find()) {
				return false;
			} else {
				String sql = "SELECT phone_number FROM student WHERE `phone_number` = '" + phoneNumber + "'";

				// mở kết nối DB
				DB.open();

				// lấy ra tất cả bản ghi
				ResultSet rs = DB.q(sql);

				if (rs.next()) {
					return false;
				}

				DB.close();
			}
		}

		return true;
	}

	// kiem tra email co dung dinh dang hoac co bi trung hay khong?
	public static boolean correctEmailFormat(String email, String studentID)
			throws ClassNotFoundException, SQLException {
		regex = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		matcher = regex.matcher(email);

		String getOldEmail = getDetails(studentID).get("email");

		if (getOldEmail == null || !getOldEmail.equalsIgnoreCase(email)) {
			if (!matcher.find()) {
				return false;
			} else {
				String sql = "SELECT email FROM student WHERE `email` = '" + email + "'";

				// mở kết nối DB
				DB.open();

				// lấy ra tất cả bản ghi
				ResultSet rs = DB.q(sql);

				if (rs.next()) {
					return false;
				}

				DB.close();
			}
		}

		return true;
	}

	// kiem tra so cccd co dung dinh dang hoac co bi trung hay khong?
	public static boolean correctCardIDFormat(String cardID, String studentID)
			throws ClassNotFoundException, SQLException {
		regex = Pattern.compile("^\\d{12}$");
		matcher = regex.matcher(cardID);

		String getOldCardID = getDetails(studentID).get("cardID");

		if (getOldCardID == null || !getOldCardID.equalsIgnoreCase(cardID)) {
			if (!matcher.find()) {
				return false;
			} else {
				String sql = "SELECT card_id FROM student WHERE `card_id` = '" + cardID + "'";

				// mở kết nối DB
				DB.open();

				// lấy ra tất cả bản ghi
				ResultSet rs = DB.q(sql);

				if (rs.next()) {
					return false;
				}

				DB.close();
			}
		}

		return true;
	}

	// thêm mới sinh viên
	public static void Add(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh thêm mới
		String sql = " INSERT INTO student SET `student_id`=?, `name`=?, `dob`=?, `card_id`=?, `gender`=?, `email`=?, "
				+ "`phone_number`=?, `address`=?, `class_id`=?, `status`=?";

		// Thực thi câu SQL INSERT
		DB.exec(sql, params);
	}

	// lay thong tin chi tiet cua sinh vien theo student_id
	public static HashMap<String, String> getDetails(String studentID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM student WHERE `student_id` = '" + studentID + "'";

		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		DB.open();

		ResultSet rs = DB.q(sql);

		HashMap<String, String> getRow = new HashMap<>();

		while (rs.next()) {
			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("name"));

			LocalDate date = LocalDate.parse(rs.getDate("dob").toString());
			String dob = date.format(formatDate);
			getRow.put("dob", dob);
			getRow.put("dobForEdit", rs.getDate("dob").toString());
			
			getRow.put("cardID", rs.getNString("card_id"));
			getRow.put("gender", String.valueOf(rs.getInt("gender")));
			getRow.put("email", rs.getNString("email"));
			getRow.put("phoneNumber", rs.getNString("phone_number"));
			getRow.put("address", rs.getNString("address"));
			getRow.put("classID", rs.getNString("class_id"));
			String getClassName = getClassNameByID(rs.getNString("class_id"));
			getRow.put("className", getClassName);
			getRow.put("status", String.valueOf(rs.getInt("status")));

			break;
		}

		DB.close();

		return getRow;
	}

	// sửa thông tin sinh viên
	public static void Update(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh update
		String sql = " UPDATE student SET `name`=?, `dob`=?, `card_id`=?, `gender`=?, `email`=?, `phone_number`=?,"
				+ " `address`=?, `class_id`=?, `status`=? WHERE `student_id`=?";

		// thực thi câu lệnh
		DB.exec(sql, params);
	}

	// tim kiem sinh vien theo ten + phan trang
	public static List<HashMap<String, String>> getStudentsByNameAndClass(int currentPage, int pageSize, String orderBy,
			String studentName, String classID, String searchByType) throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "", orderByQuery = "";

		if (studentName == null) {
			if (orderBy.equalsIgnoreCase("name-asc")) {
				orderByQuery = "ORDER BY `name` ASC";
			} else {
				orderByQuery = "ORDER BY `name` DESC";
			}

			if (classID == null) {
				sql = "SELECT * FROM `student` " + orderByQuery + " LIMIT " + ((currentPage - 1) * pageSize) + ","
						+ pageSize + "";
			} else {
				sql = "SELECT * FROM `student` WHERE `class_id` = '" + classID + "' " + orderByQuery + " LIMIT "
						+ ((currentPage - 1) * pageSize) + "," + pageSize + "";
			}
		} else {
			String baseSQL = "SELECT * FROM `student`" + " WHERE `name` LIKE '%" + studentName + "%'";

			if (orderBy != null) {
				if (orderBy.equalsIgnoreCase("name-asc")) {
					orderByQuery = "ORDER BY `name` ASC";
				} else {
					orderByQuery = "ORDER BY `name` DESC";
				}
			}

			if (searchByType.equalsIgnoreCase("student-list-by-class")) {
				if (currentPage == 0 && pageSize == 0 && orderBy == null) {
					sql = baseSQL + " AND `class_id` = '" + classID + "'";
				} else {
					sql = baseSQL + " AND `class_id` = '" + classID + "' " + orderByQuery + " LIMIT "
							+ ((currentPage - 1) * pageSize) + "," + pageSize;
				}
			} else if (searchByType.equalsIgnoreCase("find-all-students")) {
				if (currentPage == 0 && pageSize == 0 && orderBy == null && classID == null) {
					sql = baseSQL;
				} else {
					sql = baseSQL + orderByQuery + " LIMIT " + ((currentPage - 1) * pageSize) + "," + pageSize;
				}
			}
		}

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("name"));
			getRow.put("cardID", rs.getNString("card_id"));
			getRow.put("gender", String.valueOf(rs.getInt("gender")));
			getRow.put("phoneNumber", rs.getNString("phone_number"));
			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("status", String.valueOf(rs.getInt("status")));

			list.add(getRow);
		}

		DB.close();

		return list;
	}
}
