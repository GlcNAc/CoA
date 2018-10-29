package prj.coa.util.xml;

import prj.coa.util.collection.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
	private List<Attribute> attributes;
	private List<Node> childs;
	private String nodeName;
	private String nodeValue;

	public Node() {
		this.attributes = new ArrayList<Attribute>();
		this.childs = new ArrayList<Node>();
	}

	public synchronized boolean addAttribute(Attribute attribute) {
		if ((attribute.getName() == null) || (attribute.getName().trim().length() == 0)) {
			return false;
		}
		int count = this.attributes.size();
		for (int i = 0; i < count; ++i) {
			if (attribute.getName().equals(((Attribute) this.attributes.get(i)).getName())) {
				((Attribute) this.attributes.get(i)).setValue(attribute.getValue());
				return true;
			}
		}
		this.attributes.add(attribute);
		return true;
	}

	public synchronized Node getChildNode(String attrName, String attrValue) {
		if ((attrName == null) || (attrName.length() == 0) || (attrValue == null) || (attrValue.trim().length() == 0)) {
			return null;
		}
		for (Node node : this.childs) {
			String value = node.getAttributeValue(attrName);
			if (attrValue.equals(value)) {
				return node;
			}
		}
		return null;
	}

	public synchronized Node getChildNode(String tagName, String attrName, String attrValue) {
		if ((attrName == null) || (attrName.length() == 0) || (attrValue == null) || (attrValue.trim().length() == 0)
				|| (tagName == null) || (tagName.trim().length() == 0)) {
			return null;
		}
		for (Node node : this.childs) {
			if ((tagName == null) || (tagName.trim().length() == 0) || (node.getNodeName().equals(tagName))) {
				String value = node.getAttributeValue(attrName);
				if (attrValue.equals(value)) {
					return node;
				}
			}
		}
		return null;
	}

	public synchronized boolean addAttribute(String name, String value) {
		Attribute attr = new Attribute();
		attr.setName(name);
		attr.setValue(value);
		return addAttribute(attr);
	}

	public synchronized boolean removeAttribute(Attribute attribute) {
		int index = this.attributes.indexOf(attribute);
		if (index >= 0) {
			this.attributes.remove(index);
		}
		return true;
	}

	public synchronized boolean addChild(Node child) {
		this.childs.add(child);
		return true;
	}

	public synchronized boolean removeChild(Node child) {
		this.childs.remove(child);
		return true;
	}

	public synchronized Attribute getAttribute(String name) {
		Attribute attribute = new Attribute();
		int count = this.attributes.size();
		for (int i = 0; i < count; ++i) {
			Attribute tempAttribute = (Attribute) this.attributes.get(i);
			if (name.equals(tempAttribute.getName())) {
				attribute.setName(tempAttribute.getName());
				attribute.setValue(tempAttribute.getValue());
				break;
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

	public synchronized List<Attribute> getAttributes() {
		List<Attribute> copyAttributes = new ArrayList<Attribute>();
		Collections.copy(copyAttributes, this.attributes);
		return copyAttributes;
	}

	public synchronized void store(String seq, String name) {
		List<Node> childs = getChildNodes();
		if ((seq == null) || (seq.length() == 0)) {
			return;
		}

		if ((name == null) || (name.length() == 0)) {
			return;
		}

		String[] keys = seq.split("\\$");
		if (keys.length == 0) {
			return;
		}

		List<Node> nChilds = new ArrayList<Node>();
		Map<String, Node> mNodes = new HashMap<String, Node>();

		int count = childs.size();
		for (int i = 0; i < count; i++) {
			Node node = (Node) childs.get(i);
			if (node != null) {
				String value = node.getAttributeValue(name);
				if (value != null) {
					mNodes.put(value, node);
				}
			}
		}

		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			Node node = (Node) mNodes.get(key);
			if (node != null) {
				nChilds.add(node);
			}
		}

		this.childs = nChilds;
	}

	public synchronized List<Node> getChildNodes() {
		return getChildNodes(null);
	}

	public synchronized List<Node> getChildNodes(String tagName) {
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < this.childs.size(); ++i) {
			Node node = (Node) this.childs.get(i);
			if ((tagName == null) || (tagName.trim().length() == 0) || (node.getNodeName().equals(tagName))) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	public String getNodeName() {
		if (this.nodeName == null) {
			return "";
		}
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeValue() {
		return this.nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getNodeName());
		str.append(" attributes {");
		str.append(getAttributes());
		str.append("} childs {");
		str.append(getChildNodes());
		str.append("}");
		return str.toString();
	}
}