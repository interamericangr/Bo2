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
package gr.interamerican.bo2.gui.util;

import gr.interamerican.bo2.gui.components.StaticComponent;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilities of Bo2Gui.
 */
public class Bo2GuiUtils {
	
	/**
	 * Returns the {@link Container} hierarchy leading to the specifying component.
	 * The last element is the component itself.
	 * 
	 * @param component
	 * @return Component top-down hierarchy.
	 */
	public static List<Component> getContainerTopDownHierarchy(Component component) {
		List<Component> hierarchy = new ArrayList<Component>();
		hierarchy.add(component);
		
		Container current = component.getParent();
		
		while(current!=null) {
			hierarchy.add(current);
			current = current.getParent();
		}
		
		Collections.reverse(hierarchy);
		return hierarchy;
	}
	
	/**
	 * Refreshes the static content of a component and its
	 * children.
	 * 
	 * @param component
	 */
	public static void refreshStaticContent(Component component) {
		if(component instanceof StaticComponent) {
			((StaticComponent) component).refreshContent();
		}
		
		if(!(component instanceof Container)) {
			return;
		}
		
		Container container = (Container) component;
		
		Component[] components = container.getComponents();
		for(Component cmp : components) {
			refreshStaticContent(cmp);
		}
	}
	
}
