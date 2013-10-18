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
package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.gui.properties.CheckBoxProperties;
import gr.interamerican.bo2.gui.properties.LabelProperties;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;

/**
 * Predefined component properties.
 */
public class ComponentPropertiesFactory {
	
	
	/**
	 * Component properties for bare check boxes.
	 * 
	 * @param editable
	 *        Indication if it is editable.
	 * 
	 * @return Returns a check box properties object.
	 */
	public static CheckBoxProperties bareCheckBox(boolean editable) {
		CheckBoxProperties properties = Factory.create(CheckBoxProperties.class);
		properties.setEnabled(editable);
		properties.setHasLabel(false);
		properties.setLength(2);
		return properties;
	}
	
	
	/**
	 * Default component properties for text boxes.
	 * 
	 * @param length
	 *        length in chars.
	 * @param editable 
	 *        Indication if the box will e editable.
	 * 
	 * @return Returns a ComponentProperties.
	 */
	public static TextFieldProperties bareTextField(int length, boolean editable) {
		TextFieldProperties properties = Factory.create(TextFieldProperties.class);
		properties.setEnabled(editable);
		properties.setEditable(editable);	
		properties.setHasLabel(false);
		properties.setColumns(length);
		return properties;
	}
	
	/**
	 * Component properties for a label.
	 * 
	 * @param length
	 *        length in chars.
	 * 
	 * @return Returns a ComponentProperties.
	 */
	public static LabelProperties label(int length) {
		LabelProperties properties = Factory.create(LabelProperties.class);
		properties.setLabelLength(length);
		return properties;
	}
	


}
