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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link PeriodicCommand}.
 */
public class TestPeriodicCommand {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		SimpleCommand cmd = Mockito.mock(SimpleCommand.class);
		long period = 1000;
		PeriodicCommand pc = new PeriodicCommand(cmd, period);
		Assert.assertEquals(cmd, pc.command);
		Assert.assertEquals(period, pc.period);
		Assert.assertEquals(0, pc.cycle);		
	}
	
	/**
	 * Tests execute.
	 * 
	 * Created a periodic command with a period of 4. Then it executes 
	 * it 8 times and verifies that the SimpleCommand inside was executed
	 * 2 times.
	 */
	@Test
	public void testExecute() {
		SimpleCommand cmd = Mockito.mock(SimpleCommand.class);
		long period = 4;
		PeriodicCommand pc = new PeriodicCommand(cmd, period);
		for (int i = 0; i < 8; i++) {
			pc.execute();
		}
		Mockito.verify(cmd, Mockito.times(2)).execute(); 
	}
	
	

}
