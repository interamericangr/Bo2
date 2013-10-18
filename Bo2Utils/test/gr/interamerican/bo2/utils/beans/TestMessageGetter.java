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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;

import java.util.ResourceBundle;

import org.junit.Test;

/**
 * Test for MessageGetter
 */
public class TestMessageGetter {

	
	/**
	 * bean bundle name
	 */
	private static final String BEAN_BUNDLE_NAME = 
		"gr.interamerican.bo2.utils.beans.bean1"; //$NON-NLS-1$
	
	/**
	 * ResourceBundle
	 */
	ResourceBundle bundle = ResourceBundle.getBundle(BEAN_BUNDLE_NAME);
	
	/**
	 * MessageGetter
	 */
	MessageGetter getter =  new MessageGetter(BEAN_BUNDLE_NAME);
	
	/**
	 * test GetString
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetString(){
		String expected = "2";
		String actual = getter.getString("intField");
		assertEquals(expected,actual);
	}
	
	/**
	 * test GetString
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetString_fail(){
		String key = "noExisting";
		String expected = '!' + key + '!';
		String actual = getter.getString(key); 
		assertEquals(expected,actual);
	}
	
}
