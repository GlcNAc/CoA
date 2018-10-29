package prj.coa.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test4 {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		list.add("B3781S-D0108-01");
		list.add("B3781S-D0108-02");
		list.add("B3781S-D0108-03");
		list.add("B3781S-D0108-04");
		list.add("B3781S-D0108-05");
		list.add("B3781S-D0108-06");
		list.add("B3781S-D0108A-02");
		
		String str = "";
		String temp = "";
		//System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			str = (String) list.get(i);
			String[] strs = str.split("-");
			char c=strs[1].charAt(strs[1].length()-1);
			if('A'<=c&&c<='Z'){
				strs[1] = strs[1].substring(0,strs[1].length()-1);
			}
			StringBuffer sBuffer = new StringBuffer();
			for (int j = 0; j < strs.length; j++) {
				if (sBuffer.length() != 0) {
					sBuffer.append('-');
				}
				sBuffer.append(strs[j]);
			}
			temp = sBuffer.toString();
			//System.out.println(temp + ":::" + str);
			if (map.get(temp)==null) {
				map.put(temp, str);
			}else if(((String)map.get(temp)).compareToIgnoreCase(str)<0){
				map.put(temp, str);
			}
		}
		for (Iterator<?> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println(object);
		}
	}
	
}
