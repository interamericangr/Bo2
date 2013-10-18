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

import static gr.interamerican.bo2.creation.util.CodeGenerationUtilities.cleanJavaCode;
import static gr.interamerican.bo2.utils.Assertions.assertStartsWith;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class BaseTestMethodCodeTemplates {
	/**
	 * Creates a new TestPropertyCodeTemplates object. 
	 *
	 * @param templates
	 */
	public BaseTestMethodCodeTemplates(MethodCodeTemplates templates) {
		super();
		this.templates = templates;
	}

	/**
	 * Object to test.
	 */
	MethodCodeTemplates templates;
	/**
	 * Property name.
	 */
	final String methodName = "doThat"; //$NON-NLS-1$
	/**
	 * Property type.
	 */
	final Class<?> type = String.class;
	
	/**
	 * declaration parms.
	 */
	@SuppressWarnings("nls")
	final String declarationParms = "String s, int i"; 
		
	/**
	 * Test for void method.
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testVoidMethod() {
		String method = templates.getVoidMethod(getVariables());
		method = cleanJavaCode(method);
		Assert.assertFalse(StringUtils.isNullOrBlank(method));	
		assertStartsWith(method, "public void doThat(");			
	}

	/**
	 * Test for type method.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTypeMethod() {
		String method = templates.getTypeMethod(getVariables());
		method = cleanJavaCode(method);
		Assert.assertFalse(StringUtils.isNullOrBlank(method));	
		assertStartsWith(method, "public java.lang.String doThat(");		
	}
	
	/**
	 * Gets the variables.
	 * @return Gets the variables.
	 */
	protected Map<String, String> getVariables() {
		return Variables.variablesForEmptyMethod(methodName, type, declarationParms);
	}
	
	
	
	
	
	
	
	

}
