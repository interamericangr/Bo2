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
package gr.interamerican.bo2.samples.ibean;

import java.io.Serializable;

/**
 * sample interface.
 */
public interface SuperSampleInterface extends Serializable {
	
	/**
	 * Gets the field 4.
	 *
	 * @return the field 4
	 */
	public String getField4();

	/**
	 * Sets the field 4.
	 *
	 * @param field4 the new field 4
	 */
	public void setField4(String field4);

	/**
	 * Gets the field 5.
	 *
	 * @return the field 5
	 */
	public Integer getField5();

	/**
	 * Sets the field 5.
	 *
	 * @param field5 the new field 5
	 */
	public void setField5(Integer field5);

	/**
	 * Gets the field 6.
	 *
	 * @return the field 6
	 */
	public Object getField6();

	/**
	 * Sets the field 6.
	 *
	 * @param field6 the new field 6
	 */
	public void setField6(Object field6);
}
