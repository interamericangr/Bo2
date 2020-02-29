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

import org.junit.Test;

import gr.interamerican.wicket.samples.Operations.SampleBooleanInputOutputOperation;

/**
 * Unit tests {@link InputOutputOperationAction}
 */
public class TestInputOutputOperationActionTest {

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.InputOutputOperationAction#InputOutputOperationAction(java.lang.Class)}.
	 */
	@Test
	public void testInputOutputOperationAction() {
		InputOutputOperationAction<Boolean, SampleBooleanInputOutputOperation> tested = new InputOutputOperationAction<>(SampleBooleanInputOutputOperation.class);
		assertNotNull(tested);
		assertEquals(SampleBooleanInputOutputOperation.class, tested.clz);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.bo2.callbacks.InputOutputOperationAction#process(java.lang.Object)}.
	 * @throws Exception 
	 */
	@Test
	public void testProcess() throws Exception {
		InputOutputOperationAction<Boolean, SampleBooleanInputOutputOperation> tested = new InputOutputOperationAction<>(SampleBooleanInputOutputOperation.class);
		Boolean actual = tested.process(false);
		assertTrue(actual);
	}

}
