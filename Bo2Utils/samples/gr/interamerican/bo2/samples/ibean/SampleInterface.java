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

/**
 * sample interface.
 */
public interface SampleInterface extends SuperSampleInterface {

	/**
	 * Gets the field 1.
	 *
	 * @return the field 1
	 */
	public String getField1();

	/**
	 * Sets the field 1.
	 *
	 * @param field1 the new field 1
	 */
	public void setField1(String field1);

	/**
	 * Gets the field 2.
	 *
	 * @return the field 2
	 */
	public Integer getField2();

	/**
	 * Sets the field 2.
	 *
	 * @param field2 the new field 2
	 */
	public void setField2(Integer field2);

	/**
	 * Gets the field 3.
	 *
	 * @return the field 3
	 */
	public Object getField3();

	/**
	 * Sets the field 3.
	 *
	 * @param field3 the new field 3
	 */
	public void setField3(Object field3);
}
