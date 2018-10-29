/**
 * 
 */
package prj.coa.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Administrator
 * 
 */
public class DbConn {
	private static String properties = "sys.properties";
	private static Properties props = new Properties();

	private static String oracleDriver;
	private static String oracleUrl;
	private static String oracleUser;
	private static String oraclePassword;

	private static String MSSQLDriver;
	private static String MSSQLUrl;
	private static String MSSQLUser;
	private static String MSSQLPassword;

	static {
		try {
			props.load(DbConn.class.getClassLoader().getResourceAsStream(
					properties));
			oracleDriver = props.getProperty("oracle.driver");
			oracleUrl = props.getProperty("oracle.url");
			oracleUser = props.getProperty("oracle.user");
			oraclePassword = props.getProperty("oracle.password");

			MSSQLDriver = props.getProperty("MSSQL.driver");
			MSSQLUrl = props.getProperty("MSSQL.url");
			MSSQLUser = props.getProperty("MSSQL.user");
			MSSQLPassword = props.getProperty("MSSQL.password");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static Connection getConnection(String dname){
		try {
			String driver = props.getProperty(dname + ".driver");
			String oracleUrl = props.getProperty(dname + ".url");
			String oracleUser = props.getProperty(dname + ".user");
			String oraclePassword = props.getProperty(dname + ".password");
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(oracleUrl,
					oracleUser, oraclePassword);
			return conn;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能：获取数据库连接对象
	 * 
	 * @param url
	 * @param user
	 * @param passwd
	 * @param driver
	 * @return
	 */
	public static Connection getOracleConnection() {
		try {
			Class.forName(oracleDriver);
			Connection conn = DriverManager.getConnection(oracleUrl,
					oracleUser, oraclePassword);
			return conn;
		} catch (Exception e) {
			return null;
		}

	}

	public static Connection getMSSQLConnection() {
		try {
			Class.forName(MSSQLDriver);
			Connection conn = DriverManager.getConnection(MSSQLUrl, MSSQLUser,
					MSSQLPassword);
			return conn;
		} catch (Exception e) {
			return null;
		}

	}

	public static void closeAll(ResultSet rs, Statement stmt, Connection con) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
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
	
	public static void closeAll(ResultSet rs, Statement stmt) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
