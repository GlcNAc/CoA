package prj.coa.tools.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class DbMapUtil  {
	
	public static Object getObjValue(Map<?, ?> row,String key){
		Object obj = row.get(key.trim().toUpperCase());
		if(obj == null){
			obj = row.get(key);
		}
		if(obj != null){
			Iterator<?> keys = row.keySet().iterator();
			while(keys.hasNext()){
				Object rowKey = keys.next();
				if(rowKey!= null && rowKey.toString().toUpperCase().equals(key.toUpperCase())){
					obj = row.get(rowKey);
					break;
				}
			}
		}
		return obj;
	}
	
	public static int getValue(Map<?, ?>  row, String key, int tValuedefaultValue){
		
		
		if(key == null || row == null){
			return tValuedefaultValue;
		}
				
		Object obj = getObjValue(row,key);
		
		 
		if(obj ==null){return tValuedefaultValue;
		}
 
		if(obj instanceof java.lang.Number){
			return ((java.lang.Number)obj).intValue();
		}

		if(obj instanceof java.math.BigDecimal){
			return ((java.math.BigDecimal)obj).intValue();
		}
		
		
		if(obj instanceof java.math.BigInteger){
			return ((java.math.BigInteger)obj).intValue();
		}
		if(obj instanceof java.lang.Integer){
			return ((java.lang.Integer)obj).intValue();
		}
		if(obj instanceof java.lang.Long){
			return ((java.lang.Long)obj).intValue();
		}
		if(obj instanceof java.lang.Double){
			return ((java.lang.Double)obj).intValue();
		}
		if(obj instanceof java.lang.Byte){
			return ((java.lang.Byte)obj).intValue();
		}
		
		System.out.println("class not know : getValue(Map  row, String key, int defaultValue) "+obj.getClass().getName()+" coldefaultValue"+key);
		return tValuedefaultValue;
	}
	
	public static long getValue(Map<?, ?>  row, String key, long defaultValue){
		if(key == null || row == null){
			return defaultValue;
		}
		
		Object obj = getObjValue(row,key);
		
		if(obj ==null){
			return defaultValue;
		}
		
		if(obj instanceof java.math.BigDecimal){
			return ((java.math.BigDecimal)obj).longValue();
		}
		if(obj instanceof java.math.BigInteger){
			return ((java.math.BigInteger)obj).longValue();
		}
		if(obj instanceof java.lang.Integer){
			return ((java.lang.Integer)obj).longValue();
		}
		if(obj instanceof java.lang.Long){
			return ((java.lang.Long)obj).longValue();
		}
		if(obj instanceof java.lang.Double){
			return ((java.lang.Double)obj).longValue();
		}
		if(obj instanceof java.lang.Byte){
			return ((java.lang.Byte)obj).longValue();
		}
		System.out.println("class not know : getValue(Map  row, String key, int defaultValue) "+obj.getClass().getName()+" col Name "+key);
		return defaultValue;
	}

	public static double getValue(Map<?, ?>  row, String key, double defaultValue){
		if(key == null || row == null){
			return defaultValue;
		}
		
		Object obj = getObjValue(row,key);
		
		if(obj ==null){
			return defaultValue;
		}
		
		if(obj instanceof java.math.BigDecimal){
			return ((java.math.BigDecimal)obj).doubleValue();
		}
		if(obj instanceof java.math.BigInteger){
			return ((java.math.BigInteger)obj).doubleValue();
		}
		if(obj instanceof java.lang.Integer){
			return ((java.lang.Integer)obj).doubleValue();
		}
		if(obj instanceof java.lang.Long){
			return ((java.lang.Long)obj).doubleValue();
		}
		if(obj instanceof java.lang.Double){
			return ((java.lang.Double)obj).doubleValue();
		}
		if(obj instanceof java.lang.Byte){
			return ((java.lang.Byte)obj).doubleValue();
		}
		System.out.println("class not know : getValue(Map  row, String key, int defaultValue) "+obj.getClass().getName()+" col Name "+key);
		return defaultValue;
	}
	
	public static float getValue(Map<?, ?>  row, String key, float defaultValue){
		if(key == null || row == null){
			return defaultValue;
		}
		
		Object obj = getObjValue(row,key);
		
		if(obj ==null){
			return defaultValue;
		}
		
		if(obj instanceof java.math.BigDecimal){
			return ((java.math.BigDecimal)obj).floatValue();
		}
		if(obj instanceof java.math.BigInteger){
			return ((java.math.BigInteger)obj).floatValue();
		}
		if(obj instanceof java.lang.Integer){
			return ((java.lang.Integer)obj).floatValue();
		}
		if(obj instanceof java.lang.Long){
			return ((java.lang.Long)obj).floatValue();
		}
		if(obj instanceof java.lang.Double){
			return ((java.lang.Double)obj).floatValue();
		}
		if(obj instanceof java.lang.Byte){
			return ((java.lang.Byte)obj).floatValue();
		}
		System.out.println("class not know : getValue(Map  row, String key, int defaultValue) "+obj.getClass().getName()+" col Name "+key);
		return defaultValue;
	}
	
	public static String getValue(Map<?, ?>  row, String key, double defaultValue,String format){
		
		double value = getValue(row,key,defaultValue);
		java.text.DecimalFormat df =new java.text.DecimalFormat(format);
		return df.format(value);
	}
	
	public static String getValue(Map<?, ?> row, String key, String defaultValue) {
	    if ((key == null) || (row == null)) {
	      return defaultValue;
	    }

	    Object obj = getObjValue(row, key);
	    if (obj == null) {
	      return defaultValue;
	    }

	    if ((obj instanceof String)) {
	      return (String)obj;
	    }

	    if ((obj instanceof StringBuffer)) {
	      return ((StringBuffer)obj).toString();
	    }

	    if ((obj instanceof StringBuilder)) {
	      return ((StringBuilder)obj).toString();
	    }

	    if ((obj instanceof Character)) {
	      return ((Character)obj).toString();
	    }

	   

	    return defaultValue;
	  }
	
	public static java.util.Date getValue(Map<?, ?> row,String key){
		if(key == null || row == null){
			return null;
		}
		
		Object obj = getObjValue(row,key);
		if(obj == null){
			return null;
		}
		
		if(obj instanceof oracle.sql.TIMESTAMP){
			try {
				obj = ((oracle.sql.TIMESTAMP)obj).toJdbc();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   }
		
		if(obj instanceof java.sql.Date){
			return new java.util.Date(((java.sql.Date)obj).getTime());
		}
		
		if(obj instanceof java.sql.Timestamp){
			return new java.util.Date(((java.sql.Timestamp)obj).getTime());
		}
		
		if(obj instanceof java.sql.Time){
			return new java.util.Date(((java.sql.Time)obj).getTime());
		}
		
		
		
		if(obj instanceof java.util.Date){
			return new java.util.Date(((java.sql.Date)obj).getTime());
		}
		
		  //�����������Ƿ��� oracle.sql.TIMESTAMP ����ǰ������
		
		 
		
		System.out.println("class not know : getValue(Map  row, String key, int defaultValue) "+obj.getClass().getName()+" col Name "+key);

		return null;
	}
	
	public static String getDateValue(Map<?, ?> row,String key,String format){
		java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat(format);
		java.util.Date date =DbMapUtil.getValue(row, key);
		if(date == null)
		{	
			return "";
		}
		return sdf.format(date);
	}
	
}
