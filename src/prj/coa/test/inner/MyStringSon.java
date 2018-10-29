package prj.coa.test.inner;

public class MyStringSon {
	public class Str1 extends MyString {
		@Override
		public String getString() {
			return super.getString();
		}
	}

	public class Str2 extends MyStringT {
		@Override
		public String getString() {
			return super.getString();
		}
	}

	public String getStr1() {
		return new Str1().getString();
	}

	public String getStr2() {
		return new Str2().getString();
	}

	public String getStrString() {
		String str = "str is ";
		str += getStr1();
		str += " ";
		str += getStr2();
		return str;
	}

	public static void main(String[] args) {
		MyStringSon mss = new MyStringSon();
		System.out.println(mss.getStrString());
	}
}
