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
 * Code templates for field initialization. 
 */
public class FieldInitializationCodeTemplates 
extends FolderInitializedBean {
	
	/**
	 * Package with code template files.
	 */
	private static final String CODE_TEMPLATES_PATH = 
		"/gr/interamerican/bo2/creation/code/templates/initfield/"; //$NON-NLS-1$

	/**
	 * Creates a new CodeTemplates object. 
	 *
	 */
	public FieldInitializationCodeTemplates() {		
		super(CODE_TEMPLATES_PATH, true);
	}
	
	/**
	 * direct access template.
	 */
	String direct;
	
	/**
	 * reflective access template.
	 */
	String reflective;

	/**
	 * Gets the direct.
	 *
	 * @return Returns the direct
	 */
	public String getDirect() {
		return direct;
	}

	/**
	 * Assigns a new value to the direct.
	 *
	 * @param direct the direct to set
	 */
	public void setDirect(String direct) {
		this.direct = direct;
	}

	/**
	 * Gets the reflective.
	 *
	 * @return Returns the reflective
	 */
	public String getReflective() {
		return reflective;
	}

	/**
	 * Assigns a new value to the reflective.
	 *
	 * @param reflective the reflective to set
	 */
	public void setReflective(String reflective) {
		this.reflective = reflective;
	}
	
	/**
	 * Gets the reflective filled.
	 * 
	 * @param variables 
	 *
	 * @return Returns the reflective
	 */
	public String getReflective(Map<String, String> variables) {
		return TemplateUtils.fill(reflective, variables);
	}
	
	/**
	 * Gets the direct filled.
	 * 
	 * @param variables 
	 *
	 * @return Returns the reflective
	 */
	public String getDirect(Map<String, String> variables) {
		return TemplateUtils.fill(direct, variables);
	}
	
	

}
