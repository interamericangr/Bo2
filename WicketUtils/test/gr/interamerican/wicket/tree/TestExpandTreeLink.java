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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultTableTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.table.TreeColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link ExpandTreeLink}
 */
public class TestExpandTreeLink extends WicketTest {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ExpandTreeLink#ExpandTreeLink(java.lang.String, org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree)}.
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	@Test
	public void testExpandTreeLink() {
		SortableTreeProvider<String, String> provider = mock(SortableTreeProvider.class);
		TreeColumn<String, String> column = new TreeColumn<String, String>(Model.of("Column"), StringConstants.EMPTY);
		DefaultTableTree<String, String> tree = new DefaultTableTree<String, String>("tree", Arrays.asList(column),
				provider, 10);

		ExpandTreeLink<String> tested = new ExpandTreeLink<String>("test", tree);

		assertNotNull(tested);
		assertEquals(tree, tested.tree);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.tree.ExpandTreeLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	@Test
	public void testOnClickAjaxRequestTarget() {
		SortableTreeProvider<String, String> provider = mock(SortableTreeProvider.class);
		TreeColumn<String, String> column = new TreeColumn<String, String>(Model.of("Column"), StringConstants.EMPTY);
		DefaultTableTree<String, String> tree = new DefaultTableTree<String, String>("tree", Arrays.asList(column), provider, 10);
		Component node = tree.newNodeComponent("node1", Model.of("Child Node"));
		tree.add(node);

		doReturn(Arrays.asList(column).iterator()).when(provider).getRoots();
		doReturn(Arrays.asList(column).iterator()).when(provider).getChildren(any());

		ExpandTreeLink<String> tested = new ExpandTreeLink<String>("test", tree);
		AjaxRequestTarget mockedTarget = mock(AjaxRequestTarget.class);
		tested.onClick(mockedTarget);

		verify(provider).getRoots();
		verify(provider, times(2)).getChildren(any());
	}

}
