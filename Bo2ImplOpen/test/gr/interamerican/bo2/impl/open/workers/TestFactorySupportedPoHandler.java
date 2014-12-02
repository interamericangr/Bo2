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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.samples.ibean.Identified;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link FactorySupportedPoHandler}.
 */
public class TestFactorySupportedPoHandler {
	
	/**
	 * Tests the constructor.
	 */
	public void testConstructor() {
		FactorySupportedPoHandler<UserKey, User> handler = 
			new FactorySupportedPoHandler<UserKey, User>(User.class);
		Assert.assertEquals(User.class, handler.poClass);
		Assert.assertNotNull(handler.pw);		
	}
	
	
	/**
	 * Tests execute
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testCreateByKey() {
		FactorySupportedPoHandler<UserKey, User> handler = 
			new FactorySupportedPoHandler<UserKey, User>(User.class);
		UserKey key = new UserKey();
		key.setId(1);		
		User user = handler.createByKey(key);
		Assert.assertEquals(key, user.getKey());
	}
	
	/**
	 * Tests execute
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateByKeyProperties() {
		FactorySupportedPoHandler<UserKey, User> handler = 
			new FactorySupportedPoHandler<UserKey, User>(User.class);
		Integer id = 5;
		Identified<Integer> identifier = Factory.create(Identified.class);
		identifier.setId(id);
		User user = handler.createByKeyProperties(identifier);
		Assert.assertEquals(user.getId(), id);
	}
	
	
	

	
	
}
