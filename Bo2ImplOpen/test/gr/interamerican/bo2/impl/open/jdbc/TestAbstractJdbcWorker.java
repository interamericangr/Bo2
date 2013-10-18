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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;



/**
 * Basic implementation of AbstractJdbcWorker.
 *
 */
public class TestAbstractJdbcWorker extends AbstractNonTransactionalProviderTest {
	
	/**
	 * tests prepare().
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testPrepare_withoutParameterMarker() 
	throws InitializationException, DataException {		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		String sql = "select * from TEST.USERS"; //$NON-NLS-1$
		w.prepare(sql);
		PreparedStatement ps = w.statements.get(sql);
		assertNotNull(ps);
		w.close();
	}
	
	/**
	 * tests prepare().
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testPrepare_withNamedParameter() 
	throws InitializationException, DataException {		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		String sql = "select * from TEST.USERS where id = :id"; //$NON-NLS-1$
		w.prepare(sql);
		PreparedStatement ps = w.statements.get(sql);
		assertNotNull(ps);
		w.close();
	}
	
	/**
	 * tests prepare().
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testPrepare_withParameterMarker() 
	throws InitializationException, DataException {		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		String sql = "select * from TEST.USERS where id = ?"; //$NON-NLS-1$
		w.prepare(sql);
		PreparedStatement ps = w.statements.get(sql);
		assertNotNull(ps);
		w.close();
	}
	
	/**
	 * tests prepare().
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testGetPreparedStatement() 
	throws InitializationException, DataException {		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		String sql = "select * from TEST.USERS"; //$NON-NLS-1$		
		PreparedStatement ps1 = w.getPreparedStatement(sql);
		assertNotNull(ps1);
		PreparedStatement ps2 = w.getPreparedStatement(sql);
		assertSame(ps1, ps2);
		String sql3 = "select id from TEST.USERS"; //$NON-NLS-1$
		PreparedStatement ps3 = w.getPreparedStatement(sql3);
		assertNotNull(ps3);
		assertNotSame(ps1, ps3);
		PreparedStatement ps4 = w.getPreparedStatement(sql);
		assertSame(ps1, ps4);
		w.close();
	}
	
	/**
	 * tests prepare().
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testGetPreparedStatement_withSubClass() 
	throws InitializationException, DataException {		
		AbstractJdbcWorkerImplImpl w = new AbstractJdbcWorkerImplImpl();
		w.init(provider);
		w.open();
		String sql = "select * from TEST.USERS"; //$NON-NLS-1$		
		PreparedStatement ps1 = w.getPreparedStatement(sql);
		assertNotNull(ps1);
		PreparedStatement ps2 = w.getPreparedStatement(sql);
		assertSame(ps1, ps2);
		String sql3 = "select id from TEST.USERS"; //$NON-NLS-1$
		PreparedStatement ps3 = w.getPreparedStatement(sql3);
		assertNotNull(ps3);
		assertNotSame(ps1, ps3);
		PreparedStatement ps4 = w.getPreparedStatement(sql);
		assertSame(ps1, ps4);
		PreparedStatement ps5 = w.getPreparedStatement(w.selectMethod());
		assertNotNull(ps5);
		w.close();
	}
	
	
	/**
	 * tests that a field annotated as SQL can be executed as query.
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectField() 
	throws InitializationException, DataException, SQLException {		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		w.executePreparedQuery(w.select1);
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query.
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@Test
	public void testSelectMethod() 
	throws InitializationException, DataException, SQLException {	
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		w.executePreparedQuery(w.selectMethod());
		w.close();
	}
	
	/**
	 * tests that a field and a method annotated as SQL can be executed as update.
	 * 
	 * Both are tested on the same test case, because the first method inserts a row
	 * and the second deletes it.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@Test
	public void testUpdatesField() 
	throws InitializationException, DataException, SQLException{
		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		Object[] o1 = {15, "usnew", "new user", 3}; //$NON-NLS-1$ //$NON-NLS-2$
		w.executePreparedUpdate(w.update1,o1);
		
		Object[] o2 = {15};
		w.executePreparedUpdate(w.updateMethod(),o2);
		
		w.close();

	}
	
	
	/**
	 * tests that a static field and a static method annotated as SQL 
	 * can be executed as update.
	 * 
	 * Both are tested on the same test case, because the first method inserts a row
	 * and the second deletes it.
	 * 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testStaticStatements() 
	throws SQLException, DataException, InitializationException {
		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		Object[] o1 = {"static", 20}; //$NON-NLS-1$
		w.executePreparedUpdate(AbstractJdbcWorkerImpl.STATIC_UPDATE,o1);
		w.executePreparedUpdate(AbstractJdbcWorkerImpl.staticUpdateMethod(),o1);
		w.close();
		
	}
	
	/**
	 * tests showPreparedStatement.	 
	 * 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testShowStatement() 
	throws DataException, InitializationException {
		
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		Object[] o1 = {"static", 20}; //$NON-NLS-1$
		String sql1 = w.showPreparedStatement
			(AbstractJdbcWorkerImpl.STATIC_UPDATE,o1);
		String expected1 = 
			"update TEST.USERS set usr_nm = 'static' where id = 20";
		assertEquals(expected1, sql1.trim());
		
		String sql2 = 
			w.showPreparedStatement(w.select1,null);
		String expected2 = "select * from TEST.USERS";
		assertEquals(expected2, sql2.trim());
		w.close();
		
	}	
	
	/**
	 * Test createStatement
	 * @throws InitializationException
	 * @throws DataException
	 * @throws SQLException
	 */
	@Test
	public void testExecutePreparedQuery() throws InitializationException, DataException, SQLException{
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		w.executePreparedQuery(w.select1,null);
		w.close();		
	}
	
