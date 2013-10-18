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
 * 
 */
public class TestDelegateMethodWithReflectiveAccessCodeTemplates 
extends BaseTestMethodCodeTemplates{
	
	/**
	 * field name.
	 */
	String field = "child"; //$NON-NLS-1$
	
	/**
	 * field type.
	 */
	Class<?> fieldType = BeanWith1Field.class; 
	
	/**
	 * invocation parms.
	 */
	String invocationParms = "a, b"; //$NON-NLS-1$

	/**
	 * Creates a new TestEmptyMethodCodeTemplates object. 
	 */
	public TestDelegateMethodWithReflectiveAccessCodeTemplates() {
		super(new DelegateMethodWithDirectAccessCodeTemplates());
	}
	
	@Override
	protected Map<String, String> getVariables() {
		
		return Variables.variablesForDelegateMethod
			(methodName, type, declarationParms, fieldType, field, invocationParms);
	}

}
