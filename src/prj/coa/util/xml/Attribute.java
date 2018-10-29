package prj.coa.util.xml;

public class Attribute {
	private String name;
	private String value;

	public Attribute() {
	}

	public Attribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String key) {
		this.value = key;
	}

	public String toString() {
		return "name:" + this.name + " value:" + this.value;
	}
}