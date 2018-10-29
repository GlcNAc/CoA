package prj.coa.test;

import java.io.FileOutputStream;
import java.util.Properties;

public class TestObject {

	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		prop.put("name", "zs");
		prop.put("account", "zs1");
		prop.storeToXML(new FileOutputStream("d:\\text.xml"), "测试");
	}
}