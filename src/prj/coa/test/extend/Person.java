package prj.coa.test.extend;

public abstract class Person {
	public Person(String n) {
		name = n;
	}

	public abstract String getDescription();

	public String getName() {
		return name;
	}

	private String name;
}
