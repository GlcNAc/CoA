package prj.coa.util.xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import prj.coa.util.lang.ParseUtil;

public class Config {
	private final List<Node> childsList;
	private final List<Attribute> attributes;
	private String rootNodeName;

	public Config() {
		this.childsList = new ArrayList<Node>();
		this.attributes = new ArrayList<Attribute>();
		this.rootNodeName = "tables";
	}

	public synchronized boolean addAttribute(Attribute attribute) {
		int count = this.attributes.size();
		for (int i = 0; i < count; ++i) {
			if (attribute.getName().equals(this.attributes.get(i).getName())) {
				this.attributes.get(i).setValue(attribute.getValue());
				return true;
			}
		}
		this.attributes.add(attribute);
		return true;
	}

	public synchronized Attribute getAttribute(String name) {
		Attribute attribute = new Attribute();
		int count = this.attributes.size();
		for (int i = 0; i < count; ++i) {
			Attribute tempAttribute = this.attributes.get(i);
			if (name.equals(tempAttribute.getName())) {
				attribute.setName(tempAttribute.getName());
				attribute.setValue(tempAttribute.getValue());
			}
		}
		return attribute;
	}

	public synchronized String getAttributeValue(String name) {
		if (name == null) {
			return null;
		}
		Attribute attribute = getAttribute(name);
		if (attribute != null) {
			return attribute.getValue();
		}
		return null;
	}

	public synchronized boolean addNode(Node node, String attrName) {
		int index = this.childsList.indexOf(node);
		if (index >= 0)
			this.childsList.set(index, node);
		else {
			this.childsList.add(node);
		}
		return true;
	}

	public synchronized boolean removeNode(Node node) {
		int index = this.childsList.indexOf(node);
		if (index >= 0) {
			this.childsList.remove(index);
		}
		return true;
	}

	public synchronized boolean removeNodeById(String attrName, String attrValue) {
		int index = findNode(getNode(attrName, attrValue), attrName);
		if (index >= 0) {
			this.childsList.remove(index);
		}
		return true;
	}

	public synchronized Node getNode(String attrName, String attrValue) {
		if ((attrName == null) || (attrName.length() == 0) || (attrValue == null) || (attrValue.trim().length() == 0)) {
			return null;
		}
		for (Node node : this.childsList) {
			String value = node.getAttributeValue(attrName);
			if (attrValue.equals(value)) {
				return node;
			}
		}
		return null;
	}

	public synchronized List<Node> getNodes() {
		return getNodes(null);
	}

	public synchronized List<Node> getNodes(String tagName) {
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < this.childsList.size(); ++i) {
			Node node = this.childsList.get(i);
			if ((tagName == null) || (tagName.trim().length() == 0) || (node.getNodeName().equals(tagName))) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	public synchronized boolean load(InputStream inStream) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(inStream);

			return parseXml(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean load(String path) {
		if ((path == null) || (path.trim().length() == 0)) {
			return false;
		}
		FileInputStream fInputStream = null;
		try {
			fInputStream = new FileInputStream(path);
			return load(fInputStream);
		} catch (IOException ioe) {
			return false;
		} finally {
			if (fInputStream != null)
				try {
					fInputStream.close();
				} catch (IOException localIOException3) {
				}
		}
	}

	private boolean parseXml(Document document) {
		Element root = document.getDocumentElement();
		this.rootNodeName = root.getNodeName();
		List<Attribute> rootAttribute = parseAttribute(root);
		for (int i = 0; i < rootAttribute.size(); ++i) {
			addAttribute((Attribute) rootAttribute.get(i));
		}
		NodeList childNodes = root.getChildNodes();
		int childCount = childNodes.getLength();
		for (int i = 0; i < childCount; ++i) {
			org.w3c.dom.Node childNode = childNodes.item(i);

			Node node = parseNode(childNode);
			if (node != null)
				addNode(node, null);
		}
		return true;
	}

	private Node parseNode(org.w3c.dom.Node domNode) {
		if (domNode.getNodeType() == 3) {
			return null;
		}
		Node node = new Node();
		List<Attribute> attributes = parseAttribute(domNode);
		node.setNodeName(domNode.getNodeName());
		node.setNodeValue(domNode.getTextContent());
		int count = attributes.size();
		for (int i = 0; i < count; ++i) {
			Attribute tempAttribute = (Attribute) attributes.get(i);
			node.addAttribute(tempAttribute);
		}

		int len = domNode.getChildNodes().getLength();
		for (int j = 0; j < len; ++j) {
			Node child = parseNode(domNode.getChildNodes().item(j));
			if (child != null)
				node.addChild(child);
		}
		return node;
	}

	private List<Attribute> parseAttribute(org.w3c.dom.Node domNode) {
		List<Attribute> attList = new ArrayList<Attribute>();
		int count = (domNode.getAttributes() == null) ? 0 : domNode.getAttributes().getLength();
		for (int j = 0; (domNode.hasAttributes()) && (j < count); ++j) {
			Attribute attribute = new Attribute();
			org.w3c.dom.Node tempNode = domNode.getAttributes().item(j);
			attribute.setName(tempNode.getNodeName());
			attribute.setValue(tempNode.getNodeValue());
			attList.add(attribute);
		}
		return attList;
	}

	public boolean save() {
		return false;
	}

	public synchronized boolean save(String path) {
		Document doc;
		FileOutputStream outputStream = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			doc = dbf.newDocumentBuilder().newDocument();
			Element root = doc.createElement(this.rootNodeName);
			for (Node node : this.childsList) {
				appendChildNode(doc, root, node);
			}
			doc.appendChild(root);
			doc.normalize();
		} catch (ParserConfigurationException localParserConfigurationException) {
			return false;
		} finally {
			if (outputStream != null)
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException localIOException3) {
				}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getNodes());
		return str.toString();
	}

	private boolean appendAttributs(Element docNode, Node node) {
		List<Attribute> attributs = node.getAttributes();
		for (Attribute attr : attributs) {
			String name = attr.getName();
			String value = attr.getValue();
			if ((name == null) || (name.trim().length() <= 0) || (value == null) || (value.trim().length() <= 0)
					|| (value.trim().equals("null")))
				continue;
			docNode.setAttribute(name.trim(), value);
		}

		return true;
	}

	private void appendChildNode(Document doc, Element element, Node node) {
		if (node.getNodeName() == null) {
			return;
		}
		if (node.getNodeName().equals("#comment")) {
			return;
		}

		Element docNode = doc.createElement(node.getNodeName());

		appendAttributs(docNode, node);
		String nodeValue = node.getNodeValue();
		if ((nodeValue != null) && (nodeValue.trim().length() > 0)) {
			docNode.appendChild(doc.createTextNode(nodeValue.trim()));
		}
		element.appendChild(docNode);
		List<Node> childNodes = node.getChildNodes();
		for (Node n : childNodes)
			appendChildNode(doc, docNode, n);
	}

	private int findNode(Node node, String attrName) {
		if ((attrName == null) || (attrName.trim().length() == 0)) {
			return -1;
		}
		int count = this.childsList.size();
		for (int i = 0; i < count; ++i) {
			Node child = this.childsList.get(i);
			String oldValue = ParseUtil.convert2Null(child.getAttributeValue(attrName), "");
			String value = ParseUtil.convert2Null(node.getAttributeValue(attrName), "");
			if (oldValue.equals(value)) {
				return i;
			}
		}

		return -1;
	}

	public static void main(String[] args) {
	}
}