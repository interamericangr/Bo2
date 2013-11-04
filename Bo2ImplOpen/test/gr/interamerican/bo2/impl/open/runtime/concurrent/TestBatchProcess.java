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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.samples.implopen.operations.OperationWithJdbcWorker;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.StringsQuery;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link BatchProcess}
 */
public class TestBatchProcess {

	/**
	 * Clean up.
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws UnexpectedException, DataException, LogicException {
		Execute.transactional(new DeleteInvoiceData());
	}

	/**
	 * Clean up.
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	@AfterClass
	public static void tearDownAfterClass() throws UnexpectedException, DataException, LogicException  {
		Execute.transactional(new DeleteInvoiceData());
	}

	/**
	 * Creates a sample batch process with 5 initial queue processors.
	 * 
	 * @return returns the sample.
	 */
	static BatchProcess<String> sample() {
		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		EntitiesQuery<String> q = new StringsQuery(1000);
		String name = "TestBatch"; //$NON-NLS-1$
		String property = "string"; //$NON-NLS-1$
		int initialQueues = 5;
		@SuppressWarnings("unchecked")
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName(name);
		parms.setQuery(q);
		parms.setInitialThreads(initialQueues);
		parms.setFormatter(formatter);
		parms.setOperationClass(PrintStringOperation.class);
		parms.setInputPropertyName(property);
		BatchProcess<String> batch = new BatchProcess<String>(parms);
		return batch;
	}

	/**
	 * Test BatchProcess with an operation that is not doing any
	 * transactions with the database.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked")
		BatchProcessParm<Object> parms =
		Factory.create(BatchProcessParm.class);
		BatchProcess<Object> batch = new BatchProcess<Object>(parms);
		Assert.assertEquals(parms, batch.parameters);
		Assert.assertNotNull(batch.queueProcessors);
		Assert.assertNotNull(batch.inputQueue);
		Assert.assertNull(batch.startTime);
		Assert.assertNull(batch.endTime);
	}

	/**
	 * Tests forceQuit()
	 */
	@Test
	public void testForceQuit() {
		BatchProcess<String> bp = sample();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.forceQuit();
		ThreadUtils.sleep(1);
		for (QueueProcessor<String> qp : bp.queueProcessors) {
			Assert.assertTrue(qp.isFinished());
		}
	}


