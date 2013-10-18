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

import gr.interamerican.bo2.samples.bean.BeanWith1Field;

import java.util.Map;


/**
 * Test for {@link DelegatePropertyWithDirectAccessCodeTemplates}
 */
public class TestDelegatePropertyWithDirectAccessCodeTemplates 
extends BaseTestPropertyCodeTemplates {
	/**
	 * field name.
	 */
	String field = "child"; //$NON-NLS-1$
	
	/**
	 * field type.
	 */
	Class<?> fieldType = BeanWith1Field.class; 
	
	

	/**
	 * Creates a new TestPropertyWithDirectAccessCodeTemplates object. 
	 *
	 */
	public TestDelegatePropertyWithDirectAccessCodeTemplates() {
		super(new DelegatePropertyWithDirectAccessCodeTemplates());
	}
	
	@Override
	protected Map<String, String> getVariables() {	
		return Variables.variablesForProperty(property, type, field, fieldType);
	}
	
	

}
