package com.javaweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.javaweb.dao.DB;

public class SubjectDAO {
	// lấy toàn bộ danh sách môn học
	public static List<HashMap<String, String>> getAll() throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql = "SELECT \n" + "`subject_id`, \n" + "`name`,\n" + "`status`,\n"
				+ "IF(status=1, 'Đang tồn tại', 'Đã hủy') AS `getStatus`\n" + "FROM `subject`";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		while (rs.next()) {
			HashMap<String, String> getRow = new HashMap<>();

			getRow.put("subjectID", rs.getNString("subject_id"));
			getRow.put("subjectName", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}

	// kiem tra ten mon bi trong hoac bi trung hay khong?
	public static boolean isError(String subjectID, String newSubjectName) throws SQLException, ClassNotFoundException {
		String oldSubjectName = getDetails(subjectID).get("subjectName");

		if (oldSubjectName == null || !oldSubjectName.equalsIgnoreCase(newSubjectName)) {
			if (newSubjectName.length() == 0) {
				return true;
			} else {
				String sql = "SELECT `subject_id`, `name`, `status` FROM subject WHERE `name` = '" + newSubjectName
						+ "'";

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

	// thêm mới môn học
	public static void Add(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh thêm mới
		String sql = " INSERT INTO subject " + " SET `subject_id`=?, " + " `name`=?, " + " `status`=?";

		// Thực thi câu SQL INSERT
		DB.exec(sql, params);
	}

	// lấy dữ liệu 1 môn học theo subject_id
	public static HashMap<String, String> getDetails(String subjectID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT \n" + "`subject_id`, \n" + "`name`,\n" + "`status`,\n"
				+ "IF(status=1, 'Đang tồn tại', 'Đã hủy') AS `getStatus`\n" + "FROM `subject`\n"
				+ "WHERE `subject_id`='" + subjectID + "'";

		// mở kết nối DB
		DB.open();

		// lấy ra tất cả bản ghi
		ResultSet rs = DB.q(sql);

		HashMap<String, String> getRow = new HashMap<>();

		while (rs.next()) {
			getRow.put("subjectID", rs.getNString("subject_id"));
			getRow.put("subjectName", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			break;
		}

		DB.close();

		return getRow;
	}

	// sửa thông tin môn học
	public static void Update(HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		// câu lệnh update
		String sql = " UPDATE subject " + "SET `name`=?," + "`status`=?" + " WHERE `subject_id` = ?";

		// thực thi câu lệnh
		DB.exec(sql, params);
	}

	// tim kiem mon hoc + phan trang theo danh sach tim kiem
	public static List<HashMap<String, String>> getSubjectByName(int currentPage, int pageSize, String orderBy,
			String subjectName) throws SQLException, ClassNotFoundException {
		List<HashMap<String, String>> list = new ArrayList();

		String sql, orderByQuery;

		// neu subjectName null thi lay danh sach mon hoc theo tung phan trang
		if (subjectName == null) {
			if (orderBy.equalsIgnoreCase("name-asc")) {
				orderByQuery = "ORDER BY `name` ASC";
			} else {
				orderByQuery = "ORDER BY `name` DESC";
			}

			sql = "SELECT \n" + "`subject_id`, \n" + "`name`,\n" + "`status`,\n"
					+ "IF(status=1, 'Đang tồn tại', 'Đã hủy') AS `getStatus`\n" + "FROM `subject` " + orderByQuery
					+ " LIMIT " + ((currentPage - 1) * pageSize) + "," + pageSize + "";
		} else { // nguoc lai, lay danh sach mon hoc theo subjectName cho tung phan trang
			String baseSQL = "SELECT \n" + "`subject_id`, \n" + "`name`,\n" + "`status`,\n"
					+ "IF(status=1, 'Đang tồn tại', 'Đã hủy') AS `getStatus`\n" + "FROM `subject` "
					+ " WHERE `name` LIKE '%" + subjectName + "%'";

			// neu chi truyen vao subjectName thi lay toan bo danh sach tim duoc theo ten
			// mon
			if (currentPage == 0 && pageSize == 0 && orderBy == null) {
				sql = baseSQL;
			} else {
				if (orderBy.equalsIgnoreCase("name-asc")) {
					orderByQuery = "ORDER BY `name` ASC";
				} else {
					orderByQuery = "ORDER BY `name` DESC";
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

			getRow.put("subjectID", rs.getNString("subject_id"));
			getRow.put("subjectName", rs.getNString("name"));
			getRow.put("status", rs.getNString("getStatus"));

			list.add(getRow);
		}

		DB.close();

		return list;
	}
}