	/**
	 * Tests add queue processor.
	 */
	@Test
	public void testAddQueueProcessor() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		bp.addQueueProcessor();
		Assert.assertEquals(initialCount+1, bp.queueProcessors.size());
		bp.forceQuit();
	}

	/**
	 * Tests add queue processor.
	 */
	@Test
	public void testPauseQueueProcessor() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.pauseQueueProcessor(initialCount);
		ThreadUtils.sleep(1);
		QueueProcessor<String> qp = bp.queueProcessors.get(initialCount);
		Assert.assertTrue(qp.isPaused());
		QueueProcessor<String> qp1 = bp.queueProcessors.get(initialCount+1);
		Assert.assertFalse(qp1.isPaused());
		bp.forceQuit();
	}

	/**
	 * Tests add queue processor.
	 */
	@Test
	public void testResumeQueueProcessor() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.pauseQueueProcessor(initialCount);
		ThreadUtils.sleep(1);
		bp.resumeQueueProcessor(initialCount);
		QueueProcessor<String> qp = bp.queueProcessors.get(initialCount);
		ThreadUtils.sleep(1);
		Assert.assertFalse(qp.isPaused());
		bp.forceQuit();
	}

	/**
	 * Tests add queue processor.
	 */
	@Test
	public void testQuitQueueProcessor() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.addQueueProcessor();
		bp.quitQueueProcessor(initialCount);
		ThreadUtils.sleep(1);
		QueueProcessor<String> qp = bp.queueProcessors.get(initialCount);
		Assert.assertTrue(qp.isFinished());
		bp.forceQuit();
	}

	/**
	 * Tests addQueueProcessors.
	 */
	@Test
	public void testAddQueueProcessors() {
		BatchProcess<String> bp = sample();
		int count = 5;
		bp.addQueueProcessors(count);
		Assert.assertEquals(count, bp.countOfProcessorsToAdd);
	}

	/**
	 * Tests addQueueProcessors when countOfProcessorsToAdd!=0.
	 */
	@Test
	public void testAddQueueProcessorsIfCommanded_adding() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		int countOfNewProcessors = 5;
		bp.addQueueProcessors(countOfNewProcessors);
		bp.addNewProcessorsIfCommanded();
		int newCount = bp.queueProcessors.size();
		int expected = initialCount+countOfNewProcessors;
		Assert.assertEquals(expected, newCount);
		Assert.assertEquals(0, bp.countOfProcessorsToAdd);
	}

	/**
	 * Tests addQueueProcessors when countOfProcessorsToAdd=0.
	 */
	@Test
	public void testAddQueueProcessorsIfCommanded_doingNothing() {
		BatchProcess<String> bp = sample();
		int initialCount = bp.queueProcessors.size();
		bp.countOfProcessorsToAdd = 0;
		bp.addNewProcessorsIfCommanded();
		int newCount = bp.queueProcessors.size();
		Assert.assertEquals(initialCount, newCount);
		Assert.assertEquals(0, bp.countOfProcessorsToAdd);
	}

	/**
	 * Test BatchProcess with an operation that is not doing any
	 * transactions with the database.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws UnexpectedException
	 * @throws LogicException
	 */
	@Test
	public void testLifecycle()
			throws InitializationException, DataException, LogicException, UnexpectedException {
		Operation pre = Mockito.mock(Operation.class);
		Operation post = Mockito.mock(Operation.class);

		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		EntitiesQuery<String> q = new StringsQuery(1000);

		@SuppressWarnings("unchecked")
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName("TestBatch"); //$NON-NLS-1$
		parms.setQuery(q);
		parms.setInitialThreads(10);
		parms.setFormatter(formatter);
		parms.setOperationClass(PrintStringOperation.class);
		parms.setInputPropertyName("string"); //$NON-NLS-1$
		parms.setPreProcessing(pre);
		parms.setPostProcessing(post);
		BatchProcess<String> batch = new BatchProcess<String>(parms);
		batch.execute();

		verify(pre, times(1)).execute();
		verify(post, times(1)).execute();

		while(!batch.isFinished()) {
			ThreadUtils.sleep(1);
		}

		Assert.assertTrue(batch.isFinished());
		Assert.assertFalse(batch.isFinishedAbnormally());
		Assert.assertFalse(batch.isPaused());
		Assert.assertEquals(1000, batch.getProcessedCount());
	}

	/**
	 * Test BatchProcess with an operation that is not doing any
	 * transactions with the database.
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testRun_withExceptionThrown() throws LogicException, DataException{
		Operation pre = mock(Operation.class);
		doThrow(LogicException.class).when(pre).execute();
		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		EntitiesQuery<String> q = new StringsQuery(100);
		@SuppressWarnings("unchecked")
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName("TestBatch"); //$NON-NLS-1$
		parms.setQuery(q);
		parms.setInitialThreads(10);
		parms.setFormatter(formatter);
		parms.setOperationClass(PrintStringOperation.class);
		parms.setInputPropertyName("string"); //$NON-NLS-1$
		parms.setPreProcessing(pre);
		parms.setPostProcessing(null);
		BatchProcess<String> batch = new BatchProcess<String>(parms);

		batch.run();
		Assert.assertNotNull(batch.exceptionMessage);
	}

	/**
	 * Test BatchProcess with an operation that makes transactions
	 * with the database.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws UnexpectedException
	 * @throws LogicException
	 */
	@Test
	public void testLifecycle_withDb()
			throws InitializationException, DataException, LogicException, UnexpectedException {
		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		EntitiesQuery<String> q = new StringsQuery(2000);
		@SuppressWarnings("unchecked")
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName("TestBatch"); //$NON-NLS-1$
		parms.setQuery(q);
		parms.setInitialThreads(10);
		parms.setFormatter(formatter);
		parms.setOperationClass(OperationWithJdbcWorker.class);
		parms.setInputPropertyName("id"); //$NON-NLS-1$
		parms.setPreProcessing(null);
		parms.setPostProcessing(null);
		BatchProcess<String> batch = new BatchProcess<String>(parms);
		batch.execute();
	}

	/**
	 * Test for getCountOfSubProcesses().
	 */
	@Test
	public void testGetCountOfSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(bp.getInitialThreads(), bp.getCountOfSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfFinishedSubProcesses().
	 */
	@Test
	public void testGetCountOfFinishedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getCountOfFinishedSubProcesses());
		bp.queueProcessors.get(1).signalEndOfData();
		bp.queueProcessors.get(3).signalEndOfData();
		ThreadUtils.sleepMillis(1500);
		Assert.assertEquals(2, bp.getCountOfFinishedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfAbnormallyFinishedSubProcesses().
	 */
	@Test
	public void testGetCountOfAbnormallyFinishedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getCountOfAbnormallyFinishedSubProcesses());
		bp.queueProcessors.get(1).signalEndOfData();
		bp.queueProcessors.get(2).signalEndOfData();
		bp.queueProcessors.get(3).signalEndOfData();
		bp.queueProcessors.get(3).exceptionMessage = "Error"; //$NON-NLS-1$
		ThreadUtils.sleepMillis(2000);
		Assert.assertEquals(1, bp.getCountOfAbnormallyFinishedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfPausedSubProcesses().
	 */
	@Test
	public void testGetCountOfPausedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getCountOfPausedSubProcesses());
		bp.queueProcessors.get(1).pause();
		bp.queueProcessors.get(3).pause();
		Assert.assertEquals(2, bp.getCountOfPausedSubProcesses());
		bp.resume();
		Assert.assertEquals(0, bp.getCountOfPausedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getStartTime().
	 */
	@Test
	public void testGetStartTime()  {
		BatchProcess<String> bp = sample();
		bp.startTime = new Date();
		Assert.assertEquals(bp.startTime, bp.getStartTime());
	}

	/**
	 * Test for getEndTime().
	 */
	@Test
	public void testGetEndTime()  {
		BatchProcess<String> bp = sample();
		bp.endTime = new Date();
		Assert.assertEquals(bp.endTime, bp.getEndTime());
	}

	/**
	 * Test for getEndTime().
	 */
	@Test
	public void testGetExceptionMessage()  {
		BatchProcess<String> bp = sample();
		bp.exceptionMessage = "Error !!!"; //$NON-NLS-1$
		Assert.assertEquals(bp.exceptionMessage, bp.getExceptionMessage());
	}

	/**
	 * Test for getInitialThreads().
	 */
	@Test
	public void testGetInitialThreads()  {
		BatchProcess<String> bp = sample();
		Assert.assertEquals(bp.parameters.getInitialThreads(), bp.getInitialThreads());
	}

	/**
	 * Test for getName().
	 */
	@Test
	public void testGetName()  {
		BatchProcess<String> bp = sample();
		Assert.assertEquals(bp.parameters.getName(), bp.getName());
	}

	/**
	 * Test for getProcessedCount().
	 */
	@Test
	public void testGetProcessedCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getProcessedCount());
		bp.queueProcessors.get(1).processedCount = 3;
		bp.queueProcessors.get(2).processedCount = 4;
		Assert.assertEquals(7, bp.getProcessedCount());
		bp.forceQuit();
	}

	/**
	 * Test for getSuccessesCount().
	 */
	@Test
	public void testGetSuccessesCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getSuccessesCount());
		bp.queueProcessors.get(1).successesCount = 31;
		bp.queueProcessors.get(2).successesCount = 14;
		bp.queueProcessors.get(0).successesCount = 4;
		Assert.assertEquals(49, bp.getSuccessesCount());
		bp.forceQuit();
	}

	/**
	 * Test for getSuccessesCount().
	 */
	@Test
	public void testGetFailuresCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertEquals(0, bp.getFailuresCount());
		bp.queueProcessors.get(1).failuresCount = 11;
		bp.queueProcessors.get(2).failuresCount = 1;
		bp.queueProcessors.get(3).failuresCount = 3;
		Assert.assertEquals(15, bp.getFailuresCount());
		bp.forceQuit();
	}

	/**
	 * Test for pause().
	 */
	@Test
	public void testPause() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		bp.pause();
		for (LongProcess lp : bp.getSubProcesses()) {
			Assert.assertTrue(lp.isPaused());
		}
		Assert.assertTrue(bp.isPaused());
		bp.forceQuit();
	}

	/**
	 * Test for isPaused().
	 */
	@Test
	public void testIsPaused() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();

		for (QueueProcessor<String> qp : bp.queueProcessors) {
			qp.pause();
		}
		Assert.assertTrue(bp.isPaused());
		bp.forceQuit();
	}

	/**
	 * Test for isPaused().
	 */
	@Test
	public void testResume() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		for (QueueProcessor<String> qp : bp.queueProcessors) {
			qp.pause();
		}
		bp.resume();
		Assert.assertFalse(bp.isPaused());
		bp.forceQuit();
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinished() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		Assert.assertFalse(bp.isFinished());
		bp.forceQuit();
		ThreadUtils.sleepMillis(2000);
		Assert.assertTrue(bp.isFinished());
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinishedAbnormally_emptyQueueNoMessage() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		bp.forceQuit();
		bp.inputQueue.clear();
		ThreadUtils.sleepMillis(2000);
		Assert.assertTrue(bp.isFinished());
		Assert.assertFalse(bp.isFinishedAbnormally());
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinishedAbnormally_WithMessage() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		bp.forceQuit();
		bp.inputQueue.clear();
		bp.exceptionMessage="Message"; //$NON-NLS-1$
		ThreadUtils.sleepMillis(2000);
		Assert.assertTrue(bp.isFinished());
		Assert.assertTrue(bp.isFinishedAbnormally());
	}

	/**
	 * Test for isFinished().
	 */
	//@Test
	public void testIsFinishedAbnormally_NotEmptyQueue() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		bp.forceQuit();
		bp.inputQueue.add("Message"); //$NON-NLS-1$
		ThreadUtils.sleepMillis(2000);
		Assert.assertTrue(bp.isFinished());
		Assert.assertTrue(bp.isFinishedAbnormally());
	}



	/**
	 * Test for isFinished().
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testTidy() {
		BatchProcess<String> bp = sample();
		QueueProcessor<String> qp1 = Mockito.mock(QueueProcessor.class);
		QueueProcessor<String> qp2 = Mockito.mock(QueueProcessor.class);
		bp.queueProcessors.add(qp1);
		bp.queueProcessors.add(qp2);
		bp.tidy();
		Mockito.verify(qp1,Mockito.times(1)).tidy();
		Mockito.verify(qp2,Mockito.times(1)).tidy();
	}

}
