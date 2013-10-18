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
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.samples.implopen.queries.DummyAllTypesQuery;
import gr.interamerican.bo2.utils.sql.types.BigDecimalType;
import gr.interamerican.bo2.utils.sql.types.BooleanType;
import gr.interamerican.bo2.utils.sql.types.ByteType;
import gr.interamerican.bo2.utils.sql.types.CalendarType;
import gr.interamerican.bo2.utils.sql.types.DateType;
import gr.interamerican.bo2.utils.sql.types.DoubleType;
import gr.interamerican.bo2.utils.sql.types.FloatType;
import gr.interamerican.bo2.utils.sql.types.IntegerType;
import gr.interamerican.bo2.utils.sql.types.LongType;
import gr.interamerican.bo2.utils.sql.types.ShortType;
import gr.interamerican.bo2.utils.sql.types.StringType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

/**
 * Unit test of JdbcQuery.
 * 
 *
 */
public class TestJdbcQuery extends AbstractNonTransactionalProviderTest {
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() throws InitializationException, DataException {	
		JdbcQueryImpl w = new JdbcQueryImpl();		
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			String name = w.getString("USR_NM");
			String userName = w.getString(3);
			String name1 = w.get("USR_NM",StringType.INSTANCE);
			assertEquals(name, name1);
			assertEquals(userName, name1);
			
			Integer id1 = w.getInt("ID");
			Integer id2 = w.getInt(1);
			Integer id = w.get("ID",IntegerType.INSTANCE);
			assertEquals(id1, id);
			assertEquals(id2, id);
			
			Boolean b1 = w.getBoolean("ID");
			Boolean b2 =w.getBoolean(1);
			Boolean b = w.get("ID",BooleanType.INSTANCE);
			assertEquals(b1, b);
			assertEquals(b2, b);
			assertEquals(i,w.getRow());			
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test(expected=DataAccessException.class)
	public void testGetWithDataException() throws InitializationException, DataException {	
		JdbcQueryImpl w = new JdbcQueryImpl();		
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.get("",StringType.INSTANCE);
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testGetWithNamedParameter() throws InitializationException, DataException {	
		JdbcQueryParameterizedImpl w = new JdbcQueryParameterizedImpl();		
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
		}
		w.close();
	}
	
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetDouble() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Double d1 = q.getDouble("doub"); //$NON-NLS-1$
			Double d2 = q.getDouble(2);
			Double d = q.get("doub",DoubleType.INSTANCE); //$NON-NLS-1$
			assertEquals(d1, d);
			assertEquals(d2, d);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionDouble() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getDouble("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionDoubleIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getDouble(3);
		}
		w.close();
	}

	/**
	 * Unit test for setAvoidLock
	 */
	@Test
	public void testSetGetAvoidLock() {
		JdbcQuery q = new DummyAllTypesQuery();		
		q.setAvoidLock(true);
		assertTrue(q.withUR);
	}
	
	/**
	 * Unit test for isAvoidLock
	 */
	@Test
	public void testIsAvoidLock() {
		JdbcQuery q = new DummyAllTypesQuery();
		q.withUR = true;
		assertTrue(q.isAvoidLock());
	}
	
