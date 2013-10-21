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
package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.utils.adapters.Modification;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link Erasers}.
 */
public class TestErasers {
	
	/**
	 * Unit test for register.
	 */
	@Test
	public void testRegister() {
		@SuppressWarnings("unchecked")
		Modification<Invoice> mock = Mockito.mock(Modification.class);
		Erasers.register(Invoice.class,mock);
		Assert.assertEquals(mock, Erasers.erasers.get(Invoice.class));
	}
	
	/**
	 * Unit test for getEraser.
	 */
	@Test
	public void testGetEraser_onRegistered() {
		@SuppressWarnings("unchecked")
		Modification<Invoice> mock = Mockito.mock(Modification.class);
		Erasers.register(Invoice.class,mock);
		Assert.assertEquals(mock, Erasers.getEraser(Invoice.class));
	}
	
	/**
	 * Unit test for getEraser.
	 */
	@Test
	public void testGetEraser_onNonRegistered() {		
		Modification<User> del = Erasers.getEraser(User.class);		
		Assert.assertNotNull(del);
	}
	
	/**
	 * Unit test for getEraser.
	 */
	@Test(expected=RuntimeException.class)
	public void testGetEraser_onNonPo() {		
		Erasers.getEraser(Object.class);
	}
	
	/**
	 * Unit test for erase
	 */
	@Test
	public void testErase_withNotNull() {
		@SuppressWarnings("unchecked")
		Modification<Invoice> mock = Mockito.mock(Modification.class);
		Erasers.register(Invoice.class,mock);
		Erasers.erase(Invoice.class, Factory.create(Invoice.class));
	}
	
	/**
	 * Unit test for erase
	 */
	@Test
	public void testErase_withNull() {
		Erasers.erase(InvoiceCustomer.class, null);
	}

}
