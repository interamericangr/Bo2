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
package gr.interamerican.bo2.gui.layout;

import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.WEST;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * 
 */
public class Layout {
	
	/**
	 * Default layout.
	 * 
	 * @return Returns a layout.
	 */
	public static LayoutManager leftFlow() {
		return new FlowLayout(FlowLayout.LEFT, 5, 4);
	}
	
	/**
	 * Sets the layout of a panel so that all its components
	 * are adjusted in a row. <br/>
	 * 
	 * @param panel 
	 *        Panel to set the layout.
	 * @param x
	 *        Space between each component
	 * @param y
	 *        Space from the top of the panel to the top of each
	 *        component.
	 */
	public static void layAsRow(JPanel panel, int x, int y) {
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(WEST, panel.getComponent(0), x, WEST, panel);		
		layout.putConstraint(NORTH, panel.getComponent(0), y, NORTH, panel);
		for(int i=1; i<panel.getComponentCount(); i++) {
			Component relative = panel.getComponent(i-1);
			layout.putConstraint(WEST, panel.getComponent(i), x, EAST, relative);
			layout.putConstraint(NORTH, panel.getComponent(i), y, NORTH, panel);
		}
		panel.setLayout(layout);
	}
	
	/**
	 * Puts constraints so that the pair of components 
	 * a label and a field are presented as a pair.
	 * 
	 * @param layout 
	 * @param label
	 * @param field
	 * @param panel
	 * @param x 
	 */
	public static void layAsPair(SpringLayout layout, Component label, 
			Component field, JPanel panel, int x) {
		layout.putConstraint(WEST, label, x, WEST, panel);	
		layout.putConstraint(WEST, field, x, EAST, label);		
		layout.putConstraint(EAST, field, -x, EAST, panel);
	}
	
	/**
	 * Sets the layout of a panel so that all its components
	 * are adjusted in a stack of pairs of components with each
	 * pair being the label of a field and its component. <br/>
	 * 
	 * 
	 * TODO: Manage odd number of components in the panel.
	 * 
	 * @param panel 
	 *        Panel to set the layout.
	 * @param x
	 *        Space between each component
	 * @param y
	 *        Space from the top of the panel to the top of each
	 *        component.
	 * @param exceptLast 
	 * @param xForLast 
	 */
	public static void layAsStackOfLabeledFields(JPanel panel, int x, int y, int exceptLast, int xForLast) {
		SpringLayout layout = new SpringLayout();
		if (panel.getComponentCount()<=1) {
			return; 
		}
		
		Component label = panel.getComponent(0);
		Component field = panel.getComponent(1);
		
		layAsPair(layout, label, field, panel, x);		
		layout.putConstraint(NORTH, label, y, NORTH, panel);
		layout.putConstraint(NORTH, field, y, NORTH, panel);
		
		int lastElements = exceptLast;
		int firstComponentsCount = panel.getComponentCount() - lastElements;
		if (firstComponentsCount%2==1) {
			firstComponentsCount--;
			lastElements++;
		}
		for(int i=2; i<firstComponentsCount; i++) {
			Component northern = panel.getComponent(i-1);
			label = panel.getComponent(i);
			i++;
			field = panel.getComponent(i);
			layAsPair(layout, label, field, panel, x);
			layout.putConstraint(NORTH, label, y, SOUTH, northern);
			layout.putConstraint(NORTH, field, y, SOUTH, northern);
		}
		int xx = xForLast;
		if (xx==0) {
			xx=x;
		}
		for(int i=firstComponentsCount; i<panel.getComponentCount(); i++) {
			Component northern = panel.getComponent(i-1);
			Component c = panel.getComponent(i);
			layout.putConstraint(WEST, c, xx, WEST, panel);
			layout.putConstraint(NORTH, c, y, SOUTH, northern);
		}
		panel.setLayout(layout);
	}
	
	/**
	 * Sets the layout of a panel so that all its components
	 * are adjusted in a stack of pairs of components with each
	 * pair being the label of a field and its component. <br/>
	 * 
	 * @param panel 
	 *        Panel to set the layout.
	 * @param x
	 *        Space between each component
	 * @param y
	 *        Space from the top of the panel to the top of each
	 *        component.
	 */
	public static void layAsStackOfLabeledFields(JPanel panel, int x, int y) {
		layAsStackOfLabeledFields(panel,x,y,0,0);
	}
	
	/**
	 * Sets the layout of a panel so that all its components
	 * are adjusted in a stack, one under the other. <br/>
	 * 
	 * @param panel 
	 *        Panel to set the layout.
	 * @param x
	 *        Space from the left of the panel to the left of each
	 *        component.	         
	 * @param y
	 *        Space between each component.
	 */
	public static void layAsStack(JPanel panel, int x, int y) {
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(WEST, panel.getComponent(0), x, WEST, panel);		
		layout.putConstraint(NORTH, panel.getComponent(0), y, NORTH, panel);
		for(int i=1; i<panel.getComponentCount(); i++) {
			Component relative = panel.getComponent(i-1);
			layout.putConstraint(WEST, panel.getComponent(i), x, WEST, panel);
			layout.putConstraint(NORTH, panel.getComponent(i), y, SOUTH, relative);
		}
		panel.setLayout(layout);
	}
	
	
	
	
	

}
