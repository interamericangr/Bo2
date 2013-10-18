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

import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;

/**
 * Span fragment that consists of a sequence of spaces.
 */
public class SpaceSpanFragment 
implements SpanFragment {
	/**
	 * Length.
	 */
	int length;

	/**
	 * Creates a new TextStringElement object. 
	 *
	 * @param length
	 */
	SpaceSpanFragment(int length) {
		super();
		this.length = length;
	}

	
	public void appendTo(TextSpanElement span) {
		TextSElement space = span.newTextSElement();
		space.setTextCAttribute(length);
	}

}
