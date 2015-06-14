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

import java.util.Iterator;

import org.junit.Test;

/**
 * Unit test for {@link AssociationTable}. 
 */
@SuppressWarnings("nls")
public class TestAssociationTable {
	
	
	
	/**
	 * Tests associate() and getLeft(), getRight().
	 */	
	@Test
	public void testAssociate_SecondTime() {
		AssociationTable <String, Integer> table =
			new AssociationTable<String, Integer>();
		Integer one = 1;
		Integer two = 2;
		Integer three = 3;
		
		
		table.associate("ena", one);
		table.associate("dyo", two);
		table.associate("tria", three);
		
		table.associate("three", three);
		
		assertEquals(one, table.getRight("ena"));
		assertEquals(two, table.getRight("dyo"));
		assertEquals(three, table.getRight("three"));
		
		assertEquals("ena", table.getLeft(one));
		assertEquals("dyo", table.getLeft(two));
		assertEquals("three", table.getLeft(three));
		
		Integer expectedNull = table.getRight("tria"); 
		
		
		assertTrue(expectedNull==null);
		assertTrue(table.getRight("tessera")==null);
		assertTrue(table.getLeft(4)==null);
	}
	
	
	/**
	 * Tests associate() and getLeft(), getRight().
	 */	
	@Test
	public void testAssociate() {
		AssociationTable <String, Integer> table =
			new AssociationTable<String, Integer>();
		Integer one = 1;
		Integer two = 2;
		Integer three = 3;
		
		
		table.associate("ena", one);
		table.associate("dyo", two);
		table.associate("tria", three);
		
		assertEquals(one, table.getRight("ena"));
		assertEquals(two, table.getRight("dyo"));
		assertEquals(three, table.getRight("tria"));
		
		assertEquals("ena", table.getLeft(one));
		assertEquals("dyo", table.getLeft(two));
		assertEquals("tria", table.getLeft(three));
		
		assertTrue(table.getRight("tessera")==null);
		assertTrue(table.getLeft(4)==null);
	}
	
	
	/**
	 * Tests associate() and getLeft(), getRight().
	 */	
	@Test
	public void testClear() {
		AssociationTable <String, Integer> table =
			new AssociationTable<String, Integer>();
		Integer one = 1;
		Integer two = 2;
		Integer three = 3;
		
		table.associate("ena", one);
		table.associate("dyo", two);
		table.associate("tria", three);
		
		table.clear();
		
		assertNull(table.getRight("ena"));
		assertNull(table.getLeft(1));
		assertNull(table.getRight("dyo"));
		assertNull(table.getLeft(2));
		assertNull(table.getRight("tria"));
		assertNull(table.getLeft(3));
	}
	
	/**
	 * Tests associate() and getLeft(), getRight().
	 */	
	@Test
	public void testToString() {
		AssociationTable <String, Integer> table =
			new AssociationTable<String, Integer>();
		table.associate("ena", new Integer(1));
		table.associate("duo", new Integer(2));
		String s = table.toString();
		assertNotNull(s);
	}
	
	/**
	 * Tests size
	 */	
	@Test
	public void testSize() {
		AssociationTable <String, Integer> table =
			new AssociationTable<String, Integer>();
		table.associate("ena", new Integer(1));
		assertEquals(1,table.size());
	}
	
	
	/**
	 * Tests iterator
	 */	
	@Test
	public void testIterator() {
		AssociationTable <String, Integer> table =
				new AssociationTable<String, Integer>();
		table.associate("ena", new Integer(1));
		table.associate("dyo", new Integer(2));
		Iterator<Pair<String, Integer>> iterator = table.iterator();
		assertNotNull(iterator);
	}
	
	/**
	 * Tests iterator
	 */	
	@Test
	public void testRemoveLeft() {
		AssociationTable <String, Integer> table = new AssociationTable<String, Integer>();
		String one = "one";
		String two = "two";				
		table.associate(one, new Integer(1));
		table.associate(two, new Integer(2));
		boolean b1 = table.removeLeft(two);
		assertNull(table.getRight(two));
		assertNotNull(table.getRight(one));
		assertTrue(b1);
		assertFalse(table.removeLeft("foo"));
	}
	
	/**
	 * Tests iterator
	 */	
	@Test
	public void testRemoveRight() {
		AssociationTable <String, Integer> table = new AssociationTable<String, Integer>();
		String one = "one";
		String two = "two";				
		table.associate(one, new Integer(1));
		table.associate(two, new Integer(2));
		boolean b1 = table.removeRight(2);
		assertNull(table.getLeft(2));				
		assertTrue(b1);
		assertFalse(table.removeRight(666));

	}
	

	
	
	
}
