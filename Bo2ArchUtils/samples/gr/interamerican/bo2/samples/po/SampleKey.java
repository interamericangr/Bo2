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

import gr.interamerican.bo2.arch.Key;

/**
 * The Class SampleKey.
 */
public  class SampleKey implements Key{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3688328162860348174L;


	/**
	 * Creates a new UserKey object. 
	 */
	public SampleKey() {
		//empty
	}
	
	@Override
	public int compareTo(Key o) {
		return 0;
	}

}