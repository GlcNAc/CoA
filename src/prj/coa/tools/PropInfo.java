package prj.coa.tools;

import java.io.InputStream;
import java.util.Properties;

public class PropInfo {

	private static PropInfo instance;
	private String webserver;
	private String webport;
	private String uploadtemppath;
	private String fileserverroot;
	private String logFile;
	private String databaseUrl;
	private String dbuser;
	private String dbpassword;

	public PropInfo() {
		init();
	}

	public static synchronized PropInfo getInstance() {
		if (instance == null)
			instance = new PropInfo();
		return instance;
	}

	Properties dbProps = new Properties();

	private void init() {
		InputStream is = getClass().getResourceAsStream("/sys.properties");

		try {
			dbProps.load(is);
		} catch (Exception e) {
			System.err
					.println("找不到文件 sys.properties，请将sys.properties 放在  CLASSPATH 中，以便系统找到。");
			return;
		}
		logFile = dbProps.getProperty("logfile", "DBConnectionManager.log");
		webserver = dbProps.getProperty("WebPdmserver", "192.168.0.1");
		webport = dbProps.getProperty("httpport", "80");
		uploadtemppath = dbProps.getProperty("uploadtemppath",
				"c:\\temp\\uploadtemp");
		fileserverroot = dbProps.getProperty("fileserverroot",
				"c:\\temp\\fileserver");
		databaseUrl = dbProps.getProperty("lydb.url");
		this.dbuser = dbProps.getProperty("lydb.user");
		this.dbpassword = dbProps.getProperty("lydb.password");
	}

	public Properties getProperties() {
		return this.dbProps;
	}

	public String getWebServerIP() {
		return webserver;
	}

	public String getWebServerPort() {
		return webport;
	}

	public String getUploadTempPath() {
		return uploadtemppath;
	}

	public String getFileServerRoot() {
		return fileserverroot;
	}

	public String getLogFile() {
		return logFile;
	}

	public String getWebServer() {
		return "http://" + getWebServerIP() + ":" + getWebServerPort();
	}

	public String getDBUrl() {
		return this.databaseUrl;
	}

	public String getDBUser() {
		return this.dbuser;
	}

	public String getDBPassword() {
		return this.dbpassword;
	}
}
