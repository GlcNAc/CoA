package prj.coa.test;

import java.text.ParseException;
import java.util.StringTokenizer;

import prj.coa.util.lang.StringUtil;
import prj.coa.util.lang.TimeUtil;



public class Test2 {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println("-------===========test===========-------");
		String sql = "select * from test where barcode!=' ' and 1=1 and ZHENGBIAN = '' and dtype = 2 order by zhengbian";
		String orderStr = sql.substring(sql.indexOf("order by"));
		System.out.println(orderStr);
		sql = sql.replace(orderStr, "order by common_id desc");
		System.out.println(sql);
		int idx = sql.indexOf("ZHENGBIAN");
		System.out.println(idx);
		int idx2 = sql.indexOf("and", idx);
		String str = sql.substring(idx, idx2+3);
		System.out.println(str);
		System.out.println(sql.replace(str, ""));
		
		String str0 = "1234321";
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(str0);
		if (sBuffer.reverse().equals(sBuffer)) {
			System.out.println("true");
		}
		
		String strs = "81-$-";
		StringTokenizer st = new StringTokenizer(strs, "-\\$-");
		while (st.hasMoreTokens()){
			String obj = st.nextToken();
			System.out.println(obj);
		}	
		
		String fanwei = "水务部:57本; 变电部:4本; ";
		String[] strs2 = fanwei.split(";");
		int count = 0;
		for (int i = 0; i < strs2.length-1; i++) {
			System.out.println(strs2[i]);
			String str1 = strs2[i].substring(0,strs2[i].indexOf(":"));
			String str2 = strs2[i].substring(strs2[i].indexOf(":") + 1,
					strs2[i].indexOf("本"));
			
			System.out.println(str1 + "  " + str2);
		}
		
		try {
			count = Integer.parseInt("2");
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(count);
		
		int max = 0;
		try {
			String strttString = "A";
			StringBuffer sBuffer1 = new StringBuffer();
			
			char[] ch = strttString.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if (ch[i]>='0' && ch[i] <= '9') {
					sBuffer1.append(ch[i]);
				}
			}
			max = Integer.parseInt(sBuffer1.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println( "max:::::"+max);
		
		String string = StringUtil.getNumberStr("AAA");
		if (string.equals("")) {
			System.out.println("strNum::::::");
		}
		
		String ddString = "11.8.6";
		System.out.println(TimeUtil.toUtilDateFromStrDateByFormat(ddString, "yyyy-MM-dd"));
		
	}

}
