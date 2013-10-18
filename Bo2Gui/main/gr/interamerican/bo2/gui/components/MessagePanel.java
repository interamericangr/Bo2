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

import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.WEST;
import gr.interamerican.bo2.gui.layout.Sizes;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 * Panel with a text area that presents a message.
 */
public class MessagePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Message
	 */
	String message;
	
	
	
	/**
	 * Creates a new MessagePanel object.
	 * 
	 * @param message
	 *            Message to present.
	 */
	public MessagePanel(String message) {
		super();
		this.message = message;
		paint();
	}
	
	/**
	 * Paints this panel.
	 */
	void paint() {
		Dimension panelSize = Sizes.square(82, 22);
		Dimension scrollSize = Sizes.square(81, 21);
		
		JTextField fld = new JTextField();
		fld.setColumns(5);
		
		JTextArea txt = new JTextArea();
		txt.setRows(20);
		txt.setColumns(80);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		
		txt.setText(message);
		
		JScrollPane scroll = new JScrollPane(txt);
		scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(scrollSize);
		add(scroll);
		
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(NORTH, txt, 5, NORTH, this);
		layout.putConstraint(WEST, txt, 5, WEST, this);
		setLayout(layout);
		
		setPreferredSize(panelSize);		
	}
	
}
