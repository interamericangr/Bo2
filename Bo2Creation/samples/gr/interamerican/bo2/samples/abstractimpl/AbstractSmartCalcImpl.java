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
package gr.interamerican.bo2.samples.abstractimpl;

import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.samples.interfaces.SmartCalc;
import gr.interamerican.bo2.utils.beans.BdCalculator;
import gr.interamerican.bo2.utils.beans.Range;

import java.math.BigDecimal;
import java.util.List;

/**
 * Abstract implementation of {@link SmartCalc}.
 */
public abstract class AbstractSmartCalcImpl 
implements SmartCalc {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id and name.
	 */
	@DelegateProperties("")
	private IBeanWithIdAndName identity = new IBeanWithIdAndNameImpl();
	
	/**
	 * getResult,add,subtract,reset
	 */
	@DelegateMethods("getResult,add,subtract,reset")
	BdCalculator calc = new BdCalculator(2, false);
	
	/**
	 * Range.
	 */
	@DelegateProperties("left,right")
	@DelegateMethods("contains")
	private Range<BigDecimal> range = new Range<BigDecimal>();
	
	/**
	 * numbersToAdd.
	 */
	@Property private List<BigDecimal> numbersToAdd;
	
	/**
	 * alarmLimit.
	 */	
	@Property BigDecimal alarmLimit;
	

	
	public BigDecimal addNumbers() {
		this.reset();
		for (BigDecimal bd : numbersToAdd) {
			add(bd);
		}		
		return getResult();
	}

	

}
