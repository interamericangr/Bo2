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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link ChainedCallbackActionImpl}
 */
public class TestChainedCallbackActionImpl {
	
	/**
	 * Test chain.
	 */
	@Test
	public void testChain() {
		CallbackAction action1 = Mockito.mock(CallbackAction.class);
		ChainedCallbackActionImpl chain = new ChainedCallbackActionImpl();
		Assert.assertEquals(chain.actions.size(), 0);
		chain.chainAfter(action1);
		Assert.assertEquals(chain.actions.size(), 1);
		chain.callBack(null);
		Mockito.verify(action1, Mockito.times(1)).callBack(null);
		
		CallbackAction action2 = Mockito.mock(CallbackAction.class);
		chain = new ChainedCallbackActionImpl(action1);
		chain.chainBefore(action2);
		Assert.assertEquals(chain.actions.size(), 2);
		chain.callBack(null);
		Mockito.verify(action1, Mockito.times(2)).callBack(null);
		Mockito.verify(action2, Mockito.times(1)).callBack(null);
	}
	
	/**
	 * Test sanity check
	 */
	@Test(expected=RuntimeException.class)
	public void testChain_afterCallback() {
		CallbackAction action1 = Mockito.mock(CallbackAction.class);
		ChainedCallbackActionImpl chain = new ChainedCallbackActionImpl(action1);
		chain.callBack(null);
		chain.chainAfter(Mockito.mock(CallbackAction.class));
	}
	
}
