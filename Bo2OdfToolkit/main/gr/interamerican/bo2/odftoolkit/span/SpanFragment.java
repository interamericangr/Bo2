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
package gr.interamerican.bo2.odftoolkit.span;

import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Element of a String.
 * 
 * Represents a text, or a sequence of blank spaces.
 */
public interface SpanFragment {
	
	
	/**
	 * Appends this string element to the specified TextSpanElement.
	 * 
	 * @param span
	 */
	void appendTo(TextSpanElement span);
	

}
