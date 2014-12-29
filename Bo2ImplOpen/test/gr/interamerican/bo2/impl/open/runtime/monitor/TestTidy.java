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
package gr.interamerican.bo2.impl.open.runtime.monitor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Properties;

import gr.interamerican.bo2.arch.batch.LongProcess;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Tidy}.
 */
public class TestTidy {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		Tidy tidy = new Tidy();
		LongProcess lp = mock(LongProcess.class);
		tidy.execute(lp);
		verify(lp, times(1)).tidy();
	}
	
	/**
	 * Unit test for beModified(p).
	 */	
	@Test
	public void testBeModified() {
		Tidy tidy = new Tidy();
		Properties p = new Properties();
		long interval = 1000L;
		p.setProperty(Tidy.TIDY_INTERVAL, Long.toString(interval));
		tidy.beModified(p);
		Assert.assertEquals(interval, tidy.getPeriodInterval());		
	}

}
