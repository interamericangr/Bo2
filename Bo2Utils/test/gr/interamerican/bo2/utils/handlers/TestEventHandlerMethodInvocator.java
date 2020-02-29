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
package gr.interamerican.bo2.utils.handlers;

import gr.interamerican.bo2.utils.exc.ThrowingExceptionHandler;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link EventHandlerMethodInvocator}.
 */
@SuppressWarnings("nls")
public class TestEventHandlerMethodInvocator {
	
	/**
	 * Method to be executed by MethodInvocator. 
	 *
	 * @param i
	 *            the i
	 * @param s
	 *            the s
	 * @param d
	 *            the d
	 */
	@SuppressWarnings("unused")
	void someMethod(Integer i, String s, Double d) {
		/* do nothing */
	}

	/**
	 * Unit test for execute, when the method being executed requires arguments.
	 */
	@Test
	public void testGetArguments() {
		String methodName = "someMethod";
		EventHandlerComponent<Object> ehc = 
			new EventHandlerComponent<Object>(this,ThrowingExceptionHandler.INSTANCE);
		EventHandlerMethodInvocator block = 
			new EventHandlerMethodInvocator(ehc, methodName, this);
		
		Integer iParm = 7;
		String sParm = "foo";
		Double dParm = 3.5d;
		
		ehc.setHandlerParameter(Integer.class, iParm);
		ehc.setHandlerParameter(String.class, sParm);
		ehc.setHandlerParameter(Double.class, dParm);
		
		Object[] expecteds = {iParm, sParm, dParm};
		Object[] actuals = block.getArguments();
		
		Assert.assertArrayEquals(expecteds, actuals);
	}
}