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
package gr.interamerican.bo2.samples.beans;

/**
 * On-Off bean.
 */
public class OnOff {
	/**
	 * flag.
	 */
	boolean flag;
	/**
	 * value.
	 */
	int value;
	/**
	 * label.
	 */
	String label;
	
	/**
	 * set on.
	 */
	public void on() {
		flag = true;
		value = 1;
		label = "on"; //$NON-NLS-1$
	}
	
	/**
	 * set on.
	 */
	public void off() {
		flag = false;
		value = 0;
		label = "off"; //$NON-NLS-1$
	}

	/**
	 * Gets the flag.
	 *
	 * @return Returns the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * Assigns a new value to the flag.
	 *
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * Gets the value.
	 *
	 * @return Returns the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Assigns a new value to the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Gets the label.
	 *
	 * @return Returns the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Assigns a new value to the label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	
	

}
