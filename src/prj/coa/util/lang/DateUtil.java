/**
 * 
 */
package prj.coa.util.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * @Functionality: 鏈夊叧鏃ユ湡宸ュ叿绫�
 * 
 * DateUtil涓昏鍔熻兘鏈夛細 1.鏃ユ湡姣旇緝 2.鑾峰彇2涓瓧绗︽棩鏈熺殑澶╂暟宸紝鍛ㄦ暟宸紝鏈堟暟宸紝骞存暟宸� 3.鏃ユ湡娣诲姞 4.鍒ゆ柇缁欏畾鏃ユ湡鏄笉鏄鼎骞�
 */
public class DateUtil extends TimeUtil {
	/**
	 * Logger for this class
	 */
	protected static final Logger logger = Logger.getLogger(DateUtil.class);

	/** 鏃ユ湡杞寲 */
	protected static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * @Functionality: 鏋勯�犲嚱鏁�
	 */
	public DateUtil() {
	}

	// ----------------------鏃ユ湡璁＄畻---------------------------------------------------------------------------------

	/**
	 * 鏄惁寮�濮嬫棩鏈熷湪缁撴潫鏃ユ湡涔嬪墠(涓嶅寘鎷浉绛�)
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return boolean 鍦ㄧ粨鏉熸棩鏈熷墠:ture;鍚﹀垯锛歠alse
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public static boolean isStartDateBeforeEndDate(Date p_startDate,
			Date p_endDate) throws ParseException {
		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		return (l_startTime - l_endTime > 0) ? true : false;
	}

	/**
	 * 鑾峰彇2涓瓧绗︽棩鏈熺殑澶╂暟宸�
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 澶╂暟宸�
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public static long getDaysOfTowDiffDate(String p_startDate, String p_endDate)
			throws ParseException {

		Date l_startDate = toUtilDateFromStrDateByFormat(p_startDate,
				"yyyy-MM-dd");
		Date l_endDate = toUtilDateFromStrDateByFormat(p_endDate, "yyyy-MM-dd");
		long l_startTime = getMillisOfDate(l_startDate);
		long l_endTime = getMillisOfDate(l_endDate);
		long betweenDays = ((l_endTime - l_startTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}

	/**
	 * 鑾峰彇2涓瓧绗︽棩鏈熺殑鍛ㄦ暟宸�
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 鍛ㄦ暟宸�
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public static long getWeeksOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 7;
	}

	/**
	 * 鑾峰彇2涓瓧绗︽棩鏈熺殑鏈堟暟宸�
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 鏈堟暟宸�
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public static long getMonthsOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 30;
	}

	/**
	 * 鑾峰彇2涓瓧绗︽棩鏈熺殑骞存暟宸�
	 * 
	 * @param p_startDate
	 * @param p_endDate
	 * @return 骞存暟宸�
	 * @author zhuqx
	 * @Date: 2006-10-31
	 */
	public static long getYearsOfTowDiffDate(String p_startDate,
			String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 365;
	}

	/**
	 * 鍦ㄧ粰瀹氱殑鏃ユ湡鍩虹涓婃坊鍔犲勾锛屾湀锛屾棩銆佹椂锛屽垎锛岀 渚嬪瑕佸啀2006锛�10锛�21锛坲itl鏃ユ湡锛夋坊鍔�3涓湀锛屽苟涓旀牸寮忓寲涓簓yyy-MM-dd鏍煎紡锛�
	 * 杩欓噷璋冪敤鐨勬柟寮忎负 addDate(2006锛�10锛�21,3,Calendar.MONTH,"yyyy-MM-dd")
	 * 
	 * @param p_startDate
	 *            缁欏畾鐨勬棩鏈�
	 * @param p_count
	 *            鏃堕棿鐨勬暟閲�
	 * @param p_field
	 *            娣诲姞鐨勫煙
	 * @param p_format
	 *            鏃堕棿杞寲鏍煎紡锛屼緥濡傦細yyyy-MM-dd hh:mm:ss 鎴栬�厃yyy-mm-dd绛�
	 * @return 娣诲姞鍚庢牸寮忓寲鐨勬椂闂�
	 * @Date: 2006-10-31
	 */
	public static String addDate(Date p_startDate, int p_count, int p_field,
			String p_format) throws ParseException {

		// 骞达紝鏈堬紝鏃ャ�佹椂锛屽垎锛岀
		int l_year = getYearOfDate(p_startDate);
		int l_month = getMonthOfDate(p_startDate) - 1;
		int l_day = getDayOfDate(p_startDate);
		int l_hour = getHourOfDate(p_startDate);
		int l_minute = getMinuteOfDate(p_startDate);
		int l_second = getSecondOfDate(p_startDate);
		Calendar l_calendar = new GregorianCalendar(l_year, l_month, l_day,
				l_hour, l_minute, l_second);
		l_calendar.add(p_field, p_count);
		return toStrDateFromUtilDateByFormat(l_calendar.getTime(), p_format);
	}

	/**
	 * 鍒ゆ柇缁欏畾鏃ユ湡鏄笉鏄鼎骞�
	 * 
	 * @param p_date
	 *            缁欏畾鏃ユ湡
	 * @return boolean 濡傛灉缁欏畾鐨勫勾浠戒负闂板勾锛屽垯杩斿洖 true锛涘惁鍒欒繑鍥� false銆�
	 * @Date: 2006-10-31
	 */
	public static boolean isLeapYear(Date p_date) {
		int l_year = getYearOfDate(p_date);
		GregorianCalendar l_calendar = new GregorianCalendar();
		return l_calendar.isLeapYear(l_year);
	}

}