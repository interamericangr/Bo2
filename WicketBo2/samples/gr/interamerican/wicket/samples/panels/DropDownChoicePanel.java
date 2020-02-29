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
package gr.interamerican.wicket.samples.panels;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;

import gr.interamerican.bo2.arch.ext.Codified;

/**
 * Sample {@link Panel} that contains a {@link DropDownChoice} component.
 */
public class DropDownChoicePanel extends Panel{

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link DropDownChoice} component.
	 */
	@SuppressWarnings("rawtypes")
	private DropDownChoice<? extends Codified> dropDown;
	
	/**
	 * Creates a new DropDownChoicePanel object. 
	 *
	 * @param id
	 */
	@SuppressWarnings("nls")
	public DropDownChoicePanel(String id) {
		super(id);
		dropDown = new DropDownChoice<>("dropdown");
		add(dropDown);
	}
}
