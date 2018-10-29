package prj.coa.tools.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DbBase {

	public static final String SYSBASE = "SYSBASE";
	public static final String MYSQL = "MYSQL";
	public static final String MSSQL = "MSSQL";
	public static final String ORCLE = "ORCLE";
	public static final String UNKNOWN = "UNKNOWN";
	private String dbType;
	private final String configName;

	public DbBase(String configName) {
		this.configName = configName;
	}

	private final Map<String, List<Integer>> keyStore = Collections.synchronizedMap(new HashMap());

	public int doUpData(String sql) throws SQLException {
		return doUpData(sql, null, null);
	}

	/**
	 * 甯姪鏂囨。涓殑 PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEES
	 * SET SALARY = ? WHERE ID = ?"); pstmt.setBigDecimal(1, 153833.00)
	 * pstmt.setInt(2, 110592) 姝ゆ柟娉曢噰鐢⊿tring sql = "UPDATE EMPLOYEES SET SALARY =
	 * ? WHERE ID = ?"; Object obj [] = new Object[2]; obj [0] = new
	 * BigDecimal(153833.00); obj [1] = new Integer(110592); 鏁版嵁绫诲瀷涓庢暟鎹簱瀵瑰簲 濡傛灉obj[i]
	 * 涓虹┖鍒欎娇鐢� doUpDate(String sql,Object [] args,int [] sqlType) 姝ゆ柟娉曚笉鏀寔澶氱粨鏋滈泦
	 * 
	 * @param sql
	 *            璇彞
	 * @param args
	 *            鍙傛暟
	 * @return 杩斿洖褰卞搷琛屾暟
	 * @throws SQLException
	 */
	public int doUpData(String sql, Object[] args) throws SQLException {
		return doUpData(sql, args, null);
	}

	/**
	 * 鍙傝�� doUpDate(String sql,Object [] args) sqlType[i] 鎵�鍦ㄦ暟鎹簱涓槧灏勭殑鏁版嵁鏁版嵁绫诲瀷銆�
	 * 
	 * @param sql
	 * @param args
	 * @param sqlType
	 * @return
	 * @throws SQLException
	 */
	public int doUpData(String sql, Object[] args, int[] sqlType) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionFactory.getInstance(this.configName).getConnection();
			ps = con.prepareStatement(sql);
			mapPrepareDate(ps, args, sqlType);
			// ps.setClob(i, x);
			return ps.executeUpdate();
		} finally {
			DbUtil.free(null, ps, con);
		}
	}

	/**
	 * 杩斿洖List 琛岀敤Map鏉ュ瓨鍌� 缁撴瀯key 鏄〃瀛楁鍚嶅ぇ鍐欙紝value 鏄搴斿�糘bject 闇�瑕佽浆鎹€��
	 * 
	 * @param sql
	 *            sql 璇彞
	 * @param args
	 *            鍙傛暟
	 * @param sqlType
	 *            瀵瑰簲鐨勬暟鎹被鍨�
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doQuery(String sql, Object[] args, int[] sqlType) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		java.util.List<Map<String, Object>> table = new java.util.LinkedList<Map<String, Object>>();
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			ps = con.prepareStatement(sql);
			mapPrepareDate(ps, args, sqlType);
			rs = ps.executeQuery();
			java.sql.ResultSetMetaData metaDate = rs.getMetaData();
			while (rs.next()) {
				int colCount = metaDate.getColumnCount();
				Map<String, Object> row = new java.util.Hashtable<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaDate.getColumnName(i).toUpperCase();
					Object date = rs.getObject(i);
					if (date != null) {
						row.put(colName, date);
					}
				}
				table.add(row);
			}
			return table;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	/**
	 * 杩斿洖List 琛岀敤Map鏉ュ瓨鍌� 缁撴瀯key 鏄〃瀛楁鍚嶅ぇ鍐欙紝value 鏄搴斿�糘bject 闇�瑕佽浆鎹€��
	 * 
	 * @param sql
	 *            sql 璇彞
	 * @param args
	 *            鍙傛暟
	 * @param sqlType
	 *            瀵瑰簲鐨勬暟鎹被鍨�
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doQuery(String sql, Object[] args) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		java.util.List<Map<String, Object>> table = new java.util.LinkedList<Map<String, Object>>();
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			ps = con.prepareStatement(sql);
			mapPrepareDate(ps, args, null);
			rs = ps.executeQuery();
			java.sql.ResultSetMetaData metaDate = rs.getMetaData();
			while (rs.next()) {
				int colCount = metaDate.getColumnCount();

				Map<String, Object> row = new java.util.Hashtable<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaDate.getColumnName(i).toUpperCase();
					Object date = rs.getObject(i);
					if (date != null) {
						row.put(colName, date);
					}
				}
				table.add(row);
			}
			return table;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	public List<Map<String, Object>> doQuery(String sql) throws SQLException {
		return doQuery(sql, new Object[0]);
	}

	public int test(String sql) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
			}
			return i;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	public void doUpdateByTransaction(String sql, List<Object[]> args, int[] type) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// java.sql.Savepoint savepoint = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			con.setAutoCommit(false);
			// savepoint = con.setSavepoint();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < args.size(); i++) {
				mapPrepareDate(ps, args.get(i), type);
				ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	/**
	 * 鐢ㄤ簨鐗╄繘琛屾洿鏂�
	 * 
	 * @param sql
	 * @param args
	 * @param type
	 * @throws SQLException
	 */
	public void doUpdateByTransaction(List<String> sql, List<Object[]> args, List<int[]> type) throws SQLException {
		if (sql.size() != args.size() || sql.size() != type.size()) {
			throw new SQLException("sql length not equal args length or type length");
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// java.sql.Savepoint savepoint = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			con.setAutoCommit(false);
			// savepoint = con.setSavepoint();

			for (int i = 0; i < sql.size(); i++) {
				ps = con.prepareStatement(sql.get(i));

				mapPrepareDate(ps, args.get(i), type.get(i));
				ps.execute();
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷菘锟斤拷锟斤拷锟�
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getDbType() throws SQLException {
		if (dbType != null)
			return dbType;
		synchronized (this) {
			Connection con = null;
			try {
				con = ConnectionFactory.getInstance(configName).getConnection();
				java.sql.DatabaseMetaData metaData = con.getMetaData();
				String product = metaData.getDatabaseProductName();
				product = product.toUpperCase();
				if (product.indexOf("ORACLE") >= 0)
					return ORCLE;
				else if (product.indexOf("MICROSOFT") >= 0 && product.indexOf("SQL") >= 0)
					return MSSQL;
				else if (product.indexOf(MYSQL) >= 0)
					return MYSQL;
				else if (product.indexOf(SYSBASE) >= 0)
					return SYSBASE;
				else
					return UNKNOWN;
			} finally {
				DbUtil.free(null, null, con);
			}
		}
	}

	/**
	 * 妫�鏌ユ暟鎹簱琛ㄤ腑鏌愪釜瀛楁鏄惁瀛樺湪
	 * 
	 * @param tableName
	 * @param colName
	 * @return
	 * @throws SQLException
	 */
	public boolean colExist(String tableName, String colName) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			String dbType = getDbType();
			String sql = null;
			if (dbType.equals(ORCLE)) {
				sql = "select * from " + tableName + " where rownum < 1";
			} else if (dbType.equals(MSSQL)) {
				sql = "select top 1 * from " + tableName;
			}
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			java.sql.ResultSetMetaData metaDate = rs.getMetaData();
			int columnCount = metaDate.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String col = metaDate.getColumnName(i);
				if (col.equalsIgnoreCase(colName))
					return true;
			}
			return false;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	/**
	 * 灏唒s 浜巃rgs 鏄犲皠
	 * 
	 * @param ps
	 * @param args
	 * @param sqlType
	 * @throws SQLException
	 */
	private final void mapPrepareDate(PreparedStatement ps, Object[] args, int[] sqlType) throws SQLException {
		if (args == null)
			return;
		if (sqlType != null) {
			if (sqlType.length != args.length) {
				throw new SQLException("sqlType.length not equals args.length");
			}
		}
		int length = args.length;
		for (int i = 0; i < length; i++) {
			if (args[i] == null || "".equals(args[i])) {
				ps.setNull(i + 1, sqlType[i]);
			} else {
				if (args[i] instanceof java.util.Date) {
					ps.setObject(i + 1, new java.sql.Date(((java.util.Date) args[i]).getTime()));
				} else {
					ps.setObject(i + 1, args[i]);
				}
			}
		}
	}

	private String generateQuerySqlWithOutTypes(String table, String[] cols) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		if (cols == null || cols.length == 0) {
			sql.append("* ");
		} else {
			for (int i = 0; i < cols.length; i++) {
				if (cols[i] == null || cols[i].trim().length() == 0) {
					throw new NullPointerException("colName must greater than zero!");
				}
				if (i > 0) {
					sql.append(",");
				}
				sql.append(" ");
				sql.append(cols[i]);
			}
		}
		sql.append(" from ");
		sql.append(table);
		return sql.toString();
	}

	public int[] getTypes(String table, String[] cols) throws SQLException {

		if (cols == null || cols.length == 0) {
			return new int[0];
		}

		String sql = generateQuerySqlWithOutTypes(table, cols);
		sql += " where 1=-1";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			java.sql.ResultSetMetaData metaDate = rs.getMetaData();
			java.util.Map<String, Integer> typesMap = new java.util.HashMap<String, Integer>();
			int colCount = metaDate.getColumnCount();
			for (int i = 1; i <= colCount; i++) {
				String colName = metaDate.getColumnName(i).toUpperCase();
				int type = metaDate.getColumnType(i);
				typesMap.put(colName, type);
			}

			int[] types = new int[cols.length];
			for (int i = 0; i < cols.length; i++) {
				String cName = cols[i];
				if (cName == null) {
					throw new NullPointerException("Cols item must not null at " + i);
				}
				cName = cName.toUpperCase();
				Integer type = typesMap.get(cName);
				if (type == null) {
					throw new java.sql.SQLException("cols " + cName + " " + table + " not found!");
				}
				types[i] = type.intValue();
			}
			return types;
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	public String generateInsertSql(String table, String[] cols) throws SQLException {

		if (cols == null || cols.length == 0) {
			throw new java.lang.NullPointerException("cols length must greater than zero!");
		}

		// generateInsert
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(table);
		sql.append("(");
		for (int i = 0; i < cols.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append(cols[i]);
		}
		sql.append(")values(");
		for (int i = 0; i < cols.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append("?");
		}
		sql.append(")");
		return sql.toString();
	}

	public String generateInsertSql(String table, String[] cols, String seqCol, String seqName) throws SQLException {

		if (cols == null || cols.length == 0) {
			throw new java.lang.NullPointerException("cols length must greater than zero!");
		}

		// generateInsert
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(table);
		sql.append("(");
		sql.append(seqCol);
		for (int i = 0; i < cols.length; i++) {
			sql.append(" , ");
			sql.append(cols[i]);
		}
		sql.append(")values(");
		sql.append(seqName);
		sql.append(".nextval");
		for (int i = 0; i < cols.length; i++) {

			sql.append(" , ");

			sql.append("?");
		}
		sql.append(")");
		return sql.toString();
	}

	public int getUniqueId(String key) {
		if ((key == null) || (key.trim().length() == 0)) {
			throw new NullPointerException("璇锋眰鐨刱ey 涓嶈兘涓虹┖ 锛�");
		}
		try {
			return AllocateId.getInstance().getAllocateId(key.toLowerCase().trim(), configName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@SuppressWarnings({ "resource", "unused" })
	private synchronized void reloadId(String key) throws SQLException {
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
			List<Integer> values = this.keyStore.get(key);
			if (values == null) {
				values = new ArrayList<Integer>();
			}
			for (int i = 0; i < increase; i++) {
				Integer value = new Integer(nextvalue + i + 1);
				values.add(value);
			}
			this.keyStore.put(key, values);
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	private int tryFindMaxId(String pkey) throws SQLException {
		DbBase dbBase = new DbBase(configName);
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

	public boolean tableExist(String tableName) throws SQLException {
		return getTable(tableName).size() > 0;
	}

	public List<Map<String, Object>> getTable(String tableName) throws SQLException {
		List<Map<String, Object>> tbls = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			DatabaseMetaData meta = con.getMetaData();

			rs = meta.getTables(null, null, tableName.toUpperCase(), new String[] { "TABLE" });
			ResultSetMetaData metaDate = rs.getMetaData();
			while (rs.next()) {
				int colCount = metaDate.getColumnCount();
				Map<String, Object> row = new Hashtable<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaDate.getColumnName(i).toUpperCase();
					Object date = rs.getObject(i);
					if (date != null) {
						row.put(colName, date);
					}
				}
				tbls.add(row);
			}
		} finally {
			DbUtil.free(rs, ps, con);
		}
		return tbls;
	}

	public List<Map<String, Object>> getPrimaryKeys(String tableName) throws SQLException {
		List<Map<String, Object>> tbls = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(configName).getConnection();
			DatabaseMetaData meta = con.getMetaData();

			rs = meta.getPrimaryKeys(null, null, tableName.toUpperCase());
			ResultSetMetaData metaDate = rs.getMetaData();
			while (rs.next()) {
				int colCount = metaDate.getColumnCount();
				Map<String, Object> row = new Hashtable<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaDate.getColumnName(i).toUpperCase();
					Object date = rs.getObject(i);
					if (date != null) {
						row.put(colName, date);
					}
				}
				tbls.add(row);
			}
		} finally {
			DbUtil.free(rs, ps, con);
		}
		return tbls;
	}

	public void save(String tableName, List<Map<String, Object>> dta) throws SQLException {
		if ((tableName == null) || (dta == null) || (dta.size() == 0)) {
			throw new NullPointerException("tableName is null or dta is null");
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(this.configName).getConnection();

			String dbType = getDbType();

			String sql = null;

			if (dbType.equals("ORCLE"))
				sql = "select * from " + tableName + " where rownum < 1";
			else if (dbType.equals("MSSQL")) {
				sql = "select top 1 * from " + tableName;
			}

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into ");
			insertSql.append(tableName);
			insertSql.append("(");

			StringBuilder values = new StringBuilder();
			values.append("(");
			ResultSetMetaData metaDate = rs.getMetaData();

			int columnCount = metaDate.getColumnCount();

			int[] types = new int[columnCount];
			String[] cols = new String[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				String columneName = metaDate.getColumnName(i).toLowerCase();
				if (i > 1) {
					insertSql.append(",");
					values.append(",");
				}

				insertSql.append(columneName);
				values.append("?");

				types[(i - 1)] = metaDate.getColumnType(i);
				cols[(i - 1)] = columneName;
			}

			values.append(")");
			insertSql.append(")values").append(values);

			List<Object[]> args = new ArrayList<Object[]>();

			for (int i = 0; i < dta.size(); i++) {
				Object[] targs = new Object[cols.length];
				Map<String, Object> row = dta.get(i);
				for (int j = 0; j < cols.length; j++) {
					targs[j] = DbMapUtil.getObjValue(row, cols[j]);
				}
				args.add(targs);
			}

			doUpdateByTransaction(insertSql.toString(), args, types);
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}

	public void save(String tableName, Map<String, Object> dta) throws SQLException {
		if ((tableName == null) || (dta == null)) {
			throw new NullPointerException("tableName is null or dta is null");
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstance(this.configName).getConnection();

			String dbType = getDbType();

			String sql = null;

			if (dbType.equals("ORCLE"))
				sql = "select * from " + tableName + " where rownum < 1";
			else if (dbType.equals("MSSQL")) {
				sql = "select top 1 * from " + tableName;
			}

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into ");
			insertSql.append(tableName);
			insertSql.append("(");

			StringBuilder values = new StringBuilder();
			values.append("(");
			ResultSetMetaData metaDate = rs.getMetaData();

			int columnCount = metaDate.getColumnCount();

			int[] types = new int[columnCount];
			Object[] args = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				String columneName = metaDate.getColumnName(i).toLowerCase();
				if (i > 1) {
					insertSql.append(",");
					values.append(",");
				}

				insertSql.append(columneName);
				values.append("?");

				types[(i - 1)] = metaDate.getColumnType(i);
				args[(i - 1)] = DbMapUtil.getObjValue(dta, columneName);
			}

			values.append(")");
			insertSql.append(")values").append(values);

			doUpData(insertSql.toString(), args, types);
		} finally {
			DbUtil.free(rs, ps, con);
		}
	}
}
