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
import static gr.interamerican.bo2.utils.Assertions.assertContains;
import static gr.interamerican.bo2.utils.Assertions.assertEndsWith;
import static gr.interamerican.bo2.utils.Assertions.assertStartsWith;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Base unit test class for PropertyCodeTemplates.
 */
public class BaseTestPropertyCodeTemplates {
	
	/**
	 * Creates a new TestPropertyCodeTemplates object. 
	 *
	 * @param templates
	 */
	public BaseTestPropertyCodeTemplates(
	PropertyCodeTemplates templates) {
		super();
		this.templates = templates;		
	}

	/**
	 * Object to test.
	 */
	PropertyCodeTemplates templates;
	/**
	 * Property name.
	 */
	protected final String property = "myAttribute"; //$NON-NLS-1$
	/**
	 * Property type.
	 */
	protected final Class<?> type = String.class;
	/**
	 * Property getter start.
	 */
	final String getterStart = "public java.lang.String getMyAttribute("; //$NON-NLS-1$
	/**
	 * Property getter start.
	 */
	final String setterStart = "public void setMyAttribute(java.lang.String"; //$NON-NLS-1$
	
	/**
	 * Test for a getter.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetter() {
		String getter = templates.getPropertyGetter(getVariables(), "getMyAttribute");
		Assert.assertNotNull(getter);
		getter = cleanJavaCode(getter);		
		Assert.assertFalse(StringUtils.isNullOrBlank(getter));
		assertStartsWith(getter, getterStart);
		assertContains(getter,"return");
		assertEndsWith(getter,"}");
	}

	/**
	 * Test for a setter.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetter() {
		String setter = templates.getPropertySetter(getVariables());
		Assert.assertNotNull(setter);
		setter = cleanJavaCode(setter);		
		Assert.assertFalse(StringUtils.isNullOrBlank(setter));
		assertStartsWith(setter, setterStart);
		assertEndsWith(setter,"}");	
	}
	
	/**
	 * Gets the variables.
	 * @return Gets the variables.
	 */
	protected Map<String, String> getVariables() {
		return Variables.variablesForProperty(property, type, property, type); 
	}

}
