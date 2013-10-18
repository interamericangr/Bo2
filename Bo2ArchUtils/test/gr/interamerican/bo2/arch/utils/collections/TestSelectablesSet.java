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
package gr.interamerican.bo2.arch.utils.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.samples.archutil.po.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestSelectablesSet {
	
	/**
	 * 6 sample users
	 */
	private User v1;
	/**
	 * 
	 */
	private User v2;
	/**
	 * 
	 */
	private User v3;
	/**
	 * 
	 */
	private User v4;
	/**
	 * 
	 */
	private User v5;
	/**
	 * 
	 */
	private User v6;
	
	/**
	 * and a SelectablesSet
	 */
	private SelectablesSet<Long> set;
	/**
	 * an old-fashioned Set with the 3 first users
	 */
	private Set<User> plainSet; 

	/**
	 * Setup two user types (1 and 2). Type 1 has users of two subtypes
	 * (11 and 12). Type 2 has users of only one subtype (21).
	 * 
	 */
	@SuppressWarnings("nls")
	@Before
	public void setup() {

		set = new SelectablesSet<Long>();
		plainSet = new HashSet<User>();

		v1 = new User(1,"v1",1,"");
		v2 = new User(2,"v2",1,"");
		v3 = new User(3,"v3",2,"");
		v4 = new User(4,"v4",2,"");
		v5 = new User(5,"v5",2,"");
		v6 = new User(6,"v6",2,"");		

		set.add(v1);
		set.add(v2);
		set.add(v3);
		set.add(v4);
		set.add(v5);
		set.add(v6);
		
		plainSet.add(v1);
		plainSet.add(v2);
		plainSet.add(v3);
	}
	
	/**
	 * tests Collection handling methods implemented in SelectablesSet
	 */
	@Test
	public void testSetMethods() {

		assertEquals(set.size(), 6);

		assertFalse(set.isEmpty());

		assertTrue(set.contains(v1) && set.contains(v2) && set.contains(v3)
				&& set.contains(v4) && set.contains(v5) && set.contains(v6));

		Iterator<Selectable<Long>> it = set.iterator();
		while (it.hasNext()) {
			assertTrue(set.contains(it.next()));
		}
		
		Object[] usersA = set.toArray();
		assertEquals(usersA.length, 6);
		for (int i = 0; i < usersA.length; i++) {
			assertTrue(set.contains(usersA[i]));
		}
		
		
		

		User[] vehs = new User[6];
		
		vehs = set.toArray(vehs);
		assertEquals(vehs.length, 6);
		for (int i = 0; i < vehs.length; i++) {
			assertTrue(set.contains(vehs[i]));
		}

		User[] vehsLarge = new User[7];
		vehsLarge = set.toArray(vehsLarge);
		assertEquals(vehsLarge.length, 7);
		for (int i = 0; i < vehsLarge.length; i++) {
			if (i == 6)
				assertNull(vehsLarge[i]);
			else
				assertTrue(set.contains(vehsLarge[i]));
		}

		User[] vehsSmall = new User[5];
		vehsSmall = set.toArray(vehsSmall);
		assertEquals(vehsSmall.length, 6);
		for (int i = 0; i < vehsSmall.length; i++) {
			assertTrue(set.contains(vehsSmall[i]));
		}
		
		set.remove(v6);
		assertEquals(set.size(), 5);
		assertFalse(set.contains(v6));
		assertFalse(set.remove(new Object()));
		assertEquals(set.size(), 5);
		
		assertTrue(set.containsAll(plainSet));
		
		assertTrue(set.retainAll(plainSet));
		assertEquals(set.size(), plainSet.size());
		for(User v:plainSet)
			assertTrue(set.contains(v));
		
		set.add(v4);
		set.removeAll(plainSet);
		assertEquals(set.size(), 1);
		assertTrue(set.contains(v4));
		
		set.clear();
		assertEquals(set.size(), 0);

	}
	
	/**
	 * tests SelectablesSet.getByCode()
	 */
	@Test
	public void testGetByCode() {
		String[] expecteds = {"v1", "v2", "v3"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		String[] actuals = {
				set.getByCode(v1.getCode()).getName(),
				set.getByCode(v2.getCode()).getName(),
				set.getByCode(v3.getCode()).getName()
				};
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * tests SelectablesSet.equals()
	 */
	@Test
	public void testEquals() {
		SelectablesSet<Long> thatSet = new SelectablesSet<Long>();
		thatSet.add(v1);
		thatSet.add(v2);
		thatSet.add(v3);
		thatSet.add(v4);
		thatSet.add(v5);
		thatSet.add(v6);
		assertTrue(set.equals(thatSet) && thatSet.equals(set));
		thatSet.remove(v6);
		assertTrue(!set.equals(thatSet) && !thatSet.equals(set));
		
	}

	/**
	 * Test add
	 * Add a user that already exists
	 */
	@Test
	public void testAdd(){
		assertFalse(set.add(v1));
	}
	
	/**
	 * Test addAll
	 */
	@Test
	public void testAddAll(){
		Collection<User> col = new ArrayList<User>();
		User v7 = new User(7,"v7",2,"");	 //$NON-NLS-1$ //$NON-NLS-2$
		User v8 = new User(8,"v8",2,""); //$NON-NLS-1$ //$NON-NLS-2$
		col.add(v7);
		col.add(v8);
		assertTrue(set.addAll(col));	
		}
	
	
	/**
	 * Test add
	 * Add a user that already exists
	 */
	@Test
	public void testAddExistableUser(){
		assertFalse(set.add(v1));
	}
	
	/**
	 * Test equals with null value
	 */
	@Test
	public void testEqualsWithNullValue(){
		assertFalse(set.equals(null));
	}
	
	/**
	 * Test equals with null value
	 */
	@Test
	public void testEqualsWithSameSelectable(){
		assertTrue(set.equals(set));
	}
}
