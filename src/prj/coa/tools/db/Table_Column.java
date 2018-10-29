/**
 * 文件名：Table_Column.java
 * 版本信息：Version 1.0
 * 创建人 赵双
 * 日期：2013-3-4下午02:38:20
 * Copyright talkweb.com.cn Corporation 2013
 * 所属部门  ECM事业部
 * 版权所有  杭州慧智电子科技有限公司
 *
 */
package prj.coa.tools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 类描述：获取表字段列表
 * 
 * @version: 1.0
 * @author: 赵双
 * @version: 2013-3-4 下午02:38:20 编写部门 ECM事业部 版权所有 杭州慧智电子科技有限公司
 */
public class Table_Column extends DbBase  {

	
	
	 /**
	  * 所属部门  ECM事业部
	  * 版权所有  杭州慧智电子科技有限公司
	  * 类的构造方法
	  * 创建一个新的实例 Table_Column.
	  * @param 
	  * @param configName
	  */
	public Table_Column(String configName) {
		super(configName);
	}

	/**
	 * 
	  * 方法描述：获取表字段列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 赵双
	  * @version: 2013-3-4 下午02:53:27
	  * 所属部门  ECM事业部
	  * 版权所有  杭州慧智电子科技有限公司
	 */
	public String getAllColumn(String tablename) throws SQLException {
		String sql = "select column_name from USER_TAB_COLUMNS where table_name='"
				+ tablename + "'";
		List<Map<String, Object>> tablelist = this.doQuery(sql, null);
		String columns = ""; 
		for (int i = 0; i < tablelist.size(); i++) {
			Map<String, Object> row = tablelist.get(i);
			String column = DbMapUtil.getValue(row, "column_name", "");
			columns += "\"" + column.toLowerCase() + "\"";
			if (i<tablelist.size()-1) {
				columns+=",";
			}
		}
		return columns;

	}
	  public String[] getCols(String tableName,String configName) throws SQLException
	  {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	      con = ConnectionFactory.getInstance(configName).getConnection();
	      String dbType = getDbType();
	      String sql = null;
	      if (dbType.equals("ORCLE"))
	        sql = "select * from " + tableName + " where rownum < 1";
	      else if (dbType.equals("MSSQL")) {
	        sql = "select top 1 * from " + tableName;
	      }
	      ps = con.prepareStatement(sql);
	      rs = ps.executeQuery();
	      ResultSetMetaData metaDate = rs.getMetaData();
	      int columnCount = metaDate.getColumnCount();
	      String[] cols = new String[columnCount];
	      for (int i = 1; i <= columnCount; i++) {
	        cols[(i - 1)] = metaDate.getColumnName(i).toLowerCase();
	      }
	      String[] arrayOfString1 = cols;
	      return arrayOfString1;
	    }catch (Exception e) {
	    	e.printStackTrace();
		}
	    finally {
	      DbUtil.free(rs, ps, con);
	    }
		return null;
	  }

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException {
		Table_Column dColumn=new Table_Column("new");
		String columnString=dColumn.getAllColumn("OA_RESOURCECENTER_SCIENCE");
		
		String str="";
		String columns[]=dColumn.getCols("OA_RESOURCECENTER_SCIENCE", "new");
	 
		System.out.println(str);
	}
}
