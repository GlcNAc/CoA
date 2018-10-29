package prj.coa.util.xml.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
public class MyXMLReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long lasting =System.currentTimeMillis(); 
		try {
			File f=new File("src/test.xml"); 
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f); 
			NodeList nl = doc.getElementsByTagName("value"); 
			for (int i=0;i<nl.getLength();i++){ 
				System.out.print("锟斤拷锟狡猴拷锟斤拷:" + doc.getElementsByTagName("no").item(i).getFirstChild().getNodeValue()); 
				System.out.println(" 锟斤拷锟斤拷锟街�" + doc.getElementsByTagName("addr").item(i).getFirstChild().getNodeValue()); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("锟斤拷锟斤拷时锟戒："+(System.currentTimeMillis() - lasting)+" 锟斤拷锟斤拷"); 
	}
}

