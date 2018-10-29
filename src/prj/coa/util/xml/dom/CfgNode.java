package prj.coa.util.xml.dom;

import java.util.Map;

public class CfgNode {
	private String nodename;
	private String nodevalue;
	private Map<String, CfgNode> attributes;
	private Map<String, CfgNode> childsMap;

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getNodevalue() {
		return nodevalue;
	}

	public void setNodevalue(String nodevalue) {
		this.nodevalue = nodevalue;
	}

	public Map<String, CfgNode> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, CfgNode> attributes) {
		this.attributes = attributes;
	}

	public Map<String, CfgNode> getChildsMap() {
		return childsMap;
	}

	public void setChildsMap(Map<String, CfgNode> childsMap) {
		this.childsMap = childsMap;
	}


}
