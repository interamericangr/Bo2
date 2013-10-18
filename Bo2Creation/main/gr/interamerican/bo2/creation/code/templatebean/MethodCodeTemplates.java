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
 * Code templates for method implementation. 
 */
public class MethodCodeTemplates 
extends FolderInitializedBean {

	/**
	 * Creates a new MethodCodeTemplates object. 
	 * 
	 * @param path 
	 *        Package that contains the templates.
	 *
	 */
	protected MethodCodeTemplates(String path) {		
		super(path, true);
	}

	
	/**
	 * Gets the voidMethod.
	 *
	 * @return Returns the voidMethod
	 */
	public String getVoidMethod() {
		return voidMethod;
	}

	/**
	 * Assigns a new value to the voidMethod.
	 *
	 * @param voidMethod the voidMethod to set
	 */
	public void setVoidMethod(String voidMethod) {
		this.voidMethod = voidMethod;
	}

	/**
	 * Gets the typeMethod.
	 *
	 * @return Returns the typeMethod
	 */
	public String getTypeMethod() {
		return typeMethod;
	}

	/**
	 * Assigns a new value to the typeMethod.
	 *
	 * @param typeMethod the typeMethod to set
	 */
	public void setTypeMethod(String typeMethod) {
		this.typeMethod = typeMethod;
	}


	/**
	 * Code template.
	 */
	private String voidMethod;
	
	/**
	 * Code template.
	 */
	private String typeMethod;

	/**
	 * Gets the voidMethod.
	 * @param variables 
	 *
	 * @return Returns the voidMethod
	 */
	public String getVoidMethod(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(voidMethod, variables);
	}

	/**
	 * Gets the typeMethod.
	 * @param variables 
	 *
	 * @return Returns the typeMethod
	 */
	public String getTypeMethod(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(typeMethod, variables);
	}
	
	/**
	 * Gets the method code.
	 * 
	 * @param variables 
	 * @param returnType 
	 *
	 * @return Returns the method code.
	 */
	public String getMethod(Map<String, String> variables, Class<?> returnType) {
		if (returnType.equals(void.class)) {
			return getVoidMethod(variables);
		} else {
			return getTypeMethod(variables);
		}
	}
	
		
}
