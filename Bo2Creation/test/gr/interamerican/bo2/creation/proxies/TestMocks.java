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
package gr.interamerican.bo2.creation.proxies;


import static gr.interamerican.bo2.creation.proxies.Mocks.empty;
import static gr.interamerican.bo2.creation.proxies.Mocks.fake;
import static gr.interamerican.bo2.creation.proxies.Mocks.proxy;
import static gr.interamerican.bo2.creation.proxies.Mocks.throwing;
import gr.interamerican.bo2.samples.iempty.IEmpty1;
import gr.interamerican.bo2.samples.interfaces.SmartCalc;
import gr.interamerican.bo2.utils.Defaults;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Mocks}.
 */
public class TestMocks {
	
	/**
	 * Unit test for fake()
	 */
	@Test
	public void testEmpty() {
		SmartCalc calc = empty(SmartCalc.class);
		Assert.assertNotNull(calc);
		Assert.assertNull(calc.add(BigDecimal.ZERO));
	}
	
	/**
	 * Unit test for fake()
	 */
	@Test
	public void testEmpty_withReturnValue() {
		SmartCalc calc = empty(SmartCalc.class, BigDecimal.TEN);
		Assert.assertNotNull(calc);
		Assert.assertEquals(BigDecimal.TEN,calc.add(BigDecimal.ZERO));
	}
	
	
	/**
	 * Unit test for fake()
	 */
	@Test
	public void testFake() {
		SmartCalc calc = fake(SmartCalc.class);
		Assert.assertNotNull(calc);
		BigDecimal actual = calc.add(BigDecimal.ZERO);
		Assert.assertEquals(Defaults.getDefaultValue(BigDecimal.class), actual);
	}
	
	/**
	 * Unit test for throwing()
	 */
	@Test(expected=RuntimeException.class)
	public void testThrowing() {
		SmartCalc calc = throwing(SmartCalc.class, RuntimeException.class);
		Assert.assertNotNull(calc);
		calc.add(BigDecimal.ZERO);
	}
	
	/**
	 * Unit test for proxy()
	 */
	@Test()
	public void testProxy() {		
		IEmpty1 e1 = proxy(IEmpty1.class, new EmptyInvocationHandler());
		IEmpty1 e2 = proxy(IEmpty1.class, new EmptyInvocationHandler());
		Assert.assertEquals(e1, e1);
		Assert.assertFalse(e1.equals(e2));
		
		Assert.assertFalse(e1.hashCode()==e2.hashCode());
		Assert.assertFalse(e1.toString().equals(e2.toString()));
	}

}
