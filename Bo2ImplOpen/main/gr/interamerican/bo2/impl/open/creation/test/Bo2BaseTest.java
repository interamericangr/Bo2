package gr.interamerican.bo2.impl.open.creation.test;

import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoFetcher;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Base Test class for Bo2 in order to reset caches and fixtures.
 */
public abstract class Bo2BaseTest {

	/**
	 * Tear down.
	 */
	@BeforeClass
	@AfterClass
	public static void clear() {
		Factory.resetAllFixtures();
		PoFetcher.flushCaches();
		CacheRegistry.flushAllCacheContents();
	}
}