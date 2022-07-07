package com.javaweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DB {
	// thông tin máy chủ CSDL MySQL
	static String url = "jdbc:mysql://localhost:3306/school_management?useUnicode=yes&characterEncoding=UTF-8";
	static String user = "root";
	static String password = "";

	// biến duy trì kết nối máy chủ, máy khách
	static Connection dbConnection = null;

	// mở kết nối CSDL
	public static void open() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver"); //
		dbConnection = DriverManager.getConnection(url, user, password);

		Statement stmt = dbConnection.createStatement();
		stmt.executeQuery("SET CHARACTER SET 'UTF8'");
	}

	// đóng kết nối CSDL
	public static void close() throws SQLException {
		if (dbConnection != null)
			dbConnection.close();
	}

	// lấy ra dữ liệu của 1 bảng
	public static ResultSet q(String sql) throws SQLException, ClassNotFoundException {
		// Thực thi câu truy vấn
		Statement stmt = dbConnection.createStatement();

		ResultSet rs = stmt.executeQuery(sql);

		return rs;
	}

	// thực thi các câu lệnh insert, update, delete
	public static void exec(String sql, HashMap<Integer, String> params) throws SQLException, ClassNotFoundException {
		open(); // Mở kết nối

		PreparedStatement preparedSQL = dbConnection.prepareStatement(sql);

		for (Integer key : params.keySet()) {
			preparedSQL.setNString(key, params.get(key));
		}

		System.out.println(preparedSQL);

		preparedSQL.execute();

		close();
	}
}
