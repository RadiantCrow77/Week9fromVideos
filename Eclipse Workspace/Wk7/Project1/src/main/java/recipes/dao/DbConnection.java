package recipes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import recipes.exception.DbException;

public class DbConnection {
	private static final String SCHEMA = "recipes1";
	private static final String USER = "recipes1";
	private static final String PASSWORD = "recipes1";
	private static final String HOST = "localhost";
	private static final int PORT = 3306;

// method to create a connection when called

	public static Connection getConnection() {
		String url = String.format("jdbc:mysql://%s : %d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER,
				PASSWORD);
		System.out.println("Connecting with url = " + url);

		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Connection obtained!");
			return conn;
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}

}
