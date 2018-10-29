package prj.coa.test;

import java.util.Map;

import prj.coa.util.lang.StringUtil;

public class JsonTest {
	public static void main(String[] args) {
		jsonTest();
	}

	private static void jsonTest() {

		String str = " {    mapSql:{		sql:\" select * from puser where mainid = ? and isout = ? \",		args:[ \"mainid\", \"isout\" ],		types:[ \"4\", \"4\" ]    },	listSql:{		sql:\" select * from puser \"    } }";

		Map<String, Object> map = StringUtil.dealJsonStr(str);
		System.out.println(map);
	}
}
