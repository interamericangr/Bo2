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

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link StatementLocator}.
 */
@SuppressWarnings("nls")
public class TestStatementLocator {
	
	/**
	 * Tests getSqlField(clazz).
	 * 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@Test
	public void testGetSqlField_classWithOne() 
	throws SecurityException, NoSuchFieldException {
		Field expected = ClassWithOneSqlField.class.getDeclaredField("sql1");
		Field actual = StatementLocator.getSqlField(ClassWithOneSqlField.class);
		Assert.assertEquals(expected, actual);
		Assert.assertTrue(actual.isAccessible());
	}
	
	/**
	 * Tests getSqlField(clazz).
	 */
	@Test(expected=RuntimeException.class)
	public void testGetSqlField_classWithTwo() {
		StatementLocator.getSqlField(ClassWithTwoSqlFields.class);
	}
	
	/**
	 * Tests getSqlField(clazz).
	 */
	@Test(expected=RuntimeException.class)
	public void testGetSqlField_classWithNo() {
		StatementLocator.getSqlField(ClassWithoutSqlFields.class);
	}
	
	/**
	 * Tests the constructor.
	 * 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@Test()
	public void testConstructor() throws SecurityException, NoSuchFieldException {
		ClassWithOneSqlField one = new ClassWithOneSqlField();
		StatementLocator loc = new StatementLocator(one);
		Assert.assertEquals(one, loc.sqlObject);
		Field expected = ClassWithOneSqlField.class.getDeclaredField("sql1");
		Assert.assertEquals(expected, loc.sqlField);
	}
	
	/**
	 * Tests sql().
	 */
	@Test()
	public void testSql() {
		ClassWithOneSqlField one = new ClassWithOneSqlField();
		StatementLocator loc = new StatementLocator(one);
		one.sql1 = "Select * from USERS";
		String actual = loc.sql();
		Assert.assertEquals(actual, one.sql1);
	}
	
	
	
	/**
	 * Class.WithOneSqlField
	 */
	static class ClassWithOneSqlField {		
		/**
		 * SQL.
		 */
		@Sql String sql1 = "select 1 from Sysibm.sysdummy1";
	}
	/**
	 * ClassWithTwoSqlFields.
	 */
	static class ClassWithTwoSqlFields extends ClassWithOneSqlField {		
		/**
		 * SQL.
		 */
		@Sql String sql2 = "select 1 from Sysibm.sysdummy1";
	}
	/**
	 * ClassWithoutSqlFields.
	 */
	static class ClassWithoutSqlFields {		
		/* empty */
	}
	


}
