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
package gr.interamerican.bo2.samples.interfaces;

import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 */
public interface SmartCalc 
extends IBeanWithIdAndName {
	
	/**
	 * Gets the current result.
	 * 
	 * @return Returns the current result.
	 */
	public BigDecimal getResult();
	
	/**
	 * Resets the calculator.
	 */
	public void reset();
	
	/**
	 * Adds the current bigDecimal.
	 * 
	 * @param bd
	 *        Big decimal to add.
	 *        
	 * @return Returns the current result.
	 */
	public BigDecimal add(BigDecimal bd);
	
	/**
	 * Adds the current bigDecimal.
	 * 
	 * @param bd
	 *        Big decimal to subtract.
	 *        
	 * @return Returns the current result.
	 */
	public BigDecimal subtract(BigDecimal bd);
	
	/**
	 * Gets the set with the numbers to add.
	 * 
	 * @return Returns the numbers to add.
	 */
	public List<BigDecimal> getNumbersToAdd();
	
	/**
	 * Sets the numbers to add.
	 * 
	 * @param numbersToAdd
	 */
	public void setNumbersToAdd(List<BigDecimal> numbersToAdd);
	
	/**
	 * Adds all numbers.
	 * 
	 * @return Returns the current result.
	 */
	public BigDecimal addNumbers();
	
	/**
	 * Gets the alarm limit.
	 * 
	 * @return returns the alarm limit.
	 */
	public BigDecimal getAlarmLimit();
	
	/**
	 * Sets the alarmLimit.
	 * 
	 * @param alarmLimit
	 */
	public void setAlarmLimit(BigDecimal alarmLimit);
	
	/**
	 * Sets the left.
	 * @param left
	 */
	public void setLeft(BigDecimal left);
	
	/**
	 * Gets the left.
	 * 
	 * @return returns the left.
	 */
	public BigDecimal getLeft();
	
	/**
	 * Sets the right.
	 * @param right
	 */
	public void setRight(BigDecimal right);
	
	/**
	 * Gets the right.
	 * 
	 * @return returns the right.
	 */
	public BigDecimal getRight();
	
	/**
	 * Checks if bd is between left and right.
	 * 
	 * @param bd
	 * @return Returns true if bd is within the range.
	 */
	public boolean contains(BigDecimal bd);
	 

}
