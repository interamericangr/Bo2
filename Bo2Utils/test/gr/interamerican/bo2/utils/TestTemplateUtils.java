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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for {@link TemplateUtils}.
 */
@SuppressWarnings("nls")
public class TestTemplateUtils {
	
	/**
	 * test variable.
	 */	
	@Test
	public void testVariable() {		
		String var = "this";
		String expected = "_:this";
		assertEquals(expected, TemplateUtils.variable(var));
	}
	
	/**
	 * test replace.
	 */
	@Test
	public void testReplace() {
		String value = "templateName";
		String template = 
			"Template name: _:name. _:Name is _:Name";
		String expected = 
			"Template name: templateName. TemplateName is TemplateName";
		assertEquals(expected, TemplateUtils.replace(template, "name", value));
		
		String expected2 = 
			"Template name: _:name. templateName is templateName";
		assertEquals(expected2, TemplateUtils.replace(template, "Name", value));
	}
	
	/**
	 * test replace.
	 */
	@Test
	public void testFill() {
		
		String template = 
			"name is _:name. Name is _:Name, type is _:type, Type is _:Type";
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", "paris");
		vars.put("type", "trojan");		
		String expected = 
			"name is paris. Name is Paris, type is trojan, Type is Trojan";
		String actual = TemplateUtils.fill(template, vars);
		assertEquals(expected,actual);
	}


}
