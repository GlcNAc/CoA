/**
 * 
 */
package prj.coa.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 配置信息管理器
 * 
 * @author bwl
 * @version 1.0
 */
public class ConfigManager {

	/**
	 * 提供单例对象的静态内部类
	 */
	private static class SingletonHolder {
		public static ConfigManager instance = new ConfigManager();
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 */
	public static ConfigManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 存储问题列表的Map
	 */
	private final Map<String, Properties> name2properties;

	/**
	 * 构造方法，请使用getInstance()获取实例
	 */
	private ConfigManager() {
		name2properties = Collections
				.synchronizedMap(new HashMap<String, Properties>());
		doInit();
	}

	/**
	 * 初始化方法
	 */
	private void doInit() {
		try {
			File path = new File("./conf/");
			if (!path.exists()) {
				System.out
						.println("ConfilgManager Init Error: There is no folder named 'conf' under src file.");
				return;
			}
			File[] confFiles = path.listFiles(new DirFilter(".*\\.properties"));// \\
			for (int i = 0; i < confFiles.length; i++) {
				File f = confFiles[i];
				if (f.exists() && f.isFile()) {
					Properties properties = new Properties();
					InputStream is = new FileInputStream(f);
					properties.load(is);
					name2properties.put(f.getName(), properties);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置项的值
	 * 
	 * @param fileName
	 *            配置文件的名称
	 * @param key
	 *            关键码的值
	 * @return 配置项
	 */
	public String getProperty(String fileName, String key) {
		if (fileName == null || fileName.length() == 0) {
			return null;
		}
		Properties prop = name2properties.get(fileName);
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}

	/**
	 * 获取整形的配置项的值
	 * 
	 * @param fileName
	 *            配置文件的名称
	 * @param keyName
	 *            关键码的值
	 * @return 如果正确则返回数字，否则返回-1
	 */
	public int getIntProperty(String fileName, String key) {
		String value = this.getProperty(fileName, key);
		int result = -1;
		if (value == null) {
			return result;
		}
		try {
			result = Integer.parseInt(value);
			return result;
		} catch (Exception e) {
			// Do nothing
		}
		return result;
	}

	/**
	 * 过滤属性文件的内部类
	 */
	class DirFilter implements FilenameFilter {

		/**
		 * 记录文件名格式的正则对象
		 */
		private final Pattern pattern;

		public DirFilter(String regex) {
			pattern = Pattern.compile(regex);
		}

		public boolean accept(File dir, String name) {
			return pattern.matcher(new File(name).getName()).matches();
		}

	}

	public static void main(String[] args) {
		ConfigManager config = ConfigManager.getInstance();
		System.out.println(config.getIntProperty("cache.properties",
				"cache.size")
				+ "");
		System.out.println(config.getProperty("javagroups.properties",
				"bus_name")
				+ "");
	}
}
