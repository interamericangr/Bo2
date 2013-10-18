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

import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.resource.FqcnStringResourceLoader;

import javax.swing.JLabel;

/**
 * A label that shows statically defined content from resource files.
 */
public class BStaticLabel 
extends JLabel 
implements StaticComponent {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Component name.
	 */
	private String name;
	
	/**
	 * Creates a new BStaticLabel object.
	 * 
	 * @param name
	 *            Name of the label.
	 */
	public BStaticLabel(String name) {
		super();
		this.name = name;
		setPreferredSize(Sizes.FORM_COMPONENT);
	}

	@Override
	public String getComponentName() {
		return name;
	}

	@Override
	public void refreshContent() {
		setText(FqcnStringResourceLoader.get().loadStringResource(this, name));
	}

}
