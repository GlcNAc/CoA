package prj.coa.test.transients;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoggingInfoTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LoggingInfo logInfo = new LoggingInfo("MIKE", "MECHANICS");
		System.out.println(logInfo.toString());
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("logInfo.out"));
			o.writeObject(logInfo);
			o.close();
		} catch (Exception e) {
			// deal with exception
		}

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("logInfo.out"));
			LoggingInfo logInfo1 = (LoggingInfo) in.readObject();
			System.out.println(logInfo1.toString());
		} catch (Exception e) {// deal with exception}
		}
	}
}
