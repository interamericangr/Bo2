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
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link GetProperty}.
 */
public class TestMultipleStepsCreate {
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute_succeed() {
		MultipleStepsCreate<String, BigDecimal> create = 
			new MultipleStepsCreate<String, BigDecimal>
			(String.class, BigInteger.class, BigDecimal.class);
		
		String string = "378787814"; //$NON-NLS-1$
		BigDecimal actual = create.execute(string);
		BigDecimal expected = new BigDecimal(string);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test(expected=RuntimeException.class)
	public void testExecute_fail() {
		MultipleStepsCreate<String, BigDecimal> create = 
			new MultipleStepsCreate<String, BigDecimal>
			(String.class, BigInteger.class, BigDecimal.class);
		
		String string = "no good"; //$NON-NLS-1$
		create.execute(string);
	}
	
	/**
	 * Unit test for the adapter.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_fail() {
		new MultipleStepsCreate<String, BigDecimal>
			(String.class, BigInteger.class, Double.class, BigDecimal.class);
	}
	
	


}
