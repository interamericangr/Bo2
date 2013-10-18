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

import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.NotNegativeValidator;
import gr.interamerican.bo2.utils.meta.validators.NotZeroValidator;
import gr.interamerican.bo2.utils.meta.validators.NumberDecimalLengthValidator;
import gr.interamerican.bo2.utils.meta.validators.NumberIntegerLengthValidator;

/**
 * Abstract implementation of {@link NumberBoPropertyDescriptor}. 
 * 
 * @param <T> 
 *        Type of property.
 */
public abstract class AbstractNumberBoPropertyDescriptor<T extends Number> 
extends AbstractBoPropertyDescriptor<T> 
implements NumberBoPropertyDescriptor<T> {
	
	/**
	 * zero allowed flag.
	 */
	boolean zeroAllowed=true;
	/**
	 * negative allowed flag.
	 */
	boolean negativeAllowed=true;
	/**
	 * count of integer digits.
	 */
	int lengthOfIntegerPart=10;
	/**
	 * count of decimal digits.
	 */
	int lengthOfDecimalPart=10;		

	/**
	 * Creates a new AbstractNumberBoPropertyDescriptor object. 
	 *
	 * @param parser
	 */
	public AbstractNumberBoPropertyDescriptor(Parser<T> parser) {
		super(parser);
		if(!zeroAllowed) {
			validators.put(NotZeroValidator.class, NotZeroValidator.<T>getInstance());
		}
		if(!negativeAllowed) {
			validators.put(NotNegativeValidator.class, NotNegativeValidator.<T>getInstance());
		}
		validators.put(NumberIntegerLengthValidator.class, new NumberIntegerLengthValidator<T>(lengthOfIntegerPart));
		validators.put(NumberDecimalLengthValidator.class, new NumberDecimalLengthValidator<T>(lengthOfDecimalPart));
	}
	
	public boolean isZeroAllowed() {
		return zeroAllowed;
	}
	
	public void setZeroAllowed(boolean zeroAllowed) {
		this.zeroAllowed = zeroAllowed;
		if (!zeroAllowed) {
			validators.put(NotZeroValidator.class, NotZeroValidator.<T>getInstance());
		} else {
			validators.remove(NotZeroValidator.class);
		}
	}
	
	public boolean isNegativeAllowed() {
		return negativeAllowed;
	}
	
	public void setNegativeAllowed(boolean negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
		if (!negativeAllowed) {
			validators.put(NotNegativeValidator.class, NotNegativeValidator.<T>getInstance());
		} else {
			validators.remove(NotNegativeValidator.class);
		}
	}
	
	public int getLengthOfIntegerPart() {
		return lengthOfIntegerPart;
	}
	
	@SuppressWarnings("unchecked")
	public void setLengthOfIntegerPart(int lengthOfIntegerPart) {
		this.lengthOfIntegerPart = lengthOfIntegerPart;
		NumberIntegerLengthValidator<T> validator = getRegisteredValidatorWithType(NumberIntegerLengthValidator.class);
		if(validator!=null) {
			validator.setMaxIntegerLength(lengthOfIntegerPart);
		} else {
			validators.put(NumberIntegerLengthValidator.class, new NumberIntegerLengthValidator<T>(lengthOfIntegerPart));
		}
	}
	
	public int getLengthOfDecimalPart() {
		return lengthOfDecimalPart;
	}
	
	@SuppressWarnings("unchecked")
	public void setLengthOfDecimalPart(int lengthOfDecimalPart) {
		this.lengthOfDecimalPart = lengthOfDecimalPart;
		NumberDecimalLengthValidator<T> validator = getRegisteredValidatorWithType(NumberDecimalLengthValidator.class);
		if(validator!=null) {
			validator.setMaxDecimalLength(lengthOfDecimalPart);
		} else {
			validators.put(NumberDecimalLengthValidator.class, new NumberDecimalLengthValidator<T>(lengthOfDecimalPart));
		}
	}

}
