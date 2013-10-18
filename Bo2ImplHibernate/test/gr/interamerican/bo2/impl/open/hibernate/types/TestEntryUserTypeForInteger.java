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
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class TestEntryUserTypeForInteger {

	
	/**
	 * entryUserType to test
	 */
	EntryUserTypeForInteger entryUserType = new EntryUserTypeForInteger();
	
	/**
	 * Entry for the tests.
	 */
	TypedSelectable<Integer> entry = 
		new TypedSelectableImpl<Integer>(1L, null, 1, "Entry"); //$NON-NLS-1$
	
	
	
	/**
	 * ENTRY_USER_PROPERTIES_PATH
	 */
	private final String ENTRY_USER_PROPERTIES_PATH = 
		"/gr/interamerican/rsrc/hibernate/types/EntryUserType.properties"; //$NON-NLS-1$
	
	
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
	 * tests ObjectToSQLString
	 */
	@Test
	public void testObjectToSQLString(){
	    assertEquals(entry.getCode().toString(),entryUserType.objectToSQLString(entry));
	}
	
	/**
	 * tests ObjectToSQLString
	 */
	@Test
	public void testToXMLString(){
		assertEquals(entry.getCode().toString(),entryUserType.toXMLString(entry));
	}
	
	/**
	 * tests ObjectToSQLString
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFromXMLString(){
		Cache<Integer> cache = CacheRegistry.<Integer>getRegisteredCache("intCache");
		
		TypedSelectable<Integer> t = 
			new TypedSelectableImpl<Integer> (1L, null, 2, "t");
		cache.put(t);
		
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_PROPERTIES_PATH);
		entryUserType.setParameterValues(properties);
		
		@SuppressWarnings("unchecked")
		TypedSelectable<Integer> result = (TypedSelectable<Integer>) entryUserType.fromXMLString("2");
		assertEquals(t,result);
	}
	
}
