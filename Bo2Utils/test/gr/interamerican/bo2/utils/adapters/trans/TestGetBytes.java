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
package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.utils.adapters.trans.Create;
import gr.interamerican.bo2.utils.adapters.trans.GetProperty;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetBytes}.
 */
public class TestGetBytes {
	
	/**
	 * Unit test for execute().
	 */
	@Test
	public void testExecute_notNull() {
		GetBytes get = new GetBytes();
		Object o = new Object();
		String string = o.toString();
		byte[] expecteds = string.getBytes();
		byte[] actuals = get.execute(o);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Unit test for execute().
	 */
	@Test
	public void testExecute_null() {
		GetBytes get = new GetBytes();
		byte[] expecteds = new byte[0];
		byte[] actuals = get.execute(null);
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	


}
