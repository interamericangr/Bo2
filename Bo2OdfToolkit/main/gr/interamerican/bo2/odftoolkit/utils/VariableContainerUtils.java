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
package gr.interamerican.bo2.odftoolkit.utils;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclsElement;
import org.odftoolkit.odfdom.dom.element.text.TextVariableDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextVariableDeclsElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.common.field.VariableContainer;

/**
 * Utility class that depends only on OdfToolkit classes.
 */
public class VariableContainerUtils {
	
	/**
	 * Gets a list with the names of the variable fields.
	 * 
	 * @param container
	 * @return Returns the list.
	 */
	public static List<TextUserFieldDeclElement> getUserFields(VariableContainer container) {
		OdfElement containerElement = container.getVariableContainerElement();
		TextUserFieldDeclsElement userFields = 
			OdfElement.findFirstChildNode(TextUserFieldDeclsElement.class, containerElement);
		if (userFields!=null) {
			return XmlUtils.asList(userFields.getChildNodes());
		} else {
			return new ArrayList<TextUserFieldDeclElement>();
		}
		
	}
	
	/**
	 * Gets a list with the names of the variable fields.
	 * 
	 * @param container
	 * @return Returns the list.
	 */
	public static List<TextVariableDeclElement> getVariables(VariableContainer container) {
		OdfElement containerElement = container.getVariableContainerElement();
		TextVariableDeclsElement userVariables = 
			OdfElement.findFirstChildNode(TextVariableDeclsElement.class, containerElement);
		if (userVariables!=null) {
			return XmlUtils.asList(userVariables.getChildNodes());
		} else {
			return new ArrayList<TextVariableDeclElement>();
		}		
	}
	
}
