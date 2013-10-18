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
package gr.interamerican.bo2.test.impl.samples;

import gr.interamerican.bo2.test.def.samples.SampleCalculator;

/**
 * 
 */
public class SampleCalculatorImpl implements SampleCalculator{

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleCalculator#add(java.lang.Integer, java.lang.Integer)
	 */
	public Integer add(Integer i1, Integer i2) {
		return new Integer(i1+i2);
	}

	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.test.samples.SampleCalculator#subtract(java.lang.Integer, java.lang.Integer)
	 */
	public Integer subtract(Integer i1, Integer i2) {
		return new Integer(i1-i2);
	}
	

}
