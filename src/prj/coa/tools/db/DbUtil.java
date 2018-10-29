package prj.coa.tools.db;


public class DbUtil {

	public static final String SYSBASE = "SYSBASE";
	public static final String MYSQL = "MYSQL";
	public static final String MSSQL = "MSSQL";
	public static final String ORCLE = "ORCLE";
	public static final String UNKNOWN = "UNKNOWN";

	public static void free(java.sql.ResultSet rs,
			java.sql.PreparedStatement ps, java.sql.Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (java.sql.SQLException sqle) {
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (java.sql.SQLException sqle) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (java.sql.SQLException sqle) {
			}
		}
	}

	public static String getDataBaseType(java.sql.Connection con) {
		if (con == null)
			return UNKNOWN;
		try {
			java.sql.DatabaseMetaData metaData = con.getMetaData();
			String product = metaData.getDatabaseProductName();
			product = product.toUpperCase();
			if (product.indexOf("ORACLE") >= 0)
				return ORCLE;
			else if (product.indexOf("MICROSOFT") >= 0
					&& product.indexOf("SQL") >= 0)
				return MSSQL;
			else if (product.indexOf(MYSQL) >= 0)
				return MYSQL;
			else if (product.indexOf(SYSBASE) >= 0)
				return SYSBASE;
			else
				return UNKNOWN;
		} catch (java.sql.SQLException sqle) {
			return "SQLEXCEPTION";
		}
	}

	public static String transEmpty(String value, String dvalue) {
		if (isEmpty(value))
			return dvalue;
		return value;
	}

	public static boolean isEmpty(String s) {// �ж��ǲ��ǡ��ա��ַ��ܶ������Ϊ��Ч�ַ�
		if (s == null)
			return true;
		s = s.trim().replaceAll("\r", "").replaceAll("\n", "");
		if (s.length() == 0 || s.equalsIgnoreCase("null")
				|| s.equalsIgnoreCase("undefined"))
			return true;
		return false;
	}
}
