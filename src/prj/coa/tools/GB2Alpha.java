package prj.coa.tools;

/************************************************
 * <p>Title:无标题GB2Alpha</p>
 * <p>CreateData: 2005-8-16   15:40:10</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * @author 王凯
 * @version 1.0
 ***********************************************/
/**
 * Created by IntelliJ IDEA. User: 裴贺先 Date: 2004-5-17 Time: 10:59:59
 * ClassDescription:取出汉字字符串的拼音首字母
 */

public class GB2Alpha {

	// 字母Z使用了两个标签，这里有２７个值
	// i, u, v都不做声母, 跟随前面的字母
	private char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
			'击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌',
			'挖', '昔', '压', '匝', '座' };

	private char[] alphatable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',

	'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' };

	private int[] table = new int[27];

	// 初始化
	{
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	public GB2Alpha() {

	}

	// 主函数,输入字符,得到他的声母,
	// 英文字母返回对应的大写字母
	// 其他非简体汉字返回 '0'

	public char Char2Alpha(char ch) {

		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;

		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26)
			return '0';
		else
			return alphatable[i];
	}

	// 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	public String String2Alpha(String SourceStr) {
		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				Result += Char2Alpha(SourceStr.charAt(i));
			}
		} catch (Exception e) {
			Result = "";
		}
		return Result;
	}

	private boolean match(int i, int gb) {
		if (gb < table[i])
			return false;

		int j = i + 1;

		// 字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;

		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	/**
	 * **********************************************
	 * <p>
	 * 过程名称：intercept(截取文字的长度)
	 * </p>
	 * <p>
	 * 创建时间：2005-8-16,17:01:27
	 * </p>
	 * <p>
	 * 创建人员：wangkai
	 * </p>
	 * 
	 * @param FromStr
	 *            需要截取的文字
	 * @param MaxLen
	 *            截取多长
	 * @param ShowMore
	 *            是否显示更多的三点
	 * @return 截取后的文字
	 ********************************************** 
	 */
	@SuppressWarnings("unused")
	public static String intercept(String FromStr, int MaxLen, boolean ShowMore) {
		String ToStr = "";

		if (MaxLen <= 0)
			return FromStr;
		if (FromStr == null)
			return "";
		int FromLen = FromStr.length();
		if (MaxLen >= ChineseLen(FromStr))
			return FromStr;
		if (ShowMore)
			if (MaxLen > 5)
				MaxLen = MaxLen - 3;
		int k = 0;
		try {
			for (int i = 0; i < MaxLen;) {
				char str = FromStr.charAt(k);
				ToStr = ToStr + String.valueOf(str);
				if (gbValue(str) > 0)
					i = i + 2;
				else
					i++;
				k++;

			}
		} catch (Exception e) {
		}
		if (ShowMore)
			if (MaxLen > 4)
				ToStr = ToStr + "...";
		return ToStr;
	}

	/**
	 * **********************************************
	 * <p>
	 * 过程名称：ChineseLen(获得当前文字的长度，中文为2个字符)
	 * </p>
	 * <p>
	 * 创建时间：2005-8-16,17:00:51
	 * </p>
	 * <p>
	 * 创建人员：wangkai
	 * </p>
	 * 
	 * @param FromStr
	 * @return
	 ********************************************** 
	 */
	public static int ChineseLen(String FromStr) {
		if (FromStr == null)
			return 0;
		int FromLen = FromStr.length();
		int ChineseLen = 0;
		for (int i = 0; i < FromLen; i++) {
			if (gbValue(FromStr.charAt(i)) > 0) {
				ChineseLen = ChineseLen + 2;
			} else {
				ChineseLen++;
			}
		}
		return ChineseLen;
	}

	/*******
	 * **********************************************
	 * <p>
	 * 过程名称：gbValue(返回GBK的编码)
	 * </p>
	 * <p>
	 * 创建时间：2005-8-16,16:22:52
	 * </p>
	 * <p>
	 * 创建人员：wangkai
	 * </p>
	 * 
	 * @param ch
	 * @return
	 ********************************************** 
	 */
	public static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GBK");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) {
		GB2Alpha obj1 = new GB2Alpha();
		System.out.println(obj1.String2Alpha("asdf测试：中华人民共和国！"));
		System.out.println(obj1.String2Alpha("裴贺先"));
		System.out.println(GB2Alpha.gbValue('a'));
		System.out.println(obj1.String2Alpha("嫦娥"));
		return;
	}
}