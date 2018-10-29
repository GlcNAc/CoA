package prj.coa.tools.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		DbBase db = new DbBase("db");
		String sql = " select count(*) from puser ";
		Map<String,Object> dd = new HashMap<String,Object>();
		dd.put("mainid", -11);
		dd.put("username", "test11");
		dd.put("account", "test11");
		List<Map<String,Object>> dta = new ArrayList<Map<String,Object>>();
		dta.add(dd);
		try {
			List<Map<String,Object>> rList = db.doQuery(sql);
			System.out.println(rList);
			db.save("puser", dta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
