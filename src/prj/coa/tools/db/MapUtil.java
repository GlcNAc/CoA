package prj.coa.tools.db;

import java.util.Date;
import java.util.Map;

public class MapUtil {

	public static int getInt(Map<?, ?>  row,String colName){
		return DbMapUtil.getValue(row,colName,0);
	}
	
	public static long getBigDecimal(Map<?, ?>  row,String colName){
		Object obj = row.get(colName.toUpperCase());
		if(obj != null){
			if(obj instanceof java.math.BigDecimal){
				return ((java.math.BigDecimal)obj).longValue();
			}else{
				System.out.println(obj.getClass().getName()+" "+colName);
				return 0;
			}
		}
		return 0;
	}
	
	public static String getString(Map<?, ?>  row,String colName){
		return DbMapUtil.getValue(row,colName,"");
	}
	
	public static Date getDate(Map<String,Object> row,String colName){
		return DbMapUtil.getValue(row,colName);
	}
	
	public static Date getTimestamp(Map<?, ?>  row,String colName){
		return DbMapUtil.getValue(row,colName);
	}
}
