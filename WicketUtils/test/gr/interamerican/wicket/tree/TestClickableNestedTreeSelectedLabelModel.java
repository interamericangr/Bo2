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
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Unit test for {@link ClickableNestedTreeSelectedLabelModel}
 */
public class TestClickableNestedTreeSelectedLabelModel {

	/**
	 * Test method for {@link gr.interamerican.wicket.tree.ClickableNestedTreeSelectedLabelModel#ClickableNestedTreeSelectedLabelModel(gr.interamerican.wicket.tree.ClickableNestedTree)}.
	 */
	@Test
	public void testClickableNestedTreeSelectedLabelModel() {
		ClickableNestedTree tree = mock(ClickableNestedTree.class);
		ClickableNestedTreeSelectedLabelModel tested = new ClickableNestedTreeSelectedLabelModel(tree);
		assertNotNull(tested);
		assertEquals(tree, tested.tree);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.tree.ClickableNestedTreeSelectedLabelModel#getObject()}.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test
	public void testGetObject() {
		ClickableNestedTree tree = mock(ClickableNestedTree.class);
		TreeElement<String> selected = new TreeElement<String>("Item", mock(TreeElementDefinition.class), "Item", "-");
		doReturn(selected).when(tree).getSelected();
		ClickableNestedTreeSelectedLabelModel tested = new ClickableNestedTreeSelectedLabelModel(tree);
		assertNotNull(tested);
		String actual = tested.getObject();
		assertEquals("Item-Item",actual);
		verify(tree).getSelected();
	}
}
