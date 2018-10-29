package prj.coa.test.groovy;

import java.io.File;

import org.junit.Test;

import prj.coa.groovy.InvokeGroovy;

public class GTest {
	
	@Test
	public void T(){
		InvokeGroovy ig = new InvokeGroovy();
		File groovyFile = new File("src/prj/coa/groovy/Test.groovy");
		ig.runGroovy(groovyFile);
	}
	
}
