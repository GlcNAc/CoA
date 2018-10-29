package prj.coa.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccess {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@huizhi:1521:test";
	private static String user = "test";
	private static String password = "test";
	private static Connection con;
	
	public Connection getConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,
					user, password);
			return con;
		} catch (Exception e) {
			return null;
		}

	}
	
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection con) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
