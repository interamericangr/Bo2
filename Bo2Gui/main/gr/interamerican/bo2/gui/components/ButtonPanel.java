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

import gr.interamerican.bo2.gui.listeners.MethodBasedActionListener;
import gr.interamerican.bo2.gui.properties.ButtonProperties;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * 
 */
public class ButtonPanel 
extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ButtonPanel object. 
	 *
	 */
	public ButtonPanel() {
		super();		
	}
	
	/**
	 * Buttons.
	 */
	Map<String, BButton> buttons = new HashMap<String, BButton>();
	
	/**
	 * Adds a button.
	 * 
	 * @param methodName
	 *            Name of method that will be triggered. The same will be also the name of the new BButton that is
	 *            created.
	 * @param properties
	 *            Component properties.
	 * @param target
	 *            Object that owns the event handler method.
	 * 
	 * @return Returns the new button.
	 */
	public BButton addButton(String methodName, ButtonProperties properties, Object target) {
		BButton button = new BButton(methodName);
		setActionListener(button, target);
		if (properties != null) {
			button.setPreferredSize(properties.getPreferredSize());			
		}
		add(button);
		buttons.put(methodName, button);
		return button;
	}
	
	/**
	 * Sets the action listener for the specified button.
	 * 
	 * @param button
	 *            Button.
	 * @param target
	 *            Target object.
	 */
	void setActionListener(BButton button, Object target) {
		ActionListener[] listeners = button.getActionListeners();
		for (ActionListener listener : listeners) {
			button.removeActionListener(listener);
		}
		String methodName = button.getComponentName();
		MethodBasedActionListener al = new MethodBasedActionListener(methodName, target);
		button.addActionListener(al);
	}
	
	

	
	
	

}
