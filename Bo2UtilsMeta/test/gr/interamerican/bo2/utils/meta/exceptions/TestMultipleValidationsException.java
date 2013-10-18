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
package gr.interamerican.bo2.utils.meta.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.utils.meta.Bean1;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



/**
 * 
 */
public class TestMultipleValidationsException {

	
	/**
	 * map fro test
	 */
	Map<Object, String> m = new HashMap<Object, String>();
	
	/**
	 * MultipleValidationsException
	 */
	MultipleValidationsException mve = new MultipleValidationsException("ValidationsException",m); //$NON-NLS-1$
	
	/**
	 * tests GetValidationFailures
	 */
	@Test
	public void testGetValidationFailures(){
		Bean1 bean = new Bean1();
		m.put(bean, "value"); //$NON-NLS-1$
		String expected = "value"; //$NON-NLS-1$
		String actual = mve.getValidationFailures().get(bean);
		assertEquals(expected,actual);
	}
	/**
	 * test getAllMessages
	 */
	@Test
	public void testGetAllMessages() {
		Bean1 bean = new Bean1();
		m.put(bean, "value"); //$NON-NLS-1$
		String expected = "value\n"; //$NON-NLS-1$
		String actual = mve.getAllMessages();
		assertEquals(expected,actual);
		
		m.put(new RuntimeException(), "value2"); //$NON-NLS-1$
		expected = "value\nvalue2\n"; //$NON-NLS-1$
		String expected2="value2\nvalue\n"; //$NON-NLS-1$
		actual = mve.getAllMessages();
		assertTrue(expected.equals(actual) || expected2.equals(actual));
		
		m.clear();
		expected = ""; //$NON-NLS-1$
		actual = mve.getAllMessages();
		assertEquals(expected,actual);
		
	}

}
