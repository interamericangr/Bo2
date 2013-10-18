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
package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.archutil.beans.MoneyBean;
import gr.interamerican.bo2.utils.adapters.CalculatePercentageWithFixedRounding;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CalculatePercentageWithFixedRounding}.
 */
public class TestCalculatePercentageOfMoney {
	/**
	 * Euro.
	 */
	private static final Currency EURO = Currency.getInstance("EUR"); //$NON-NLS-1$
	
	/**
	 * Unit test for execute.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExecute_Bd2Bd() {
		MoneyBean nb = new MoneyBean();	
		BigDecimal bd1 = new BigDecimal("13.145");
		nb.setBdAmount1(bd1);
		CalculatePercentageOfMoney<MoneyBean> calc = 
			new CalculatePercentageOfMoney<MoneyBean> (
			"bdAmount1", "bdAmount2", MoneyBean.class, 
			new BigDecimal("0.23"),EURO , true);
		calc.execute(nb);
		BigDecimal expected = new BigDecimal("3.02");
		Assert.assertEquals(expected, nb.getBdAmount2());
		
		nb.setBdAmount1(null);
		calc.execute(nb);
		Assert.assertNull(nb.getBdAmount2());
	}
	
	/**
	 * Unit test for execute.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExecute_Money2Money() {
		MoneyBean nb = new MoneyBean();	
		Money money1 = new MoneyImpl(new BigDecimal("13.14"), EURO);
		nb.setMoneyAmount1(money1);
		CalculatePercentageOfMoney<MoneyBean> calc = 
			new CalculatePercentageOfMoney<MoneyBean> (
			"moneyAmount1", "moneyAmount2", MoneyBean.class, 
			new BigDecimal("0.23"), EURO, true);
		calc.execute(nb);
		Money expected = new MoneyImpl(new BigDecimal("3.02"),EURO);
		Assert.assertEquals(expected, nb.getMoneyAmount2());
		
		nb.setMoneyAmount1(null);
		calc.execute(nb);
		Assert.assertNull(nb.getMoneyAmount2());
	}
	
	/**
	 * Unit test for execute.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExecute_Bd2Money() {
		MoneyBean nb = new MoneyBean();	
		BigDecimal bd1 = new BigDecimal("13.145");
		nb.setBdAmount1(bd1);
		CalculatePercentageOfMoney<MoneyBean> calc = 
			new CalculatePercentageOfMoney<MoneyBean> (
			"bdAmount1", "moneyAmount2", MoneyBean.class, 
			new BigDecimal("0.23"),EURO , true);
		calc.execute(nb);
		Money expected = new MoneyImpl(new BigDecimal("3.02"),EURO);
		Assert.assertEquals(expected, nb.getMoneyAmount2());
		
		nb.setBdAmount1(null);
		calc.execute(nb);
		Assert.assertNull(nb.getMoneyAmount2());
	}
	
	/**
	 * Unit test for execute.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExecute_Money2Bd() {
		MoneyBean nb = new MoneyBean();	
		Money money1 = new MoneyImpl(new BigDecimal("13.14"), EURO);
		nb.setMoneyAmount1(money1);
		CalculatePercentageOfMoney<MoneyBean> calc = 
			new CalculatePercentageOfMoney<MoneyBean> (
			"moneyAmount1", "bdAmount2", MoneyBean.class, 
			new BigDecimal("0.23"), EURO, true);
		calc.execute(nb);
		BigDecimal expected = new BigDecimal("3.02");
		Assert.assertEquals(expected, nb.getBdAmount2());
		
		nb.setMoneyAmount1(null);
		calc.execute(nb);
		Assert.assertNull(nb.getBdAmount2());
	}
	

}
