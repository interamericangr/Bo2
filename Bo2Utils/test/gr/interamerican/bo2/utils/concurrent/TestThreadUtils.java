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
package gr.interamerican.bo2.utils.concurrent;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for ThreadUtils.
 */
public class TestThreadUtils {
	
	/**
	 * Unit test for sleep(int)
	 */
	@Test
	public void testSleep() {
		Date start = new Date();
		ThreadUtils.sleep(1);
		Date end = new Date();
		long interval = end.getTime()-start.getTime()-1000;
		Assert.assertTrue(interval>=0);
		Assert.assertTrue(interval<90);
	}
	
	/**
	 * Unit test for sleep(int)
	 */
	@Test
	public void testSleepMillis() {
		Date start = new Date();
		ThreadUtils.sleepMillis(100);
		Date end = new Date();
		long interval = end.getTime()-start.getTime()-100;
		long abs = Math.abs(interval);
		boolean ok = abs<10;
		Assert.assertTrue(ok);		
	}

}
