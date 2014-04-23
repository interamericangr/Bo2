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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.LogicException;

import java.io.Serializable;

import org.junit.Test;

/**
 * Unit tests for {@link MethodBasedBo2WicketBlock}.
 */
@SuppressWarnings("nls")
public class TestMethodBasedBo2WicketBlock implements Serializable { //HACK
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Value used for testing.
	 */
	Integer integer = 0;

	/**
	 * Method to be executed by MethodBasedBo2WicketBlock.
	 */
	void onEvent() {
		integer = 1;
	}

	/**
	 * Method to be executed by MethodBasedBo2WicketBlock.
	 * 
	 * @param i
	 * 
	 */
	void onEventWithParams(Integer i) {
		integer = i;
	}


	/**
	 * Overloaded method.
	 */
	void ambiguous() {/* empty */}

	/**
	 * Overloaded method.
	 * 
	 * @param argument
	 */
	void ambiguous(@SuppressWarnings("unused") Integer argument) {/* empty */}

	/**
	 * Method to be executed by a {@link Bo2WicketBlock} and throw
	 * an Exception.
	 * 
	 * @throws LogicException
	 */
	void doThrow() throws LogicException {
		throw new LogicException();
	}


	/**
	 * Unit test for the creation of a MethodBasedBo2WicketBlock based on an
	 * existing method.
	 */
	@Test
	public void testConstructor_WithExistingMethod() {
		String method = "onEvent";
		MethodBasedBo2WicketBlock block =
				new MethodBasedBo2WicketBlock(method, this);
		assertNotNull(block.handler);
		assertNotNull(block.mi);
	}

	/**
	 * Unit test for the creation of a MethodBasedBo2WicketBlock based on a
	 * method that has an ambiguous name.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_WithAmbiguousMethod() {
		@SuppressWarnings("unused")
		MethodBasedBo2WicketBlock methodBasedBo2WicketBlock = new MethodBasedBo2WicketBlock("ambiguous", this);
	}

	/**
	 * Unit test for the creation of a MethodBasedBo2WicketBlock based on a
	 * method that does not exist.
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_WithNotExistingMethod() {
		@SuppressWarnings("unused")
		MethodBasedBo2WicketBlock methodBasedBo2WicketBlock = new MethodBasedBo2WicketBlock("foo", this);
	}

	/**
	 * Unit test for execute, when the even handler method
	 * does not have any parameter.
	 */
	@Test
	public void testExecute_WithoutParameters() {
		MethodBasedBo2WicketBlock block =
				new MethodBasedBo2WicketBlock("onEvent", this);
		integer = 0;
		block.execute();
		assertEquals(Integer.valueOf(1), integer);
	}

	/**
	 * Unit test for execute, throwing an exception.
	 */
	@Test
	public void testExecute_Throwing() {
		MethodBasedBo2WicketBlock block =
				new MethodBasedBo2WicketBlock("doThrow", this);
		integer = 0;
		try {
			block.execute();
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			assertTrue(cause instanceof LogicException);
		}
	}

	/**
	 * Unit test for execute, when the method being executed requires
	 * arguments.
	 */
	@Test
	public void testExecute_WithParameters() {
		MethodBasedBo2WicketBlock block =
				new MethodBasedBo2WicketBlock("onEventWithParams", this);
		integer = 0;
		block.setHandlerParameter(Integer.class, 7);
		block.execute();
		assertEquals(Integer.valueOf(7), integer);
	}

}
