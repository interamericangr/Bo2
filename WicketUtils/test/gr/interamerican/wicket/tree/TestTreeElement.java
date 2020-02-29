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
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;

/**
 * Unit tests for {@link TreeElement}.
 */
public class TestTreeElement {
	/**
	 * Action invoked flag. 
	 */
	boolean isClicked = false;

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.TreeElement#TreeElement(java.lang.Object, gr.interamerican.wicket.tree.TreeElementDefinition, java.lang.String, java.lang.String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTreeElement() {
		TreeElementDefinition<String> definition = new TreeElementDefinition<String>();
		TreeElement<String> tested = new TreeElement<String>("test", definition, "label", StringConstants.MINUS);
		assertNotNull(tested);
		assertEquals("test", tested.item);
		assertEquals("label-test", tested.label);
		assertEquals(definition, tested.definition);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.TreeElement#addChildren(gr.interamerican.wicket.tree.TreeElementSubDefinition, java.lang.String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddChildren() {
		TreeElementDefinition<String> definition = new TreeElementDefinition<String>();
		TreeElementDefinition<String> childDefinition = new TreeElementDefinition<String>();
		TreeElementSubDefinition<String, String> subDefinition = new TreeElementSubDefinition<String, String>(
				Arrays::asList, childDefinition);

		TreeElement<String> tested = new TreeElement<String>("test", definition, "label", StringConstants.MINUS);
		tested.addChildren(subDefinition, StringConstants.MINUS);

		assertTrue(tested.getChildren().hasNext());
		assertEquals("label-test-test", tested.getChildren().next().getLabel());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.TreeElement#invoke(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testInvoke() {
		TreeElementDefinition<Boolean> definition = new TreeElementDefinition<Boolean>((t, b) -> isClicked = true);
		TreeElement<Boolean> tested = new TreeElement<Boolean>(false, definition, "Clicked", StringConstants.MINUS);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		tested.invoke(target);
		assertTrue(isClicked);
	}
}
