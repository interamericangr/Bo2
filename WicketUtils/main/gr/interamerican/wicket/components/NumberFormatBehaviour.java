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
package gr.interamerican.wicket.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * 
 */
public class NumberFormatBehaviour extends AbstractBehavior {

	/**
	 *  the serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the number of decimals.
	 * 
	 */
	private Integer decimals;

	/**
	 * Creates a new NumberFormatBehaviour object. 
	 * 
	 * @param decimals 
	 *
	 */
	public NumberFormatBehaviour(Integer decimals){
		this.decimals = decimals;
	}
	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)
	 */
		@Override
		public void onComponentTag(final Component component, final ComponentTag tag) { 
			tag.getAttributes().put("onchange","format_number(id,value,"+decimals+");");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$  
		}
}
