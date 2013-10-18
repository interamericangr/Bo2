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


/**
 * Code templates for field declaration, and accessor methods 
 * with direct access. 
 */
public class Delegate2MapWithReflectiveAccessCodeTemplates 
extends PropertyCodeTemplates {
	
	/**
	 * Package with code template files.
	 */
	private static final String CODE_TEMPLATES_PATH = 
		"/gr/interamerican/bo2/creation/code/templates/property/delegate2map/reflective/"; //$NON-NLS-1$

	/**
	 * Creates a new CodeTemplates object. 
	 *
	 */
	public Delegate2MapWithReflectiveAccessCodeTemplates() {		
		super(CODE_TEMPLATES_PATH);
	}
	

}
