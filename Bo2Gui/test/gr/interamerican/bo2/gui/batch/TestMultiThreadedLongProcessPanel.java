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
package gr.interamerican.bo2.gui.batch;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParm;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringWithDelayOperation;
import gr.interamerican.bo2.samples.queries.SampleEntitiesQuery;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link MultiThreadedLongProcessPanel}.
 */
public class TestMultiThreadedLongProcessPanel {
	
	/**
	 * Creates a mock MultiThreadedLongProcess with the specified count
	 * of mock sub-processes.
	 * 
	 * @param countOfSubProcesses
	 *        Count of sub-processes.
	 * 
	 * @return Returns a MultiThreadedLongProcess.
	 */
	public static MultiThreadedLongProcess mockProcess(int countOfSubProcesses) {
		MultiThreadedLongProcess process = mock(MultiThreadedLongProcess.class);
		List<LongProcess> subProcesses = new ArrayList<LongProcess>();
		for (int i = 0; i < countOfSubProcesses; i++) {
			LongProcess lp = mock(LongProcess.class);
			subProcesses.add(lp);			
		}
		when(process.getCountOfSubProcesses()).thenReturn(countOfSubProcesses);
		when(process.getSubProcesses()).thenReturn(subProcesses);
		return process;
	}
	
	/**
	 * @return Sample input for the {@link BatchProcess}
	 */
	public static BatchProcessParm<String> sampleBatchProcessInput() {
		@SuppressWarnings("unchecked")
		BatchProcessParm<String> input = Factory.create(BatchProcessParm.class);
		input.setInitialThreads(1);		
		input.setInputPropertyName("string"); //$NON-NLS-1$
		input.setName("TestBatch"); //$NON-NLS-1$
		input.setQuery(new SampleEntitiesQuery()); 
		input.setOperationClass(PrintStringWithDelayOperation.class);
		return input;
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor_withMockProcess() {
		int count = 7;
		MultiThreadedLongProcess model = mockProcess(7);
		MultiThreadedLongProcessPanel panel = new MultiThreadedLongProcessPanel(model);
		Assert.assertNotNull(panel.sysInfo);
		Assert.assertNotNull(panel.statsPanel);
		Assert.assertNotNull(panel.statusLabelsPanel);
		Assert.assertNotNull(panel.aggregatePanel);
		Assert.assertNotNull(panel.subProcessesPanels);
		Assert.assertEquals(count, panel.subProcessesPanels.size());
	}
	
	

	/**
	 * test method for the constructor.
	 */
	@Test
	public void testConcstructor_withRealProcess() {
		BatchProcessParm<String> input = sampleBatchProcessInput();
		MultiThreadedLongProcess mlp = new BatchProcess<String>(input);
		MultiThreadedLongProcessPanel p = new MultiThreadedLongProcessPanel(mlp);		
		Assert.assertNotNull(p.statusLabelsPanel);
		Assert.assertNotNull(p.aggregatePanel);
		Assert.assertNotNull(p.subProcessesPanels);
	}

	/**
	 * test method for refresh
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testRefresh() {
		BatchProcess<?> model = Mockito.mock(BatchProcess.class);
		Mockito.when(model.getParameters()).thenReturn(Mockito.mock(BatchProcessParm.class));
		MultiThreadedLongProcessPanel p = new MultiThreadedLongProcessPanel(model);
		p.refresh();
		verify(model, atLeastOnce()).getFailuresCount();
	}

	/**
	 * test method for newThread.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testNewThread()  {
		BatchProcess<?> model = Mockito.mock(BatchProcess.class);
		Mockito.when(model.getParameters()).thenReturn(Mockito.mock(BatchProcessParm.class));
		MultiThreadedLongProcessPanel panel = new MultiThreadedLongProcessPanel(model);
		panel.newThread();
		verify(model, atLeastOnce()).addQueueProcessors(1);
	}

}