	/**
	 * Unit test for isAvoidLock
	 */
	@Test
	public void testCil() {
		JdbcQuery q = new DummyAllTypesQuery();
		q.setAvoidLock(true);
		String stmt = "statement"; //$NON-NLS-1$
		assertTrue(q.cil(stmt).endsWith(JdbcQuery.WITH_UR));
		
		q.setAvoidLock(false);
		assertEquals(stmt, q.cil(stmt));
	}

	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetFloat() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Float f1 = q.getFloat("flt"); //$NON-NLS-1$
			Float f2 = q.getFloat(5);
			Float f = q.get("flt",FloatType.INSTANCE); //$NON-NLS-1$
			assertEquals(f1, f);
			assertEquals(f2, f);
		}
		q.close();
	
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionFloat() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getFloat("USR_NM");//$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionFloatIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getFloat(3);
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionInt() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getInt("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionIntArgsInt() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getInt(3);
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetLong() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Long l1 = q.getLong("lng"); //$NON-NLS-1$
			Long l2 = q.getLong(7);
			Long l = q.get("lng",LongType.INSTANCE); //$NON-NLS-1$
			assertEquals(l1, l);
			assertEquals(l2, l);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionLong() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getLong("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionLongIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getLong(3);
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetShort() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Short s1 = q.getShort("shrt"); //$NON-NLS-1$
			Short s2 = q.getShort(1);
			Short s = q.get("shrt",ShortType.INSTANCE); //$NON-NLS-1$
			assertEquals(s1, s);
			assertEquals(s2, s);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionShort() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getShort("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionShortIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getShort(3);
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetBigDecimal() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			BigDecimal bd1 = q.getBigDecimal("BigDec"); //$NON-NLS-1$
			BigDecimal bd2 = q.getBigDecimal(6);
			BigDecimal bd = q.get("BigDec",BigDecimalType.INSTANCE); //$NON-NLS-1$
			assertEquals(bd1, bd);
			assertEquals(bd2, bd);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBigDecimal() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getBigDecimal("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBigDecimalIntArg() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getBigDecimal(3);
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetByte() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Byte b1 = q.getByte("bt"); //$NON-NLS-1$
			Byte b2 =q.getByte(10);
			Byte b = q.get("bt",ByteType.INSTANCE); //$NON-NLS-1$
			assertEquals(b1, b);
			assertEquals(b2, b);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionByte() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getByte("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionByteIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getByte(3); 
		}
		w.close();
	}
	
	
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBytes() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getBytes("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBytesIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getBytes(3);
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetCalendar() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Calendar c1 = q.getCalendar("dt"); //$NON-NLS-1$
			Calendar c2 = q.getCalendar(4);
			Calendar d =  q.get("dt",CalendarType.INSTANCE); //$NON-NLS-1$
			assertEquals(c1, d);
			assertEquals(c2, d);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionCalendar() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getCalendar("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionCalendarIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getCalendar(3);
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetDate() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			Date dt1 = (Date) q.getDate("dt"); //$NON-NLS-1$
			Date dt2 =(Date) q.getDate(4);
			Date d = (Date) q.get("dt",DateType.INSTANCE); //$NON-NLS-1$
			assertEquals(dt1, d);
			assertEquals(dt2, d);
		}
		q.close();
	
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionDate() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getDate("USR_NM"); //$NON-NLS-1$
		}
		w.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionDateIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQueryImpl w = new JdbcQueryImpl();
		w.init(provider);
		w.open();
		w.execute();
		int i=0;
		while (w.next()) {
			i++;
			w.getDate(3); 
		}
		w.close();
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testExecute_GetObject() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getObject("object"); //$NON-NLS-1$
			q.getObject(9);
		}
		q.close();
	
	}
	
	/**
	 * Simple Implementation of {@link JdbcQuery} used in this unit test.
	 */
	@ManagerName("LOCALDB")
	private static class JdbcQueryImpl extends JdbcQuery {
		

		@Override
		protected Object[] parameters() {
			return null;
		}

		@Override
		protected String sql() {		
			return "select * from X__X.users"; //$NON-NLS-1$
		}
	}
	
	/**
	 * Implementation of {@link JdbcQuery} used in this unit test
	 * based on a named parameter.
	 */
	@ManagerName("LOCALDB")
	private static class JdbcQueryParameterizedImpl extends JdbcQuery {
		
		/**
		 * named parameter
		 */
		@SuppressWarnings("unused")
		@Parameter Long minId = -1L;
		
		@Override
		protected String sql() {		
			return "select * from X__X.users where ID > :minId"; //$NON-NLS-1$
		}
	}
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBoolean() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getBoolean("dt");  //$NON-NLS-1$
		}
		q.close();
	}
	
	
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionBooleanIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getBoolean(4);
		}
		q.close();
	
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionObject() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getObject(""); //$NON-NLS-1$
		}
		q.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionObjectIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getObject(0);
		}
		q.close();
	}
	
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionString() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getString(""); //$NON-NLS-1$
		}
		q.close();
	}
		
	/**
	 * tests that a method annotated as SQL can be executed as query
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test(expected=DataAccessException.class)
	public void testExecute_DataExceptionStringIntArgs() 
	throws DataException, InitializationException {
		
		JdbcQuery q = new DummyAllTypesQuery();
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		q.execute();
		int i=0;
		while (q.next()) {
			i++;
			q.getString(0);
		}
		q.close();
	}		

}
