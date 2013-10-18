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
package gr.interamerican.bo2.gui.util;

import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.gui.util.model.SystemMonitorModel;
import gr.interamerican.bo2.impl.open.creation.Factory;

/**
 * panel for monitoring the status of jvm
 */
public class SystemMonitorPanel extends BPanel<SystemMonitorModel> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fields
	 */
	@SuppressWarnings("nls")
	private static String[] FIELDS = { "usedMemory", "freeMemory", "totalMemory", "maxMemory", "gcEvents", "gcTime" };
	/**
	 * default constructor.
	 */
	public SystemMonitorPanel() {
		super(Factory.create(SystemMonitorModel.class));
		
	}
	
	/**
	 * Component properties.
	 * 
	 * @return Returns a ComponentProperties object.
	 */
	TextFieldProperties properties() {
		TextFieldProperties properties = Factory.create(TextFieldProperties.class);
		properties.setEnabled(true);
		properties.setEditable(false);
		properties.setColumns(10);
		properties.setHasLabel(true);
		properties.setLabelLength(10);
		return properties;
	}
	
	@Override
	public void paint() {			
		TextFieldProperties fp = properties();		
		addModelBoundTextFields(FIELDS, fp); 		
		setPreferredSize(Sizes.square(20, FIELDS.length+1, true));
		Layout.layAsStackOfLabeledFields(this, 5, 5);
	}

	

	

}
