package prj.coa.util.lang;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
	public static String format(Date date, String pattern, Locale locale) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(date);
	}

	public static String format(Date date, String pattern) {
		return format(date, pattern, Locale.CHINESE);
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String format(Double value, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(value.doubleValue()));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 4).toString();
	}

	public static String format(BigDecimal value, int scale) {
		BigDecimal one = new BigDecimal("1");
		return value.divide(one, scale, 4).toString();
	}
}