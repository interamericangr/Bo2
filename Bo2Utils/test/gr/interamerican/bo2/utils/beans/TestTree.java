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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link Tree}.
 */
@SuppressWarnings("nls")
public class TestTree {
	
	/**
	 * Unit test for constructor with two arguments.
	 */
	@Test
	public void testConstructor_withTwoArgs() {
		Tree<Integer> tree = new Tree<Integer>(4 , "root");
		assertEquals(Integer.valueOf(4), tree.rootElement);
		assertEquals("root", tree.name);
		assertTrue(tree.nodes.isEmpty());
		assertNull(tree.parent);
	}
	
	/**
	 * Unit test for constructor with one argument.
	 */	
	@Test
	public void testConstructor_withOneArgs() {
		Tree<Integer> tree = new Tree<Integer>(4);
		assertEquals(Integer.valueOf(4), tree.rootElement);
		assertEquals(Integer.valueOf(4).toString(), tree.name);
		assertTrue(tree.nodes.isEmpty());
		assertNull(tree.parent);
	}
	
	/**
	 * Unit test for getters and setters.
	 */	
	@Test
	public void testGettersSetters() {
		Tree<Integer> tree = new Tree<Integer>(4);
		assertEquals(tree.nodes, tree.getNodes());
		assertEquals(tree.name, tree.getName());
		tree.setName("name");
		assertEquals("name", tree.name);
		assertEquals(Integer.valueOf(4), tree.getRootElement());
	}
	
	/**
	 * Unit test for constructor with one argument.
	 */	
	@Test
	public void testAllMethods() {
		/*
		 * create empty nodes.
		 */
		Tree<String> tree = new Tree<String>("root");
		Tree<String> l1_No1 = new Tree<String>("l1_No1");
		Tree<String> l1_No2 = new Tree<String>("l1_No2");
		Tree<String> l1_No3 = new Tree<String>("l1_No3");
		Tree<String> l2_No1_1 = new Tree<String>("l2_No1_1");
		Tree<String> l2_No1_2 = new Tree<String>("l2_No1_2");
		Tree<String> l2_No3_1 = new Tree<String>("l2_No3_1");
		Tree<String> l3_No1 = new Tree<String>("l3_No1");
		Tree<String> l3_No2 = new Tree<String>("l3_No2");
		/*
		 * Add first level 
		 * 
		 *    root - l1_No1
		 *         - l1_No2
		 *         - l1_No2
		 */
		tree.add(l1_No1);				
		tree.add(l1_No2);
		tree.add(l1_No3);	
		
		assertEquals(4, tree.getTotalNodesCount());
		
		/*
		 * Second level
		 *  
		 *  root --+- l1_No1 +- l2_No1_1
		 *         |         +- l2_No1_2
		 *         +- l1_No2
		 *         +- l1_No3 +- l2_No3_1 
		 */
		l1_No1.add(l2_No1_1);
		l1_No1.add(l2_No1_2);		
		l1_No3.add(l2_No3_1);
		
		assertEquals(7, tree.getTotalNodesCount());
		
		/*
		 * Third level
		 *  
		 *  root --+-l1_No1 +-l2_No1_1
		 *         |        +-l2_No1_2 +- l3_No1
		 *         |                   +- l3_No2       
		 *         +-l1_No2
		 *         +-l1_No3 +-l2_No3_1 
		 */
		l2_No1_2.addAt(0, l3_No1);
		l2_No1_2.addAt(1, l3_No2);
		assertEquals(9, tree.getTotalNodesCount());
		/*
		 * Now add some elements
		 *  root --+-l1_No1 +-l2_No1_1
		 *         |        +-l2_No1_2 +- l3_No1
		 *         |                   +- l3_No2 +- value1
		 *         +-l1_No2
		 *         +-l1_No3 +-l2_No3_1 
		 *         +-value1
		 *         +-value2 
		 *         +-value1          
		 */
		tree.add("value1"); //a
		tree.add("value2");		
		tree.add("value1"); //b
		l3_No2.add("value1");
		assertEquals(13, tree.getTotalNodesCount());
		/*
		 * Now print it.
		 */
		String s = tree.toString();
		assertNotNull(s);
		System.out.println();
		System.out.println(s);
		System.out.println();
		
		/*
		 * Check structure is ok.
		 */
		assertContains(tree, l1_No1, 0);
		assertContains(tree, l1_No2, 1);
		assertContains(tree, l1_No3, 2);
		assertContains(l1_No1, l2_No1_1, 0);
		assertContains(l1_No1, l2_No1_2, 1);		
		assertContains(l1_No3, l2_No3_1, 0);	
		assertContains(l2_No1_2, l3_No1, 0);
		assertContains(l2_No1_2, l3_No2, 1);
		/*
		 * Do some searches. 
		 */
		assertEquals(l1_No2, tree.getChildNodeOf("l1_No2"));
		assertEquals(l1_No1, tree.getChildNodeOf("l1_No1"));
		assertNull(tree.getChildNodeOf("foo"));
		assertNull(tree.getChildNodeOf("l3_No2"));
		assertEquals(l3_No2, tree.getAnyNodeOf("l3_No2"));
		assertNull(tree.getAnyNodeOf("foo"));
		assertEquals(l1_No2, tree.getAnyNodeOf("l1_No2"));
		Tree<String> value1 = tree.getAnyNodeOf("value1");
		assertNotNull(value1);
		assertEquals(l3_No2, value1.parent);		
		Tree<String> value1_a = tree.getNodes().get(3);
		Tree<String> value2 = tree.getAnyNodeOf("value2");
		assertNotNull(value2);
		assertContains(tree, value1_a, 3);
		assertContains(tree, value2, 4);
		Tree<String> value1_b = tree.getNodes().get(5);
		assertEquals(value1_b.rootElement, "value1");
		assertContains(tree, value1_b, 5);
		/*
		 * remove the all value1 and then value2
		 */
		tree.removeAll("value1");
		assertTrue(l3_No2.nodes.isEmpty());
		assertEquals(4, tree.getNodes().size());
		tree.remove("value2");
		assertNull(value2.parent);		
		/*
		 * Remove l1_No1
		 * 
		 *  root --+-l1_No2
		 *         +-l1_No3 +-l2_No3_1 
		 */
		tree.remove(l1_No1);		
		assertNull(l1_No1.parent);
		assertFalse(tree.nodes.contains(l1_No1));
		assertContains(tree, l1_No2, 0);
		assertContains(tree, l1_No3, 1);
		assertNull(tree.getAnyNodeOf("l3_No2"));
		assertEquals(l3_No2, l1_No1.getAnyNodeOf("l3_No2"));
		/*
		 * Remove l1_No3
		 * 
		 *  root --+-l1_No2 
		 */
		Tree<String> removed = tree.removeAt(1);
		assertEquals(l1_No3, removed);
		assertNull(removed.parent);	
		assertContains(tree, l1_No2, 0);
		assertFalse(tree.nodes.contains(l1_No3));
		Tree<String> nonExisting = tree.removeAt(2);
		assertNull(nonExisting);
		boolean expectedFalse = tree.remove(l1_No3);
		assertFalse(expectedFalse);
	}
	
	/**
	 * Check
	 * @param <T>
	 * @param parent
	 * @param node
	 * @param index
	 */
	static <T> void assertContains(Tree<T> parent, Tree<T> node, int index) {
		assertEquals(parent, node.parent);
		assertEquals(node, parent.nodes.get(index));		
	}
	
	

}
