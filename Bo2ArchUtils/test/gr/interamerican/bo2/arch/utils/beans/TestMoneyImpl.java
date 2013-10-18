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
package gr.interamerican.bo2.arch.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.Money;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestMoneyImpl {

	
	/**
	 * empty constructor
	 */
	@SuppressWarnings("unused")
	private MoneyImpl moneyImpl = new MoneyImpl();
	
	/**
	 * ammount to test
	 */
	private static final BigDecimal AMOUNT = new BigDecimal(10).setScale(2);
	
    /**
     * currency code to test
     */
    private String CURRENCY_CODE ="EUR"; //$NON-NLS-1$
	
    /**
     * currency to test
     */
    private Currency CURRENCY=Currency.getInstance("EUR"); //$NON-NLS-1$
    
    /**
     * constructor with amount
     */
    @SuppressWarnings("unused")
	private MoneyImpl newMoney = new MoneyImpl(AMOUNT);
    
    /**
     * constructor with amount and currency
     */
    private MoneyImpl newMoneyImpl = new MoneyImpl(AMOUNT,CURRENCY);
	
	/**
	 * test getCurrencyCode().
	 */
	@Test
	public void testGetCurrencyCode() {
		assertEquals(CURRENCY_CODE, newMoneyImpl.getCurrencyCode());		
	}
	
	/**
	 * test setCurrencyCode().
	 */
	@Test
	public void testSetCurrencyCode() {
		newMoneyImpl.setCurrencyCode(CURRENCY_CODE);
		assertEquals(CURRENCY_CODE, newMoneyImpl.getCurrencyCode());		
	}
	
	/**
	 * test getAmount().
	 */
	@Test
	public void testGetAmount() {
		assertEquals(AMOUNT, newMoneyImpl.getAmount());		
	}
	
	
	/**
	 * test setAmount().
	 */
	@Test
	public void testSetAmount() {
		newMoneyImpl.setAmount(new BigDecimal(11));
		assertEquals(new BigDecimal("11.00"), newMoneyImpl.getAmount());//$NON-NLS-1$
	}
		
	/**
	 * test toString.
	 */
	@Test
	public void testToString() {
		assertEquals("10.00", newMoneyImpl.toString());//$NON-NLS-1$
	}
	
	/**
	 * Test that a PoSet can be serialized.
	 * @throws IOException 
	 */
	@Test
	public void testSerialization() throws IOException {
		BigDecimal amount = new BigDecimal(100.);
		MoneyImpl subject = new MoneyImpl(amount, Currency.getInstance("EUR")); //$NON-NLS-1$
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(subject);
		oos.close();
		assertTrue(baos.toByteArray().length>0);
	}
	
	/**
	 * test toString.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testEquals() {
		
		BigDecimal amount = new BigDecimal(1000);
		Currency currency = Currency.getInstance("EUR");
		
		MoneyImpl money1 = new MoneyImpl(amount,currency);
		assertFalse(money1.equals(null));

		assertTrue(money1.equals(money1));
		
		MoneyImpl money2 = new MoneyImpl(amount,currency);
		assertTrue(money1.equals(money2));
		
		MoneyImpl money3 = new MoneyImpl(new BigDecimal(3000),currency);
		assertFalse(money1.equals(money3));
		
		assertFalse(money1.equals(new Date()));
	}
	
	/**
	 * test toString.
	 */
	@Test
	public void testHashCode() {
		
		MoneyImpl money1 = new MoneyImpl();
		assertTrue(money1.hashCode()!=0);
	}
	
	/**
	 * test compareTo
	 */
	@Test
	public void testCompareTo() {
		Money m1 = new MoneyImpl(new BigDecimal(100.)); 
		Assert.assertEquals(1, m1.compareTo(null));
		Money m2 = new MoneyImpl();
		Assert.assertEquals(1, m1.compareTo(m2)); //m1 has larger amount
		m2.setCurrencyCode("USD"); //$NON-NLS-1$
		Assert.assertTrue(m1.compareTo(m2)<0); //m2 has 'larger' currency
		m2 = new MoneyImpl(new BigDecimal(200.));
		Assert.assertEquals(-1, m1.compareTo(m2)); //m2 has 'larger' amount
	}
	
}
