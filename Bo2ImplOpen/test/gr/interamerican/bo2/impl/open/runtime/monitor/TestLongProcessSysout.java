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
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit tests for {@link LongProcessSysout}.
 */
public class TestLongProcessSysout {

	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		LongProcessSysout sysout = new LongProcessSysout();
		MultiThreadedLongProcess lp = mock(MultiThreadedLongProcess.class);
		sysout.msgCreator = mock(LongProcessToString.class);
		sysout.execute(lp);
		verify(sysout.msgCreator, times(1)).execute(lp);
	}

	/**
	 * Unit test for beModified(p).
	 */
	@Test
	public void testBeModified() {
		LongProcessSysout sysout = new LongProcessSysout();
		Properties p = new Properties();
		long interval = 1000L;
		p.setProperty(MonitoringConstants.SYSOUT_INTERVAL, Long.toString(interval));
		sysout.beModified(p);
		Assert.assertEquals(interval, sysout.getPeriodInterval());
	}

}
