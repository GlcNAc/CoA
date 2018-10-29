/**
 * 
 */
package prj.coa.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Administrator
 * 
 */
public class Test {
	/*
	 * plt姓氏-$23281168279961-$图幅-$tf-$专业-$zy-$设计-$bz-$校对-$jd-$审核-$sh-$项目名称-$zxmc-$工程名称-$xmmc-$图号-$th2-$图名-$电气设备及主要材料表-$该图条形码-$
	 * 32225QET-$打印区域为文件一部分-$temphuizhi.dwg-$打印草稿-$未定义-$plt文件-$2.plt-$打印时间-$
	 * 2008-8-2 16:16:57-$打印所用设备-$HP LaserJet 6L-$打印数量-$ 1-$打印方向-$
	 * 0-$打印图幅-$A4-$打印文件名-$图纸汇总测试.dwg-$全图文字-$948932-$
	 * 
	 * 说明: 1,根据"-$"分割, 2,将分割出来的段,用HashMap 存放(变量名统一用"hashMap"),单位是名,双位是值. 完成效果
	 * hashMap.get("plt姓氏") 取出来的值要为 23281168279961 , hashMap.get("项目名称") 取出来的值要为
	 * zxmc
	 * 3,根据tuqian.config.xml文件,将对应的值放入数据库中,如根据项目名称,得到数据库中的字段为:itemname,再将值zxmc
	 * 存入数据库(数据库自己建)
	 * 4,如test.htm中的格式,用$+字段名的形式,就可以让网页中显示出数据库中对应字段的值.注意,是html或htm格式.
	 */

	private static File file = new File("src/tuqian.config.xml");

	public static String getCmdInfo(String str) {
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(file);
			Element root = doc.getRootElement();
			Element foo;
			for (Iterator<?> it = root.elementIterator("node"); it.hasNext();) {
				foo = (Element) it.next();
				if (str.equals(foo.attributeValue("name"))) {
					return foo.attributeValue("fieldname");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取配置文件 tuqian.config.xml 字段信息
	 * @return
	 */
	public static Map<String, String> getColsMap() {
		SAXReader saxReader = new SAXReader();
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = saxReader.read(file);
			Element root = doc.getRootElement();
			Element foo;
			for (Iterator<?> it = root.elementIterator("node"); it.hasNext();) {
				foo = (Element) it.next();
				String colsKey = foo.attributeValue("name");
				String colsValue = foo.attributeValue("fieldname");
				map.put(colsKey, colsValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 存入记录
	 * @param hashMap
	 * @param colsMap
	 */
	public static void saveRecord(Map<String, String> hashMap,Map<String, String> colsMap){
	    PreparedStatement stmt = null;
	    ResultSet rs=null;
	    Connection con = null;
	    DBAccess db = new DBAccess();
	    
		
		String sql0 = "select commonid.nextval from dual";
		int commonid = 0;
		try {
		    con = db.getConnection();
		    stmt=con.prepareStatement(sql0);	    
			rs = stmt.executeQuery();
			if (rs.next()) {
				commonid = rs.getInt(1);
			}
			db.closeAll(rs, stmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		String sql1 = "";
		String sql2 = "";
		Iterator<Entry<String, String>> it = colsMap.entrySet().iterator();
	    while (it.hasNext()) {	   
	        Map.Entry<String,String> entry = it.next();	   
	        String cols = entry.getKey();
	        String value = entry.getValue();
	        for (Map.Entry<String, String> m : hashMap.entrySet()) {
	            String key = m.getKey();
	            String valueString = m.getValue();
	            if (cols.equals(key)) {
	            	if (!sql1.equals("")) {
						sql1+= ",";
						sql2+=",";
					}
					sql1 += value;
					sql2 +="'"+valueString+"'";
				}
	        }  
	    }

	    String sql = "insert into test(commonid,";
	    sql += sql1;
	    sql+=") values (" + commonid + ",";
	    sql+= sql2;
	    sql+= ")";
	    //System.out.println(sql);
	    try {
	    	con = db.getConnection();
	    	stmt=con.prepareStatement(sql);	    
			stmt.execute();
			db.closeAll(null, stmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> hashMap = new HashMap<String, String>();
		String str = "plt姓氏-$23281168279961-$图幅-$tf-$专业-$zy-$设计-$bz-$校对-$jd-$审核-$sh-$项目名称-$zxmc-$工程名称-$xmmc-$图号-$th2-$图名-$电气设备及主要材料表-$该图条形码-$32225QET-$打印区域为文件一部分-$temphuizhi.dwg-$打印草稿-$未定义-$plt文件-$2.plt-$打印时间-$2008-8-2 16:16:57-$打印所用设备-$HP LaserJet 6L-$打印数量-$ 1-$打印方向-$0-$打印图幅-$A4-$打印文件名-$图纸汇总测试.dwg-$全图文字-$948932-$";
		String[] strs = str.split("-\\$");
		for (int i = 0; i < strs.length; i = i + 2) {
			hashMap.put(strs[i], strs[i + 1]);
		}
		System.out.println(hashMap);
		
		Map<String, String> colsMap = getColsMap();
		
		System.out.println(colsMap);
		
		//saveRecord(hashMap, colsMap);
		
	}
}
