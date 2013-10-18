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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Unit test for {@link MoneyFormatter}.
 */
public class TestMoneyFormatter {

	
	/**
	 * Formatter to test
	 */
	MoneyFormatter formatter = MoneyFormatter.INSTANCE;
	
	/**
	 * Tests format
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat(){
		Money money = new MoneyImpl();
		money.setAmount(new BigDecimal("15000"));
		String actual = formatter.format(money);
		assertEquals("15.000,00", actual);
	}
	
}
