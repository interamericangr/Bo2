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

import static gr.interamerican.bo2.gui.batch.ComponentPropertiesFactory.bareCheckBox;
import static gr.interamerican.bo2.gui.batch.ComponentPropertiesFactory.bareTextField;
import gr.interamerican.bo2.arch.batch.LongProcessStatus;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.frames.PopUpFrame;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;

import java.awt.Dimension;

/**
 * Long process status panel.
 */
public class LongProcessStatusPanel 
extends BPanel<LongProcessStatus> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Names of non-boolean fields
	 */
	@SuppressWarnings("nls")
	protected static final String[] NAME_FIELDS = { 
		"name" 
	};
	
	/**
	 * Names of boolean fields
	 */
	@SuppressWarnings("nls")
	protected static final String[] FLAGS = { 
		"finished", "finishedAbnormally", "paused" 
	};
	
	/**
	 * Names of non-boolean fields
	 */
	@SuppressWarnings("nls")
	protected static final String[] TIME_FIELDS = { 
		"startTime", "endTime", 
	};
	
	/**
	 * Names of non-boolean fields
	 */
	@SuppressWarnings("nls")
	protected static final String[] COUNT_FIELDS = { 
		"processedCount", "successesCount", "failuresCount" 
	};
	

	/**
	 * Creates a new LongProcessStatusPanel object. 
	 *
	 * @param model
	 */
	public LongProcessStatusPanel(LongProcessStatus model) {
		super(model);
	}

	@Override
	public void paint() {
		int nfl = 5;
		int ffl = 2;
		int tfl = 13;
		int cfl = 6;
	
		addModelBoundTextFields(NAME_FIELDS, bareTextField(nfl, false));  
		addModelBoundCheckBoxes(FLAGS, bareCheckBox(false));
		addModelBoundTextFields(TIME_FIELDS, bareTextField(tfl, false));
		addModelBoundTextFields(COUNT_FIELDS, bareTextField(cfl, false));
		addSimpleButton("showException", null); //$NON-NLS-1$
		
		int space = 5;
		Layout.layAsRow(this, space, space);
		
		double width = 
			(NAME_FIELDS.length * (nfl * Sizes.FORM_COMPONENT_CHAR_WIDTH + space)) + 
			(FLAGS.length * (ffl * Sizes.FORM_COMPONENT_CHAR_WIDTH + space)) +		          
			(TIME_FIELDS.length * (tfl * Sizes.FORM_COMPONENT_CHAR_WIDTH + space)) +		           
			(COUNT_FIELDS.length * (cfl * Sizes.FORM_COMPONENT_CHAR_WIDTH + space)) +
			(19 * Sizes.FORM_COMPONENT_CHAR_WIDTH + space) +
			5 * space;		    
		double height = Sizes.FORM_COMPONENT_HEIGHT + 2*space;
		Dimension size = Sizes.dimension(width, height);		
		setPreferredSize(size);
		
		Layout.layAsRow(this, space, space);
	}
	
	/**
	 * Shows the exception message.
	 */
	protected void showException() {		
		String msg = model.getExceptionMessage();
		String title = "Error in thread " + model.getName(); //$NON-NLS-1$
		PopUpFrame.popUp(msg, title);				
	}
	
	

}
