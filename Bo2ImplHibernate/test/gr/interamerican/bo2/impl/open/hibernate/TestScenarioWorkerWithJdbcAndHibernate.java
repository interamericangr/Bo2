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


import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.test.scenarios.DeleteUser;
import gr.interamerican.bo2.test.scenarios.FailingOperation;
import gr.interamerican.bo2.test.scenarios.ReadUser;
import gr.interamerican.bo2.test.scenarios.hibernate.HibSaveJdbcDeleteJdbcStoreHibRead;
import gr.interamerican.bo2.test.scenarios.hibernate.HibSaveJdbcRead;
import gr.interamerican.bo2.test.scenarios.hibernate.JdbcSave1HibSave2;
import gr.interamerican.bo2.test.scenarios.hibernate.JdbcSaveHibRead;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests Operations using Hibernate and Jdbc together.
 */
public class TestScenarioWorkerWithJdbcAndHibernate {

	/**
	 * Initialization of tests.
	 */
	@SuppressWarnings("nls")
	@BeforeClass
	public static void initializeSuite() {
		Bo2AnnoUtils.setManagerName(User.class, "LOCALDB");
	}

	/**
	 * Test case, see scenario operation.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	@Test
	public void testHibSaveJdbcRead()
			throws DataException, LogicException, UnexpectedException {
		int id = 1815;
		Execute.transactional(new DeleteUser(id));
		Execute.transactional(new HibSaveJdbcRead(id));
	}

	/**
	 * Test case, see scenario operation.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	@Test
	public void testJdbcSaveHibRead()
			throws DataException, LogicException, UnexpectedException {
		int id = 2815;
		Execute.transactional(new DeleteUser(id));
		Execute.transactional(new JdbcSaveHibRead(id));
	}

	/**
	 * Test case, see scenario operation.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	@Test
	public void testHibSaveJdbcDeleteJdbcStoreHibRead()
			throws DataException, LogicException, UnexpectedException {
		int id = 3815;
		Execute.transactional(new DeleteUser(id));
		Execute.transactional(new HibSaveJdbcDeleteJdbcStoreHibRead(id));
	}

	/**
	 * Test case.
	 * 
	 * Save one user with jdbc and one user with hibernate.
	 * Commit.
	 * Read both users on another unit of work.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	@Test
	public void testSuccessfulCommit()
			throws DataException, LogicException, UnexpectedException {
		int id1 = 4815;
		int id2 = 4816;

		Operation[] setup = {
				new DeleteUser(id1),
				new DeleteUser(id2)
		};
		Operation[] scenario = {
				new JdbcSave1HibSave2(id1, id2)
		};
		Operation[] control = {
				new ReadUser(id1),
				new ReadUser(id2)
		};
		Execute.transactional(setup);
		Execute.transactional(scenario);
		Execute.transactional(control);
	}

	/**
	 * Test case.
	 * 
	 * Save one user with jdbc and one user with hibernate.
	 * Fail and Rollback.
	 * Read both users on another unit of work.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	@Test(expected=LogicException.class)
	public void testSuccessfulRollback()
			throws DataException, LogicException, UnexpectedException {
		int id1 = 5815;
		int id2 = 5816;

		Operation[] setup = {
				new DeleteUser(id1),
				new DeleteUser(id2)
		};
		Operation[] scenario = {
				new JdbcSave1HibSave2(id1, id2),
				new FailingOperation()
		};
		Operation[] control = {
				new ReadUser(id1),
				new ReadUser(id2)
		};
		Execute.transactional(setup);
		Execute.transactional(scenario);
		Execute.transactional(control);

	}

}
