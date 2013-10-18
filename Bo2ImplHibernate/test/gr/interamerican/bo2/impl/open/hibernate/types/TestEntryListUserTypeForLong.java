/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.hibernate.types;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.scenarios.DeleteCompanyUser;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for {@link EntryListUserType}.
 */
public class TestEntryListUserTypeForLong {
	
	/**
	 * Cache name.
	 */
	String CACHE_NAME = "cache"; //$NON-NLS-1$
	
	/**
	 * User id.
	 */
	Long id = 1L;
	
	/**
	 * sample role
	 */
	CompanyRole role1 = new CompanyRole(1L, null, 1L, "user"); //$NON-NLS-1$
	
	/**
	 * sample role
	 */
	CompanyRole role2 = new CompanyRole(1L, null, 2L, "admin"); //$NON-NLS-1$
	
	/**
	 * sample role
	 */
	CompanyRole role3 = new CompanyRole(1L, null, 3L, "guest"); //$NON-NLS-1$
	
	/**
	 * clears the test table
	 */
	DeleteCompanyUser clear = new DeleteCompanyUser();
	
	/**
	 * setup test.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Before
	public void setupTests() throws UnexpectedException, DataException, LogicException {
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
		if (cache==null) {
			cache = new CacheImpl<Long>();			
			CacheRegistry.registerCache(CACHE_NAME, cache, Long.class);
		} else {
			cache.clear();
		}
		cache.put(role1);
		cache.put(role2);
		cache.put(role3);
		Execute.transactional(clear);
	}
	
	/**
	 * Tests storing an entity with this usertype.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testStore() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
				CompanyUser sample = Factory.create(CompanyUser.class);
				sample.setId(id);
				List<CompanyRole> roles = cache.getSubCacheAsList(1L, null); 
				sample.setAdditionalRoles(roles);
				PersistenceWorker<CompanyUser> pw = openPw(CompanyUser.class);
				sample = pw.store(sample);
				Assert.assertEquals(3, sample.getAdditionalRoles().size());
				pw.delete(sample);
			}
		}.execute();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParseCode(){
		
		String code = "100";
		EntryListUserTypeForLong userType = new EntryListUserTypeForLong();
		Long actual = userType.parseCode(code);
		assertEquals((Long)100l,actual);
		
	}

}
