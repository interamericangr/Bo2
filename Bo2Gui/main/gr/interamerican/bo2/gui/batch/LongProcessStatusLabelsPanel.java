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

import static gr.interamerican.bo2.gui.batch.ComponentPropertiesFactory.label;
import static javax.swing.SpringLayout.WEST;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;

import java.awt.Dimension;

import javax.swing.SpringLayout;

/**
 * Panel containing the labels for a {@link LongProcessStatusPanel}.
 * 
 * This panel is used when a lot {@link LongProcessStatusPanel}s are
 * piled one under the other. This panel is drawn on top of the pile
 * and contains the labels for the rest panels.
 */
public class LongProcessStatusLabelsPanel 
extends BPanel<Void> {
	
	/**
	 * Fields
	 */
	@SuppressWarnings("nls")
	protected static final String[] FIELDS = { 
		"name", 
		"finished", "finishedAbnormally", "paused", 
		"startTime", "endTime",
		"processedCount", "successesCount", "failuresCount"
	};
	
	/**
	 * Lengths
	 */
	protected static final int[] LENGTHS = { 
		7, 2, 2, 2, 16, 16, 8, 8, 8  
	};
	
	
	
	/**
	 * Creates a new LongProcessStatusLabelsPanel object. 
	 */
	public LongProcessStatusLabelsPanel() {
		super(null);
	}

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void paint() {
		int space = 5;
		int length = 0;
		for (int i = 0; i < FIELDS.length; i++) {
			length+=LENGTHS[i];
			addStaticLabel(FIELDS[i], label(LENGTHS[i]));
		}
		double width = length * (Sizes.FORM_COMPONENT_CHAR_WIDTH + space) +
			3 * space;		    
		double height = Sizes.FORM_COMPONENT_HEIGHT + 2*space;
		Dimension size = Sizes.dimension(width, height);		
		setPreferredSize(size);
		
		Layout.layAsRow(this, space, space);
		SpringLayout layout = (SpringLayout)getLayout();
		layout.putConstraint(WEST, getComponent(0), 10, WEST, this);
	}
}
