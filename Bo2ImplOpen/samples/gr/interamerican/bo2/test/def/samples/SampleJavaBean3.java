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
package gr.interamerican.bo2.test.def.samples;

import java.io.Serializable;

/**
 * The Interface SampleJavaBean3.
 */
public interface SampleJavaBean3 extends Serializable {
	
	/**
	 * Gets the picture.
	 *
	 * @return the picture
	 */
	public byte[] getPicture();
	
	/**
	 * Sets the picture.
	 *
	 * @param pic the new picture
	 */
	public void setPicture(byte[] pic);
	
	/**
	 * Gets the measurements.
	 *
	 * @return the measurements
	 */
	public Integer[] getMeasurements();
	
	/**
	 * Sets the measurements.
	 *
	 * @param measurements the new measurements
	 */
	public void setMeasurements(Integer[] measurements);
}