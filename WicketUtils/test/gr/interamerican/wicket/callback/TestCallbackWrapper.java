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
package gr.interamerican.wicket.callback;

import static org.junit.Assert.*;

import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.WicketPage;

/**
 * Unit test for {@link CallbackWrapper}.
 */
@Deprecated
public class TestCallbackWrapper extends WicketTest {

	/**
		 * 
		 */
	public class InnerDummyCallback extends SimpleCallbackAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Shows if it has been excuted.
		 */
		private boolean executed = false;

		@Override
		protected void work() {
			executed = true;
		}

		/**
		 * Gets the executed.
		 *
		 * @return Returns the executed
		 */
		public boolean isExecuted() {
			return executed;
		}

		/**
		 * Assigns a new value to the executed.
		 *
		 * @param executed the executed to set
		 */
		public void setExecuted(boolean executed) {
			this.executed = executed;
		}

	}

	/** action to test. */
	InnerDummyCallback action = new InnerDummyCallback();

	/**
	 * Test setup.
	 */
	@Before
	public void setup() {
		action.setExecuted(false);
	}

	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor_withArg() {
		CallbackWrapper wrapper = new CallbackWrapper(action);
		assertSame(action, wrapper.action);
	}

	/**
	 * Unit test for the no arg constructor.
	 */
	@Test
	public void testConstructor_withoutArg() {
		CallbackWrapper wrapper = new CallbackWrapper();
		assertNull(wrapper.action);
	}

	/**
	 * Test CallBack with action..
	 */
	@Test
	public void testCallBack_withAction() {
		WicketPage page = new WicketPage();
		AjaxRequestTarget target = new AjaxRequestHandler(page);
		CallbackWrapper wrapper = new CallbackWrapper(action);
		wrapper.callBack(target);
		assertTrue(action.isExecuted());
	}

	/**
	 * Test CallBack with action.
	 * 
	 * No assert. just make sure there is no Exception here.
	 */
	@Test
	public void testCallBack_withoutAction() {
		WicketPage page = new WicketPage();
		AjaxRequestTarget target = new AjaxRequestHandler(page);
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.callBack(target);
	}

	/**
	 * Test getAction.
	 */
	@Test
	public void testSetAction() {
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.setAction(action);
		assertEquals(action, wrapper.action);
	}

	/**
	 * Test getAction.
	 */
	@Test
	public void testGetAction() {
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.action = action;
		assertEquals(action, wrapper.getAction());
	}

}
