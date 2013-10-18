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
 * 
 */
public interface SampleJavaBean1 extends Serializable, SampleJavaBeanSuperType {
	
	public void setName(String name);
	
	public String getName();
	
	public byte[] getPicture();
	
	public void setPicture(byte[] pic);
	
	public Integer[] getMeasurements();
	
	public void setMeasurements(Integer[] measurements);

}
