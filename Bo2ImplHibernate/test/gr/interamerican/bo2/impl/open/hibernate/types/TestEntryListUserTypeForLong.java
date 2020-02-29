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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;


/**
 * Unit tests for {@link EntryListUserType}.
 */
public class TestEntryListUserTypeForLong extends BaseCompanyUserTest {

	/**
	 * Tests storing an entity with this usertype.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testStore() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
				CompanyUser sample = Factory.create(CompanyUser.class);
				sample.setId(1L);
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
	 * Test parse code.
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