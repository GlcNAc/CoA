package prj.coa.exception;

public class ExceptionTest {
	public static void execute(String a) throws MyException {
		System.out.println("execute...");
		if ("true".equals(a)) {
			throw new MyException("参数不能为 true");
		}
	}

	public static void main(String[] args) throws MyException {

		try {
			execute("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
