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

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.beans.BdCalculator;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Money calculator does calculations for {@link Money} keeping always the 
 * same count of decimal places on the results. <br/>
 * 
 * The count of decimal places is defined as a parameter in this object's 
 * constructor. The calculator keeps always two numbers. The first number
 * is the result of the last calculation that is always round to the nearest
 * BigDecimal that has scale equal to the number of decimal places of this
 * calculator. Rounding is done always using <code>BigDecimal.ROUND_HALF_EVEN</code>.
 * The second number kept by the calculator is the remainder. The remainder
 * keeps the remainder of the result's rounding. <br/>
 * The constructor argument <code>addRemainder</code> specifies the use of
 * the remainder in calculations. If this argument is set to true, then the
 * remainder that was before a calculation will be added to the result of the
 * next calculation, before its rounding. 
 *  
 */
public class MoneyCalculator {
	/**
	 * Calculator.
	 */
	BdCalculator calc;
	
	/**
	 * Currency of the result.
	 */
	Currency currency;
	
	/**
	 * Creates a new MoneyCalculator object. 
	 * 
	 * @param currency
	 *        Currency for the money being calculated by this calculator.
	 *        	
	 * @param addRemainder 
	 *        Indicator if the remainder that was before a calculation 
	 *        will be added to the result of the calculation.
	 */
	public MoneyCalculator(Currency currency, boolean addRemainder) {
		super();
		this.currency = currency;
		calc = new BdCalculator(currency.getDefaultFractionDigits(), addRemainder);
	}
	
	/**
	 * Validates that the specified money has the same currency
	 * as this calculator.
	 * 
	 * @param money
	 */
	private void validate(Money money) {
		/* for the time being empty */
	}


	/**
	 * Sets the remainder and the result to zero.
	 */
	public void reset() {
		calc.reset();
	}

	/**
	 * Sets the result to zero, without modifying the remainder.
	 */
	public void resetResult() {
		calc.resetResult();
	}
	
	/**
	 * Sets the remainder to zero, without modifying the result.
	 */
	public void resetRemainder() {
		calc.resetRemainder();
	}

	/**
	 * Sets the specified argument to this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not modified.
	 * 
	 * @param bd
	 *        Money to add.
	 *        
	 * @return Returns the current result. 
	 */
	public BigDecimal set(Money bd) {
		validate(bd);
		return calc.set(bd.getAmount());
	}
	
	/**
	 * Adds the specified argument to this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not affected.
	 * 
	 * @param bd
	 *        Money to add.
	 *        
	 * @return Returns the current result. 
	 */
	public Money add(Money bd) {
		validate(bd);
		BigDecimal amount = calc.add(bd.getAmount());
		return new MoneyImpl(amount, currency);
	}

	/**
	 * Subtracts the specified argument from this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not affected.
	 * 
	 * @param bd
	 *        Money to subtract from the current result.
	 *        
	 * @return Returns the current result. 
	 */
	public Money subtract(Money bd) {
		validate(bd);
		BigDecimal amount =  calc.subtract(bd.getAmount());
		return new MoneyImpl(amount, currency);
	}
	
	/**
	 * Multiplies the current result with the specified argument
	 * and sets the result of the multiplication as the m to this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. <br/>
	 * The remainder of the truncation is not affected.
	 * 
	 * @param bd
	 *        Big decimal to multiply with the current result.
	 *        
	 *  @return Returns the current result. 
	 */
	public Money multiply(BigDecimal bd) {
		BigDecimal amount = calc.multiply(bd);
		return new MoneyImpl(amount, currency);
	}
		
	/**
	 * Gets the result.
	 * 
	 * @return Returns the result.
	 */
	public Money getResult() {
		return new MoneyImpl(calc.getResult(), currency);
	}

	/**
	 * Gets the remainder.
	 * 
	 * @return Returns the remainder.
	 */
	public BigDecimal getRemainder() {
		return calc.getRemainder();
	}
	
	

}
