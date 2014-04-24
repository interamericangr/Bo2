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
package gr.interamerican.bo2.arch.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CacheImpl}
 */
public class TestCacheImpl {
	/**
	 * named cache
	 */
	private static CacheImpl<Long> cache = new CacheImpl<Long>();

	/**
	 * sample role
	 */
	private static TypedSelectableImpl<Long> role1 = new TypedSelectableImpl<Long>(1L, null, 1L, "role1-1"); //$NON-NLS-1$

	/**
	 * sample role
	 */
	private static TypedSelectableImpl<Long> role2 = new TypedSelectableImpl<Long>(1L, null, 2L, "role1-2"); //$NON-NLS-1$

	/**
	 * sample role
	 */
	private static TypedSelectableImpl<Long> role3 = new TypedSelectableImpl<Long>(2L, null, 1L, "role2-3"); //$NON-NLS-1$

	/**
	 * sample role
	 */
	private static TypedSelectableImpl<Long> role4 = new TypedSelectableImpl<Long>(2L, null, 2L, "role2-4"); //$NON-NLS-1$

	/**
	 * test setup
	 */
	@Before
	public void setupTests() {
		cache.clear();
		cache.put(role1);
		cache.put(role2);
		cache.put(role3);
		cache.put(role4);
	}

	/**
	 * Tests putting an existing entry in cache.
	 */
	@Test
	public void testPut_existing() {
		String name = "newRole2-4"; //$NON-NLS-1$
		TypedSelectable<Long> newRole4 = new TypedSelectableImpl<Long>(2L, null, 2L, name);
		cache.put(newRole4);
		assertEquals(name, cache.get(2L, 2L).getName()); //Assert was put in cache properly.

		boolean found = false;
		for(TypedSelectable<Long> ts : cache.getSubCacheAsList(2L, null)) {
			if(ts.getCode().equals(2L)) {
				found = true;
				assertEquals(name, ts.getName()); //Assert was put properly in subcache.
			}
		}
		assertTrue(found); //Assert was put properly in subcache.

		Set<TypedSelectable<Long>> entries = cache.getTypeEntries(2L);
		assertTrue(entries.contains(newRole4));


	}

	/**
	 * tests get()
	 */
	@Test
	public void testGet() {
		assertEquals(role1, cache.get(role1.getTypeId(), role1.getCode()));
		assertEquals(role2, cache.get(role2.getTypeId(), role2.getCode()));
		assertEquals(role3, cache.get(role3.getTypeId(), role3.getCode()));
		assertEquals(role4, cache.get(role4.getTypeId(), role4.getCode()));
	}

	/**
	 * tests getSubCache()
	 */
	@Test
	public void testGetSubCache() {
		Set<TypedSelectable<Long>> roles = cache.getSubCache(1L, null);
		Iterator<TypedSelectable<Long>> iter = roles.iterator();
		while(iter.hasNext()) {
			TypedSelectable<Long> role = iter.next();
			assertTrue(role.equals(role1)||role.equals(role2));
		}
		roles = cache.getSubCache(2L, null);
		iter = roles.iterator();
		while(iter.hasNext()) {
			TypedSelectable<Long> role = iter.next();
			assertTrue(role.equals(role3)||role.equals(role4));
		}
	}

	/**
	 * tests getSubCache() <br>
	 * TODO dummy test, roles1 and roles1 will always be equal....
	 */
	@Test
	public void testGetSubCache_ForAllEntries() {
		Set<TypedSelectable<Long>> roles1 = cache.getSubCache(1L, Cache.SUBTYPEID_FOR_ALL_TYPE_ENTRIES);
		Set<TypedSelectable<Long>> roles2 = cache.getTypeEntries(1L);
		assertEquals(roles1, roles1);
	}


	/**
	 * tests getAllTypeEntries()
	 */
	@Test
	public void testGetTypeEntries() {
		Set<TypedSelectable<Long>> roles = cache.getTypeEntries(1L);
		Iterator<TypedSelectable<Long>> iter = roles.iterator();
		while(iter.hasNext()) {
			TypedSelectable<Long> role = iter.next();
			assertTrue(role.equals(role1)||role.equals(role2));
		}
	}

