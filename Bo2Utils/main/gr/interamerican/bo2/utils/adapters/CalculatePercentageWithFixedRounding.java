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

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.beans.BdCalculator;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * {@link CalculatePercentageWithFixedRounding} calculates a property of an object
 * by applying a percentage to another property of the object. <br/>
 * 
 * Both properties must be of type {@link BigDecimal}. <br/>
 * The amounts are always rounded to the closest number that has the same
 * number of decimal places as specified in this calculator's constructor. <br/>
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
public class CalculatePercentageWithFixedRounding<T> implements VoidOperation<T>{
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
	 * @param fractionDigits
	 *        fractionDigits.
	 * @param addRemainder
	 *        Used to set if the remainder of the previous rounding will
	 *        be added to the result of the next multiplication. 
	 */
	public CalculatePercentageWithFixedRounding (
			String baseAmountName, String calculatedAmountName, Class<T> clazz, BigDecimal 
			percentage, int fractionDigits, boolean addRemainder) {
		super();
		this.baseAmountPd = JavaBeanUtils.getPropertyDescriptor(clazz, baseAmountName);
		this.calculatedAmountPd = JavaBeanUtils.getPropertyDescriptor(clazz, calculatedAmountName);
		this.calc = new BdCalculator(fractionDigits, addRemainder);
		this.percentage = percentage;
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
		return (BigDecimal) JavaBeanUtils.getProperty(baseAmountPd, t);
	}

	public void execute(T t) {
		BigDecimal result = null;
		BigDecimal amount = baseAmount(t);
		if (amount!=null) {
			calc.set(amount);
			result = calc.multiply(percentage);			
		}
		JavaBeanUtils.setProperty(calculatedAmountPd, result, t);		
	}
	

}
