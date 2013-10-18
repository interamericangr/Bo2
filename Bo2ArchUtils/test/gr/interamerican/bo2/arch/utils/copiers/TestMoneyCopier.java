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
package gr.interamerican.bo2.arch.utils.copiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.po.SampleMoney;
import gr.interamerican.bo2.samples.po.SampleMoneyOtherConstr;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

/**
 * Test for MoneyCopier
 */
public class TestMoneyCopier {

	
	/**
	 * MoneyCopier
	 */
	MoneyCopier copier = new MoneyCopier();
	
	
	/**
	 * ����.
	 */
	BigDecimal amount = new BigDecimal(1000);
	
	
	/**
	 * Currency.
	 */
	private Currency currency=Currency.getInstance("EUR"); //$NON-NLS-1$
	
	/**
	 * Test copy
	 */
	@Test
	public void testCopy(){
		
		assertNull(copier.copy(null));
		
		//constructor with bigDecimal arg
		Money money = new MoneyImpl();
		money.setAmount(amount);
		Money newMoney = copier.copy(money);
		assertEquals(money.getAmount(),newMoney.getAmount());
		
		//default constructor
		SampleMoney sample = new SampleMoney();
		sample.setAmount(amount);
		Money newSampleMoney = copier.copy(sample);
		assertEquals(sample.getAmount(),newSampleMoney.getAmount());
	}
	
	/**
	 * Test copy
	 */
	@Test(expected = RuntimeException.class)
	public void testCopy_fail(){

		SampleMoneyOtherConstr sample = new SampleMoneyOtherConstr(amount,currency);
		copier.copy(sample);

	}
	
	
}
