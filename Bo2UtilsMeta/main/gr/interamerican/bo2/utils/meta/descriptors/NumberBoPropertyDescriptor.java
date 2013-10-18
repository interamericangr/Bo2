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
package gr.interamerican.bo2.utils.meta.descriptors;


/**
 * {@link BoPropertyDescriptor} for numeric properties. 
 *  
 * @param <T> Type of this property. 
 */
public interface NumberBoPropertyDescriptor<T extends Number> 
extends BoPropertyDescriptor<T> {
	
	
	/**
	 * Sets the length of the integer part of the number.
	 * 
	 * @param lengthOfIntPart
	 */
	void setLengthOfIntegerPart(int lengthOfIntPart);
	
	/**
	 * Gets the length of the integer part.
	 * 
	 * @return Returns the length of the integer part.
	 */
	int getLengthOfIntegerPart();
	
	/**
	 * Sets the count of the decimal digits.
	 * 
	 * @param lengthOfIntPart New count of decimal digits.
	 */
	void setLengthOfDecimalPart(int lengthOfIntPart);
	
	/**
	 * Gets the count of decimal digits.
	 * 
	 * @return Returns the count of decimal digits.
	 */
	int getLengthOfDecimalPart();
	
	/**
	 * Indicates if zero is an acceptable value.
	 * 
	 * @return Returns true if this business property can accept
	 *         a value of zero.
	 */
	boolean isZeroAllowed();
	
	/**
	 * Sets the flag on and of for allowing this property to accept
	 * values of zero.
	 * 
	 * @param zeroAllowed
	 */
	void setZeroAllowed(boolean zeroAllowed);
	
	/**
	 * Indicates if negative values are acceptable.
	 * 
	 * @return Returns true if this business property can accept
	 *         negative values.
	 */	
	boolean isNegativeAllowed();
	
	/**
	 * Sets the flag for accepting negative values.
	 * 
	 * @param negativeAllowed
	 */
	void setNegativeAllowed(boolean negativeAllowed);

}
