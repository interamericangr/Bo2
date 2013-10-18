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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Self-drawn Label.
 */
public class SelfDrawnLabel extends Label{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new SelfDrawnLabel object. 
	 *
	 * @param id
	 * @param label
	 */
	public SelfDrawnLabel(String id, String label) {
		super(id, label);
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag){
		tag.setName(MarkupConstants.SPAN);
		super.onComponentTag(tag);
	}
	
}
