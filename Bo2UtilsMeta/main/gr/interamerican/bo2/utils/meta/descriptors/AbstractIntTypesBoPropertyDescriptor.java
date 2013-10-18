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

/**
 * {@link BoPropertyDescriptor} for types without decimal digits.
 * 
 * @param <T> 
 */
public abstract class AbstractIntTypesBoPropertyDescriptor<T extends Number> 
extends AbstractNumberBoPropertyDescriptor<T> {

	/**
	 * Creates a new IntegerBoPropertyDescriptor object.
	 * @param parser 
	 */
	public AbstractIntTypesBoPropertyDescriptor(Parser<T> parser) {
		super(parser);
		this.lengthOfDecimalPart = 0;
	}
	
	@Override
	public void setLengthOfDecimalPart(int lengthOfDecimalPart) {		
		super.setLengthOfDecimalPart(0);
	}
	
}
