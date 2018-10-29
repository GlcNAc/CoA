package prj.coa.tools.db;
 
import java.sql.SQLException;

public class C3p0Config {

		private String jdbcUrl;
		private String driverClass;
		private String userName;
		private String passWord;
 
		public String getJdbcUrl() {
			return jdbcUrl;
		}

		public void setJdbcUrl(String jdbcUrl) throws SQLException {
			if (jdbcUrl == null)
				throw new SQLException("jdbcUrl must't null");
			if (jdbcUrl.trim().length() == 0) {
				throw new SQLException("jdbcUrl length must Than Zero");
			}
			this.jdbcUrl = jdbcUrl.trim();
		}

		public String getDriverClass() {
			return driverClass;
		}

		public void setDriverClass(String driverClass) throws SQLException {
			if (driverClass == null)
				throw new SQLException("driverClass must't null");
			if (driverClass.trim().length() == 0) {
				throw new SQLException("driverClass length must Than Zero");
			}
			this.driverClass = driverClass;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) throws SQLException {
			if (userName == null)
				throw new SQLException("userName must't null");
			 
			this.userName = userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public void setPassWord(String passWord) throws SQLException {
			if (passWord == null)
				throw new SQLException("passWord must't null");
			
			this.passWord = passWord;
		}
		 
	}