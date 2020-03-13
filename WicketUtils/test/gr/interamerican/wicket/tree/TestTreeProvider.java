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
package gr.interamerican.wicket.tree;

import static org.junit.Assert.*;

import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;

/**
 * Unit tests for {@link TreeProvider}
 */
public class TestTreeProvider {

	/**
	 * Test method for {@link gr.interamerican.wicket.tree.TreeProvider#TreeProvider(TreeElement, TreeElement...)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTreeProvider() {
		TreeElementDefinition<String> def = new TreeElementDefinition<>();
		TreeElement<String> root = new TreeElement<String>("root", def , "label", StringConstants.MINUS);
		TreeProvider tested = new TreeProvider(root);
		
		assertNotNull(tested);
		assertTrue(tested.getRoots().hasNext());
		assertFalse(tested.hasChildren(root));
		assertEquals(Model.of(root), tested.model(root));
	}

}
