package gr.interamerican.bo2;


import org.junit.Test;


/**
 * Initialization class.
 */
public class Bo2MavenTestEnvironmentInitialization {
	
	/**
	 * for test
	 */
	public static boolean initialized = false;
	
	static {
		initialized = true;
	}
	
	/**
	 * Empty test used in order to load the class from the test suite.
	 */
	@Test
	public void testDummy() {/*empty*/}

}
