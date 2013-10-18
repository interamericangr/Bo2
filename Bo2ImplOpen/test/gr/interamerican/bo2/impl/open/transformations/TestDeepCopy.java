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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CopyProperties}.
 */
public class TestDeepCopy {
	
	/**
	 * test
	 */		
	@Test
	public void testCopy() {
		Invoice invoice = SamplesFactory.getBo2Factory().sampleInvoiceFull(5);
		DeepCopy<Invoice> deepcopy = new DeepCopy<Invoice>();
		Invoice copied = deepcopy.copy(invoice);
		Assert.assertTrue(PoUtils.deepEquals(invoice, copied));
	}
	
}