	/**
	 * tests getSubCacheAsList()
	 */
	@Test
	public void testGetSubCacheAsList() {
		List<TypedSelectable<Long>> roles = cache.getSubCacheAsList(1L, null);
		for(TypedSelectable<Long> role : roles) {
			assertTrue(role.equals(role1)||role.equals(role2));
		}
		roles = cache.getSubCacheAsList(2L, null);
		for(TypedSelectable<Long> role : roles) {
			assertTrue(role.equals(role3)||role.equals(role4));
		}
	}

	/**
	 * tests remove()
	 */
	@Test
	public void testRemove() {

		cache.remove(role1);
		assertNull(cache.get(role1.getTypeId(), role1.getCode()));
		assertNotNull(cache.get(role2.getTypeId(), role2.getCode()));
		assertNotNull(cache.get(role3.getTypeId(), role3.getCode()));
		assertNotNull(cache.get(role4.getTypeId(), role4.getCode()));
		assertFalse(cache.getSubCache(role1.getTypeId(), role1.getSubTypeId()).contains(role1));
		assertEquals(1, cache.getSubCache(1L, null).size());
		assertEquals(2, cache.getSubCache(2L, null).size());
		assertEquals(1, cache.getTypeEntries(1L).size());
		assertEquals(2, cache.getTypeEntries(2L).size());

		cache.remove(role2);
		assertNull(cache.get(role2.getTypeId(), role2.getCode()));
		assertNotNull(cache.get(role3.getTypeId(), role3.getCode()));
		assertNotNull(cache.get(role4.getTypeId(), role4.getCode()));
		assertFalse(cache.getSubCache(role2.getTypeId(), role2.getSubTypeId()).contains(role2));
		assertEquals(0, cache.getSubCache(1L, null).size());
		assertEquals(2, cache.getSubCache(2L, null).size());
		assertEquals(0, cache.getTypeEntries(1L).size());

		cache.remove(role3);
		assertNull(cache.get(role3.getTypeId(), role3.getCode()));
		assertNotNull(cache.get(role4.getTypeId(), role4.getCode()));
		assertFalse(cache.getSubCache(role3.getTypeId(), role3.getSubTypeId()).contains(role3));
		assertEquals(0, cache.getSubCache(1L, null).size());
		assertEquals(1, cache.getSubCache(2L, null).size());

		cache.remove(role4);
		assertNull(cache.get(role4.getTypeId(), role4.getCode()));
		assertFalse(cache.getSubCache(role4.getTypeId(), role4.getSubTypeId()).contains(role4));
		assertEquals(0, cache.getSubCache(1L, null).size());
		assertEquals(0, cache.getSubCache(2L, null).size());
	}



	/**
	 * tests clear()
	 */
	@Test
	public void testClear() {
		assertEquals(2, cache.getSubCache(1L, null).size());
		assertEquals(2, cache.getSubCache(2L, null).size());
		assertNotNull(cache.get(1L, 1L));
		assertNotNull(cache.get(1L, 2L));
		assertNotNull(cache.get(2L, 1L));
		assertNotNull(cache.get(2L, 2L));
		cache.clear();
		assertEquals(0, cache.getSubCache(1L, 1L).size());
		assertEquals(0, cache.getSubCache(1L, 2L).size());
		assertNull(cache.get(1L, 1L));
		assertNull(cache.get(1L, 2L));

		assertEquals(0, cache.getTypeEntries(1L).size());
		assertEquals(0, cache.getTypeEntries(2L).size());
	}

	/**
	 * tests refill()
	 */
	@Test
	public void testRefill() {

		Set<TypedSelectable<Long>> roles = new HashSet<TypedSelectable<Long>>();
		cache.refill(1L, roles);
		assertNull(cache.get(1L, 1L));
		assertNull(cache.get(1L, 2L));
		assertEquals(0, cache.getSubCache(1L, null).size());

		roles.add(role1);
		roles.add(role2);
		cache.refill(1L, roles);
		assertNotNull(cache.get(1L, 1L));
		assertNotNull(cache.get(1L, 2L));
		assertEquals(2, cache.getSubCache(1L, null).size());

		roles.clear();
		cache.clear();
		roles.add(role1);
		roles.add(role3);
		cache.refill(1L, roles);
		assertNotNull(cache.get(1L, 1L));
		assertNull(cache.get(2L, 1L));
		assertEquals(1, cache.getSubCache(1L, null).size());
		assertEquals(0, cache.getSubCache(2L, null).size());


	}





}

