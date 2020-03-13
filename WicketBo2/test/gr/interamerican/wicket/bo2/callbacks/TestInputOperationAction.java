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
import static org.mockito.Mockito.*;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.wicket.samples.Operations.SampleBooleanInputOperation;

/**
 * Unit tests for {@link InputOperationAction}
 */
public class TestInputOperationAction extends Bo2BaseTest {
	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.InputOperationAction#InputOperationAction(java.lang.Class)}.
	 */
	@Test
	public void testInputOperationAction() {
		InputOperationAction<Boolean, SampleBooleanInputOperation> tested = new InputOperationAction<>(SampleBooleanInputOperation.class);
		assertNotNull(tested);
		assertEquals(SampleBooleanInputOperation.class, tested.clz);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.InputOperationAction#consume(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConsume() throws Exception {
		SampleBooleanInputOperation fixture = mock(SampleBooleanInputOperation.class);
		Factory.registerFixture(SampleBooleanInputOperation.class, fixture);

		InputOperationAction<Boolean, SampleBooleanInputOperation> tested = new InputOperationAction<>(SampleBooleanInputOperation.class);
		tested.consume(false);

		verify(fixture).execute();
	}

}
