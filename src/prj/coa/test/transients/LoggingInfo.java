package prj.coa.test.transients;

import java.util.Date;

public class LoggingInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2437984159486251547L;
	private Date loggingDate = new Date();
	private String uid;
	private transient String pwd;

	LoggingInfo(String user, String password) {
		uid = user;
		pwd = password;
	}

	public String toString() {
		String password = null;
		if (pwd == null) {
			password = "NOT SET";
		} else {
			password = pwd;
		}
		return "logon info: \n   " + "user: " + uid + "\n   logging date : "
				+ loggingDate.toString() + "\n   password: " + password;
	}
}
