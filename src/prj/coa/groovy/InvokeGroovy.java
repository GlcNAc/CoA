package prj.coa.groovy;

import java.io.File;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class InvokeGroovy {
	//static Logger logger = Logger.getLogger(InvokeGroovy.class); 
	
	public void runGroovy(File groovyFile){
		runGroovy(groovyFile, "run", null);
	}
	
	public void runGroovy(File groovyFile, String method){
		runGroovy(groovyFile, method, null);
	}
	
	public void runGroovy(File groovyFile, String method, Object[] args){
		ClassLoader cl = new InvokeGroovy().getClass().getClassLoader();
		GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
		try {
			Class<?> groovyClass = groovyCl.parseClass(groovyFile);
			GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
			groovyObject.invokeMethod(method, args);
			groovyCl.close();
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) {
		InvokeGroovy ig = new InvokeGroovy();
		File groovyFile = new File("src/prj/coa/groovy/Foo.groovy");
		ig.runGroovy(groovyFile);
		//logger.info("11");
		ig.showUrl();
	}
	
	public void showUrl() {
		System.out.println(this.getClass().getResource("").getPath());
        System.out.println(System.getProperty("user.dir"));
        File classFile = new File(this.getClass().getResource("/").getPath());
        System.out.println(classFile.getParent());
        File groovyFile = new File(classFile.getParent() + File.separator + "groovys" + File.separator + "Prop.groovy" );
        ClassLoader cl = new InvokeGroovy().getClass().getClassLoader();
		GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
		try {
			Class<?> groovyClass = groovyCl.parseClass(groovyFile);
			GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
			System.out.println(groovyObject.getProperty("P_TEST"));
			groovyCl.close();
		} catch (Exception e) {
		}
	}
}
