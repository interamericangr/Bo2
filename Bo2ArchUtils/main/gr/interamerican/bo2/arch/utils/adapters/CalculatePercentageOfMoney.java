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
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.BdCalculator;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * {@link CalculatePercentageOfMoney} calculates a property of an object
 * by applying a percentage to another property of the object. <br/>
 * 
 * Either property can be of type {@link Money} or of type {@link BigDecimal}.
 * The operation will do automatically any conversion. <br/>
 * The amounts are always rounded to the closest number that has the same
 * number of decimal places as the currency that has been specified in
 * this calculator's constructor. <br/>
 * The same calculator can be used sequentially for more than one objects.
 * In this case, it is sometimes required to add the remainder of the previous
 * rounding to the result of the current multiplication. This is useful
 * when the sum of the calculated amounts, mast match with the sum of the
 * base amounts multiplied with the percentage. In this case, the parameter
 * <code>addRemainder</code> of the constructor must be set to true.
 * 
 * @param <T>
 *        Type of object on which the operation is being applied.
 */
public class CalculatePercentageOfMoney<T> implements VoidOperation<T>{
	/**
	 * Property descriptor of the property that contains the base amount.
	 */
	PropertyDescriptor baseAmountPd;
	/**
	 * Property descriptor for the property that contains the calculated amount.
	 */
	PropertyDescriptor calculatedAmountPd;
	/**
	 * Currency.
	 */
	Currency currency;
	/**
	 * Percentage to apply.
	 */
	BigDecimal percentage;
	/**
	 * Indicates if the base amount is of type Money or BigDecimal.
	 */
	boolean baseIsMoney = false;
	/**
	 * Indicates if the calculated amount is of type Money or BigDecimal.
	 */

	boolean calculatedIsMoney = false;
	/**
	 * Calculator used for the calculations.
	 */
	BdCalculator calc;
	

	/**
	 * 
	 * Creates a new ApplyPercentage object. 
	 *
	 * @param baseAmountName
	 *        Name of the property that holds the base amount.
	 * @param calculatedAmountName
	 *        Name of the property that holds the amount that is calculated
	 *        by multiplying the base amount with the percentage. 
	 * @param clazz
	 *        Class of T.
	 * @param percentage 
	 *        Percentage being multiplied.
	 * @param currency
	 *        Currency.
	 * @param addRemainder
	 *        Used to set if the remainder of the previous rounding will
	 *        be added to the result of the next multiplication. 
	 */
	public CalculatePercentageOfMoney (
			String baseAmountName, String calculatedAmountName, Class<T> clazz, BigDecimal 
			percentage, Currency currency, boolean addRemainder) {
		super();
		this.baseAmountPd = JavaBeanUtils.getPropertyDescriptor(clazz, baseAmountName);
		this.calculatedAmountPd = JavaBeanUtils.getPropertyDescriptor(clazz, calculatedAmountName);
		this.baseIsMoney = baseAmountPd.getPropertyType().equals(Money.class);
		this.calculatedIsMoney = calculatedAmountPd.getPropertyType().equals(Money.class);
		this.currency = currency;
		this.calc = new BdCalculator(currency.getDefaultFractionDigits(), addRemainder);
		this.percentage = percentage;
	}
	
	/**
	 * Returns the object to set on the calculated property. <br/>
	 * 
	 * This object is either a {@link Money} object with the specified
	 * amount and the currency of this object, or the specified {@link BigDecimal}
	 * <code>amount</code> itself.
	 * 
	 * @param amount
	 * 
	 * @return Returns the object to set to the calculated property.
	 */
	private Object objectToSet(BigDecimal amount) {
		if (calculatedIsMoney) {
			return new MoneyImpl(amount, currency);
		} else {
			return amount;
		}
	}
	
	/**
	 * Gets the base amount for the calculation from the specified object.
	 * 
	 * @param t
	 *        Object that contains the amount.
	 *        
	 * @return Returns the base amount.
	 */
	private BigDecimal baseAmount(T t) {
		Object o = JavaBeanUtils.getProperty(baseAmountPd, t);
		if (o==null) {
			return null;
		}
		if (baseIsMoney) {			
			Money m = (Money) o;
			return m.getAmount();
		} else {
			return (BigDecimal) o;
		}
	}

	public void execute(T t) {
		Object val = null;
		BigDecimal amount = baseAmount(t);
		if (amount!=null) {
			calc.set(amount);
			BigDecimal result = calc.multiply(percentage);
			val = objectToSet(result);
		}
		JavaBeanUtils.setProperty(calculatedAmountPd, val, t);		
	}
	

}
