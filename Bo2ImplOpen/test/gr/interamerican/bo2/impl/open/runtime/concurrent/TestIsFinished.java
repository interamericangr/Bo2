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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.batch.LongProcess;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IsFinished}.
 */
public class TestIsFinished {
	
	/**
	 * Test for check().
	 */
	@Test
	public void testCheck() {
		LongProcess lp = mock(LongProcess.class);
		when(lp.isFinished()).thenReturn(false,false,true);
		IsFinished<LongProcess> isFinished = new IsFinished<LongProcess>();
		Assert.assertFalse(isFinished.check(lp));
		Assert.assertFalse(isFinished.check(lp));
		Assert.assertTrue(isFinished.check(lp));
	}

}
