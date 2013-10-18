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
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.impl.open.hibernate.RefreshMode;
import gr.interamerican.bo2.impl.open.hibernate.refreshmodes.JustGet;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.test.def.posamples.CompanyUser;
import gr.interamerican.bo2.test.def.samples.enums.Sex;
import gr.interamerican.bo2.test.scenarios.DeleteCompanyUser;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.io.Serializable;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestOneBasedEnumUserType {
	
	/**
	 * clears the test table
	 */
	private DeleteCompanyUser clear = new DeleteCompanyUser();
	
	/**
	 * enumUserType to test
	 */
	OneBasedEnumUserType enumUserType = new OneBasedEnumUserType();

	/**
	 * setup the test
	 * 
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Before
	public void setup() 
	throws DataException, LogicException, UnexpectedException {				
		Execute.transactional(clear);
	}
	
	/**
	 * Stores a company user.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testStoreCompanyUser() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				RefreshMode mode = 
					new RefreshMode(JustGet.INSTANCE, JustGet.INSTANCE, JustGet.INSTANCE);
				PersistenceWorker<CompanyUser> pw = 
					new GenericHibernatePersistenceWorker<CompanyUser>
					(CompanyUser.class,mode);
				pw.init(getProvider());
				pw.open();
				CompanyUser companyUser = Factory.create(CompanyUser.class);
				companyUser.setId(1L);		
				companyUser.setSex(Sex.MALE);
				companyUser = pw.store(companyUser);
				assertNotNull(companyUser.getSex());		
				assertEquals(Sex.MALE, companyUser.getSex());
				companyUser.setSex(Sex.FEMALE);
				companyUser = pw.update(companyUser);
				assertEquals(Sex.FEMALE, companyUser.getSex());
				pw.delete(companyUser);
				
			}
		}.execute();
		
		
	}
	
	
	/**
	 * tests Assemble
	 */
	@Test
	public void testAssemble(){
	    Cache cache = new Cache();
		String owner = "owner"; //$NON-NLS-1$
		enumUserType.assemble(cache, owner);
		assertEquals(cache,enumUserType.assemble(cache, owner));
	}
		
	/**
	 * tests Disassemble
	 */
	@Test
	public void testDisassemble(){
		Sex sex = Sex.FEMALE;
		assertEquals(sex,enumUserType.disassemble(sex));
	}
	
	/**
	 * tests has code
	 */
	@Test
	public void testHashCode(){
		String test = "test"; //$NON-NLS-1$
		enumUserType.hashCode(test);
	}
	
	/**
	 * test returned class when cache is registered,typedId is not null
	 */
	@Test
	public void testReturnedClass(){
		Properties properties = UtilityForBo2Test.getTestProperties();
		enumUserType.setParameterValues(properties);
		assertEquals(gr.interamerican.bo2.test.def.samples.enums.Sex.class,enumUserType.returnedClass());
	}
	
	
	/**
	 * tests ObjectToSQLString
	 */
	@Test
	public void testObjectToSQLString(){
		Sex sex = Sex.FEMALE;
		assertEquals("'FEMALE'",enumUserType.objectToSQLString(sex)); //$NON-NLS-1$
	}
	
	/**
	 * tests ObjectToSQLString
	 */
	@Test
	public void testFromXMLString(){
		Properties properties = UtilityForBo2Test.getTestProperties();
		enumUserType.setParameterValues(properties);
		String xmlValue = "FEMALE"; //$NON-NLS-1$
		enumUserType.fromXMLString(xmlValue);
	}
	
	/**
	 * cache to test
	 */
	public class Cache implements Serializable{

		/**
		 * uid.
		 */
		private static final long serialVersionUID = 1L;
	 //empty	
	}
	
}
