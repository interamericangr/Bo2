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
package gr.interamerican.bo2.impl.open.hibernate.abstractimpl;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.GenericHibernatePersistenceWorker;
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * Test for {@link CacheUpdatingPersistenceWorker}
 */
public class TestCacheUpdatingPersistenceWorker {

	/**
	 * Test for {@link CacheUpdatingPersistenceWorker} update.
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 */
	@Test
	public void testUpdate() throws PoNotFoundException, DataException {
		User user = Factory.create(User.class);
		user.setId(0);
		Cache<Long> cache = new CacheImpl<>();
		cache.put(user);
		CacheUpdatingPersistenceWorker<Long, User> cPw = new CacheUpdatingPersistenceWorker<Long, User>(cache,User.class);
		@SuppressWarnings("unchecked")
		GenericHibernatePersistenceWorker<User> hiPw = mock(GenericHibernatePersistenceWorker.class);
		when(hiPw.update(any(User.class))).thenReturn(User.getDummy(1));
		cPw.setHibPw(hiPw);
		assertNotNull(cache.get(user.getTypeId(), user.getCode()));
		User updated = cPw.update(user);
		assertEquals(1, (int) updated.getId());
		assertNull(cache.get(user.getTypeId(), user.getCode()));
		assertSame(cache.get(user.getTypeId(), updated.getCode()), updated);
	}

	/**
	 * Test for {@link CacheUpdatingPersistenceWorker} delete.
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 */
	@Test
	public void testDelete() throws PoNotFoundException, DataException {
		User user = Factory.create(User.class);
		user.setId(0);
		Cache<Long> cache = new CacheImpl<>();
		cache.put(user);
		CacheUpdatingPersistenceWorker<Long, User> cPw = new CacheUpdatingPersistenceWorker<Long, User>(cache,User.class);
		@SuppressWarnings("unchecked")
		GenericHibernatePersistenceWorker<User> hiPw = mock(GenericHibernatePersistenceWorker.class);
		when(hiPw.delete(any(User.class))).thenReturn(User.getDummy(1));
		cPw.setHibPw(hiPw);
		assertNotNull(cache.get(user.getTypeId(), user.getCode()));
		User actual = cPw.delete(user);
		assertEquals(1, (int) actual.getId());
		assertNull(cache.get(actual.getTypeId(), actual.getCode()));
		assertNull(cache.get(user.getTypeId(), user.getCode()));
	}

	/**
	 * Test for {@link CacheUpdatingPersistenceWorker} store.
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 */
	@Test
	public void testStore() throws PoNotFoundException, DataException {
		User user = Factory.create(User.class);
		user.setId(0);
		Cache<Long> cache = new CacheImpl<>();
		CacheUpdatingPersistenceWorker<Long, User> cPw = new CacheUpdatingPersistenceWorker<Long, User>(cache,User.class);
		@SuppressWarnings("unchecked")
		GenericHibernatePersistenceWorker<User> hiPw = mock(GenericHibernatePersistenceWorker.class);
		when(hiPw.store(any(User.class))).thenReturn(User.getDummy(1));
		cPw.setHibPw(hiPw);
		User actual = cPw.store(user);
		assertEquals(1, (int) actual.getId());
		assertSame(cache.get(actual.getTypeId(), actual.getCode()), actual);
	}
}