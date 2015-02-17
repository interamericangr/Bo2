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

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessUtility;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.runnables.Monitor;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * test method for {@link BatchProcessFrame}
 */
public class TestBatchProcessFrame {
	/**
	 * test the constructor.
	 */
	@Test
	public void testConstructor() {
		Properties model = new Properties();
		BatchProcessFrame frame = new BatchProcessFrame(model);
		Assert.assertNotNull(frame.inputPanel);
	}
	
	/**
	 * test the constructor.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testStart() {
		String path = "/gr/interamerican/rsrc/batchprocess/PrintStrings.properties"; //$NON-NLS-1$
		Properties p = CollectionUtils.readProperties(path);
		BatchProcessFrame frame = new BatchProcessFrame(p);
		frame.setVisible(true);		
		frame.controller = mock(BatchProcessUtility.class);
		BatchProcess batch = mock(BatchProcess.class);
		Monitor monitor = mock(Monitor.class);
		
		when(frame.controller.createBatchProcess(p)).thenReturn(batch);
		when(frame.controller.createMonitor(batch, p)).thenReturn(monitor);
		
		frame.start();
		Assert.assertNotNull(frame.batch);
		Assert.assertNotNull(frame.processPanel);
		Assert.assertNotNull(frame.monitor);
		verify(frame.controller, times(1)).startBatchProcess(batch);
		verify(frame.controller, times(1)).startMonitor(monitor);
	}
	
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "/gr/interamerican/rsrc/batchprocess/PrintStrings.properties"; //$NON-NLS-1$
		Properties p = CollectionUtils.readProperties(path);
		BatchProcessFrame frame = new BatchProcessFrame(p);
		frame.setVisible(true);
	}
}
