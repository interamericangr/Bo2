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
package gr.interamerican.bo2.samples.pib;

import gr.interamerican.bo2.utils.beans.PropertiesInitializedBean;

import java.util.Properties;

/**
 * Sample properties initialized bean.
 * 
 */
public class SamplePib extends PropertiesInitializedBean {
	/**
	 * property.
	 */
	String sample;

	/**
	 * Creates a new SamplePIB object. 
	 *
	 * @param properties
	 */
	public SamplePib(Properties properties) {
		super(properties);
	}

	/**
	 * Gets the sample.
	 *
	 * @return Returns the sample
	 */
	public String getSample() {
		return sample;
	}

	/**
	 * Assigns a new value to the sample.
	 *
	 * @param sample the sample to set
	 */
	public void setSample(String sample) {
		this.sample = sample;
	}
	
}
