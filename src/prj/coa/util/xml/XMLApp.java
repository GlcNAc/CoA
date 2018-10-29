/**
 * 
 */
package prj.coa.util.xml;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Administrator
 * 
 */
public class XMLApp {
	static File file = new File("src/sql.xml");

	public String getCmdInfo(String str) {
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(file);
			Element root = doc.getRootElement();
			Element foo;
			for (Iterator<?> it = root.elementIterator("command"); it.hasNext();) {
				foo = (Element) it.next();
				if (str.equals(foo.attributeValue("id"))) {
					return foo.attributeValue("cmd");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		XMLApp xmlApp = new XMLApp();
		System.out.println(xmlApp.getCmdInfo("select"));
		System.out.println(xmlApp.getCmdInfo("update"));
		System.out.println(xmlApp.getCmdInfo("delete"));
		
		Config config = new Config();
		config.load("src/sql.xml");
		List<Node> nodes = config.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println(nodes.get(i).getAttributeValue("id"));
		}
	}
}
