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
package gr.interamerican.bo2.impl.open.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for {@link PropertiesProviderImpl}.
 */
@SuppressWarnings("nls")
public class TestPropertiesProviderImpl {
	
	/**
	 * properties for the test 
	 */
	private Properties properties;
	
	/**
	 * tested object
	 */
	private PropertiesProviderImpl impl;
	
	/**
	 * keys of properties
	 */
	private String[] keys = {"0","1","2","3","4","5","6","7","8"};
	
	/**
	 * values of properties
	 */
	private Object[] values;
	
	/**
	 * expected values of getProperty methods
	 */
	private Object[] expected;
	

	/**
	 * Creates a new PropertiesProviderImplTest object. 
	 *
	 */	
	public TestPropertiesProviderImpl() {	
		properties=new Properties();
		impl = new PropertiesProviderImpl(properties);
		
		
		Date date3;
		Date date6;
		try {
			date3 = DateUtils.getDate("17/04/2004");
			date6 = DateUtils.getDate("13/11/1998");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		Object[] in = {
				"string", //0
				"13,22",  //1			 
				"20",     //2
				"17/4/2004", //3
				"false",     //4
				"1",         //5
				date6,       //6			 
				7,           //7
				8.1,         //8
		};
		values = in;

		Object[] ex = {
				"string", //0
				13.22,  //1			 
				20.0,     //2
				date3, //3
				false,     //4
				true,         //5
				date6,       //6			 
				7.0,           //7
				8.1          //8
		};
		expected = ex;
		
		for (int i = 0; i < keys.length; i++) {
			properties.put(keys[i], values[i]);
		}	}
	
	/**
	 * tests getProperty
	 */
	@Test
	public void testGetProperty() {
		int idx=0;
		String v = impl.getProperty(keys[idx]);
		assertEquals(v, expected[idx]);		
	}
	
	/**
	 * tests getPropertyAsDouble for string double
	 */
	@Test
	public void testGetDoubleString() {		
		int idx=1;
		try {
			double v = impl.getPropertyAsDouble(keys[idx]);
			assertEquals(v, expected[idx]);	
		} catch (UnexpectedException e) {
			fail(e.toString());
		}	
	}
	
	/**
	 * tests getPropertyAsDouble for string integer
	 */
	@Test
	public void testGetIntString() {
		int idx=2;
		try {
			double v = impl.getPropertyAsDouble(keys[idx]);
			assertEquals(v, expected[idx]);	
		} catch (UnexpectedException e) {
			fail(e.toString());
		}	
	}
	
	/**
	 * tests getPropertyAsDate for string property
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetDateString() throws UnexpectedException {
		int idx=3;
		Date v = impl.getPropertyAsDate(keys[idx]);
		assertEquals(v, expected[idx]);	
	}
	
	/**
	 * tests getPropertyAsDate for date property
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetDate() throws UnexpectedException {
		int idx=6;		
		Date v = impl.getPropertyAsDate(keys[idx]);
		assertEquals(v, expected[idx]);	
	}
	
	/**
	 * tests getPropertyAsDate for date property
	 * @throws UnexpectedException 
	 */
	@Test(expected=UnexpectedException.class)
	public void testGetDate_withInvalidFormat() throws UnexpectedException {
		int idx=0;		
		impl.getPropertyAsDate(keys[idx]);
	}
	
	/**
	 * tests getProperty as boolean for false 
	 */
	@Test
	public void testGetBooleanString() {
		int idx=4;
		boolean v = impl.getPropertyAsBoolean(keys[idx]);
		assertEquals(v, expected[idx]);		
	}
	
	/**
	 * tests getProperty as boolean for false
	 */
	@Test
	public void testGetBooleanInt() {
		int idx=5;
		boolean v = impl.getPropertyAsBoolean(keys[idx]);
		assertEquals(v, expected[idx]);		
	}
	
	/**
	 * tests getPropertyAsDouble for double
	 */
	@Test
	public void testGetDouble() {		
		int idx=7;
		try {
			double v = impl.getPropertyAsDouble(keys[idx]);
			assertEquals(v, expected[idx]);	
		} catch (UnexpectedException e) {
			fail(e.toString());
		}	
	}
	
	/**
	 * tests getPropertyAsDouble for integer
	 */
	@Test
	public void testGetInt() {
		int idx=8;
		try {
			double v = impl.getPropertyAsDouble(keys[idx]);
			assertEquals(v, expected[idx]);	
		} catch (UnexpectedException e) {
			fail(e.toString());
		}	
	}

	/**
	 * Tests getProperties
	 */
	@Test
	public void testGetProperties(){
		Integer expectedSize = keys.length;
		Integer actualSize = impl.getProperties().size();
		assertEquals(expectedSize,actualSize);
	}
	
	/**
	 * Tests getPropertyValue
	 */
	@Test
	public void testGetPropertyValue(){
		int idx=0;
		String actual = (String) impl.getPropertyValue(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * Tests getMainProperty
	 */
	@Test
	public void testGetMainProperty(){
		int idx=0;
		String actual = impl.getMainProperty(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * Tests getMainAsDateProperty()
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetMainAsDateProperty() throws UnexpectedException{
		int idx=3;
		Date actual = impl.getMainPropertyAsDate(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * Tests getMainPropertyAsDouble
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetMainPropertyAsDouble() throws UnexpectedException{
		int idx=1;
		Double actual = impl.getMainPropertyAsDouble(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * Tests getMainPropertyAsBoolean
	 */
	@Test
	public void testGetMainPropertyAsBoolean(){
		int idx=4;
		Boolean actual = impl.getMainPropertyAsBoolean(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * Tests getMainPropertyValue
	 */
	@Test
	public void testGetMainPropertyValue(){
		int idx=0;
		String actual = (String) impl.getMainPropertyValue(keys[idx]);
		assertEquals(expected[idx],actual);
	}
	
	/**
	 * tests getProperty
	 */
	@Test
	public void testGetProperty_nullValue() {
		assertNull(impl.getProperty("9"));
	}
	
	/**
	 * tests getPropertyAsDouble
	 * @throws UnexpectedException 
	 */
	@Test
	public void testGetPropertyAsDouble_nullValue() throws UnexpectedException {
		assertNull(impl.getPropertyAsDouble("9"));
	}
	
	/**
	 * tests getPropertyAsDouble when value is not a number
	 * @throws UnexpectedException 
	 */
	@Test(expected=UnexpectedException.class)
	public void testGetPropertyAsDouble_Exception() throws UnexpectedException {
		int idx=4;
		impl.getPropertyAsDouble(keys[idx]);
	}
	
	/**
	 * tests getPropertyAsDouble
	 */
	@Test
	public void testGetPropertyAsBoolean_nullValue() {
		assertNull(impl.getPropertyAsBoolean("9"));
	}
}

