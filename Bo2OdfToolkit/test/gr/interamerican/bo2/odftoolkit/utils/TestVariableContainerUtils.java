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
package gr.interamerican.bo2.odftoolkit.utils;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextVariableDeclElement;
import org.odftoolkit.simple.TextDocument;

/**
 * Unit tests for {@link VariableContainerUtils}.
 */
@SuppressWarnings("nls")
public class TestVariableContainerUtils {
	
	/**
	 * Unit test for getUserFields(VariableContainer).
	 * 
	 * @throws Exception
	 */	
	@Test
	public void testGetUserFields() throws Exception {
		String inPath = ResourceUtils.inputPath("TemplateWithAllTypesOfFields.odt");
		TextDocument template = TextDocument.loadDocument(inPath);
		List<TextUserFieldDeclElement> list = VariableContainerUtils.getUserFields(template);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
		for (TextUserFieldDeclElement field : list) {
			Assert.assertTrue(field.getTextNameAttribute().startsWith("field"));
		}		
	}
	
	/**
	 * Unit test for getVariables(VariableContainer).
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetVariabless() throws Exception {
		String inPath = ResourceUtils.inputPath("TemplateWithAllTypesOfFields.odt");
		TextDocument template = TextDocument.loadDocument(inPath);		
		List<TextVariableDeclElement> list = VariableContainerUtils.getVariables(template);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
		for (TextVariableDeclElement field : list) {
			Assert.assertTrue(field.getTextNameAttribute().startsWith("var"));
		}	
	}

}
