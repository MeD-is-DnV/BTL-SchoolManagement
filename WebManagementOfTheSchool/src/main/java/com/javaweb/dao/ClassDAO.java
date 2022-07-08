package com.javaweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassDAO {
	// lấy toàn bộ danh sách lớp học
	public static List<HashMap<String, String>> getAll() throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT \n" + "`class_id`, \n" + "`name`,\n" + "`status`,\n"
				+ "IF(status=1, 'Đang hoạt động', 'Đã ngưng hoạt động') AS `getStatus`\n" + "FROM `class`";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("className", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay toan bo mon hoc co trang thai = 1 (Dang ton tai)
	public static List<HashMap<String, String>> getActiveSubjects() throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT \n" + "`subject_id`, \n" + "`name`\n" + "FROM `subject` WHERE `status` = " + 1 + "";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("subjectID", rs.getNString("subject_id"));
			getRow.put("subjectName", rs.getNString("name"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// lay chi tiet lop hoc theo class_id
	public static HashMap<String, String> getDetails(String classID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT \n" + "`class_id`, \n" + "`name`,\n" + "`status`,\n"
				+ "IF(status=1, 'Đang hoạt động', 'Đã ngưng hoạt động') AS `getStatus`\n" + "FROM `class`\n"
				+ "WHERE `class_id`='" + classID + "'";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		HashMap<String, String> getRow = new HashMap<>();

		while (rs.next()) {
			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("className", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			break;
		}

		DB.close();

		return getRow;
	}

	// kiem tra ten class xem co bi trong hay bi trung khong?
	public static boolean isError(String classID, String newClassName) throws SQLException, ClassNotFoundException {
		String oldClassName = getDetails(classID).get("className");

		if (oldClassName == null || !oldClassName.equalsIgnoreCase(newClassName)) {
			if (newClassName.length() == 0) {
				return true;
			} else {
				String sql = "SELECT `class_id`, `name`, `status` FROM class WHERE `name` = '" + newClassName + "'";

				// mở kết nối DB
				DB.open();

				// lấy ra tất cả bản ghi
				ResultSet rs = DB.q(sql);

				if (rs.next()) {
					return true;
				}

				DB.close();
			}
		}

		return false;
	}

	// thêm mới lớp học
	public static void Add(HashMap<Integer, String> params, String[] listOfSubjectsID)
			throws SQLException, ClassNotFoundException {
		// câu lệnh thêm mới
		String sqlToAddClass = " INSERT INTO class " + " SET `class_id`=?, " + " `name`=?, " + " `status`=?";

		// Thực thi câu SQL INSERT
		DB.exec(sqlToAddClass, params);

		String sqlToAddClassDetails = "";

		String getClassID = params.get(1);

		HashMap<Integer, String> listOfSelectedSubjectsID = new HashMap<>();

		for (int i = 0; i < listOfSubjectsID.length; i++) {
			listOfSelectedSubjectsID.put(1, listOfSubjectsID[i]);

			sqlToAddClassDetails = "INSERT INTO class_details SET `class_id` = '" + getClassID + "', `subject_id` = ?";

			DB.exec(sqlToAddClassDetails, listOfSelectedSubjectsID);
		}
	}

	// lay so luong sinh vien dang theo hoc theo class_id
	public static int getNumberOfStudents(String classID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM `class` LEFT JOIN `student` on class.class_id = student.class_id WHERE class.class_id = '"
				+ classID + "' AND student.status = 1";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		int numberOfStudents = 0;

		while (rs.next()) {
			numberOfStudents++;
		}

		DB.close();

		return numberOfStudents;
	}

	// lay danh sach id mon hoc theo class_id
	public static List<String> getSubjectsIDListByClassID(String classID) throws SQLException, ClassNotFoundException {
		List<String> subjectsIDListByClassID = new ArrayList<String>();

		String sql = "SELECT `subject_id` FROM class_details WHERE `class_id` = '" + classID + "'";

		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			subjectsIDListByClassID.add(rs.getNString("subject_id"));
		}

		DB.close();

		return subjectsIDListByClassID;
	}

	// sửa thông tin lớp học
	public static void Update(HashMap<Integer, String> params, String[] newListOfSubjectsID)
			throws SQLException, ClassNotFoundException {
		// câu lệnh update class
		String sql = " UPDATE class " + "SET `name`=?," + "`status`=?" + " WHERE `class_id` = ?";

		// thực thi câu lệnh update class
		DB.exec(sql, params);

		// lay mang subject_id cu da duoc chon
		List<String> oldListOfSubjectsID = getSubjectsIDListByClassID(params.get(3));

		// tao ban sao cho oldListOfSubjectsID
		List<String> cloneOldListOfSubjectsID = new ArrayList<String>(oldListOfSubjectsID);

		// su dung Set de lay ra tung phan tu khac biet trong 2 list:
		// oldListOfSubjectsID, newListOfSubjectsID
		Set<String> oldList = new HashSet<String>(oldListOfSubjectsID);
		Set<String> newList = new HashSet<String>(Arrays.asList(newListOfSubjectsID));

		oldList.removeAll(newList);
		newList.removeAll(cloneOldListOfSubjectsID);

		// merge cac phan tu khac nhau tim duoc vao 1 HashSet
		Set<String> difference = new HashSet<String>();
		difference.addAll(oldList);
		difference.addAll(newList);

		HashMap<Integer, String> temp = new HashMap<>();
		for (String subjectID : difference) {
			temp.put(1, subjectID);

			if (oldListOfSubjectsID.contains(subjectID)) {
				String sqlDelete = "DELETE FROM class_details WHERE `class_id` = '" + params.get(3)
						+ "' AND `subject_id` = ?";

				DB.exec(sqlDelete, temp);
			} else {
				String sqlToAddClassDetails = "INSERT INTO class_details SET `class_id` = '" + params.get(3)
						+ "', `subject_id` = ?";

				DB.exec(sqlToAddClassDetails, temp);
			}
		}
	}

	// lay danh sach ten mon hoc cua lop bang cach join 2 bang: class_details va
	// subject thong qua subject_id
	// va lay ra du lieu theo clas_id
	public static List<HashMap<String, String>> getSubjectsListOfClass(String classID)
			throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT * FROM `class_details` LEFT JOIN `subject` ON class_details.subject_id = subject.subject_id WHERE `class_id` = '"
				+ classID + "' AND subject.status = 1";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("subjectID", rs.getNString("subject_id"));
			getRow.put("subjectName", rs.getNString("name"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// tim kiem lop hoc + phan trang
	public static List<HashMap<String, String>> getClassListByNameAndPage(int currentPage, int pageSize, String orderBy,
			String className) throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql, orderByQuery;
		
		// neu className null thi lay danh sach lop hoc theo tung phan trang
		if (className == null) {
			if (orderBy.equalsIgnoreCase("name-asc")) {
				orderByQuery = "ORDER BY `name` ASC";
			} else {
				orderByQuery = "ORDER BY `name` DESC";
			}

			sql = "SELECT \n" + "`class_id`, \n" + "`name`,\n" + "`status`,\n"
					+ "IF(status=1, 'Đang hoạt động', 'Đã ngưng hoạt động') AS `getStatus`\n" + "FROM `class` "
					+ orderByQuery + " LIMIT " + ((currentPage - 1) * pageSize) + "," + pageSize + "";
		} else { // nguoc lai, lay danh sach lop hoc theo className cho tung phan trang
			String baseSQL = "SELECT \n" + "`class_id`, \n" + "`name`,\n" + "`status`,\n"
					+ "IF(status=1, 'Đang hoạt động', 'Đã ngưng hoạt động') AS `getStatus`\n" + "FROM `class`"
					+ " WHERE `name` LIKE '%" + className + "%'";

			if (currentPage == 0 && pageSize == 0 && orderBy == null) {
				sql = baseSQL;
			} else {
				if (orderBy.equalsIgnoreCase("name-asc")) {
					orderByQuery = " ORDER BY `name` ASC";
				} else {
					orderByQuery = " ORDER BY `name` DESC";
				}

				sql = baseSQL + orderByQuery + " LIMIT " + ((currentPage - 1) * pageSize) + "," + pageSize;
			}
		}

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("classID", rs.getNString("class_id"));
			getRow.put("className", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}
}
