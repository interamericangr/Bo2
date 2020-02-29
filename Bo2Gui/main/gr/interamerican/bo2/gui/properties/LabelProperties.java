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
package gr.interamerican.bo2.gui.properties;

/**
 * Properties for a label.
 */
public interface LabelProperties {
	
	/**
	 * Gets the length of the label.
	 * 
	 * @return Returns the length of the label.
	 */
	int getLabelLength();
	
	/**
	 * Sets the length of the label.
	 *
	 * @param labelLength the new label length
	 */
	void setLabelLength(int labelLength);

}
