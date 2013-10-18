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
package gr.interamerican.bo2.utils.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@link BdCalculator} makes calculations keeping always the same count of
 * decimal places on the results. <br/>
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
public class BdCalculator {
	/**
	 * Length of decimal places. 
	 */
	int decimalPlaces;
	
	/**
	 * Indicates if the current remainder will be added to the
	 * result of the next calculation.
	 */
	boolean addRemainder;
	
	/**
	 * Current result.
	 */
	BigDecimal result;
	
	/**
	 * Current remainder.
	 */
	BigDecimal remainder;
	
	

	/**
	 * Creates a new BdCalculator object. 
	 *
	 * @param decimalPlaces
	 *        Count of decimal places for this calculator.
	 * @param addRemainder 
	 *        Indicator if the remainder that was before a calculation 
	 *        will be added to the result of the calculation.
	 */
	public BdCalculator(int decimalPlaces, boolean addRemainder) {
		super();
		this.decimalPlaces = decimalPlaces;
		this.addRemainder = addRemainder;
		reset();
	}
	
	/**
	 * This method is called as a last step from all calculation method
	 * in order to set the values of this calculator's <code>result</code> 
	 * and <code>remainder</code> according to the argument calculated,
	 * which is expected to be the not rounded result of the current
	 * calculation. <br/>
	 * 
	 * If the <code>useRemainder</code> flag is true, then the current
	 * remainder will be added to the <code>calculated</code> argument
	 * before any rounding takes place. Then this calculator's result 
	 * will be set to the rounded value. The result of the subtraction
	 * of the calculated value and the rounded value is set to this
	 * calculator's remainder.
	 * 
	 * @param calculated
	 *        Not rounded result of the current calculation.
	 */
	private void calculate(BigDecimal calculated) {
		BigDecimal bd;
		if (addRemainder) {
			bd = calculated.add(this.remainder);
		} else {
			bd = calculated;
		}
		this.result = bd.setScale(decimalPlaces, RoundingMode.HALF_EVEN);
		this.remainder = bd.subtract(this.result); 	
	}


	/**
	 * Sets the remainder and the result to zero.
	 */
	public void reset() {		
		this.result = round(BigDecimal.ZERO);
		this.remainder = BigDecimal.ZERO; 
	}
	
	/**
	 * Sets the result to zero, without modifying the remainder.
	 */
	public void resetResult() {
		this.result = round(BigDecimal.ZERO);
	}
	
	/**
	 * Sets the remainder to zero, without modifying the result.
	 */
	public void resetRemainder() {
		this.remainder = BigDecimal.ZERO;
	}
	
	/**
	 * Rounds the specified BigDecimal.
	 * 
	 * @param bd
	 *        Big decimal to round.
	 *        
	 * @return Returns the rounded BigGecimal.
	 */
	public BigDecimal round(BigDecimal bd) {
		return bd.setScale(decimalPlaces, RoundingMode.HALF_EVEN);
	}
	
	/**
	 * Sets the specified argument to this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not modified.
	 * 
	 * @param bd
	 *        Big decimal to add.
	 *        
	 * @return Returns the current result. 
	 */
	public BigDecimal set(BigDecimal bd) {
		this.result = round(bd);	
		return this.result;
	}
	
	/**
	 * Negates this calculator's result. <br/>
	 * 
	 * The remainder is not modified.
	 * 
	 * @return Returns the current result. 
	 */
	public BigDecimal negate() {
		this.result = this.result.negate();	
		return this.result;
	}
	
	/**
	 * Adds the specified argument to this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not affected.
	 * 
	 * @param bd
	 *        Big decimal to add.
	 *        
	 * @return Returns the current result. 
	 */
	public BigDecimal add(BigDecimal bd) {
		calculate(this.result.add(bd));	
		return this.result;
	}
	
	/**
	 * Subtracts the specified argument from this calculator's result. <br/>
	 * 
	 * The result is always rounded to the number of decimal digits, 
	 * specified for this calculator. The remainder is not affected.
	 * 
	 * @param bd
	 *        Big decimal to subtract from the current result.
	 *        
	 * @return Returns the current result. 
	 */
	public BigDecimal subtract(BigDecimal bd) {
		calculate(this.result.subtract(bd));	
		return this.result;
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
	public BigDecimal multiply(BigDecimal bd) {
		calculate(this.result.multiply(bd));
		return this.result;
	}
	
	/**
	 * Gets the result.
	 *
	 * @return Returns the result
	 */
	public BigDecimal getResult() {
		return result;
	}

	/**
	 * Gets the remainder.
	 *
	 * @return Returns the remainder
	 */
	public BigDecimal getRemainder() {
		return remainder;
	}
	
	

}
