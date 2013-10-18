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
 * Unit test for {@link FieldInitializationCodeTemplates}.
 */
public class TestFieldInitializationCodeTemplates {

	/**
	 * Test 
	 */
	@Test
	public void testConstructor() {
		FieldInitializationCodeTemplates templates = new FieldInitializationCodeTemplates();
		Assert.assertNotNull(templates.getDirect());
		Assert.assertNotNull(templates.getReflective());
		Map<String, String> map = new HashMap<String, String>();
		Assert.assertNotNull(templates.getDirect(map));
		Assert.assertNotNull(templates.getReflective(map));
	}

}
