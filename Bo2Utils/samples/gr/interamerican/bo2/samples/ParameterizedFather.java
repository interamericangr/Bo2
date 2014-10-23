package gr.interamerican.bo2.samples;

/**
 * 
 */
public abstract class ParameterizedFather<T> {
	
	protected void foo(T t) {
		//EMPTY
	}
	
	protected void foobar(T t) {
		//EMPTY
	}
	
	protected abstract void bar();
	
	abstract void def();
	
	void fed() {
		//EMPTY
	}
}
