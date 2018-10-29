package prj.coa.util.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ParseUtil {

	public static Date parseDate(String date) {
		if (date.length() <= 0) {
			return null;
		}
		StringTokenizer str = new StringTokenizer(date, "/");
		String t1 = str.nextToken();
		String t2 = str.nextToken();
		String t3 = str.nextToken();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(t1), Integer.parseInt(t2) - 1,
				Integer.parseInt(t3));
		Date d = calendar.getTime();
		return d;
	}

	public static int convertInt(String str) {
		if (convertNull(str).length() == 0) {
			return 0;
		}
		int k = 0;
		try {
			k = Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
		return k;
	}

	public static String convertTimeToDb(String ctime) {
		if ((ctime == null) || (ctime.length() == 0)) {
			return "''";
		}
		if (ctime.indexOf(":") > 0) {
			return convertTimeToDb2(ctime);
		}
		if (!isDateFormat(ctime))
			return "''";
		return "to_date('" + ctime + "','yyyy-mm-dd')";
	}

	public static String convertTimeToDb2(String ctime) {
		if ((ctime == null) || (ctime.length() == 0))
			return "null";
		return "to_date('" + ctime + "','yyyy-mm-dd hh24:MI:ss')";
	}

	public static boolean isDateFormat(String date) {
		int f = date.indexOf("-");
		int l = date.lastIndexOf("-");
		if ((f > 0) && (f < l - 1) && (l < date.length() - 1)) {
			return true;
		}
		f = date.indexOf("/");
		l = date.lastIndexOf("/");

		return (f > 0) && (f < l - 1) && (l < date.length() - 1);
	}

	public static String convertNull(String s) {
		if ((s == null) || (s.equals("null"))) {
			return "";
		}
		return s.trim();
	}

	public static Date convert2Date(String date, String format) {
		if ((date == null) || (date.trim().length() == 0)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
		}
		return null;
	}

	public static Number convert2Number(String data) {
		if (data == null)
			return null;
		try {
			return Double.valueOf(Double.parseDouble(data));
		} catch (Exception e) {
		}
		return null;
	}

	public static String convert2Null(String val) {
		return convert2Null(val, "");
	}

	public static String convert2Null(String val, String def) {
		if ((val == null) || (val.equals("null"))) {
			return def;
		}
		return val.trim();
	}

	public static String convertEncode(String in) {
		return convertEncode(in, "ISO-8859-1", "gb2312");
	}

	public static String convertEncode(String src, String srcCharSet,
			String distCharSet) {
		if (src == null)
			return null;
		try {
			return new String(src.getBytes(srcCharSet), distCharSet);
		} catch (UnsupportedEncodingException e) {
		}
		return src;
	}

	public static Object convert2Object(Object value) {
		return value;
	}

	public static String convert2String(Object value) {
		Object answer = value;

		if (answer != null) {
			if (answer instanceof Number)
				return answer.toString();
			if (answer instanceof Date)
				return FormatUtil.format((Date) value);
			if (answer instanceof Character)
				return answer.toString();
			if (answer instanceof CharSequence) {
				return answer.toString();
			}
			return answer.toString();
		}
		return null;
	}

	public static String convert2String(Object[] value) {
		if (value == null) {
			return null;
		}
		StringBuilder plainText = new StringBuilder();
		for (int i = 0; i < value.length; ++i) {
			if (i > 0) {
				plainText.append(" , ");
			}
			plainText.append(convert2String(value[i]));
		}

		return plainText.toString();
	}

	public static <T> String convertCollection2String(Collection<T> collection,
			Character separator) {
		if (separator == null) {
			separator = Character.valueOf('$');
		}
		if (collection == null) {
			return "";
		}
		StringBuilder str = new StringBuilder();
		Iterator<T> collIt = collection.iterator();
		boolean isFirst = true;
		while (collIt.hasNext()) {
			if (!(isFirst))
				str.append(separator);
			else {
				isFirst = false;
			}
			str.append(collIt.next());
		}
		return str.toString();
	}

	public static Boolean convert2Boolean(Object value) {
		Object answer = value;
		if (answer != null) {
			if (answer instanceof Boolean)
				return ((Boolean) answer);
			if (answer instanceof String) {
				String a = answer.toString();
				if ((a.equalsIgnoreCase("yes")) || (a.equals("1")))
					return Boolean.valueOf(true);
				if (a.equalsIgnoreCase("no")) {
					return Boolean.valueOf(false);
				}
				return Boolean.valueOf((String) answer);
			}
			if (answer instanceof Number) {
				Number n = (Number) answer;
				return ((n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE);
			}
		}
		return null;
	}

	public static Number convert2Number(Object value) {
		Object answer = value;
		if (answer != null) {
			if (answer instanceof Number)
				return ((Number) answer);
			if (answer instanceof String)
				try {
					String text = (String) answer;
					return NumberFormat.getInstance().parse(text);
				} catch (ParseException localParseException) {
				}
		}
		return null;
	}

	public static Byte convert2Byte(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Byte) {
			return ((Byte) answer);
		}
		return new Byte(answer.byteValue());
	}

	public static Short convert2Short(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Short) {
			return ((Short) answer);
		}
		return new Short(answer.shortValue());
	}

	public static Integer convert2Integer(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Integer) {
			return ((Integer) answer);
		}
		return new Integer(answer.intValue());
	}

	public static Long convert2Long(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Long) {
			return ((Long) answer);
		}
		return new Long(answer.longValue());
	}

	public static Float convert2Float(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Float) {
			return ((Float) answer);
		}
		return new Float(answer.floatValue());
	}

	public static Double convert2Double(Object value) {
		Number answer = convert2Number(value);
		if (answer == null)
			return null;
		if (answer instanceof Double) {
			return ((Double) answer);
		}
		return new Double(answer.doubleValue());
	}

	public static Object convert2Object(Object value, Object defaultValue) {
		Object answer = value;
		if (answer != null) {
			return answer;
		}
		return defaultValue;
	}

	public static String convert2String(Object value, String defaultValue) {
		String answer = convert2String(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Boolean convert2Boolean(Object value, Boolean defaultValue) {
		Boolean answer = convert2Boolean(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Number convert2Number(Object value, Number defaultValue) {
		Number answer = convert2Number(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Byte convert2Byte(Object value, Byte defaultValue) {
		Byte answer = convert2Byte(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Short convert2Short(Object value, Short defaultValue) {
		Short answer = convert2Short(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Integer convert2Integer(Object value, Integer defaultValue) {
		Integer answer = convert2Integer(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Long convert2Long(Object value, Long defaultValue) {
		Long answer = convert2Long(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Float convert2Float(Object value, Float defaultValue) {
		Float answer = convert2Float(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Double convert2Double(Object value, Double defaultValue) {
		Double answer = convert2Double(value);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static boolean convert2BooleanValue(Object value) {
		Boolean booleanObject = convert2Boolean(value);
		if (booleanObject == null) {
			return false;
		}
		return booleanObject.booleanValue();
	}

	public static byte convert2ByteValue(Object value) {
		Byte byteObject = convert2Byte(value);
		if (byteObject == null) {
			return 0;
		}
		return byteObject.byteValue();
	}

	public static short convert2ShortValue(Object value) {
		Short shortObject = convert2Short(value);
		if (shortObject == null) {
			return 0;
		}
		return shortObject.shortValue();
	}

	public static int convert2IntValue(Object value) {
		Integer integerObject = convert2Integer(value);
		if (integerObject == null) {
			return 0;
		}
		return integerObject.intValue();
	}

	public static long convert2LongValue(Object value) {
		Long longObject = convert2Long(value);
		if (longObject == null) {
			return 0L;
		}
		return longObject.longValue();
	}

	public static float convert2FloatValue(Object value) {
		Float floatObject = convert2Float(value);
		if (floatObject == null) {
			return 0.0F;
		}
		return floatObject.floatValue();
	}

	public static double convert2DoubleValue(Object value) {
		Double doubleObject = convert2Double(value);
		if (doubleObject == null) {
			return 0.0D;
		}
		return doubleObject.doubleValue();
	}

	public static boolean convert2BooleanValue(Object value,
			boolean defaultValue) {
		Boolean booleanObject = convert2Boolean(value);
		if (booleanObject == null) {
			return defaultValue;
		}
		return booleanObject.booleanValue();
	}

	public static byte convert2ByteValue(Object value, byte defaultValue) {
		Byte byteObject = convert2Byte(value);
		if (byteObject == null) {
			return defaultValue;
		}
		return byteObject.byteValue();
	}

	public static short convert2ShortValue(Object value, short defaultValue) {
		Short shortObject = convert2Short(value);
		if (shortObject == null) {
			return defaultValue;
		}
		return shortObject.shortValue();
	}

	public static int convert2IntValue(Object value, int defaultValue) {
		Integer integerObject = convert2Integer(value);
		if (integerObject == null) {
			return defaultValue;
		}
		return integerObject.intValue();
	}

	public static long convert2LongValue(Object value, long defaultValue) {
		Long longObject = convert2Long(value);
		if (longObject == null) {
			return defaultValue;
		}
		return longObject.longValue();
	}

	public static float convert2FloatValue(Object value, float defaultValue) {
		Float floatObject = convert2Float(value);
		if (floatObject == null) {
			return defaultValue;
		}
		return floatObject.floatValue();
	}

	public static double convert2DoubleValue(Object value, double defaultValue) {
		Double doubleObject = convert2Double(value);
		if (doubleObject == null) {
			return defaultValue;
		}
		return doubleObject.doubleValue();
	}

	public static String convert2String(Throwable cause) {
		StringWriter stackTrace = new StringWriter();
		cause.printStackTrace(new PrintWriter(stackTrace));
		String st = stackTrace.toString();
		return st;
	}
}