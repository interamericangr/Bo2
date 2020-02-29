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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link ClickableNestedTree}.
 */
public class TestClickableNestedTree extends WicketTest {

	/**
	 * Sample Element Definition
	 */
	TreeElementDefinition<String> definition;
	/**
	 * Root Node
	 */
	TreeElement<String> root;
	/**
	 * Root Child Node
	 */
	TreeElement<String> firstChild;
	/**
	 * Root Child Node
	 */
	TreeElement<String> secondChild;
	/**
	 * Tree Structure Model
	 */
	ITreeProvider<TreeElement<?>> model;
	/**
	 * Node Behaviors
	 */
	Behavior[] behaviors;
	
	/**
	 * Tested object
	 */
	ClickableNestedTree tested;

	/**
	 * Initialize test data.
	 */
	@SuppressWarnings("nls")
	@Before
	public void buildUp() {
		definition = new TreeElementDefinition<>();
		root = new TreeElement<String>("root", definition, "root", "-");
		firstChild = new TreeElement<String>("child1", definition, "First Child", "-");
		secondChild = new TreeElement<String>("child2", definition, "Second Child", "-");
		model = new TreeProvider(root, firstChild, secondChild);
		behaviors = new Behavior[] {};
		tested = new ClickableNestedTree("test", model, behaviors);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTree#ClickableNestedTree(java.lang.String, org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider, org.apache.wicket.behavior.Behavior[])}.
	 */
	@Test
	public void testClickableNestedTree() {
		assertNotNull(tested);
		assertArrayEquals(new Behavior[] {},tested.behaviors);
		assertEquals(Model.of(root), tested.selected);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTree#newContentComponent(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testNewContentComponentStringIModelOfTreeElementOfQ() {
		Component treeFolder = tested.newContentComponent("testFolder", Model.of(firstChild));
		assertNotNull(treeFolder);
		assertEquals("testFolder", treeFolder.getId());
		assertEquals(Model.of(firstChild), treeFolder.getDefaultModel());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTree#setSelected(gr.interamerican.wicket.tree.TreeElement)}.
	 */
	@Test
	public void testSetSelected() {
		tested.setSelected(firstChild);
		assertEquals(Model.of(firstChild), tested.selected);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTree#getSelected()}.
	 */
	@Test
	public void testGetSelected() {
		tested.setSelected(firstChild);
		assertEquals(Model.of(firstChild).getObject(),tested.getSelected());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ClickableNestedTree#pickAndExpand(gr.interamerican.wicket.tree.TreeElement, java.util.Set)}.
	 */
	@Test
	public void testPickAndExpand() {
		tested.pickAndExpand(root, Sets.newSet(firstChild, secondChild));
		assertEquals(Model.of(root), tested.selected);
	}
}
