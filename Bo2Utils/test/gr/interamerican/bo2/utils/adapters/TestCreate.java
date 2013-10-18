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
package gr.interamerican.bo2.utils.adapters;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetProperty}.
 */
public class TestCreate {
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_succeed() {
		Create<String, BigDecimal> create = 
			new Create<String, BigDecimal>(String.class, BigDecimal.class);		
		String string = "3.14"; //$NON-NLS-1$
		BigDecimal actual = create.execute(string);
		BigDecimal expected = new BigDecimal(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_fail() {
		Create<String, BigDecimal> create = 
			new Create<String, BigDecimal>(String.class, BigDecimal.class);		
		String string = "no good"; //$NON-NLS-1$
		BigDecimal actual = create.execute(string);
		BigDecimal expected = new BigDecimal(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected=RuntimeException.class)
	public void testConstructor_fail() {		
		new Create<String, Class>(String.class, Class.class);
	}
	
	


}
