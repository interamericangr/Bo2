package gr.interamerican.bo2;


import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;

import java.util.Locale;

import org.junit.Test;


/**
 * Initialization class.
 */
public class Bo2MavenTestEnvironmentInitialization {
	
	/**
	 * test cache
	 */
	private static Cache<Long> dummy;
	
	static {
		try {
			dummy = new CacheImpl<Long>();
			CacheRegistry.registerCache("cache", dummy, Long.class); //$NON-NLS-1$
			
			
		} catch (RuntimeException e) {
			/* if this cache exists, then it is no problem, continue */
		}
		Locale.setDefault(new Locale("el", "GR")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Empty test used in order to load the class from the test suite.
	 */
	@Test
	public void testDummy() {/*empty*/}
	


}
