package prj.coa.test;

import java.lang.reflect.Method;

public class DLTest {
	public static void main(String[] args) {
		String cl = "prj.coa.test.MyTest";
		try {
			Class<?> c = Class.forName(cl);
			Method m = c.getDeclaredMethod("getCountNum", new String().getClass());
			System.out.println(m.invoke(c.newInstance(), new String("提取33字符串中55数字")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
