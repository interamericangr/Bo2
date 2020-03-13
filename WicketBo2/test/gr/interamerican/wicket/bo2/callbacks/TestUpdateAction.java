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

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * Unit tests for {@link UpdateAction}
 */
public class TestUpdateAction extends Bo2BaseTest {
	
	/**
	 * Init test fixtures
	 */
	@BeforeClass
	public static void buildUp() {
		Factory.registerPwFixture(User.class, MockUtils.mockPw(null, Factory.create(User.class), null));
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.UpdateAction#UpdateAction(java.lang.Class)}.
	 */
	@Test
	public void testUpdateAction() {
		UpdateAction<User> tested = new UpdateAction<>(User.class);
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.UpdateAction#process(gr.interamerican.bo2.arch.PersistentObject)}.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws InitializationException 
	 */
	@Test
	public void testProcess() throws InitializationException, DataException, LogicException {
		UpdateAction<User> tested = new UpdateAction<>(User.class);
		tested.process(Factory.create(User.class));
	}

}
