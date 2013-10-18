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

import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.TextFieldProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;

import java.awt.Dimension;

/**
 * panel for monitoring the status of jvm
 */
public class MultiThreadedLongProcessStatsPanel 
extends BPanel<MultiThreadedLongProcess> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fields
	 */
	@SuppressWarnings("nls")
	private static String[] FIELDS = { 
		"countOfSubProcesses",  
		"countOfFinishedSubProcesses",
		"countOfAbnormallyFinishedSubProcesses",
		"countOfPausedSubProcesses"
	};
	
	
	/**
	 * Creates a new MultiThreadedLongProcessStatsPanel object. 
	 *
	 * @param model
	 */
	public MultiThreadedLongProcessStatsPanel(MultiThreadedLongProcess model) {
		super(model);
	}

	@Override
	public void paint() {
		addModelBoundTextFields(FIELDS, textFieldProperties());
		Dimension size = Sizes.square(26,FIELDS.length+1,false); 
		setPreferredSize(size);		
		Layout.layAsStackOfLabeledFields(this, 5, 5);
	}

	/**
	 * Default component properties for text boxes..
	 * 
	 * @return Returns a ComponentProperties.
	 */
	TextFieldProperties textFieldProperties() {
		TextFieldProperties properties = Factory.create(TextFieldProperties.class);
		properties.setEnabled(false);
		properties.setEditable(false);
		properties.setColumns(10);
		properties.setHasLabel(true);
		properties.setLabelLength(15);
		return properties;
	}

	

}
