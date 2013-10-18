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
package gr.interamerican.bo2.utils.beans;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link SystemState}.
 */
public class TestSystemState {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		SystemState state = new SystemState();
		Assert.assertNotNull(state.toString());
		System.out.println(state.toString());
	}
	
	/**
	 * Tests change().
	 */
	@Test
	public void testChange() {
		SystemState first = new SystemState();
		SystemState last = new SystemState();
		SystemState change = SystemState.change(first, last);
		Assert.assertEquals(change.getFreeMemory(), last.getFreeMemory() - first.getFreeMemory(),0.0);
		Assert.assertEquals(change.getMaxMemory(), last.getMaxMemory() - first.getMaxMemory(),0.0);
		Assert.assertEquals(change.getTotalMemory(), last.getFreeMemory() - first.getFreeMemory(),0.0);
		Assert.assertEquals(change.getUsedMemory(), last.getUsedMemory() - first.getUsedMemory(),0.0);
		
	}

}
