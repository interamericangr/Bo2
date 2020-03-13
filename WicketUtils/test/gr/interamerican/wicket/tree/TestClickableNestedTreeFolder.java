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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link ClickableNestedTreeFolder}
 */
public class TestClickableNestedTreeFolder extends WicketTest {
	/**
	 * Tested Object
	 */
	ClickableNestedTreeFolder tested;

	/**
	 * Selected child node.
	 */
	TreeElement<String> firstChild;

	/**
	 * Root node.
	 */
	TreeElement<String> root;

	/**
	 * Initialize test data.
	 */
	@SuppressWarnings("nls")
	@Before
	public void buildUp() {
		TreeElementDefinition<String> definition = new TreeElementDefinition<String>();
		root = spy(new TreeElement<String>("root", definition, "root", "-"));
		firstChild = new TreeElement<String>("child1", definition, "First Child", "-");
		TreeProvider model = new TreeProvider(root, firstChild);
		ClickableNestedTree tree = new ClickableNestedTree("testTree", model, new Behavior[] {});
		tested = new ClickableNestedTreeFolder("test", tree, Model.of(root), Model.of(root), new Behavior[] {});
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTreeFolder#isClickable()}.
	 */
	@Test
	public void testIsClickable() {
		assertFalse(tested.isClickable());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTreeFolder#isSelected()}.
	 */
	@Test
	public void testIsSelected() {
		assertTrue(tested.isSelected());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTreeFolder#onClick(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@Test
	public void testOnClickAjaxRequestTarget() {
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		doNothing().when(root).invoke(target);
		tested.onClick(target);
		assertEquals(Model.of(root), tested.selected);
		verify(root).invoke(target);
	}
}
