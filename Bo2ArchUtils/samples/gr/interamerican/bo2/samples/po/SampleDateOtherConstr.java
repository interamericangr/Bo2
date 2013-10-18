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
package gr.interamerican.bo2.samples.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * SampleDate.
 * 
 */
public class SampleDateOtherConstr extends Date {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3955331052948511684L;
	
	/**
	 * time
	 */
	BigDecimal time;

	/**
	 * Creates a new SampleDate object. 
	 *
	 * @param time
	 */
	public SampleDateOtherConstr(BigDecimal time){
		this.time = time;
	}
	
}
