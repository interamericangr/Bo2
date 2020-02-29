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
package gr.interamerican.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

import gr.interamerican.wicket.utils.MarkupConstants;

/**
 * Behavior that will enforce a Font Size to a Component.
 */
public class FontUpdatingBehavior extends Behavior {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Desired Font Size
	 */
	final String fontSize;

	/**
	 * Public Constructor
	 *
	 * @param fontSize
	 *            Desired Font Size
	 */
	public FontUpdatingBehavior(String fontSize) {
		this.fontSize = fontSize;
	}

	@SuppressWarnings("nls")
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		tag.append(MarkupConstants.STYLE, "font-size: " + fontSize, "; ");
	}
}