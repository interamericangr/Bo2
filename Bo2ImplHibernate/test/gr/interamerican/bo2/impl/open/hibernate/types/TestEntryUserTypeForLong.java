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
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.impl.open.hibernate.RefreshMode;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.GetAndRefresh;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.JustGet;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.scenarios.DeleteCompanyUser;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link EntryUserTypeForLong}.
 */
public class TestEntryUserTypeForLong {
	
	/**
	 * the name of the cache used by the EntryUserType on this test.
	 */
	public static final String CACHE_NAME = "cache"; //$NON-NLS-1$
	
	/**
	 * clears the test table
	 */
	DeleteCompanyUser clear = new DeleteCompanyUser();
	
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
	 * entryUserType to test
	 */
	EntryUserTypeForLong entryUserType= new EntryUserTypeForLong();
	
	/**
	 * ENTRY_USER_PROPERTIES_PATH
	 */
	private final String ENTRY_USER_PROPERTIES_PATH = 
		"/gr/interamerican/rsrc/hibernate/types/EntryUserTypeNumber.properties"; //$NON-NLS-1$
	
	/**
	 * Tests setup.
	 */
	@BeforeClass
	public static void setup() {
		String name = "longCache"; //$NON-NLS-1$
		Cache<?> cache = CacheRegistry.getRegisteredCache(name);
		if (cache==null) {
			Cache<Long> newCache = new CacheImpl<Long>();
			CacheRegistry.registerCache(name, newCache, Long.class);
		}
	}
	
	/**
	 * setup
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Before
	public void setupTests() throws DataException, LogicException, UnexpectedException {
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
	 * Stores a company user.
	 * 
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testStoreCompanyUser() 
	throws PoNotFoundException, DataException, LogicException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				RefreshMode mode = 
					new RefreshMode(GetAndRefresh.INSTANCE, JustGet.INSTANCE, GetAndRefresh.INSTANCE);
				PersistenceWorker<CompanyUser> pw = 
					new GenericHibernatePersistenceWorker<CompanyUser>
					(CompanyUser.class,mode);
				pw.init(getProvider());
				pw.open();
				CompanyUser companyUser = Factory.create(CompanyUser.class);
				companyUser.setId(1L);		
				companyUser.setCompanyRole(role1);
				companyUser = pw.store(companyUser);
				assertNotNull(companyUser.getCompanyRole());		
				assertEquals(role1.getCode(), companyUser.getCompanyRole().getCode());
				companyUser.setCompanyRole(role2);
				companyUser = pw.update(companyUser);
				assertEquals(role2.getCode(), companyUser.getCompanyRole().getCode());
				pw.delete(companyUser);
			}
		}.execute();
	}
	
	/**
	 * tests ObjectToSQLString
	 */
	@Test
	public void testObjectToSQLString(){
		entryUserType.objectToSQLString(role1);
	}
	
	
	/**
	 * tests toXMLString
	 */
	@Test
	public void testToXMLString(){
		
		Long code = 1l;
		assertEquals(code.toString(),entryUserType.toXMLString(role1));
	}
	
	
	/**
	 * tests ObjectToSQLString
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	@Test
	public void testFromXMLString(){
		
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache("longCache"); 
		
		TypedSelectable<Long> typed = new TypedSelectableImpl<Long>();
		typed.setCode(1L);
		typed.setTypeId(1000L);
		cache.put(typed);
		
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_PROPERTIES_PATH);
		entryUserType.setParameterValues(properties);
		
		TypedSelectable<Long> result = (TypedSelectable<Long>) entryUserType.fromXMLString("1");
		assertEquals((Long)1L, result.getCode());
	}
}
