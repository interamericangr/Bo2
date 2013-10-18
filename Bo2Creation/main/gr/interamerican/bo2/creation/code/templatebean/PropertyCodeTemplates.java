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
package gr.interamerican.bo2.creation.code.templatebean;

import gr.interamerican.bo2.utils.TemplateUtils;
import gr.interamerican.bo2.utils.beans.FolderInitializedBean;

import java.util.Map;

/**
 * Code templates for field declaration, and accessor methods 
 * with direct access. 
 */
public class PropertyCodeTemplates 
extends FolderInitializedBean {

	/**
	 * Creates a new PropertyCodeTemplates object. 
	 *
	 * @param path
	 *        Package of the template files.
	 */
	protected PropertyCodeTemplates(String path) {		
		super(path, true);
	}
	
	/**
	 * Code template.
	 */
	private String propertyGetter;
	
	/**
	 * Code template.
	 */
	private String propertySetter;
	
	/**
	 * Code template.
	 */
	private String fieldDeclaration;


	/**
	 * Gets the property getter.
	 * 
	 * @param variables
	 *        Variable for the getter creation
	 * @param getterName 
	 *        Name of the getter. This is necessary, because a getter can have
	 *        the getX() as well as the isX() form. 
	 * 
	 * @return Returns the getter code.
	 */
	@SuppressWarnings("nls")
	public String getPropertyGetter(Map<String, String> variables, String getterName) {
		String getterTemplate = propertyGetter;
		if (getterName.startsWith("is")) {
			getterTemplate = getterTemplate.replace(" get", " is");
		}
		return TemplateUtils.nullSafeFill(getterTemplate, variables);
	}

	/**
	 * Gets the propertySetter.
	 * 
	 * @param variables 
	 *
	 * @return Returns the propertySetter
	 */
	public String getPropertySetter(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(propertySetter, variables);
	}

	/**
	 * Gets the field declaration code.
	 * 
	 * @param variables 
	 *
	 * @return Returns the field declaration code
	 */
	public String getFieldDeclarationCode(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(fieldDeclaration, variables);
	}

	/**
	 * Gets the propertyGetter.
	 *
	 * @return Returns the propertyGetter
	 */
	public String getPropertyGetter() {
		return propertyGetter;
	}

	/**
	 * Assigns a new value to the propertyGetter.
	 *
	 * @param propertyGetter the propertyGetter to set
	 */
	public void setPropertyGetter(String propertyGetter) {
		this.propertyGetter = propertyGetter;
	}

	/**
	 * Gets the propertySetter.
	 *
	 * @return Returns the propertySetter
	 */
	public String getPropertySetter() {
		return propertySetter;
	}

	/**
	 * Assigns a new value to the propertySetter.
	 *
	 * @param propertySetter the propertySetter to set
	 */
	public void setPropertySetter(String propertySetter) {
		this.propertySetter = propertySetter;
	}

	/**
	 * Gets the fieldDeclaration.
	 *
	 * @return Returns the fieldDeclaration
	 */
	public String getFieldDeclaration() {
		return fieldDeclaration;
	}

	/**
	 * Assigns a new value to the fieldDeclaration.
	 *
	 * @param fieldDeclaration the fieldDeclaration to set
	 */
	public void setFieldDeclaration(String fieldDeclaration) {
		this.fieldDeclaration = fieldDeclaration;
	}	
	
	
}
