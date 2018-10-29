package prj.coa.util.lang;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class StringUtil {

	// 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	/**
	 * 半角转全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 转换为GBK
	 * 
	 * @param strvalue
	 * @return
	 */
	public static String toGBK(String strvalue) {

		try {
			if (strvalue == null) { // 当变量strvalue为null时
				return ""; // 将返回空的字符串
			} else {
				// 将字符串转换为GBK编码
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");
				return strvalue; // 返回转换后的输入变量strvalue
			}
		} catch (Exception e) {
			return "";
		}

	}

	// 对输入的字符串进行一次编码转换，防止SQL注入
	public static String StringtoSql(String str) {
		if (str == null) { // 当变量str为null时
			return ""; // 返回空的字符串
		} else {
			try {
				// 将'号转换化为空格
				str = str.trim().replace('\'', (char) 32);
			} catch (Exception e) {
				return "";
			}
		}
		return str;

	}

	// 验证URL地址是否存在
	public static int isURLExist(String url) {

		int rtn = 0;
		try {
			URL u = new URL(url);
			HttpURLConnection urlconn = (HttpURLConnection) u.openConnection();
			int state = urlconn.getResponseCode();
			if (state == 200) { // 表示URL地址存在
				// String succ = urlconn.getURL().toString();
				rtn = 1;
			} else { // 表示URL地址不存在
				rtn = 0;
			}
		} catch (Exception e) {
			rtn = 0;
		}
		return rtn;
	}

	// 获取字符串中数字
	public static String getNumberStr(String str) {
		String numStr = "";
		StringBuffer sBuffer = new StringBuffer();
		System.out.println(sBuffer);
		if (str != null) {
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if (ch[i] >= '0' && ch[i] <= '9') {
					sBuffer.append(ch[i]);
				}
			}
			if (sBuffer.length() > 0) {
				numStr = sBuffer.toString();
			}
		}

		return numStr;
	}
	
	/**
	 * 获取字符串中的数字串
	 * 
	 * @param str
	 * @return
	 */
	public static String getNumStr(String str) {
		StringBuffer sb = new StringBuffer();
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (Character.isDigit(c[i])) {
				sb.append(c[i]);
			}
		}
		return sb.toString();
	}

	
	/**
	 * 方法描述：json字符串 转 Map
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 吴国栋
	 * @version: 2013年9月30日 上午8:31:45 所属部门 ECM事业部 版权所有 杭州慧智电子科技有限公司
	 */
	public static Map<String, Object> dealJsonStr(String str) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(str);
			Map<String, Object> map = new HashMap<String, Object>();
			for (Iterator<?> itr = jsonObj.keys(); itr.hasNext();) {
				String key = String.valueOf(itr.next()).trim();
				String value = jsonObj.getString(key).trim();
				Map<String, Object> valueMap = dealJsonStr(value);
				if (valueMap != null) {
					map.put(key, valueMap);
				} else {
					if(value.startsWith("[") && value.endsWith("]")){
						value = value.substring(1, value.length() - 1);
						map.put(key, value.split(","));
					} else {
						map.put(key, value);
					}
				}
			}
			return map;
		} catch (JSONException e) {
			System.out.println("not a json str");
		}
		return null;
	}
	
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
}
