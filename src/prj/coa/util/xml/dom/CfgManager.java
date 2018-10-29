package prj.coa.util.xml.dom;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CfgManager {
	private static final String CFG_NODE_KEY = "name";

	public static CfgNode readConfig(File file) {
		try {
			Map<String, CfgNode> childsMap = new HashMap<String, CfgNode>();
			CfgNode rootConfig = new CfgNode();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			int len = nodes.getLength();
			for (int i = 0; i < len; i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == 3) {
					continue;
				}
				CfgNode childCfgNode = parseNode(node);
				childsMap.put(childCfgNode.getNodename(), childCfgNode);
			}
			rootConfig.setChildsMap(childsMap);
			rootConfig.setNodename(root.getNodeName());
			return rootConfig;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * ��������������
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: ���
	 * @version: 2013��12��3�� ����4:07:54 �������� ECM��ҵ�� ��Ȩ���� ���ݻ��ǵ��ӿƼ����޹�˾
	 */
	public static CfgNode parseNode(Node node) {
		CfgNode cfgNode = new CfgNode();
		Map<String, CfgNode> atrrs = new HashMap<String, CfgNode>();
		org.w3c.dom.NamedNodeMap atrrMap = node.getAttributes();
		int atrrLen = atrrMap.getLength();
		String key = "";
		for (int j = 0; j < atrrLen; j++) {
			Node atrrNode = atrrMap.item(j);
			if (atrrNode.getNodeType() == 3) {
				continue;
			}
			String atrrname = atrrNode.getNodeName();
			if (atrrNode.getNodeName().equalsIgnoreCase(CFG_NODE_KEY)) {
				key = atrrNode.getNodeValue();
			}
			String atrrvalue = atrrNode.getNodeValue();

			CfgNode atrrCfgNode = new CfgNode();
			atrrCfgNode.setNodename(atrrname);
			atrrCfgNode.setNodevalue(atrrvalue);
			atrrs.put(atrrname, atrrCfgNode);
		}
		cfgNode.setAttributes(atrrs);

		Map<String, CfgNode> childsMap = new HashMap<String, CfgNode>();
		NodeList childs = node.getChildNodes();
		int childsLen = childs.getLength();
		for (int j = 0; j < childsLen; j++) {
			Node childNode = childs.item(j);
			if (childNode.getNodeType() == 3) {
				continue;
			}
			CfgNode child = parseNode(childNode);
			childsMap.put(child.getNodename(), child);
		}
		cfgNode.setChildsMap(childsMap);

		cfgNode.setNodename(key);

		return cfgNode;
	}
}
