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
package gr.interamerican.bo2.utils.adapters.vo;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Function;

import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.BdCalculator;

/**
 * {@link CalculateRoundedPercentage} calculates a property of an object
 * by applying a percentage to another property of the object. <br>
 * 
 * Both properties must be of type {@link BigDecimal}. <br>
 * The amounts are always rounded to the closest number that has the same
 * number of decimal places as specified in this calculator's constructor. <br>
 * The same calculator can be used sequentially for more than one objects.
 * In this case, it is sometimes required to add the remainder of the previous
 * rounding to the result of the current multiplication. This is useful
 * when the sum of the calculated amounts, mast match with the sum of the
 * base amounts multiplied with the percentage. In this case, the parameter
 * <code>addRemainder</code> of the constructor must be set to true.
 * 
 * @param <T>
 *        Type of object on which the operation is being applied.
 *        
 */
public class CalculateRoundedPercentage<T> implements VoidOperation<T>{
	/**
	 * Function that extracts the base amount from the input.
	 */
	final Function<T,BigDecimal> getter;
	
	/**
	 * Function that sets the calculated amount.
	 */
	final BiConsumer<T, BigDecimal> setter;

	/**
	 * Percentage to apply.
	 */
	final BigDecimal percentage;	
	
	/**
	 * Calculator used for the calculations.
	 */
	final BdCalculator calc;

	/**
	 * Creates a new {@link CalculateRoundedPercentage} object.
	 *
	 * @param getter
	 *            Function that extracts the base amount from the input.
	 * @param setter
	 *            Function that sets the calculated,by multiplying the base amount with the percentage, amount.
	 * @param percentage
	 *            Percentage being multiplied.
	 * @param fractionDigits
	 *            fractionDigits.
	 * @param addRemainder
	 *            Used to set if the remainder of the previous rounding will be added to the result of the next multiplication.
	 */
	public CalculateRoundedPercentage (Function<T, BigDecimal> getter, BiConsumer<T, BigDecimal> setter, BigDecimal percentage, int fractionDigits, boolean addRemainder) {
		this.getter = getter;
		this.setter = setter;
		this.calc = new BdCalculator(fractionDigits, addRemainder);
		this.percentage = percentage;
	}
	
	@Override
	public void execute(T t) {
		BigDecimal result = null;
		BigDecimal amount = getter.apply(t);
		if (amount!=null) {
			calc.set(amount);
			result = calc.multiply(percentage);			
		}
		setter.accept(t, result);
	}
	

}
