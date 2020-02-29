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
package gr.interamerican.wicket.bo2.callbacks;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.samples.def.po.City;

/**
 * Unit tests for {@link SaveAction}
 */
public class TestSaveAction  extends Bo2BaseTest{
	
	/**
	 * Init Factory Fixtures
	 */
	@BeforeClass
	public static void buildUp() {
		Factory.registerPwFixture(City.class, MockUtils.mockPw(null, null, null));
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.SaveAction#SaveAction(java.lang.Class)}.
	 */
	@Test
	public void testSaveAction() {
		SaveAction<City> tested = new SaveAction<>(City.class);
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.SaveAction#process(gr.interamerican.bo2.arch.PersistentObject)}.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testProcess() throws InitializationException, DataException, LogicException {
		SaveAction<City> tested = new SaveAction<>(City.class);
		
		City bean = Factory.create(City.class);
		City actual = tested.process(bean);
		
		assertNull(actual);
	}

}
