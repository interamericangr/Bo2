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
package gr.interamerican.bo2.impl.open.jdbc;


import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for JdbcSimpleCommand.
 */
public class TestJdbcSimpleCommand extends AbstractNonTransactionalProviderTest {
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 */
	@Test
	public void testExecute() throws InitializationException, DataException {
		
		JdbcSimpleCommandImpl w = new JdbcSimpleCommandImpl();
		JdbcSimpleCommand cmd = w;
		cmd.recordsAffected = Integer.MAX_VALUE;
		w.init(provider);
		w.open();
		w.execute();
		w.close();		
		Assert.assertNotEquals(Integer.MAX_VALUE, cmd.recordsAffected);
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query.
	 */
	@Test
	public void testGetEntitiesCount()  {
		JdbcSimpleCommandImpl w = new JdbcSimpleCommandImpl();
		JdbcSimpleCommand cmd = w;		
		cmd.recordsAffected =5;
		Assert.assertEquals(cmd.recordsAffected, cmd.getEntitiesCount());
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 */
	@Test(expected=DataException.class)
	public void testExecute_throwingException() throws InitializationException, DataException {
		
		FailingCommand w = new FailingCommand();
		w.init(provider);
		w.open();
		w.execute();
		w.close();	
		
	}
	
	/**
	 * Simple implementation of JdbcSimpleCommand used in this unit test.
	 */
	@ManagerName("LOCALDB")
	private static class JdbcSimpleCommandImpl extends JdbcSimpleCommand {

		@Override
		protected Object[] parameters() {
			Object[] parms = {"name", 1}; //$NON-NLS-1$
			return parms;
		}

		@Override
		protected String sql() {		
			return "update X__X.users set usr_nm = ? where id = ?"; //$NON-NLS-1$
		}
	}
	
	/**
	 * This command throws an exception when executed.
	 */
	@ManagerName("LOCALDB")
	private static class FailingCommand extends JdbcSimpleCommandImpl {

		@Override
		protected Object[] parameters() {			
			return null;
		}

		
	}
	

}
