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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.Date;

import org.junit.Test;


/**
 * Unit tests for {@link LongProcessToString}.
 */
public class TestLongProcessToString {
	
	/**
	 * Creates a mock MultiThreadedLongProcess.
	 * 
	 * @param start
	 * @param end
	 * @param finished 
	 * @return Returns the mock.
	 */
	static MultiThreadedLongProcess mockForToString(Date start, Date end, boolean finished) {
		MultiThreadedLongProcess mlp = mock(MultiThreadedLongProcess.class);
		when(mlp.getName()).thenReturn("MOCK"); //$NON-NLS-1$
		when(mlp.isFinished()).thenReturn(finished);
		when(mlp.isPaused()).thenReturn(false);
		when(mlp.getCountOfSubProcesses()).thenReturn(5);
		when(mlp.getSuccessesCount()).thenReturn(10000L);
		when(mlp.getFailuresCount()).thenReturn(1000L);
		when(mlp.getProcessedCount()).thenReturn(11000L);
		when(mlp.getStartTime()).thenReturn(start);
		when(mlp.getEndTime()).thenReturn(end);
		return mlp;
	}
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testExecute() {
		Date end = new Date();
		long dur = 4 * 60 * 60 * 1000; //4 hours.
		Date start = new Date(end.getTime()-dur);
		MultiThreadedLongProcess mlp = mockForToString(start, end, false);
		LongProcessToString toString = new LongProcessToString();
		
		assertNull(toString.previous);
		assertNull(toString.previousTmstmp);
		
		String str = toString.execute(mlp); //first call.
		assertNotNull(str);
		assertNotNull(toString.current);
		assertNotNull(toString.currentTmstmp);
		assertNotNull(toString.previous);
		assertNotNull(toString.previousTmstmp);

		
		System.out.println(str);
		
		ThreadUtils.sleep(1);
		long processed = Double.valueOf(mlp.getProcessedCount()*1.2).longValue();
		long success = Double.valueOf(mlp.getSuccessesCount()* 1.2).longValue();
		long failures = Double.valueOf(mlp.getFailuresCount()* 1.2).longValue();		
		when(mlp.getProcessedCount()).thenReturn(processed);
		when(mlp.getSuccessesCount()).thenReturn(success);
		when(mlp.getFailuresCount()).thenReturn(failures);		
		str = toString.execute(mlp); //second call.
		assertNotNull(str);		
		assertNotNull(toString.current);
		assertNotNull(toString.currentTmstmp);
		System.out.println(str);
		
		assertNotNull(toString.previous);
		assertNotNull(toString.previousTmstmp);

		
		
	}

}
