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
package gr.interamerican.bo2.impl.open.creation;

import gr.interamerican.bo2.creation.creators.BaseTestForInterfaceImplementors;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Impl4Interfaces}.
 */
public class TestImpl4Interfaces extends BaseTestForInterfaceImplementors {
	

	/**
	 * Object to test.
	 * Must override the suffix, so the classes it creates don't mess
	 * with classes created by other tests.
	 */
	static Impl4Interfaces impl = new Impl4Interfaces() {
		@Override
		protected String getSuffix() {
			return "_forTestOfImpl4Interfaces";		 //$NON-NLS-1$
		}
	};
	
	/**
	 * Creates a new TestImpl4Abstract object. 
	 */
	public TestImpl4Interfaces() {
		super(impl);
	}
	
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_Key() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		Class<InvoiceKey> keyClass = (Class<InvoiceKey>) creator.create(InvoiceKey.class);
		InvoiceKey key1 =  keyClass.newInstance();
		InvoiceKey key2 =  keyClass.newInstance();
		String one = "1";
		key1.setInvoiceNo(one);
		key2.setInvoiceNo(one);
		Assert.assertEquals(one, key1.getInvoiceNo());
		int comparison = key1.compareTo(key2);
		Assert.assertEquals(key1, key2);
		Assert.assertEquals(0, comparison);
		
		String two = "2";
		key2.setInvoiceNo(two);
		comparison = key1.compareTo(key2);
		Assert.assertTrue(comparison<0);
	}
	
	

}
