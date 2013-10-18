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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.meta.descriptors.AbstractBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.formatters.MoneyFormatter;
import gr.interamerican.bo2.utils.meta.ext.parsers.MoneyParser;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Property description for a {@link Money} property.
 */
public class MoneyBoPropertyDescriptor
extends AbstractBoPropertyDescriptor<Money> {
	
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
	 * Creates a new MoneyBoPropertyDescriptor object. 
	 */
	public MoneyBoPropertyDescriptor() {
		super(null);		
	}

	/**
	 * Gets the zeroAllowed.
	 *
	 * @return Returns the zeroAllowed
	 */
	public boolean isZeroAllowed() {
		return zeroAllowed;
	}

	/**
	 * Assigns a new value to the zeroAllowed.
	 *
	 * @param zeroAllowed the zeroAllowed to set
	 */
	public void setZeroAllowed(boolean zeroAllowed) {
		this.zeroAllowed = zeroAllowed;
	}

	/**
	 * Gets the negativeAllowed.
	 *
	 * @return Returns the negativeAllowed
	 */
	public boolean isNegativeAllowed() {
		return negativeAllowed;
	}

	/**
	 * Assigns a new value to the negativeAllowed.
	 *
	 * @param negativeAllowed the negativeAllowed to set
	 */
	public void setNegativeAllowed(boolean negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
	}

	/**
	 * Gets the lengthOfIntegerPart.
	 *
	 * @return Returns the lengthOfIntegerPart
	 */
	public int getLengthOfIntegerPart() {
		return lengthOfIntegerPart;
	}

	/**
	 * Assigns a new value to the lengthOfIntegerPart.
	 *
	 * @param lengthOfIntegerPart the lengthOfIntegerPart to set
	 */
	public void setLengthOfIntegerPart(int lengthOfIntegerPart) {
		this.lengthOfIntegerPart = lengthOfIntegerPart;
	}

	/**
	 * Gets the lengthOfDecimalPart.
	 *
	 * @return Returns the lengthOfDecimalPart
	 */
	public int getLengthOfDecimalPart() {
		return lengthOfDecimalPart;
	}

	/**
	 * Assigns a new value to the lengthOfDecimalPart.
	 *
	 * @param lengthOfDecimalPart the lengthOfDecimalPart to set
	 */
	public void setLengthOfDecimalPart(int lengthOfDecimalPart) {
		this.lengthOfDecimalPart = lengthOfDecimalPart;
	}

	@Override
	protected Formatter<Money> getFormatter() {
		return MoneyFormatter.INSTANCE;
	}
	
	@Override
	public Money parse(String value) throws ParseException {
		return new MoneyParser(getLengthOfDecimalPart()).parse(value);
	}

}
