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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.dom.element.text.TextUserFieldGetElement;
import org.odftoolkit.simple.TextDocument;

/**
 * Unit test for {@link XmlUtils}.
 */
@SuppressWarnings("nls")
public class TestXmlUtils {
	
	/**
	 * Unit test for getAllNodesOfType(node, clazz, list).
	 * @throws Exception 
	 */
	@Test
	public void testGetAllNodesOfType() throws Exception {		
		String path = ResourceUtils.inputPath("TemplateWithUserFields.odt");
		TextDocument doc = TextDocument.loadDocument(path);
		List<TextUserFieldGetElement> list = new ArrayList<TextUserFieldGetElement>();
		XmlUtils.getAllNodesOfType(doc.getContentDom().getRootElement(), TextUserFieldGetElement.class, list);
		Assert.assertEquals(5, list.size());
	}

}
