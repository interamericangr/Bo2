package gr.interamerican.bo2.utils.meta.ext;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;

import org.junit.Before;

/**
 * Base test case with cache registration fixture.
 */
public abstract class AbstractCacheRelatedTest {
	
	/**
	 * Cache name.
	 */
	protected static final String TEST_CACHE_NAME = "TEST_CACHE"; //$NON-NLS-1$
	
	/**
	 * cache registration fixture.
	 */
	@Before
	public void before() {
		if(CacheRegistry.getRegisteredCache(TEST_CACHE_NAME)!=null) {
			CacheRegistry.getRegisteredCache(TEST_CACHE_NAME).clear();
		} else {
			CacheRegistry.registerCache(TEST_CACHE_NAME, new CacheImpl<Long>(), Long.class);
		}
	}
	
	/**
	 * @return Returns the test cache
	 */
	protected Cache<Long> cache() {
		return CacheRegistry.<Long>getRegisteredCache(TEST_CACHE_NAME);
	}

}
