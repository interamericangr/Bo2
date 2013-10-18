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
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.samples.implopen.entities.CompanyRole;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class TestEntryUserType {

	/**
	 * ENTRY_USER_PROPERTIES_PATH
	 */
	private final String ENTRY_USER_PROPERTIES_PATH = 
		"/gr/interamerican/rsrc/hibernate/types/EntryUser.properties"; //$NON-NLS-1$

	
	/**
	 * ENTRY_USER_TYPE_PROPERTIES_PATH
	 */
	private final String ENTRY_USER_TYPE_PROPERTIES_PATH = 
		"/gr/interamerican/rsrc/hibernate/types/EntryUserType.properties"; //$NON-NLS-1$

	
	/**
	 * implementation to test
	 */
	EntryUserTypeImpl userType = new EntryUserTypeImpl();
	
	
	/**
	 * tests Disassemble
	 */
	@Test
	public void testDisassemble(){
		CompanyRole cp = new CompanyRole(1L, 1L, 1L, "name"); //$NON-NLS-1$
		assertEquals(cp,userType.disassemble(cp));
	}
	
	/**
	 * Tests setup.
	 */
	@BeforeClass
	public static void setup() {
		String name = "intCache"; //$NON-NLS-1$
		Cache<?> cache = CacheRegistry.getRegisteredCache(name);
		if (cache==null) {
			Cache<Integer> newCache = new CacheImpl<Integer>();
			CacheRegistry.registerCache(name, newCache, Integer.class);
		}
	}
	
	/**
	 * test setParameterValues when cache has not been registered
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testSetParameterValues_withoutCache(){
		Properties p = new Properties();
		p.setProperty("cacheName", "noCache");
		userType.setParameterValues(p);
	}
	
	/**
	 * tests setParameters when cache is registered but entryTypeId is null
	 */	
	@Test(expected = RuntimeException.class)
	public void testSetParameterValues_withCache(){
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_PROPERTIES_PATH);
		userType.setParameterValues(properties);
	}
	
	/**
	 * test returned class when cache is registered,typedId is not null
	 */
	@Test
	public void testReturnedClass(){
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_TYPE_PROPERTIES_PATH);
		userType.setParameterValues(properties);
		assertEquals(gr.interamerican.bo2.samples.implopen.entities.CompanyRole.class,userType.returnedClass());
	}
	
	
	
	
	
	/**
	 * tests FromXMLString
	 */
	@Test
	public void testFromXMLString(){
        String xmlValue = "xmlValue";  //$NON-NLS-1$
		userType.fromXMLString(xmlValue);
	}
	
	/**
	 * implemementation to test
	 */
	private class EntryUserTypeImpl extends EntryUserType<Integer>{


		public String objectToSQLString(Object value) {
			return null;
		}

		public String toXMLString(Object value) {
			return null;
		}

		public void nullSafeSet(PreparedStatement st, Object value, int index)
		throws HibernateException, SQLException {
			/* empty */
		}

		@Override
		protected Integer getCode(ResultSet rs, String name) throws SQLException {		
			return null;
		}

		public Object fromXMLString(String xmlValue) {
			return null;
		}
	}
	
	
}
