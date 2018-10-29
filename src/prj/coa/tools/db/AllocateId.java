/**
  * 鏂囦欢鍚嶏細AllocateId.java
  * 鐗堟湰淇℃伅锛歏ersion 1.0
  * 鍒涘缓浜� 璧靛弻
  * 鏃ユ湡锛�2013-3-4涓嬪崍04:19:44
  * Copyright talkweb.com.cn Corporation 2013
  * 鎵�灞為儴闂�  ECM浜嬩笟閮�
  * 鐗堟潈鎵�鏈�  鏉窞鎱ф櫤鐢靛瓙绉戞妧鏈夐檺鍏徃
  *
  */
package prj.coa.tools.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AllocateId 
{
  private static AllocateId allocateId = null;
  private static final Object lockObject = new Object();

 

private Map<String, List<Integer>> keyStore = Collections.synchronizedMap(new HashMap());

  private AllocateId() throws SQLException {
	
    DbBase dbBase = new DbBase("new");
    if (!dbBase.tableExist("DB_UNIQUEVALUE")) {
      dbBase.doUpData(getCreateTableSql());
    } else if (!dbBase.colExist("DB_UNIQUEVALUE", "increase")) {
      String sql = "alter table DB_UNIQUEVALUE add increase int default 1";
      dbBase.doUpData(sql);
    }
  }

  public static AllocateId getInstance()
    throws SQLException
  {
    if (allocateId == null) {
      synchronized (lockObject) {
        if (allocateId == null) {
          allocateId = new AllocateId();
        }
      }
    }
    return allocateId;
  }

  public synchronized int getAllocateId(String key,String configname)
    throws SQLException
  {
    if ((key == null) || (key.trim().length() == 0)) {
      throw new NullPointerException(
        "key must greater than zero");
    }

    List<?> values = (List<?>)this.keyStore.get(key);
    if ((values != null) && (values.size() > 0)) {
      return ((Integer)values.remove(0)).intValue();
    }
    try
    {
      reloadId(key,configname);
    } catch (SQLException sql) {
      SQLException s = new SQLException("妫�鏌ユ湇鍔″櫒鏁版嵁搴撴槸鍚﹀瓨鍦ㄨ琛� 锛�" + getCreateTableSql());
      s.initCause(sql);
      throw s;
    }

    return getAllocateId(key,configname);
  }

  private String getCreateTableSql()
  {
    StringBuffer createSql = new StringBuffer();
    createSql.append("create table DB_UNIQUEVALUE ");
    createSql.append("( ");
    createSql.append(" PKEY  VARCHAR2(1000) not null, ");
    createSql.append("  VALUE int not null, ");
    createSql.append("  increase int ");
    createSql.append(") ");
    return createSql.toString();
  }

  @SuppressWarnings("resource")
private synchronized void reloadId(String key,String configName)
    throws SQLException
  {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    try {
      con = ConnectionFactory.getInstance(configName).getConnection();

      int nextvalue = 1;
      int increase = 100;
      String sql = "select PKEY,VALUE,increase from DB_UNIQUEVALUE where PKEY =?";
      ps = con.prepareStatement(sql);
      ps.setString(1, key);
      rs = ps.executeQuery();
      if (rs.next()) {
        nextvalue = rs.getInt("VALUE");
        increase = rs.getInt("increase");
        DbUtil.free(rs, ps, null);
      } else {
        sql = "insert into DB_UNIQUEVALUE(pkey,value,increase) values(?,?,?)";
        nextvalue = tryFindMaxId(key);
        ps = con.prepareStatement(sql);
        ps.setString(1, key);
        ps.setInt(2, nextvalue);
        ps.setInt(3, increase);
        ps.execute();
        DbUtil.free(rs, ps, null);
      }

      if (nextvalue <= 0) {
        nextvalue = 1;
      }
      if (increase <= 0) {
        increase = 100;
      }
      sql = "update DB_UNIQUEVALUE set value =? ,increase =? where pkey = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, nextvalue + increase);
      ps.setInt(2, increase);

      ps.setString(3, key);
      ps.execute();
      con.commit();
      List<Integer> values = (List<Integer>)this.keyStore.get(key);
      if (values == null) {
        values = new ArrayList<Integer>();
      }
      for (int i = 0; i < increase; i++) {
        Integer value = new Integer(nextvalue + i + 1);
        values.add(value);
      }
      this.keyStore.put(key, values);
    }
    finally {
      DbUtil.free(rs, ps, con);
    }
  }

  private int tryFindMaxId(String pkey) throws SQLException {
    DbBase dbBase = new DbBase("new");
    if (dbBase.tableExist(pkey)) {
      List<Map<String, Object>> primarys = dbBase.getPrimaryKeys(pkey);
      if (primarys.size() == 1) {
        String columnName = DbMapUtil.getValue(primarys.get(0), "column_name", "");
        if (columnName.trim().length() > 0) {
          String sql = "select max(" + columnName + ") maxValue from " + pkey;
          List<Map<String, Object>> maxValue = dbBase.doQuery(sql);
          if (maxValue.size() > 0) {
            return DbMapUtil.getValue(primarys.get(0), "maxValue", 1);
          }
          return 1;
        }

        return 1;
      }

      return 1;
    }

    return 1;
  }
}