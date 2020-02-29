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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * Unit tests for {@link DeleteAction}
 */
public class TestDeleteAction extends Bo2BaseTest{

	/**
	 * Sample Persistent worker.
	 */
	static PersistenceWorker<User> mockPw;

	/**
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		mockPw = MockUtils.mockPw(null, null, null);
		Factory.registerPwFixture(User.class, mockPw);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.DeleteAction#DeleteAction(java.lang.Class)}.
	 */
	@Test
	public void testDeleteAction() {
		DeleteAction<User> tested = new DeleteAction<>(User.class);
		assertNotNull(tested);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.DeleteAction#consume(gr.interamerican.bo2.arch.PersistentObject)}.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws InitializationException
	 */
	@Test
	public void testConsume() throws InitializationException, DataException, LogicException {
		DeleteAction<User> tested = new DeleteAction<>(User.class);
		tested.consume(Factory.create(User.class));
		verify(mockPw).delete(any(User.class));
	}

}
