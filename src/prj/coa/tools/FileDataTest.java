package prj.coa.tools;

import java.io.Serializable;
import java.util.Map;

public class FileDataTest implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 3961812838795455326L;
	
	private Map<String, Object> row ;

	public Map<String, Object> getRow() {
		return row;
	}

	public void setRow(Map<String, Object> row) {
		this.row = row;
	}
	
	
}
