package prj.coa.util.xml.dom4j;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MyXMLReader {
	public static void main(String arge[]) {
		long lasting = System.currentTimeMillis();
		try {
			File f = new File("src/test.xml");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element foo;
			for (Iterator<?> i = root.elementIterator("value"); i.hasNext();) {
				foo = (Element) i.next();
				System.out.print("���ƺ���:" + foo.elementText("no"));
				System.out.println(" �����ַ:" + foo.elementText("addr"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("����ʱ�䣺" + (System.currentTimeMillis() - lasting)
				+ " ����");
	}
}
