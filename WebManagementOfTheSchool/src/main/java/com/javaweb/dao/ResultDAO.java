package com.javaweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultDAO {
	private static int i;
	private static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static LocalDate date;

	// kiem tra du lieu co day du hay khong?
	public static boolean isNullError(String startDay, String endDate, HashMap<String, String> points) {
		if (startDay.length() == 0 || endDate.length() == 0) {
			return true;
		}

		for (String key : points.keySet()) {
			if (points.get(key).length() == 0) {
				return true;
			}
		}

		return false;
	}

	// kiem tra diem nhap vao co hop le hay khong?
	public static boolean correctPointFormat(HashMap<String, String> points) {
		Pattern regex = Pattern.compile("^(10|\\d{1}([\\.]\\d+)?)$");

		for (String key : points.keySet()) {
			Matcher matcher = regex.matcher(points.get(key));

			if (!matcher.find()) {
				return false;
			}
		}

		return true;
	}

	// thêm mới kết quả thi
	public static void Add(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh thêm mới
		String sql = " INSERT INTO result SET `student_id`=?, `subject_id`=?, `point`=?, `start_day`=?, `end_date`=?, `status`=?, `result_id`=?";

		// Thực thi câu SQL INSERT
		DB.exec(sql, params);
	}

	// lay danh sach sinh vien dang theo hoc theo class_id
	public static List<HashMap<String, String>> getStudentListByClassID(String classID)
			throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT * FROM student WHERE `class_id` = '" + classID + "' AND `status` = 1";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("name"));
			getRow.put("cardID", rs.getNString("card_id"));
			getRow.put("classID", rs.getNString("class_id"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay thoi gian thi theo student_id
	public static List<HashMap<String, String>> getTime(String studentID) throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT `start_day`, `end_date` FROM result WHERE `student_id` = '" + studentID
				+ "' GROUP BY `start_day`";

		DB.open();

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			date = LocalDate.parse(rs.getDate("start_day").toString());
			String startDay = date.format(formatDate);
			getRow.put("startDay", startDay);

			date = LocalDate.parse(rs.getDate("end_date").toString());
			String endDate = date.format(formatDate);
			getRow.put("endDate", endDate);
			
			getRow.put("startDayForURL", rs.getDate("start_day").toString());
			getRow.put("endDateForURL", rs.getDate("end_date").toString());

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay ten mon hoc theo subject_id
	public static String getSubjectNameByID(String subjectID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT name FROM subject WHERE `subject_id` = '" + subjectID + "'";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		HashMap<String, String> getRow = new HashMap<>();

		while (rs.next()) {
			getRow.put("subjectName", rs.getNString("name"));
			break;
		}

		DB.close();

		return getRow.get("subjectName");
	}

	// lay chi tiet ket qua thi (voi cac mon thi khong bi huy mon:
	// subject_not_active = 0) cua 1
	// sinh vien theo student_id
	public static List<HashMap<String, String>> getResultDetails(String studentID, String startDay, String endDate)
			throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList<>();

		String sql = "SELECT * FROM result WHERE `student_id` = '" + studentID + "' AND `start_day` = '" + startDay
				+ "' AND `end_date` = '" + endDate + "' AND `subject_not_active` = 0";

		DB.open();

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("resultID", rs.getNString("result_id"));
			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("subjectID", rs.getNString("subject_id"));
			String getSubjectName = getSubjectNameByID(rs.getNString("subject_id"));
			getRow.put("subjectName", getSubjectName);
			getRow.put("point", String.valueOf(rs.getFloat("point")));
			getRow.put("startDay", rs.getDate("start_day").toString());
			getRow.put("endDate", rs.getDate("end_date").toString());
			getRow.put("status", String.valueOf(rs.getInt("status")));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// sửa kết quả thi sinh viên
	public static void Update(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh update
		String sql = " UPDATE result SET `point`=?, `status`=? WHERE `result_id`=?";

		// thực thi câu lệnh
		DB.exec(sql, params);
	}

	// lay danh sach student_id theo class_id
	public static List<String> getStudentIDByClassID(String classID) throws SQLException, ClassNotFoundException {
		List<String> studentsIDList = new ArrayList();

		DB.open();

		String sql = "SELECT `student_id` FROM student WHERE `class_id` = '" + classID + "' AND `status` = 1";

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			studentsIDList.add(rs.getNString("student_id"));
		}

		DB.close();

		return studentsIDList;
	}

	// lay danh sach subject_id theo class_id
	public static List<String> getSubjectIDByClassID(String classID) throws SQLException, ClassNotFoundException {
		List<String> subjectIDList = new ArrayList();

		DB.open();

		String sql = "SELECT `subject_id` FROM class_details WHERE `class_id` = '" + classID + "'";

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			subjectIDList.add(rs.getNString("subject_id"));
		}

		DB.close();

		return subjectIDList;
	}

	// lay danh sach thoi gian bat dau thi theo student_id va subject_id
	public static List<HashMap<String, String>> getResultTimeList(String studentID, String subjectID)
			throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> timeList = new ArrayList<>();

		DB.open();

		// lay danh sach ma status khac 2 (tuc la mon khong bi huy)
		String sql = "SELECT `start_day`, `end_date` FROM result WHERE `student_id` = '" + studentID
				+ "' AND `subject_id` = '" + subjectID + "' AND `status` < 2 ORDER BY `start_day` DESC";

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<String, String>();

			date = LocalDate.parse(rs.getDate("start_day").toString());
			String startDay = date.format(formatDate);
			getRow.put("startDay", startDay);

			date = LocalDate.parse(rs.getDate("end_date").toString());
			String endDate = date.format(formatDate);
			getRow.put("endDate", endDate);

			timeList.add(getRow);
		}

		DB.close();

		return timeList;
	}

	// lay diem so
	public static List<HashMap<String, String>> getPoint() throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList<>();

		String sql = "SELECT s.student_id, s.name AS student_name, s.card_id, s.dob, sub.name AS subject_name, r.point, r.start_day, r.end_date"
				+ " FROM `result` r JOIN `student` s ON r.student_id = s.student_id JOIN `subject` sub on r.subject_id = sub.subject_id"
				+ " WHERE r.subject_not_active = 0";

		DB.open();

		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("studentID", rs.getNString("student_id"));
			getRow.put("studentName", rs.getNString("student_name"));
			getRow.put("cardID", rs.getNString("card_id"));

			date = LocalDate.parse(rs.getDate("dob").toString());
			String dob = date.format(formatDate);
			getRow.put("dob", dob);

			getRow.put("subjectName", rs.getNString("subject_name"));
			getRow.put("point", String.valueOf(rs.getFloat("point")));
			
			date = LocalDate.parse(rs.getDate("start_day").toString());
			String startDay = date.format(formatDate);
			getRow.put("startDay", startDay);

			date = LocalDate.parse(rs.getDate("end_date").toString());
			String endDate = date.format(formatDate);
			getRow.put("endDate", endDate);
			
			getRow.put("startDayForURL", rs.getDate("start_day").toString());
			getRow.put("endDateForURL", rs.getDate("end_date").toString());

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// sua subject_not_active khi subject tuong ung thay doi status
	// neu subject bi huy (subject.status = 0) thi chuyen subject_not_active = 1
	// (tuc la: mon thi da bi huy)
	public static void cancelTheSubject(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh update
		String sql = "UPDATE result SET `subject_not_active`=b? WHERE `subject_id`=?";

		// thực thi câu lệnh
		DB.exec(sql, params);
	}
}
