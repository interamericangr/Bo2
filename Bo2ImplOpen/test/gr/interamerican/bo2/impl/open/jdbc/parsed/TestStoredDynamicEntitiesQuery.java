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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.implopen.pw.UserPwImpl;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * Unit tests for {@link StoredDynamicEntitiesQuery}.
 */
public class TestStoredDynamicEntitiesQuery {
	
	/**
	 * name.
	 */
	private static final String NAME = "George Using"; //$NON-NLS-1$
	
	/**
	 * provider.
	 */
	protected Provider provider;
		
	
	/**
	 * Unit test for the whole life cycle.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 * @throws LogicException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws UnexpectedException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLifecycle() 
	throws InitializationException, DataException, LogicException, 
	SecurityException, NoSuchFieldException, UnexpectedException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() 
			throws LogicException, DataException, InitializationException, UnexpectedException {
				UserPwImpl pw = new UserPwImpl();
				pw.setManagerName("LOCALDB");
				User us = new User();
				us.setId(UtilityForBo2Test.getExistingUserId());
				pw.init(getProvider());
				pw.open();
				us = pw.read(us);
				us.setName(NAME);
				us = pw.update(us);
				pw.close();
			}
		}.execute();
		
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();		
		String path = "/gr/interamerican/rsrc/sql/SelectIdAndNameFromUsers.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider);
		q.open();
		Object criteria = q.getCriteria();		
		
		Field field = criteria.getClass().getDeclaredField("name");
		assertNotNull(field);
		assertEquals(String.class, field.getType());
		
		assertEquals(criteria.getClass(), q.getArgumentType());
		
		ReflectionUtils.set("name", NAME, criteria);		
		q.execute();
		int fieldsCount = q.getFieldsCount();
		
		while (q.next()) {
			for (int i = 1; i <= fieldsCount; i++) {				
				String fieldName = q.getFieldName(i);
				assertEquals(q.getObject(fieldName), q.getObject(i));
			}
			
			Object entity = q.getEntity();
			assertEquals(entity.getClass(), q.getResultType());
			String name = (String) ReflectionUtils.get("name", entity);
			assertEquals(NAME, name.trim());
			Integer id = (Integer) ReflectionUtils.get("id", entity);
			assertNotNull(id);
		}
		q.close();
		
		provider.close();
	}
	
	
	
	/**
	 * Unit test for failed init.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test(expected=InitializationException.class)
	public void testInit_Fail() throws InitializationException, DataException {
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		String path = "/com/foo/bar.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.init(provider);
		provider.close();
	}

	
	/**
	 * Test get values from fields
	 * @throws InitializationException
	 * @throws DataException
	 * @throws ParseException 
	 */
	@Test
	public void testGetFields() throws InitializationException, DataException, ParseException{
		Provider provider2 = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();		
		String path = "/gr/interamerican/rsrc/sql/SelectAllDataTypes.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.setManagerName("LOCALDB"); //$NON-NLS-1$
		q.init(provider2);
		q.open();
		q.execute();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		while (q.next()) {
			assertEquals(1,q.getInt("integ")); //$NON-NLS-1$
			assertEquals(1,q.getInt(1)); 
			
			assertEquals((Double)1.0,(Double)q.getDouble("doub")); //$NON-NLS-1$
			assertEquals((Double)1.0,(Double)q.getDouble(2));
			
			assertEquals("character",q.getString("charact")); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("character",q.getString(3)); //$NON-NLS-1$
			
			Date dt = df.parse("2011-06-14"); //$NON-NLS-1$
			assertEquals(dt,q.getDate("dt")); //$NON-NLS-1$
			assertEquals(dt,q.getDate(4));
			
			Float f = (float) 0.01;
			assertEquals(f,(Float)q.getFloat("flt")); //$NON-NLS-1$
			assertEquals(f,(Float)q.getFloat(5));
			
			assertEquals(new BigDecimal(10),q.getBigDecimal("bigDec")); //$NON-NLS-1$
			assertEquals(new BigDecimal(10),q.getBigDecimal(6));
			
			assertEquals(100000l,q.getLong("lng")); //$NON-NLS-1$
			assertEquals(100000l,q.getLong(7));
			
			assertEquals((short)1,q.getShort("shrt")); //$NON-NLS-1$
			assertEquals((short)1,q.getShort(8));
			
			assertEquals("obj",q.getObject("object")); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("obj",q.getObject(9)); //$NON-NLS-1$
						
			assertEquals(true,q.getBoolean("integ"));  //$NON-NLS-1$
			assertEquals(true,q.getBoolean(1)); 
			
			assertEquals(1,q.getByte("integ"));  //$NON-NLS-1$
			assertEquals(1,q.getByte(1)); 
			
			assertEquals(1,q.getFieldOrder("integ")); //$NON-NLS-1$
			assertEquals(1,q.getRow());
		}
	}
	
	
	/**
	 * Test IsAvoidLock
	 */
	@Test
	public void testIsAvoidLock(){
		String path = "/gr/interamerican/rsrc/sql/SelectAllDataTypes.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.avoidLock = true;
		assertEquals(true, q.isAvoidLock());
	}
	
	
	/**
	 * Test setAvoidLock
	 */
	@Test
	public void testSetAvoidLock(){
		String path = "/gr/interamerican/rsrc/sql/SelectAllDataTypes.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.setAvoidLock(true);
		assertEquals(true, q.avoidLock);
	}
	
	
	/**
	 * Test SetCriteria
	 */
	@Test
	public void testSetCriteria(){
		String path = "/gr/interamerican/rsrc/sql/SelectAllDataTypes.sql"; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		q.setCriteria(1);
		assertEquals(1,q.criteria);
	}

	
	/**
	 * Test getInputObjectType
	 */
	@Test
	public void testGetInputObjectType_nullValue(){
		String path = ""; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		assertNull(q.getArgumentType());
	}
	
	/**
	 * Test getInputObjectType
	 */
	@Test
	public void testGetOutputObjectType_nullValue(){
		String path = ""; //$NON-NLS-1$
		StoredDynamicEntitiesQuery q = new StoredDynamicEntitiesQuery(path);
		assertNull(q.getResultType());
	}
}
