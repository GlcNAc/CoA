/**
 * 
 */
package prj.coa.test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

/**
 * @author Administrator
 * 
 */
public class TestA {
	public static void main(String[] args) throws SerialException,
			SQLException, UnsupportedEncodingException {
		// long start = System.currentTimeMillis();
		// Connection conn = DbConn.getOracleConnection();
		// System.out.println(conn);
		// long end = System.currentTimeMillis();
		// long time = end - start;
		// System.out.println(time);
		// // Connection conn1 = DbConn.getMSSQLConnection();
		// // System.out.println(conn1);
		// start = System.currentTimeMillis();
		// Connection conn2 = DbManager.getInstance().getConnection();
		// System.out.println(conn2);
		// end = System.currentTimeMillis();
		// time = end - start;
		// System.out.println(time);

		// String str1 = "500kV双信5465线(#234-#235塔)安龙5466线(#5-#6塔)升高改造";
		// String str2 = "500kV双信5465线（#234-#235塔）安龙5466线（#5-#6塔）升高改造";
		// str2 = StringUtils.ToDBC(str2);
		// System.out.println(str2);
		// System.out.println(str1.equals(str2));

		// String s1 = "1231dsdgasd的飒飒大";
		// Clob c = new SerialClob(s1.toCharArray());// String 转 clob
		// Blob b = new SerialBlob(s1.getBytes("GBK"));// String 转 blob
		// // Blob b = new SerialBlob(s1.getBytes());
		// String clobString = c.getSubString(1, (int) c.length());// clob 转
		// String
		// String blobString = new String(b.getBytes(1, (int) b.length()),
		// "GBK");// blob
		// // 转
		// // String
		// // String blobString = new String(b.getBytes(1, (int) b.length()));
		// System.out.println(c);
		// System.out.println(b);
		// System.out.println(clobString);
		// System.out.println(blobString);
		String str = "1234 43543     623 4235 5 4 645 6";
		System.out.println(str.replace(" ", ""));
	}
}
