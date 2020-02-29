package gr.interamerican.bo2;


import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;

import org.junit.Test;


/**
 * Initialization class.
 */
public class Bo2MavenTestEnvironmentInitialization {

	/** for test. */
	public static boolean initialized = false;


	static {
		CacheRegistry.registerCache("cache", new CacheImpl<Long>(), Long.class); //$NON-NLS-1$
		initialized = true;
	}

	/**
	 * Empty test used in order to load the class from the test suite.
	 */
	@Test
	public void testDummy() {/*empty*/}

}
