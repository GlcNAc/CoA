package prj.coa.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {
	
	public static void main(String[] args) {
		String str = "提取33字符串中55数字";
		System.out.println(getMaxNum(str));
		System.out.println(getMinNum(str));
		
		String role = "会签1";
		String hq_num = role.replace("会签", "");
		System.out.println(hq_num);
	}
	
	/**
	  * 方法描述：提取字符串中数字
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 吴国栋
	  * @version: 2014年2月10日 下午4:00:19
	  * 所属部门  ECM事业部
	  * 版权所有  杭州慧智电子科技有限公司
	  */
	public static List<Integer> getCountNum(String countStr) {		
		List<Integer> digitList = new ArrayList<Integer>();		
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(countStr);
		String result = m.replaceAll(",").trim();
		String[] strs = result.split(",");
		for (String str : strs) {
			if(str.length()>0){
				try {
					digitList.add(Integer.parseInt(str));
				} catch (Exception e) {
				}
			}
		}
		return digitList;
	}
	
	public static int getMaxNum(String countStr){
		List<Integer> digitList = getCountNum(countStr);
		Collections.reverse(digitList);
		if(digitList.size()>0){
			return digitList.get(0);
		}
		return -1;
	}

	public static int getMinNum(String countStr){
		List<Integer> digitList = getCountNum(countStr);
		Collections.sort(digitList);
		if(digitList.size()>0){
			return digitList.get(0);
		}
		return -1;
	}
}
