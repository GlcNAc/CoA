package prj.coa.tools.prop;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
	private final Properties prop = new Properties();
	private String propPath = "/WEB-INF/classes/sys.properties";

	public PropUtil() {
		init();
	}
	
	public PropUtil(String pPath) {
		propPath = pPath;
		init();
	}
	
	private void init() {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(
					propPath));
			prop.load(in);
		} catch (IOException e1) {
			System.err.println("找不到文件 properties");
			return;
		}
	}

	public String getPropConfig(String key, String defaultvalue) {
		return prop.getProperty(key, defaultvalue);
	}

	public static void main(String[] args) {
		PropUtil pUtil = new PropUtil("src/sys.properties");
		System.out.println(pUtil.getPropConfig("test", "111"));
	}
}
