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
package gr.interamerican.bo2.impl.open.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Generic test of {@link AbstractHibernateWorker}.
 */
public class TestAbstractHibernateWorker {
	
	/**
	 * sample id1.
	 */
	private int id1 = 12806;
	
	/**
	 * sample id2.
	 */
	private int id2 = 12807;
	
	/**
	 * user name 1
	 */
	private String name1 = "user name 1"; //$NON-NLS-1$
		
	/**
	 * Deletes users.
	 * @param ids
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	private void deleteUsers(final Integer... ids) 
	throws DataException, LogicException, UnexpectedException{
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				UserPwImpl jdbc = new UserPwImpl();
				jdbc.init(getProvider());
				jdbc.open();
				for (int i = 0; i < ids.length; i++) {
					User user = new User();
					user.setId(ids[i].intValue());
					try {
						jdbc.delete(user);
					} catch (PoNotFoundException e) {
						/* ignore */
					}			
				}
				jdbc.close();
			}		
		}.execute();
	}

	
	/**
	 * Creates a user.
	 * @param id
	 * @param name
	 * @return returns the user.
	 */
	private User createUser(int id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setUsrid("U" +StringUtils.int2str(id,7)); //$NON-NLS-1$
		user.setRoleId(0);
		return user;
	}
	
	/**
	 * reads a user.
	 * 
	 * @param id
	 * @return returns the user.
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	private User readuser(final int id) 
	throws DataException, LogicException, UnexpectedException {
		final User us = new User();
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				UserPwImpl jdbc = new UserPwImpl();
				User user = new User();
				user.setId(id);
				jdbc.init(getProvider());
				jdbc.open();
				user = jdbc.read(user);
				us.setCode(user.getCode());
				us.setId(user.getId());
				us.setName(user.getName());
				us.setRoleId(us.getRoleId());
				us.setSubTypeId(user.getSubTypeId());
				us.setTypeId(user.getTypeId());
				us.setUsrid(user.getUsrid());
				jdbc.close();
			}		
		}.execute();
		return us;
	}
	
	
	/**
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Before
	public void before() throws LogicException,  DataException, UnexpectedException {		
		deleteUsers(id1,id2);
	}
	
	/**
	 * Test setup.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@After
	public void after() throws LogicException,  DataException, UnexpectedException {		
		deleteUsers(id1,id2);
	}
	
	/**
	 * Tests init, open, close.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testInitOpenClose() 
	throws InitializationException, DataException {
		AbstractHibernateWorker w = new AbstractHibernateWorker();
		w.setManagerName("LOCALDB"); //$NON-NLS-1$
		w.init(Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider()); //Non transactional provider.
		assertTrue(w.isInitialized());
		w.open();
		assertTrue(w.isOpen());
		w.close();
		assertFalse(w.isOpen());
	}
	
	
		
	
	/**
	 * Tests that persist will not flush and then
	 * closing the ResourceManager will not flush automatically.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testCloseDoesNotFlush() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation save = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				worker.save(id1,name1);
			}
		};
		Execute.transactional(save);		
		readuser(id1);
	}
	
	/**
	 * Tests that persist will not flush.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=PoNotFoundException.class)
	public void testSaveDoesNotFlush() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation save = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				worker.save(id1,name1);
				worker.read(id1);
			}
		};	
		Execute.transactional(save);	
	}
	
	
	
	
	/**
	 * Tests that get will always get an existing user.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testGetFetchesAnExistingUser() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				User us = worker.get(UtilityForBo2Test.getExistingUserId());
				assertNotNull(us);
			}
		};	
		Execute.transactional(read);
	}	
	
	/**
	 * Tests that get will always get an existing user.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testGetDoesNotCreateNotExistingObject() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				User us = worker.get(id1);
				assertNull(us);
			}
		};	
		Execute.transactional(read);
	}
	
	/**
	 * Test that get will always get the same object.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testCallingGetTwiceForSameObject() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				User us1 = worker.get(UtilityForBo2Test.getExistingUserId());
				assertNotNull(us1);
				User us2 = worker.get(UtilityForBo2Test.getExistingUserId());
				assertNotNull(us2);
				assertSame(us1,us2);
			}
		};
		Execute.transactional(read);
		
	}	
	
	/**
	 * Test that load() will load a saved object.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testUpdateWithExistingUser() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				String oldName = "Old Name"; //$NON-NLS-1$
				String newName = "New Name"; //$NON-NLS-1$ 
				
				User us1 = worker.read(UtilityForBo2Test.getExistingUserId());
				us1.setName(oldName);
				us1 = worker.userPw.update(us1);

				us1.setName(newName);
				User us2 = worker.update(us1);
				worker.flush(us2);
				
				User us3 = worker.read(UtilityForBo2Test.getExistingUserId());
				assertEquals(us1.getName().trim(), us2.getName().trim());
				
				us3.setName(oldName);				
				User us4 = worker.update(us3);
				worker.flush(us3);
				
				assertSame(us2, us4);
				
				assertEquals(oldName.trim(), us3.getName().trim());
				assertEquals(oldName.trim(), us4.getName().trim());
				
				User us5 = worker.read(UtilityForBo2Test.getExistingUserId());
				assertEquals(oldName.trim(), us5.getName().trim());
			}
		};
		Execute.transactional(read);		
	}	
	
	
	/**
	 * Test the management of an exception.
	 * 
	 * @throws DataException
	 * @throws LogicException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=UnexpectedException.class)
	public void testExceptionManagement() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {				
				User us1 = new User();
				us1.setId(null);
				worker.saveEntity(us1);				
			}
		};
		Execute.transactional(read);		
	}	
	
	
	/**
	 * Test that load() will load a saved object.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testUpdateWithNonExistingUser() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				String oldName = "Old Name"; //$NON-NLS-1$
				String newName = "New Name"; //$NON-NLS-1$ 
				
				User us1 = createUser(id1, "Not existing"); //$NON-NLS-1$

				us1.setName(newName);
				User us2 = worker.update(us1);
				worker.flush(us2);
				
				User us3 = worker.read(id1);
				assertEquals(us1.getName().trim(), us2.getName().trim());
				
				us3.setName(oldName);				
				User us4 = worker.update(us3);
				worker.flush(us3);
				
				assertEquals(oldName.trim(), us3.getName().trim());
				assertEquals(oldName.trim(), us4.getName().trim());
				
				User us5 = worker.read(id1);
				assertEquals(oldName.trim(), us5.getName().trim());
			}
		};
		Execute.transactional(read);
	}	
	
	/**
	 * Test that load() will load a saved object.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testGetEntity() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				Integer id = UtilityForBo2Test.getExistingUserId();
				UserKey key = new UserKey(id);
				User us = (User) worker.getEntity(User.class, key);
				Assert.assertEquals(id, us.getId());
			}
		};
		Execute.transactional(read);
	}	
	
	/**
	 * Test that load() will load a saved object.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException 
	 */
	@Test()
	public void testGetEntity_throwing() 
	throws DataException, LogicException, UnexpectedException {
		TestOperation read = new TestOperation() {
			@Override
			public void execute() throws LogicException ,DataException {
				Integer id = UtilityForBo2Test.getNotExistingUserId();
				UserKey key = new UserKey(id);
				User us;
				try {
					us = (User) worker.getEntity(User.class, key);
				} catch (HibernateException e) {
					us = null;
				}
				Assert.assertNull(us);
			}
		};
		Execute.transactional(read);
	}	
	
	
	
	/**
	 * Sample testing class.
	 */
	@ManagerName("LOCALDB")
	public class Worker 
	extends AbstractHibernateWorker {
		/**
		 * User PW.
		 */
		@Child private UserPwImpl userPw = new UserPwImpl();
		
		/**
		 * @param id 
		 * @param name
		 * @return user
		 */
		User save(int id, String name){
			User hib = createUser(id, name);
			UserKey key = (UserKey) session.save(hib);
			assertEquals(new Integer(id), new Integer(key.getId()));
			return hib;
		}
		
		/**
		 * updates an object
		 * @param user
		 * @return returns the object.
		 */
		User update(User user) {
			User us1 = (User) session.merge(user);
			return us1;
		}
		
		/**
		 * @param id
		 * @return user
		 */
		User get(int id){
			UserKey key = Factory.create(UserKey.class);
			key.setId(id);
			return (User) session.get(User.class, key);
		}
		
		/**
		 * @param id 
		 * @return user
		 * @throws DataException
		 */
		User read(int id) 
		throws DataException {
			User user = new User();
			user.setId(id);
			return userPw.read(user);
		}
	}
	
	/**
	 * Test operation.
	 */
	public abstract class TestOperation extends AbstractOperation {
		/**
		 * Worker for test.
		 */
		@Child Worker worker = new Worker();
	}
	

	
}
