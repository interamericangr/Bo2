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

import static gr.interamerican.bo2.utils.Assertions.assertContains;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestPropertyWithDirectAccessCodeTemplates 
extends BaseTestPropertyCodeTemplates {
	
	

	/**
	 * Creates a new TestPropertyWithDirectAccessCodeTemplates object. 
	 *
	 */
	public TestPropertyWithDirectAccessCodeTemplates() {
		super(new PropertyWithDirectAccessCodeTemplates());
	}
	
	/**
	 * Unit test for getField
	 */
	@Test	
	public void testGetFieldDeclarationCode() {
		String code = templates.getFieldDeclarationCode(getVariables());
		Assert.assertFalse(StringUtils.isNullOrBlank(code));		
		assertContains(code,property);
		assertContains(code,type.getCanonicalName());
	}
	

}
