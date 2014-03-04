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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CompareToCodeTemplates}.
 */
public class TestCompareToCodeTemplates {

	/**
	 * Test 
	 */
	@Test
	public void testConstructor() {
		CompareToCodeTemplates templates = new CompareToCodeTemplates();
		Assert.assertNotNull(templates.getCompareToBody());		
		Map<String, String> map = new HashMap<String, String>();
		Assert.assertNotNull(templates.getCompareToFragment(map));
	}

}
