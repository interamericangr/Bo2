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
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestPoSet {

	/**
	 * 6 sample vehicles
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
	 * and a poset
	 */
	private PoSet<User> set;
	/**
	 * an old-fashioned set with the 3 first vehicles
	 */
	private Set<User> plainSet; 

	/**
	 * Setup two vehicle types (1 and 2). Type 1 has vehicles of two subtypes
	 * (11 and 12). Type 2 has vehicles of only one subtype (21).
	 * 
	 */
	@SuppressWarnings("nls")
	@Before
	public void setup() {

		set = new PoSet<User>();
		plainSet = new HashSet<User>();

		v1 = new User(1,"v1",1,"");
		v2 = new User(2,"v2",1,"");
		v3 = new User(3,"v3",1,"");
		v4 = new User(4,"v4",1,"");
		v5 = new User(5,"v5",1,"");
		v6 = new User(6,"v6",1,"");

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
	 * tests Collection handling methods implemented in PoSet
	 */
	@Test
	public void testSetMethods() {

		assertEquals(set.size(), 6);

		assertFalse(set.isEmpty());

		assertTrue(set.contains(v1) && set.contains(v2) && set.contains(v3)
				&& set.contains(v4) && set.contains(v5) && set.contains(v6));

		Iterator<User> it = set.iterator();
		while (it.hasNext()) {
			assertTrue(set.contains(it.next()));
		}
		
		Object[] vehicles = set.toArray();
		assertEquals(vehicles.length, 6);
		for (int i = 0; i < vehicles.length; i++) {
			assertTrue(set.contains(vehicles[i]));
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
		
		set.addAll(plainSet);
		assertEquals(set.size(), 3);

	}
	
	/**
	 * tests PoSet.getByKey()
	 */
	@Test
	public void testGetByKey() {
		String[] expecteds = {"v1", "v2", "v3"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$\
		String[] actuals = {
				(set.getByKey(new UserKey(1)).getName()),
				(set.getByKey(new UserKey(2)).getName()),
				(set.getByKey(new UserKey(3)).getName()),
				};
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * tests PoSet.getByKey()
	 */
	@Test
	public void testGetKeys() {
		Set<Object> keys = set.getKeys();
		assertTrue(keys.contains(new UserKey(1)));
		assertTrue(keys.contains(new UserKey(2)));
		assertTrue(keys.contains(new UserKey(3)));
		assertTrue(keys.contains(new UserKey(4)));
		assertTrue(keys.contains(new UserKey(5)));
		assertTrue(keys.contains(new UserKey(6)));
	}
	
	/**
	 * tests PoSet.equals()
	 */
	@Test
	public void testEquals() {
		PoSet<User> thatSet = new PoSet<User>();
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
	 * PoSet with parameters
	 * Add a user to PoSet
	 */
	@Test
	public void  createPoSet(){
	 Set<User> userSet = new HashSet<User>();
	 User v7 = new User(7,"v7",1,""); //$NON-NLS-1$ //$NON-NLS-2$
	 userSet.add(v7);
	 PoSet<User> sampleSet = new PoSet<User>(userSet);
	 assertEquals(1,sampleSet.size());
	}

	/**
	 * Test Add
	 * Add a user that already exists
	 */
	@Test
	public void testAdd(){
		 assertFalse(set.add(v1));
	}
	
	/**
	 * Test Equals with null value
	 */
	@Test
	public void testEqualsWithNullValue(){
		assertFalse(set.equals(null));
	}
	
	/**
	 * Test Equals with the same PoSet
	 */
	@Test
	public void testEqualsWithSamePoSet(){
		assertTrue(set.equals(set));
	}
	
	/**
	 * Test that a PoSet can be serialized.
	 * @throws IOException 
	 */
	@Test
	public void testSerialization() throws IOException {
		PoSet<User> subject = new PoSet<User>();
		subject.addAll(set);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(subject);
		oos.close();
		assertTrue(baos.toByteArray().length>0);
	}
	
}
