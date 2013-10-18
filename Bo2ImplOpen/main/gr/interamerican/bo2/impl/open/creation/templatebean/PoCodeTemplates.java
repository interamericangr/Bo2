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
package gr.interamerican.bo2.impl.open.creation.templatebean;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import gr.interamerican.bo2.creation.code.templatebean.Variables;
import gr.interamerican.bo2.utils.TemplateUtils;
import gr.interamerican.bo2.utils.beans.FolderInitializedBean;

import java.util.Map;


/**
 * Code templates for field declaration, and accessor methods 
 * with direct access. 
 */
public class PoCodeTemplates 
extends FolderInitializedBean {
	
	/**
	 * Package with code template files.
	 */
	private static final String CODE_TEMPLATES_PATH = 
		"/gr/interamerican/bo2/impl/open/creation/templates/po/"; //$NON-NLS-1$

	/**
	 * Creates a new CodeTemplates object. 
	 *
	 */
	public PoCodeTemplates() {		
		super(CODE_TEMPLATES_PATH, true);
	}
	
	/**
	 * Templete to fill the getObjects.
	 */
	private String abstractKeyGetElements;
	
	/**
	 * key constructor code.
	 */
	private String poConstructor;
	
	/**
	 * Factory create code.
	 */
	private String factoryCreate;
	
	/**
	 * Fix child code.
	 */
	private String fixChildDirect;
	
	/**
	 * Fix child code.
	 */
	private String fixChildReflective;
	

	/**
	 * Gets the poConstructor.
	 *
	 * @return Returns the poConstructor
	 */
	public String getPoConstructor() {
		return poConstructor;
	}

	/**
	 * Assigns a new value to the poConstructor.
	 *
	 * @param poConstructor the poConstructor to set
	 */
	public void setPoConstructor(String poConstructor) {
		this.poConstructor = poConstructor;
	}
	
	
	/**
	 * Gets the factoryCreate.
	 *
	 * @return Returns the factoryCreate
	 */
	public String getFactoryCreate() {
		return factoryCreate;
	}

	/**
	 * Assigns a new value to the factoryCreate.
	 *
	 * @param factoryCreate the factoryCreate to set
	 */
	public void setFactoryCreate(String factoryCreate) {
		this.factoryCreate = factoryCreate;
	}
	
	/**
	 * Gets the fixChildDirect.
	 *
	 * @return Returns the fixChildDirect
	 */
	public String getFixChildDirect() {
		return fixChildDirect;
	}

	/**
	 * Assigns a new value to the fixChildDirect.
	 *
	 * @param fixChildDirect the fixChildDirect to set
	 */
	public void setFixChildDirect(String fixChildDirect) {
		this.fixChildDirect = fixChildDirect;
	}

	/**
	 * Gets the fixChildReflective.
	 *
	 * @return Returns the fixChildReflective
	 */
	public String getFixChildReflective() {
		return fixChildReflective;
	}

	/**
	 * Assigns a new value to the fixChildReflective.
	 *
	 * @param fixChildReflective the fixChildReflective to set
	 */
	public void setFixChildReflective(String fixChildReflective) {
		this.fixChildReflective = fixChildReflective;
	}
	
	/**
	 * Gets the factory create code filled.
	 * 
	 * @param type 
	 *
	 * @return Returns the poConstructor
	 */
	public String getFactoryCreate(Class<?> type) {		
		Map<String, String> vars = Variables.variablesForInitialization
			(type, EMPTY, EMPTY);
		return TemplateUtils.nullSafeFill(factoryCreate, vars);
	}

	/**
	 * Gets the fixChildReflective filled.
	 * @param name 
	 *
	 * @return Returns the fixChildReflective
	 */
	public String getFixChildReflective(String name) {		
		Map<String, String> vars = Variables.variablesForInitialization(null, name, EMPTY);
		return TemplateUtils.nullSafeFill(fixChildReflective, vars);
	}
	
	/**
	 * Gets the fixChildReflective filled.
	 * @param name 
	 *
	 * @return Returns the fixChildReflective
	 */
	public String getFixChildDirect(String name) {		
		Map<String, String> vars = Variables.variablesForInitialization(null, name, EMPTY);
		return TemplateUtils.nullSafeFill(fixChildDirect, vars);
	}

	/**
	 * Gets the poConstructor filled.
	 * 
	 * @param variables 
	 *
	 * @return Returns the poConstructor
	 */
	public String getPoConstructor(Map<String, String> variables) {		
		return TemplateUtils.nullSafeFill(poConstructor, variables);
	}

	/**
	 * Gets the abstractKeyGetElements.
	 *
	 * @return Returns the abstractKeyGetElements
	 */
	public String getAbstractKeyGetElements() {
		return abstractKeyGetElements;
	}

	/**
	 * Assigns a new value to the abstractKeyGetElements.
	 *
	 * @param abstractKeyGetElements the abstractKeyGetElements to set
	 */
	public void setAbstractKeyGetElements(String abstractKeyGetElements) {
		this.abstractKeyGetElements = abstractKeyGetElements;
	}

	/**
	 * Fills the template for the abstractKey.getElements() method.
	 * 
	 * @param variables
	 * 
	 * @return Returns the filled template.
	 */
	public String getAbstractKeyGetElements(Map<String, String> variables) {
		return TemplateUtils.nullSafeFill(abstractKeyGetElements, variables);
	}

}
