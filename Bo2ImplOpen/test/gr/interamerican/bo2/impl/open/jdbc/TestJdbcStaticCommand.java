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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link JdbcStaticCommand}.
 */
public class TestJdbcStaticCommand 
extends AbstractNonTransactionalProviderTest {
	
	/**
	 * SQL.
	 */
	String sql = "delete from X__X.users where 1 = 2"; //$NON-NLS-1$
	
	
	/**
	 * Tests the life cycle.
	 */
	@Test
	public void testConstructor() {
		JdbcStaticCommand cmd = new JdbcStaticCommand(sql);
		Assert.assertEquals(sql, cmd.statement);
	}
	
	/**
	 * Tests the life cycle.
	 * 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testLifecycle() 
	throws DataException, InitializationException {
		JdbcStaticCommand cmd = new JdbcStaticCommand(sql);
		cmd.rowsModified = 100; 
		cmd.setManagerName("LOCALDB"); //$NON-NLS-1$
		cmd.init(provider);
		cmd.open();
		cmd.execute();
		cmd.close();
		/*
		 * the statement should not modify any row (where 1=2)
		 */
		Assert.assertEquals(0, cmd.rowsModified); 
	}
	
	/**
	 * Tests getRowsModified().
	 * 
	 */
	@Test
	public void testGetRowsModified() {
		JdbcStaticCommand cmd = new JdbcStaticCommand(sql);
		cmd.rowsModified = 100; 
		Assert.assertEquals(cmd.rowsModified, cmd.getRowsModified()); 
	}
	
	
	
			
		
	
	
	
}