	/**
	 * Test createStatement
	 * @throws InitializationException
	 * @throws DataException
	 * @throws SQLException
	 */
	@Test
	public void testExecuteUpdate() throws InitializationException, DataException, SQLException{
		AbstractJdbcWorkerImpl w = new AbstractJdbcWorkerImpl();
		w.init(provider);
		w.open();
		int i = w.updateSomething();
		Assert.assertEquals(0, i);
		w.close();		
	}
	
	
	
	/**
	 * simple implementation of AbstractJdbcWorker used in this unit test.
	 *
	 */
	@ManagerName("LOCALDB")
	private static class AbstractJdbcWorkerImpl extends AbstractJdbcWorker {
		
		/**
		 * select statement string
		 */
		private String select1 = "select * from X__X.USERS"; //$NON-NLS-1$
		
		/**
		 * update statement
		 */		
		private String update1 = 
			"insert into X__X.USERS "  //$NON-NLS-1$
		 +	" (ID, USR_ID, USR_NM, ROLE_ID)  values (?, ?, ?, ?) "; //$NON-NLS-1$
			                  
		/**
		 * static field update statement
		 */		
		private static final String STATIC_UPDATE = 
			"update X__X.USERS set usr_nm = ? where id = ? ";  //$NON-NLS-1$
		
			
		/**
		 * @return an update statement string
		 */
		private static final String staticUpdateMethod() {
			return STATIC_UPDATE;
		}
		
		
		
		/**
		 * @return a select statement string
		 */
		protected String selectMethod() {
			return "select 1 from SYSIBM.SYSDUMMY1"; //$NON-NLS-1$
		}
		
		/**
		 * @return a select statement string
		 */
		private String updateMethod() {
			return "delete from X__X.USERS where ID = ? "; //$NON-NLS-1$
		}
		
		/**
		 * Makes an update.
		 * @return count of lines.
		 * @throws SQLException
		 * @throws DataException 
		 */
		int updateSomething() throws SQLException, DataException {
			String stmt = "Update X__X.USERS set id=? where id=?"; //$NON-NLS-1$
			Object[] parms = {4578266, 2545788};
			int i = executePreparedUpdate(stmt, parms);
			return i;
		}
	}
	
	/**
	 * simple implementation of AbstractJdbcWorker used in this unit test.
	 */
	private static class AbstractJdbcWorkerImplImpl extends AbstractJdbcWorkerImpl {
		/* empty */
	}
	

	
	

}
