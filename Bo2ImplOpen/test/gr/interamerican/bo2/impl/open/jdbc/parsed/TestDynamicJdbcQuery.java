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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link DynamicJdbcQuery}
 */
@SuppressWarnings("nls")
public class TestDynamicJdbcQuery {
	
	/**
	 * provider.
	 */
	protected Provider provider;
	
	/**
	 * Setup tests.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
	}
	
	/**
	 * Unit test for Sql.
	 * @throws DataException 
	 * @throws InitializationException 
	 */	
	@Test
	public void testSql_allNull() throws DataException, InitializationException {
		DynamicJdbcQueryImpl q = new DynamicJdbcQueryImpl();
		q.init(provider);
		q.open();
		BeanWith2Fields criteria = new BeanWith2Fields();
		q.setCriteria(criteria);		
		criteria.setField1(null);
		criteria.setField2(null);		
		String actual = q.sql();
		actual = StringUtils.removeParenthesis(actual).trim().toLowerCase();
		String expected = "select * from x__x.users";
		assertEquals(expected, actual);		
		assertNull(q.parms);
		assertNull(q.parameters());
		q.close();
	}
	
	/**
	 * Unit test for Sql.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testSql_noNull() throws InitializationException, DataException {
		DynamicJdbcQueryImpl q = new DynamicJdbcQueryImpl();
		q.init(provider);
		q.open();
		BeanWith2Fields criteria = new BeanWith2Fields();
		q.setCriteria(criteria);		
		criteria.setField1("name");
		criteria.setField2(2);		
		String actual = q.sql();
		actual = StringUtils.removeParenthesis(actual).trim().toLowerCase();
		String expected = "select * from x__x.users where id = ? and name like ?"; 
		assertEquals(expected, actual);		
		assertEquals(q.parameters().length, 2);
		assertEquals(2, q.parms[0]);
		assertEquals("name", q.parms[1]);
		q.close();
	}
	
	/**
	 * Unit test for Sql.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testSql_oneNull() throws InitializationException, DataException {
		DynamicJdbcQueryImpl q = new DynamicJdbcQueryImpl();
		q.init(provider);
		q.open();
		BeanWith2Fields criteria = new BeanWith2Fields();
		q.setCriteria(criteria);		
		criteria.setField1("name");
		criteria.setField2(null);		
		String actual = q.sql();
		actual = StringUtils.removeParenthesis(actual).trim().toLowerCase();
		String expected = "select * from x__x.users where name like ?"; 
		assertEquals(expected, actual);		
		assertEquals(q.parameters().length, 1);
		assertEquals("name", q.parms[0]);
		q.close();
	}
	
	/**
	 * Unit test for parameters.
	 */
	@Test
	public void testParameters() {
		DynamicJdbcQueryImpl q = new DynamicJdbcQueryImpl();		
		Object[] objects = {};
		q.parms = objects;
		assertSame(objects, q.parameters());
	}
	
	/**
	 * Unit test for the whole lifecycle.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test
	public void testLifecycle() throws InitializationException, DataException {		
		DynamicJdbcQueryImpl q = new DynamicJdbcQueryImpl();
		
		q.init(provider);
		q.open();
		BeanWith2Fields criteria = new BeanWith2Fields();
		q.setCriteria(criteria);		
		criteria.setField1(null);
		criteria.setField2(1);		
		q.execute();
		while (q.next()) {
			System.out.println(q.getInt("id"));
		}
		q.close();
		
	}
	
	
	
	/**
	 * Implementation of {@link DynamicJdbcQuery}.
	 */	
	@ManagerName("LOCALDB")
	class DynamicJdbcQueryImpl extends DynamicJdbcQuery<BeanWith2Fields> {
		
		@Override
		public String baseSql() {			
			return "select * from X__X.users where id = :field2 and name like :field1"; 
		}
		
	}
	
	

}
