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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamUtils;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.implopen.operations.OperationWithJdbcWorker;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.StringsQuery;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Unit tests for {@link BatchProcess}.
 */
public class TestBatchProcess {

	/**
	 * Clean up.
	 * 
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
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	@AfterClass
	public static void tearDownAfterClass() throws UnexpectedException, DataException, LogicException {
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
	 * Returns a {@link BatchProcess} that should appear as finished.
	 * 
	 * @return A 'finished' {@link BatchProcess}
	 */
	BatchProcess<String> getFinishedProcess() {
		BatchProcess<String> bp = sample();
		bp.queueProcessors = new ArrayList<QueueProcessor<String>>();
		QueueProcessor<String> queue = SampleQueueProcessor.sample();
		queue.finished = true;
		bp.queueProcessors.add(queue);
		bp.endTime = new Date();
		return bp;
	}

	/**
	 * Test BatchProcess with an operation that is not doing any transactions
	 * with the database.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked")
		BatchProcessParm<Object> parms = Factory.create(BatchProcessParm.class);
		BatchProcess<Object> batch = new BatchProcess<Object>(parms);
		assertEquals(parms, batch.parameters);
		assertNotNull(batch.queueProcessors);
		assertNotNull(batch.inputQueue);
		assertNull(batch.startTime);
		assertNull(batch.endTime);
	}

	/**
	 * Tests forceQuit().
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
			assertTrue(qp.isFinished());
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
		assertEquals(initialCount + 1, bp.queueProcessors.size());
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
		assertTrue(qp.isPaused());
		QueueProcessor<String> qp1 = bp.queueProcessors.get(initialCount + 1);
		assertFalse(qp1.isPaused());
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
		assertFalse(qp.isPaused());
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
		assertTrue(qp.isFinished());
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
		assertEquals(count, bp.countOfProcessorsToAdd);
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
		int expected = initialCount + countOfNewProcessors;
		assertEquals(expected, newCount);
		assertEquals(0, bp.countOfProcessorsToAdd);
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
		assertEquals(initialCount, newCount);
		assertEquals(0, bp.countOfProcessorsToAdd);
	}

	/**
	 * Test BatchProcess with an operation that is not doing any transactions
	 * with the database.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testLifecycle() throws InitializationException, DataException, LogicException, UnexpectedException {
		Operation pre = mock(Operation.class);
		Operation post = mock(Operation.class);

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

		while (!batch.isFinished()) {
			ThreadUtils.sleep(1);
		}

		assertTrue(batch.isFinished());
		assertFalse(batch.isFinishedAbnormally());
		assertFalse(batch.isPaused());
		assertEquals(1000, batch.getProcessedCount());
	}

	/**
	 * Test BatchProcess with an operation that is not doing any transactions
	 * with the database.
	 * 
	 *
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	@Test
	public void testRun_withExceptionThrown() throws LogicException, DataException {
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
		assertNotNull(batch.exceptionMessage);
	}

	/**
	 * Test BatchProcess with an operation that makes transactions with the
	 * database.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testLifecycle_withDb() throws InitializationException, DataException, LogicException,
			UnexpectedException {
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
		parms.setBatchProcessInputAsText("textual representation of input"); //$NON-NLS-1$
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
		assertEquals(bp.getInitialThreads(), bp.getCountOfSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfFinishedSubProcesses().
	 */
	@Test
	public void testGetCountOfFinishedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getCountOfFinishedSubProcesses());
		bp.queueProcessors.get(1).signalEndOfData();
		bp.queueProcessors.get(3).signalEndOfData();
		ThreadUtils.sleepMillis(1500);
		assertEquals(2, bp.getCountOfFinishedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfAbnormallyFinishedSubProcesses().
	 */
	@Test
	public void testGetCountOfAbnormallyFinishedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getCountOfAbnormallyFinishedSubProcesses());
		bp.queueProcessors.get(1).signalEndOfData();
		bp.queueProcessors.get(2).signalEndOfData();
		bp.queueProcessors.get(3).signalEndOfData();
		bp.queueProcessors.get(3).exceptionMessage = "Error"; //$NON-NLS-1$
		ThreadUtils.sleepMillis(2000);
		assertEquals(1, bp.getCountOfAbnormallyFinishedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getCountOfPausedSubProcesses().
	 */
	@Test
	public void testGetCountOfPausedSubProcesses() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getCountOfPausedSubProcesses());
		bp.queueProcessors.get(1).pause();
		bp.queueProcessors.get(3).pause();
		assertEquals(2, bp.getCountOfPausedSubProcesses());
		bp.resume();
		assertEquals(0, bp.getCountOfPausedSubProcesses());
		bp.forceQuit();
	}

	/**
	 * Test for getStartTime().
	 */
	@Test
	public void testGetStartTime() {
		BatchProcess<String> bp = sample();
		bp.startTime = new Date();
		assertEquals(bp.startTime, bp.getStartTime());
	}

	/**
	 * Test for getEndTime().
	 */
	@Test
	public void testGetEndTime() {
		BatchProcess<String> bp = sample();
		bp.endTime = new Date();
		assertEquals(bp.endTime, bp.getEndTime());
	}

	/**
	 * Test for getEndTime().
	 */
	@Test
	public void testGetExceptionMessage() {
		BatchProcess<String> bp = sample();
		bp.exceptionMessage = "Error !!!"; //$NON-NLS-1$
		assertEquals(bp.exceptionMessage, bp.getExceptionMessage());
	}

	/**
	 * Test for getInitialThreads().
	 */
	@Test
	public void testGetInitialThreads() {
		BatchProcess<String> bp = sample();
		assertEquals(bp.parameters.getInitialThreads(), bp.getInitialThreads());
	}

	/**
	 * Test for getName().
	 */
	@Test
	public void testGetName() {
		BatchProcess<String> bp = sample();
		assertEquals(bp.parameters.getName(), bp.getName());
	}

	/**
	 * Test for getProcessedCount().
	 */
	@Test
	public void testGetProcessedCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getProcessedCount());
		bp.queueProcessors.get(1).processedCount = 3;
		bp.queueProcessors.get(2).processedCount = 4;
		assertEquals(7, bp.getProcessedCount());
		bp.forceQuit();
	}

	/**
	 * Test for getSuccessesCount().
	 */
	@Test
	public void testGetSuccessesCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getSuccessesCount());
		bp.queueProcessors.get(1).successesCount = 31;
		bp.queueProcessors.get(2).successesCount = 14;
		bp.queueProcessors.get(0).successesCount = 4;
		assertEquals(49, bp.getSuccessesCount());
		bp.forceQuit();
	}

	/**
	 * Test for getSuccessesCount().
	 */
	@Test
	public void testGetFailuresCount() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertEquals(0, bp.getFailuresCount());
		bp.queueProcessors.get(1).failuresCount = 11;
		bp.queueProcessors.get(2).failuresCount = 1;
		bp.queueProcessors.get(3).failuresCount = 3;
		assertEquals(15, bp.getFailuresCount());
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
			assertTrue(lp.isPaused());
		}
		assertTrue(bp.isPaused());
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
		assertTrue(bp.isPaused());
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
		assertFalse(bp.isPaused());
		bp.forceQuit();
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinished() {
		BatchProcess<String> bp = sample();
		bp.createInitialQueueProcessors();
		assertFalse(bp.isFinished());
		bp.forceQuit();
		ThreadUtils.sleepMillis(2000);
		assertTrue(bp.isFinished());
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinishedAbnormally_emptyQueueNoMessage() {
		BatchProcess<String> bp = getFinishedProcess();
		assertTrue(bp.isFinished());
		assertFalse(bp.isFinishedAbnormally());
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinishedAbnormally_WithMessage() {
		BatchProcess<String> bp = getFinishedProcess();
		bp.exceptionMessage = "Message"; //$NON-NLS-1$
		assertTrue(bp.isFinished());
		assertTrue(bp.isFinishedAbnormally());
	}

	/**
	 * Test for isFinished().
	 */
	@Test
	public void testIsFinishedAbnormally_NotEmptyQueue() {
		BatchProcess<String> bp = getFinishedProcess();
		bp.inputQueue.add("Message"); //$NON-NLS-1$
		assertTrue(bp.isFinished());
		assertTrue(bp.isFinishedAbnormally());
	}

	/**
	 * Test for isFinished().
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testTidy() {
		BatchProcess<String> bp = sample();
		QueueProcessor<String> qp1 = mock(QueueProcessor.class);
		QueueProcessor<String> qp2 = mock(QueueProcessor.class);
		bp.queueProcessors.add(qp1);
		bp.queueProcessors.add(qp2);
		bp.tidy();
		verify(qp1, times(1)).tidy();
		verify(qp2, times(1)).tidy();
	}

	/**
	 * Tests the corner case scenario that a {@link QueueProcessor} is added on
	 * a {@link BatchProcess} while tidy is being run.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSynchronizationFail() throws Exception {
		final BatchProcess<String> bp = sample();
		bp.queueProcessors.add(delayingQueueProcessor());
		bp.queueProcessors.add(delayingQueueProcessor());
		Thread tidy = new Thread(new Runnable() {

			@Override
			public void run() {
				bp.tidy();
			}
		});
		ExceptionHandler eh = new ExceptionHandler();
		tidy.setUncaughtExceptionHandler(eh);
		tidy.start();
		ThreadUtils.sleep(2);
		bp.addQueueProcessor();
		tidy.join();
		assertNull(eh.getThrown());
	}

	/**
	 * A Mocked {@link QueueProcessor} that sleeps for 10 seconds during tidy.
	 * 
	 * @return Mocked {@link QueueProcessor}
	 */
	private QueueProcessor<String> delayingQueueProcessor() {
		@SuppressWarnings("unchecked")
		QueueProcessor<String> qp1 = mock(QueueProcessor.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				ThreadUtils.sleep(5);
				return null;
			}

		}).when(qp1).tidy();
		return qp1;
	}

	/**
	 * Test for {@link BatchProcess#registerNamedStreams()}.
	 * 
	 * @throws DataException
	 * @throws InitializationException
	 */
	@Test
	public void testRegisterNamedStreams() throws InitializationException, DataException {
		// Setup
		NamedStreamDefinition def = Factory.create(NamedStreamDefinition.class);
		def.setName("DummyDefinitionForBpTest"); //$NON-NLS-1$
		def.setType(StreamType.PRINTSTREAM);
		def.setResourceType(StreamResourceEnum.SYSTEM);
		def.setUri("sysout"); //$NON-NLS-1$
		List<NamedStreamDefinition> defs = new ArrayList<NamedStreamDefinition>();
		defs.add(def);
		BatchProcess<String> bp = sample();
		bp.parameters.setNamedStreamDefinitions(defs);
		Provider provider = Bo2.getProvider();
		// Test
		try {
			NamedStreamUtils.getDefaultNamedStream(provider, def.getName());
			fail("Above line was expected to fail."); //$NON-NLS-1$
		} catch (InitializationException expected) {
			// expected
		}
		assertNull(Bo2.getDefaultDeployment().getProperties().getProperty(def.getName()));
		assertNull(Bo2Session.getProvider());
		bp.registerNamedStreams();
		assertNotNull(NamedStreamUtils.getDefaultNamedStream(provider, def.getName()));
		assertNull(Bo2Session.getProvider());
		provider.close();
	}
}
/**
 * {@link UncaughtExceptionHandler}.
 */
class ExceptionHandler implements UncaughtExceptionHandler {
	/**
	 * Thrown Exception
	 */
	private Throwable thrown;

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
		thrown=e;
	}
	/**
	 * Gets the thrown.
	 *
	 * @return Returns the thrown
	 */
	public Throwable getThrown() {
		return thrown;
	}
}