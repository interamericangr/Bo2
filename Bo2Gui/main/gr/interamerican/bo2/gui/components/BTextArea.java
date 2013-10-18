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
package gr.interamerican.bo2.gui.components;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.utils.StringUtils;

import javax.swing.JTextArea;

/**
 * TextArea.
 */
public class BTextArea extends JTextArea implements ValueComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
		
	/**
	 * Name.
	 */
	String name;
	
	
	/**
	 * Creates a new TextArea object.
	 * 
	 * @param name
	 *            Name of the text field.
	 * 
	 */
	public BTextArea(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getComponentName() {
		return name;
	}

	@Override
	public Object getValue() {
		return getText();
	}

	@Override
	public void setValue(Object value) {
		setText(StringUtils.toString(value,EMPTY));		
	}

}
