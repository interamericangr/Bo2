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
import gr.interamerican.bo2.utils.StringConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class TestEntryListUserType {

	
	/**
	 * EntryListUserType
	 */
	EntryListUserType<Long> userType = new EntryListUserTypeForLong();
	
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
	 * 
	 */
	@Test
	public void testReturnedClass(){
		assertEquals(ArrayList.class,userType.returnedClass());
	}
	
	/**
	 * test toXMLString
	 */
	@Test
	public void testToXMLString(){
		assertEquals(StringConstants.EMPTY,userType.toXMLString(null));
	}
	
	/**
	 * test toXMLString
	 */
	@Test(expected=RuntimeException.class)
	public void testToXMLString_fail(){
		userType.toXMLString(1);
	}
	
	/**
	 * test toXMLString
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testToXMLString_fail2(){

      List<String> list = new ArrayList<String>();
      list.add("string1");
      userType.toXMLString(list);
	}
	
	/**
	 * test toXMLString
	 */
	@Test
	public void testToXMLString_succeed(){

		Long code1 = 1l;
		TypedSelectable<Long> typed1 = new TypedSelectableImpl<Long>();
		typed1.setCode(code1);
		typed1.setTypeId(1000L);
		
		Long code2 = 2l;
		TypedSelectable<Long> typed2 = new TypedSelectableImpl<Long>();
		typed2.setCode(code2);
		typed2.setTypeId(2000L);
		
		List<TypedSelectable<Long>> list = new ArrayList<TypedSelectable<Long>>();
		list.add(typed1);
		list.add(typed2);
		
		String actual = userType.toXMLString(list);
		String expected = code1.toString().concat(StringConstants.COMMA).concat(code2.toString());
		
		assertEquals(expected, actual);

	}
	
	/**
	 * test fromXMLString
	 */
	
	@Test
	public void testFromXMLString_succeed(){
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache("longCache"); //$NON-NLS-1$
		TypedSelectable<Long> typed = new TypedSelectableImpl<Long>();
		typed.setCode(1L);
		typed.setTypeId(1000L);
		cache.put(typed);

		
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_PROPERTIES_PATH);
		userType.setParameterValues(properties);
		
		@SuppressWarnings("unchecked")
		ArrayList<TypedSelectable<Long>> list = 
			(ArrayList<TypedSelectable<Long>>) userType.fromXMLString("1"); //$NON-NLS-1$
		assertEquals(1,list.size());
	}
	
	
	/**
	 * test fromXMLString
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testFromXMLString_fail(){
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache("longCache");
		
		TypedSelectable<Long> typed = new TypedSelectableImpl<Long>();
		typed.setCode(1L);
		typed.setTypeId(1000L);
		cache.put(typed);

		CacheRegistry.registerCache("testCache2", cache, Long.class); //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(ENTRY_USER_PROPERTIES_PATH);
		userType.setParameterValues(properties);		
		userType.fromXMLString("2");
	}
	
	/**
	 * test objectToSQLString
	 */
	@Test
	public void testObjectToSQLString(){
		Long code1 = 1l;
		TypedSelectable<Long> typed1 = new TypedSelectableImpl<Long>();
		typed1.setCode(code1);
		typed1.setTypeId(1000L);
		
		List<TypedSelectable<Long>> list = new ArrayList<TypedSelectable<Long>>();
		list.add(typed1);

		String actual = userType.objectToSQLString(list);
		String expected = code1.toString();
		
		assertEquals(expected, actual);

	}

}
