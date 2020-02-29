package gr.interamerican.bo2.impl.open.hibernate.types;

import org.junit.Before;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.scenarios.DeleteCompanyUser;

/**
 * Base Class for tests that work with a {@link CompanyUser}.
 */
public abstract class BaseCompanyUserTest {

	/**
	 * Cache name.
	 */
	String CACHE_NAME = "cache"; //$NON-NLS-1$

	/** sample role. */
	CompanyRole role1 = new CompanyRole(1L, null, 1L, "user"); //$NON-NLS-1$

	/** sample role. */
	CompanyRole role2 = new CompanyRole(1L, null, 2L, "admin"); //$NON-NLS-1$

	/** sample role. */
	CompanyRole role3 = new CompanyRole(1L, null, 3L, "guest"); //$NON-NLS-1$

	/**
	 * setup test.
	 *
	 * @throws UnexpectedException
	 *             the unexpected exception
	 * @throws DataException
	 *             the data exception
	 * @throws LogicException
	 *             the logic exception
	 */
	@Before
	public void setupTests() throws UnexpectedException, DataException, LogicException {
		Execute.transactional(new DeleteCompanyUser());
		Cache<Long> cache = CacheRegistry.<Long> getRegisteredCache(CACHE_NAME);
		if (cache == null) {
			cache = new CacheImpl<Long>();
			CacheRegistry.registerCache(CACHE_NAME, cache, Long.class);
		} else {
			cache.clear();
		}
		cache.put(role1);
		cache.put(role2);
		cache.put(role3);
	}
}