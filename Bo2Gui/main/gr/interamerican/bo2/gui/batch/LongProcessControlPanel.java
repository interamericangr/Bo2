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

import gr.interamerican.bo2.arch.batch.LongProcessControl;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;

/**
 * Long process command panel.
 */
public class LongProcessControlPanel extends BPanel<LongProcessControl> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Names of buttons
	 */
	@SuppressWarnings("nls")
	private static final String[] COMMANDS = { "pause", "resume", "forceQuit", "tidy" };
	
	/**
	 * Creates a new LongProcessCommandPanel object. 
	 *
	 * @param model
	 */
	public LongProcessControlPanel(LongProcessControl model) {
		super(model);
	}
	
	@Override
	public void paint() {
		int space = 5;
		setLayout(Layout.leftFlow());
		for (String cmd : COMMANDS) {			
			addModelBoundButton(cmd, null);			
		}
		double width = 35*Sizes.FORM_COMPONENT_CHAR_WIDTH + 2*space; 
		double height = Sizes.FORM_COMPONENT_HEIGHT + 2*space;		
		setPreferredSize(Sizes.dimension(width, height));
		Layout.layAsRow(this, space, space);
	}

}
