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

import gr.interamerican.bo2.gui.components.BButton;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.listeners.MethodBasedActionListener;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * JFrame that keeps a BPanel.
 */
public class BFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a BFrame with the specified BPanel.
	 * @param panel
	 * 
	 * @return Returns a BRfame that keeps the specified panel. 
	 */
	public static BFrame show(JPanel panel) {
		BFrame frame = new BFrame();
		frame.setPanel(panel);
		frame.setVisible(true);
		frame.setEnabled(true);
		return frame;
	}
	
	/**
	 * Creates a BFrame with the specified BPanel.
	 * @param panel
	 * 
	 * @return Returns a BRfame that keeps the specified panel. 
	 */
	public static BFrame mainWindow(JPanel panel) {
		BFrame frame = show(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	/**
	 * Panel presented in this frame.
	 */
	JPanel panel;
	

	/**
	 * Creates a new BFrame object.
	 */
	public BFrame() {		
		super();
	}
	
	/**
	 * Sets the panel of this frame.
	 * 
	 * @param panel
	 */
	public void setPanel(JPanel panel) {
		removeOldPanel();
		this.panel = panel;
		addPanel();
		repaint();
	}
	
	/**
	 * Adds the panel to the frame.
	 */
	void addPanel() {
		setTitle(panel.getName());
		int w = Double.valueOf(panel.getPreferredSize().getWidth()).intValue();
		int h = Double.valueOf(panel.getPreferredSize().getHeight()).intValue();		
		Dimension size = new Dimension(w+40,h+60);
		setPreferredSize(size);
		setSize(size);
		Container c = getContentPane();
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(size);
		scrollPane.setSize(size);
		c.add(scrollPane);
	}
	
	/**
	 * Removes the old panel.
	 */
	void removeOldPanel() {
		if (panel!=null) {
			getContentPane().removeAll();
			invalidate();
		}
	}
	
	/**
	 * Adds an action listener to a button of the child panel.
	 * 
	 * @param methodName
	 */
	protected void addActionListener(String methodName) {
		if (panel instanceof BPanel) {
			BPanel<?> b = (BPanel<?>) panel;
			BButton button = b.getButton(methodName);
			MethodBasedActionListener listener = 
				new MethodBasedActionListener(methodName, this);
			button.addActionListener(listener);
		} else {
			String msg = "Panel is not a BPanel"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		

}
