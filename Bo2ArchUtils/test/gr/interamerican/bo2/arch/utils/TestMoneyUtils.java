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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyCalculator;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.archutil.beans.MoneyBean;
import gr.interamerican.bo2.samples.archutil.conditions.IsGreaterThanZero;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.adapters.MultipleStepsCreate;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MoneyUtils}.
 */
public class TestMoneyUtils {
	/**
	 * EURO.
	 */
	private static final Currency EURO = Currency.getInstance("EUR"); //$NON-NLS-1$
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSum_withoutCondition() {		
		MoneyBean[] nb = {
				new MoneyBean(),
				new MoneyBean(),
				new MoneyBean(),
				new MoneyBean(),
		};
		nb[0].setMoneyAmount1(new MoneyImpl(new BigDecimal("1.23")));
		nb[1].setMoneyAmount1(new MoneyImpl((new BigDecimal("-1.03"))));
		nb[3].setMoneyAmount1(new MoneyImpl((new BigDecimal("1.00"))));
		Money actual = MoneyUtils.sum(Arrays.asList(nb), MoneyBean.class, "moneyAmount1", EURO);
		Money expected = new MoneyImpl(new BigDecimal("1.20"), EURO);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSum_withCondition() {
		Condition<MoneyBean> condition = new IsGreaterThanZero();
		
		MoneyBean[] nb = {
				new MoneyBean(),
				new MoneyBean(),
				new MoneyBean(),
				new MoneyBean(),
		};
		nb[0].setMoneyAmount1(new MoneyImpl(new BigDecimal("1.23")));
		nb[1].setMoneyAmount1(new MoneyImpl((new BigDecimal("-1.03"))));
		nb[3].setMoneyAmount1(new MoneyImpl((new BigDecimal("1.00"))));
		Money actual = MoneyUtils.sum
			(Arrays.asList(nb), MoneyBean.class, "moneyAmount1", EURO, condition);
		Money expected = new MoneyImpl(new BigDecimal("2.23"), EURO);
		Assert.assertEquals(expected, actual);
	}
	
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCalculatePercentage_withPercentage() {
		Transformation<String, MoneyBean> create = 
			new MultipleStepsCreate<String, MoneyBean>
			(String.class, BigDecimal.class, MoneyBean.class);
		String[] amountsArray = {"32.25", "78.15", "96.22", "11.11", "23.14"};
		List<String> amounts = Arrays.asList(amountsArray);
		
		List<MoneyBean> beans = AdapterUtils.apply(amounts, create);
		BigDecimal prc = new BigDecimal("0.23");
		
		MoneyUtils.calcPercentage
			(beans, MoneyBean.class, prc, "moneyAmount1", "moneyAmount2", EURO);
		
		
		for (MoneyBean moneyBean : beans) {
			Assert.assertNotNull(moneyBean.getMoneyAmount2());
			Assert.assertNotNull(moneyBean.getMoneyAmount2().getAmount());
		}
		Money totalAmount = MoneyUtils.sum(beans, MoneyBean.class, "moneyAmount1", EURO);
		Money totalVat = MoneyUtils.sum(beans, MoneyBean.class, "moneyAmount2", EURO);
		
		MoneyCalculator calc = new MoneyCalculator(EURO, false);		
		calc.set(totalAmount);
		Money expected = calc.multiply(prc);
		
		Assert.assertEquals(expected.getAmount(), totalVat.getAmount());
	}
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCalculatePercentage_withProperty() {
		Transformation<String, MoneyBean> create = 
			new MultipleStepsCreate<String, MoneyBean>
			(String.class, BigDecimal.class, MoneyBean.class);
		String[] amountsArray = {"32.25", "78.15", "96.22", "11.11", "23.14"};
		List<String> amounts = Arrays.asList(amountsArray);
		BigDecimal prc = new BigDecimal("0.23");
		
		List<MoneyBean> beans = AdapterUtils.apply(amounts, create);
		for (MoneyBean moneyBean : beans) {
			moneyBean.setBdAmount2(prc);
		}
		MoneyUtils.calcPercentage
			(beans, MoneyBean.class, "bdAmount2", "moneyAmount1", "moneyAmount2", EURO);
		
		
		for (MoneyBean moneyBean : beans) {
			Assert.assertNotNull(moneyBean.getMoneyAmount2());
			Assert.assertNotNull(moneyBean.getMoneyAmount2().getAmount());
		}
		Money totalAmount = MoneyUtils.sum(beans, MoneyBean.class, "moneyAmount1", EURO);
		Money totalVat = MoneyUtils.sum(beans, MoneyBean.class, "moneyAmount2", EURO);
		
		MoneyCalculator calc = new MoneyCalculator(EURO, false);		
		calc.set(totalAmount);
		Money expected = calc.multiply(prc);
		
		Assert.assertEquals(expected.getAmount(), totalVat.getAmount());
	}
	
	
	

}
