/**
 * 
 */
package prj.coa.tools.db;

import java.util.Properties;

/**
 * @author Administrator
 * 
 */
public class PropUtil {

	private static String properties = "sys.properties";

	/**
	 * 获取配置文件键值
	 * 
	 * @param key
	 * @return
	 */
	public String getProperties(String key) {
		Properties props = new Properties();
		try {
			props.load(PropUtil.class.getClassLoader().getResourceAsStream(
					properties));
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
