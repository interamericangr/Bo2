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
public class CompareToCodeTemplates 
extends FolderInitializedBean {
	
	/**
	 * Package with code template files.
	 */
	private static final String CODE_TEMPLATES_PATH = 
		"/gr/interamerican/bo2/creation/code/templates/method/compareTo/"; //$NON-NLS-1$


	/**
	 * Creates a new MethodCodeTemplates object. 
	 *
	 */
	public CompareToCodeTemplates() {		
		super(CODE_TEMPLATES_PATH, true);
	}
	

	/**
	 * Code template.
	 */
	private String compareToBody;
	
	/**
	 * Code template.
	 */
	private String compareToFragment;

	

	/**
	 * Gets the voidMethod.
	 * @param variables 
	 *
	 * @return Returns the voidMethod
	 */
	public String getCompareToBody(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(compareToBody, variables);
	}
	
	/**
	 * Gets the voidMethod.
	 * @param variables 
	 *
	 * @return Returns the voidMethod
	 */
	public String getCompareToFragment(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(compareToFragment, variables);
	}

	/**
	 * Gets the compareToBody.
	 *
	 * @return Returns the compareToBody
	 */
	public String getCompareToBody() {
		return compareToBody;
	}

	/**
	 * Assigns a new value to the compareToBody.
	 *
	 * @param compareToBody the compareToBody to set
	 */
	public void setCompareToBody(String compareToBody) {
		this.compareToBody = compareToBody;
	}

	/**
	 * Gets the compareToFragment.
	 *
	 * @return Returns the compareToFragment
	 */
	public String getCompareToFragment() {
		return compareToFragment;
	}

	/**
	 * Assigns a new value to the compareToFragment.
	 *
	 * @param compareToFragment the compareToFragment to set
	 */
	public void setCompareToFragment(String compareToFragment) {
		this.compareToFragment = compareToFragment;
	}



	
		
		
}
