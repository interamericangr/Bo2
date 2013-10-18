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

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Unit test for JdbcCommand
 *
 */
public class TestJdbcCommand  extends AbstractNonTransactionalProviderTest {
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testExecute() throws InitializationException, DataException {		
		JdbcCommandImpl w = new JdbcCommandImpl();
		w.init(provider);
		w.open();
		w.execute();
		w.close();
		assertTrue(w.getI0()>=0);
		assertTrue(w.getI1()==1);
		assertTrue(w.getI2()==1);
		assertTrue(w.getI3()==1);
	}
	
	/**
	 * Sample implementation of {@link JdbcCommand} used in this test.
	 */
	@ManagerName("LOCALDB")
	private static class JdbcCommandImpl extends JdbcCommand {
		/**
		 * statement
		 */		
		private String stmt0 = 
			"delete from X__X.USERS where id = ?"; //$NON-NLS-1$
		
		
		/**
		 * statement
		 */
		private String stmt1 = 
			"insert into X__X.USERS "  //$NON-NLS-1$
			 +	" (ID, USR_ID, USR_NM, ROLE_ID)  values (?, ?, ?, ?) "; //$NON-NLS-1$
		
		/**
		 * statement
		 */
		private String stmt2 = 
			"update X__X.USERS set USR_ID = ? where ID = ?"; //$NON-NLS-1$
		
		/**
		 * statement
		 */
		private String stmt3 = 
			"update X__X.USERS set USR_NM = ? where ID = ?"; //$NON-NLS-1$
		

		/**
		 * records affected by statement 1
		 */
		int i0=0;
		
		/**
		 * records affected by statement 1
		 */
		int i1=0;
		
		/**
		 * @return the i0
		 */
		public int getI0() {
			return i0;
		}


		/**
		 * @return the i1
		 */
		public int getI1() {
			return i1;
		}


		/**
		 * @return the i2
		 */
		public int getI2() {
			return i2;
		}


		/**
		 * @return the i3
		 */
		public int getI3() {
			return i3;
		}


		/**
		 * records affected by statement 2
		 */
		int i2=0;
		
		/**
		 * records affected by statement 2
		 */
		int i3=0;
		

		@Override
		protected void work() throws DataException {
			Object[] parms0 = {1}; 
			Object[] parms1 = {1, "New" , "Name", 0}; //$NON-NLS-1$ //$NON-NLS-2$
			Object[] parms2 = {"Old" , 1}; //$NON-NLS-1$
			Object[] parms3 = {"Hi" , 1}; //$NON-NLS-1$
			try {	
				i0=executePreparedUpdate(stmt0, parms0);
				i1=executePreparedUpdate(stmt1, parms1);
				i2=executePreparedUpdate(stmt2, parms2);
				i3=executePreparedUpdate(stmt3, parms3);
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}
		
	}
	

}
