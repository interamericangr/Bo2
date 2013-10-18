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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CrudCmd}.
 */
public class TestCrudCmd {
	
	/**
	 * Unit test for all methods.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAllMethods() throws UnexpectedException, DataException, LogicException {
		PersistenceWorker<User> pw = Factory.createPw(User.class);		
		CrudCmd<User> cmd = new CrudCmd<User>(pw);
		
		User user = Factory.create(User.class);
		user.setId(555);
		user.setName("name");
		user.setUsrid("usrid00");
		
		User saved = cmd.store(user);
		Assert.assertNotNull(saved);
		User read = cmd.read(user);
		Assert.assertNotNull(read);
		read.setName("Updated");
		User updated = cmd.update(read);
		Assert.assertNotNull(updated);
		
		cmd.delete(updated);
		try {
			read = cmd.read(user);
		} catch (PoNotFoundException e) {
			/*
			 * Expecting a PoNotFoundException.
			 */
		}
	}
	
	/**
	 * Unit test for the constructors.
	 */
	@Test
	public void testConstructors() {
		PersistenceWorker<User> pw = Factory.createPw(User.class);		
		
		CrudCmd<User> cmd1 = new CrudCmd<User>(pw, true);
		Assert.assertEquals(pw, cmd1.pw);
		Assert.assertTrue(cmd1.ignorePnfeOnDelete);
		
		CrudCmd<User> cmd2 = new CrudCmd<User>(pw, false);
		Assert.assertEquals(pw, cmd2.pw);
		Assert.assertFalse(cmd2.ignorePnfeOnDelete);
	}

}
