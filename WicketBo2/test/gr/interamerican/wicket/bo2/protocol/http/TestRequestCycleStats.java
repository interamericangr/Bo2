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
package gr.interamerican.wicket.bo2.protocol.http;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.beans.Timer;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import org.junit.Test;

/**
 * Tests fro {@link RequestCycleStats}.
 */
public class TestRequestCycleStats {
	
	/**
	 * Test for newCycle()
	 */
	@Test
	public void testNewCycle() {
		RequestCycleStats stats = new RequestCycleStats();
		assertEquals(0L, stats.cycles);
		stats.newCycle();
		assertEquals(1L, stats.cycles);
		stats.newCycle();
		assertEquals(2L, stats.cycles);
	}
	
	
	/**
	 * Tests doLogForDebugging().
	 */
	@Test
	public void testDoLogForDebbuging() {
		Timer t = new Timer();
		ThreadUtils.sleepMillis(RequestCycleStats.TOO_SLOW + 10);		
		RequestCycleStats stats = new RequestCycleStats();
		stats.doLogForDebugging("workers", t); //$NON-NLS-1$
	}
	
	/**
	 * Tests doLogForDebugging().
	 */
	@Test
	public void testLogForDebbuging() {
		RequestCycleStats stats = new RequestCycleStats();
		stats.logForDebugging("workers", new Timer()); //$NON-NLS-1$
	}
	
	/**
	 * Tests doLogForDebugging().
	 */
	@Test
	public void testLogExceptionStats() {
		RequestCycleStats stats = new RequestCycleStats();
		stats.logExceptionStats();
	}
	
	/**
	 * Tests doLogForDebugging().
	 */
	@Test
	public void testUpdateExceptionStats() {
		RequestCycleStats stats = new RequestCycleStats();
		assertEquals(0L, stats.cyclesUntrackedEx);
		
		stats.updateExceptionStats(new RuntimeException());
		assertEquals(1L, stats.cyclesUntrackedEx);
		stats.updateExceptionStats(new RuntimeException(new DataException()));
		assertEquals(1L, stats.cyclesUntrackedEx);
	}
	
	
	

}
