/**
 * 
 */
package prj.coa.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Administrator
 * 
 */
public class PropUtil {
	private static String properties = "sys.properties";
	private static Properties props = new Properties();

	public static String getProperty(Properties properties, String key,
			String defaultValue, boolean required, boolean canEmpty)
			throws Exception {

		String value = properties.getProperty(key);

		if (value == null) {
			// The value is null
			if (required) {
				throw new Exception("The property '" + key + "' is required.");
			}

			return defaultValue;
		}

		// Trim the value
		value = value.trim();

		if ((value.length() == 0) && (!canEmpty)) {
			// The value is empty
			throw new Exception("The property '" + key + "' can not be empty.");
		}

		return value;
	}

	// 根据key读取value
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterName + " value error");
		}
	}

	public static void main(String[] args) throws Exception {
		props.load(PropUtil.class.getClassLoader().getResourceAsStream(
				properties));
		String url = getProperty(props, "oracle.url", "", true, false);
		System.out.println(url);
	}
}
