package gr.interamerican.bo2.samples;

/**
 * The Class ParameterizedFather.
 *
 * @param <T> the generic type
 */
public abstract class ParameterizedFather<T> {
	
	/**
	 * Foo.
	 *
	 * @param t the t
	 */
	@SuppressWarnings("unused")
	protected void foo(T t) {
		//EMPTY
	}
	
	/**
	 * Foobar.
	 *
	 * @param t the t
	 */
	@SuppressWarnings("unused")
	protected void foobar(T t) {
		//EMPTY
	}
	
	/**
	 * Bar.
	 */
	protected abstract void bar();
	
	/**
	 * Def.
	 */
	abstract void def();
	
	/**
	 * Fed.
	 */
	void fed() {
		//EMPTY
	}
}
