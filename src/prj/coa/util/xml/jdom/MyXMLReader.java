package prj.coa.util.xml.jdom;

import java.io.File;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class MyXMLReader {

	public static void main(String arge[]) {
		long lasting = System.currentTimeMillis();
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File("src/test.xml"));
			Element foo = doc.getRootElement();
			List<?> allChildren = foo.getChildren();
			for (int i = 0; i < allChildren.size(); i++) {
				System.out.print("���ƺ���:"
						+ ((Element) allChildren.get(i)).getChild("no")
								.getText());
				System.out.println(" �����ַ:"
						+ ((Element) allChildren.get(i)).getChild("addr")
								.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("����ʱ�䣺" + (System.currentTimeMillis() - lasting)
				+ " ����");
	}
}
