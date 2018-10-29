package prj.coa.test.extend;

public class Student extends Person {
	/**
	 * @param n
	 *            the student's name
	 * @param m
	 *            the student's major
	 */
	public Student(String n, String m) {
		// pass n to superclass constructor
		super(n);
		setMajor(m);
	}

	@Override
	public String getDescription() {
		return null;
	}


	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}


	private String major;
	
}
