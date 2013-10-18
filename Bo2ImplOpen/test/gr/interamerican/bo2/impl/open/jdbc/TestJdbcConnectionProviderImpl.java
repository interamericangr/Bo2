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
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link JdbcConnectionProviderImpl}
 * 
 *
 */
public class TestJdbcConnectionProviderImpl {
	
	/**
	 * Object to test.
	 */
	JdbcConnectionProviderImpl manager;
	
	/**
	 * tests setup.
	 * @throws InitializationException 
	 */
	@Before
	public void setup() throws InitializationException {
		manager = new JdbcConnectionProviderImpl(UtilityForBo2Test.getSampleJdbcProperties());
	}
	
	/**
	 * Tests close.
	 *   
	 * @throws DataException 
	 */
	@Test
	public void testClose() throws DataException {	
		manager.close();
		assertTrue(manager.getConnection() == null);
		manager.close();
		assertTrue(manager.getConnection() == null);
	}

}
