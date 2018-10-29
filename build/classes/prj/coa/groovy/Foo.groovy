package prj.coa.groovy

class Foo implements IFoo {

	@Override
	public Object run(Object foo) {
		println 'Hello World!' 
		x = 123 
		foo * 10 
	}

}
