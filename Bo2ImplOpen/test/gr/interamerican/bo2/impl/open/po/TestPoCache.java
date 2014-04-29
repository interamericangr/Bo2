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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for cache.
 */
public class TestPoCache {

	/**
	 * Test for get,
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testGet() throws UnexpectedException, DataException, LogicException {
		final PoCache<UserKey, User> cache = new PoCache<UserKey, User>(User.class,0,null);
		final User[] users = new User[3];

		final UserKey exists = Factory.create(UserKey.class);
		exists.setId(UtilityForBo2Test.getExistingUserId());
		final UserKey notExists = Factory.create(UserKey.class);
		notExists.setId(UtilityForBo2Test.getNotExistingUserId());
		Bo2AnnoUtils.setManagerName(User.class, "LOCALDB"); //$NON-NLS-1$

		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				users[0] = cache.get(exists);
			}
		}.execute();
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				users[1] = cache.get(exists);
				users[2] = cache.get(notExists);
			}
		}.execute();
		Assert.assertNotNull(users[0]);
		Assert.assertNotSame(users[0], users[1]);
		Assert.assertNull(users[2]);

	}

	/**
	 * Test for get throwing an exception.
	 */
	@Test(expected=RuntimeException.class)
	public void testGet_withoutProvider() {
		final PoCache<UserKey, User> cache = new PoCache<UserKey, User>(User.class,0,null);
		UserKey k = new UserKey(5);
		cache.get(k);
	}


	/**
	 * Test for set.
	 */
	@Test
	public void testSet() {
		final PoCache<UserKey, User> cache = new PoCache<UserKey, User>(User.class,0,null);
		User expected = new User();
		expected.setId(54);
		cache.set(expected);
		UserKey key = Factory.create(UserKey.class);
		key.setId(54);
		User actual = cache.get(key);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * test for remove
	 */
	@Test
	public void testRemove() {
		final PoCache<UserKey, User> cache = new PoCache<UserKey, User>(User.class, 0, null);
		User expected = new User();
		expected.setId(54);
		cache.set(expected);
		Assert.assertTrue(cache.map.containsKey(expected.getKey()));
		cache.remove(expected.getKey());
		Assert.assertNull(cache.map.get(expected.getKey()));
	}

}
