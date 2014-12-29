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
package gr.interamerican.bo2.utils.runnables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.utils.adapters.cmd.SimpleCommandSequence;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.conditions.Condition;

import org.junit.Test;

/**
 * Unit test for {@link Monitor}.
 */
public class TestMonitor {
	
	/**
	 * test for the constructor.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testConstructor() {
		Object o = new Object();
		Condition<Object> stop = mock(Condition.class);		
		int i = 1000;
		Monitor<Object> monitor = new Monitor<Object>(o, i, stop);
		assertEquals(o, monitor.system);
		assertEquals(i, monitor.interval);
		assertEquals(stop, monitor.mustStop);
		assertNotNull(monitor.sequence);
	}
	
	/**
	 * test for the constructor.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testAddOperation() {
		Object system = new Object();		
		Condition<Object> stop = mock(Condition.class);			
		Monitor<Object> monitor = new Monitor<Object>(system, 2, stop);
		monitor.sequence = mock(SimpleCommandSequence.class);
		SimpleCommand cmd = mock(SimpleCommand.class);		
		monitor.addCommand(cmd, 0);
		verify(monitor.sequence, times(1)).addCommand(any(SimpleCommand.class));
	}
	
	/**
	 * test for the constructor.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testRun() {
		Object o = new Object();
		Condition<Object> stop = mock(Condition.class);		
		SimpleCommand operation = mock(SimpleCommand.class);
		when(stop.check(o)).thenReturn(false,false,false,true);		
		Monitor<Object> monitor = new Monitor<Object>(o, 2, stop);
		monitor.addCommand(operation, 0);		
		monitor.run();
		verify(operation,times(4)).execute();
	}

}
