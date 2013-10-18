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
package gr.interamerican.bo2.gui.frames;

import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.WEST;
import gr.interamerican.bo2.gui.components.MessagePanel;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.utils.StringConstants;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

/**
 * Pop up window.
 */
public class PopUpFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Pops up a {@link PopUpFrame} with the
	 * specified message.
	 * 
	 * @param msg
	 *        The message to present. 
	 * @param title 
	 *        Title of the pop up frame.
	 */
	public static void popUp (String msg, String title) {
		PopUpFrame frame = new PopUpFrame(msg,title);
		frame.setVisible(true);
		frame.setEnabled(true);		
	}
	
	

	
	/**
	 * Creates a new PopUpFrame and makes it visible.
	 * 
	 * @param text
	 * @param title 
	 */
	public PopUpFrame(String text, String title) {
		super(title);
		MessagePanel panel = new MessagePanel(text); 
		add(panel);
		
		Dimension size = Sizes.increase(panel.getPreferredSize(), 20, 20);		
		setSize(size);
		
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(NORTH, panel, 10, NORTH, this);
		layout.putConstraint(WEST, panel, 10, WEST, this);
		
		setLayout(layout);
	}
	
	/**
	 * constructor
	 * 
	 * @param text 
	 */
	public PopUpFrame(String text) {
		this(text,StringConstants.EMPTY);
	}
	
	
}
