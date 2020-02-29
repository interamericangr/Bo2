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
import static org.mockito.Mockito.doThrow;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.samples.archutil.po.User;

/**
 * Unit tests for {@link ReadAction}
 */
public class TestReadAction extends Bo2BaseTest {
	
	/**
	 * Mocked persistence worker.
	 */
	static PersistenceWorker<User> mockPw;
	
	/**
	 * Init fixtures
	 */
	@BeforeClass
	public static void buildUp() {
		mockPw = MockUtils.mockPw(Factory.create(User.class), null, null);
		Factory.registerPwFixture(User.class, mockPw);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.ReadAction#ReadAction(java.lang.Class)}.
	 */
	@Test
	public void testReadAction() {
		ReadAction<User> tested = new ReadAction<>(User.class);
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.ReadAction#apply(gr.interamerican.bo2.arch.PersistentObject)}.
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 */
	@Test
	public void testApply() throws PoNotFoundException, DataException {
		ReadAction<User> tested = new ReadAction<>(User.class);
		tested.apply(Factory.create(User.class));
		
		doThrow(DataException.class).when(mockPw).read(any(User.class));
	}
	
	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.ReadAction#apply(gr.interamerican.bo2.arch.PersistentObject)} where a {@link DataException} is thrown.
	 * @throws DataException 
	 * @throws PoNotFoundException 
	 */
	@Test(expected=RuntimeException.class)
	public void testApply_failure() throws PoNotFoundException, DataException {
		ReadAction<User> tested = new ReadAction<>(User.class);
		doThrow(DataException.class).when(mockPw).read(any(User.class));
		tested.apply(Factory.create(User.class));
	}

}
