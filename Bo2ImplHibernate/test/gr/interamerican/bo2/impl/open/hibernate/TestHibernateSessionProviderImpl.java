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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link HibernateSessionProviderImpl}.
 */
public class TestHibernateSessionProviderImpl {
	
	/**
	 * Object to test.
	 */
	HibernateSessionProviderImpl manager;
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		manager = new HibernateSessionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
	}
	
	/**
	 * Unit test for constructor.
	 * 
	 */
	@Test
	public void testConstructor() {
		assertNotNull(manager);
		assertNotNull(manager.getConnection());
		assertNotNull(manager.getHibernateSession());

	}
	
	/**
	 * Unit test for constructor.
	 * @throws DataException 
	 * 
	 */
	@Test
	public void testOpenAndClose() throws DataException {
		manager.close();
		assertNull(manager.getConnection());
		assertNull(manager.getHibernateSession());
	}
	
	/**
	 * Unit test for register.
	 * 
	 */
	@Test
	public void testRegister()  {
		Invoice o = Factory.create(Invoice.class);//All three implementations are AbstractBasePo		
		manager.register(o);
		assertTrue(manager.entities.contains(o));
		DetachStrategy strategy = PoUtils.getDetachStrategy(o);
		assertEquals(HibernateDetachStrategy.INSTANCE, strategy);
	}
	
	/**
	 * Unit test for setExcluded.
	 */
	@Test
	public void testSetExcluded() {
		Invoice o = Factory.create(Invoice.class);
		manager.setExcluded(o);
		assertTrue(manager.excluded.contains(o));
	}
	
	/**
	 * Unit test for setNotExcluded.
	 */
	@Test
	public void testSetNotExcluded() {
		Invoice o = Factory.create(Invoice.class);
		manager.setNotExcluded(o);
		assertTrue(!manager.excluded.contains(o));
		manager.setExcluded(o);
		assertTrue(manager.excluded.contains(o));
		manager.setNotExcluded(o);
		assertTrue(!manager.excluded.contains(o));
	}
	
	/**
	 * Unit test for setFlushStrategy.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testSetFlushStrategy() {
		FlushStrategy strategy = (FlushStrategy) ReflectionUtils.get("flushStrategy", manager);
		assertEquals(FlushStrategy.SESSION, strategy);
		manager.setFlushStrategy(FlushStrategy.OBJECT);
		strategy = (FlushStrategy) ReflectionUtils.get("flushStrategy", manager);
	assertEquals(FlushStrategy.OBJECT, strategy);
	}
	

}
