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

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.TransactionManagerException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.namedstreams.NamedPrintStream;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link QueueProcessor}.
 */
public class TestQueueProcessor {
	
	/**
	 * Mock NamedPrintStream that writes to System.out. 
	 * 
	 * @return Returns a Mock NamedPrintStream that writes to System.out.
	 */	
	NamedPrintStream mockStream() {
		NamedPrintStream nps = Mockito.mock(NamedPrintStream.class);
		Mockito.when(nps.getStream()).thenReturn(System.out);
		return nps;
	}
	
	
	/**
	 * Sample for the tests.
	 * 
	 * @return Returns a sample.
	 */
	@SuppressWarnings({"nls" })
	QueueProcessor<String> sample() {
		String name = "TQ";
		Operation op = new PrintStringOperation();
		String inputPropertyName = "string"; //invalid property name.
		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		return new QueueProcessor<String> 
			(new LinkedList<String>(), name, name, op, inputPropertyName, formatter, null);
	}
	
	/**
	 * Tests the 6 args constructor.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test
	public void testConstructor_succeed() {
		Session<?, ?> session = Mockito.mock(Session.class);
		Session<?, ?> prev = Bo2Session.getSession();
		Bo2Session.setSession(session);
		
		String name = "N";
		Operation op = new PrintStringOperation();
		String inputPropertyName = "string";
		Formatter<String> formatter = Mockito.mock(Formatter.class);
		Queue<String> q = Mockito.mock(Queue.class);
		Modification<Object> mod = Mockito.mock(Modification.class);
		QueueProcessor<String> qp = new QueueProcessor<String>		
			(q, name, name, op, inputPropertyName, formatter, mod);
		Assert.assertEquals(q, qp.inputQueue);
		Assert.assertEquals(inputPropertyName, qp.inputPropertyName);
		Assert.assertEquals(op, qp.operation);
		Assert.assertEquals(formatter, qp.formatter);
		Assert.assertEquals(name, qp.name);				
		Assert.assertEquals(session, qp.session);
		Assert.assertEquals(mod, qp.operationParametersSetter);
		
		Bo2Session.setSession(prev);
	}
	

	/**
	 * Tests the constructor.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test(expected=RuntimeException.class)
	public void testConstructor_fail() {
		String name = "N";
		Operation op = new PrintStringOperation();
		String inputPropertyName = "notthis"; //invalid property name.
		Formatter<String> formatter = Mockito.mock(Formatter.class);
		new QueueProcessor<String> (new LinkedList<String>(), name, name, op, inputPropertyName, formatter, null);
	}
	
	
	/**
	 * test doContinue.
	 * No eod or quit.
	 */
	@Test
	public void testDoContinue_noEodOrQuit() {
		QueueProcessor<String> qp = sample();
		qp.eod = false;
		qp.quit = false;
		Assert.assertTrue(qp.doContinue());
	}
	
	/**
	 * test doContinue.
	 * EOD and no data in queue.
	 */
	@Test
	public void testDoContinue_endOfProcessing() {
		QueueProcessor<String> qp = sample();
		qp.eod = true;
		qp.inputQueue.clear();
		qp.quit = false;
		Assert.assertFalse(qp.doContinue());
	}
	
	/**
	 * test doContinue.
	 * EOD but still data exist in queue.
	 */
	@Test
	public void testDoContinue_eodStillProcessing() {
		QueueProcessor<String> qp = sample();
		qp.eod = true;
		qp.inputQueue.offer("S"); //$NON-NLS-1$
		qp.quit = false;
		Assert.assertTrue(qp.doContinue());
	}
	
	/**
	 * test doContinue.
	 * EOD but still data exist in queue.
	 */
	@Test
	public void testDoContinue_quit() {
		QueueProcessor<String> qp = sample();
		qp.eod = false;		
		qp.quit = true;
		Assert.assertFalse(qp.doContinue());
	}
	
	/**
	 * test safeToString.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test
	public void testSafeToString_withFailingFormatter() {		
		Formatter<String> formatter = Mockito.mock(Formatter.class);
		Mockito.when(formatter.format("S")).thenThrow(RuntimeException.class);
		String name = "N";
		Operation op = new PrintStringOperation();
		String inputPropertyName = "string"; //invalid property name.		
		QueueProcessor<String> qp = new QueueProcessor<String> 
			(new LinkedList<String>(), name, name, op, inputPropertyName, formatter, null);		
		String s = qp.safeToString("S");
		Assert.assertNotNull(s);
	}
	
	/**
	 * test safeToString.
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testSafeToString_withSucceedingFormatter() {		
		QueueProcessor<String> qp = sample();					
		String s = qp.safeToString("S");
		Assert.assertEquals("S", s);
	}
	
	/**
	 * test Process.
	 * 
	 * @throws TransactionManagerException 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testProcess_succeeding() throws TransactionManagerException {		
		TransactionManager mockTm = Mockito.mock(TransactionManager.class);
		QueueProcessor<String> qp = sample();
		qp.successesLog = mockStream();
		qp.failuresLog = mockStream();
		qp.tm=mockTm;
		Assert.assertEquals(0L, qp.processedCount);
		Assert.assertEquals(0L, qp.successesCount);
		Assert.assertEquals(0L, qp.failuresCount);
		String input = "input";
		qp.process(input);
		Assert.assertEquals(1L, qp.processedCount);
		Assert.assertEquals(1L, qp.successesCount);
		Assert.assertEquals(0L, qp.failuresCount);
	}
	
	
	/**
	 * test Process.
	 * @param exceptionClass 
	 *        Class of exception being thrown by the operation.
	 * 
	 * @throws TransactionManagerException 
	 * @throws DataException 
	 * @throws LogicException 
	 */	
	@SuppressWarnings({ "nls" })
	void testProcess_WithException(Class<? extends Exception> exceptionClass) 
	throws TransactionManagerException, LogicException, DataException {		
		QueueProcessor<String> qp = sample();
		PrintStringOperation mockPrint = Mockito.mock(PrintStringOperation.class);
		Mockito.doThrow(exceptionClass).when(mockPrint).execute();
		qp.operation = mockPrint;
		qp.successesLog = mockStream();
		qp.failuresLog = mockStream();
		qp.tm = Mockito.mock(TransactionManager.class);
		
		Assert.assertEquals(0L, qp.processedCount);
		Assert.assertEquals(0L, qp.successesCount);
		Assert.assertEquals(0L, qp.failuresCount);
		String input = "input";
		qp.process(input);
		Assert.assertEquals(1L, qp.processedCount);
		Assert.assertEquals(0L, qp.successesCount);
		Assert.assertEquals(1L, qp.failuresCount);
		
	}
	
	/**
	 * test Process when an Exception is thrown by the operation.
	 * 
	 * @throws TransactionManagerException 
	 * @throws DataException 
	 * @throws LogicException 
	 */	
	@Test
	public void testProcess_LogicException() 
	throws TransactionManagerException, LogicException, DataException {
		testProcess_WithException(LogicException.class);
	}
		
	/**
	 * test Process when an Exception is thrown by the operation.
	 * 
	 * @throws TransactionManagerException 
	 * @throws DataException 
	 * @throws LogicException 
	 */	
	@Test
	public void testProcess_DataException() 
	throws TransactionManagerException, LogicException, DataException {
		testProcess_WithException(DataException.class);
	}
	
	/**
	 * test Process when an Exception is thrown by the operation.
	 * 
	 * @throws TransactionManagerException 
	 * @throws DataException 
	 * @throws LogicException 
	 */	
	@Test
	public void testProcess_RuntimeException() 
	throws TransactionManagerException, LogicException, DataException {
		testProcess_WithException(RuntimeException.class);
	}
	
	/**
	 * test the main loop().
	 * 
	 * @throws TransactionManagerException 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testLoop_throwingException() 
	throws TransactionManagerException {
		TransactionManager mockTm = Mockito.mock(TransactionManager.class);
		Mockito.doThrow(CouldNotCommitException.class).doNothing().when(mockTm).commit();
		QueueProcessor<String> qp = sample();
		String input = "input";
		qp.inputQueue.offer(input);
		qp.initialize();
		qp.eod = true;
		qp.tm = mockTm; // So an exception will be thrown.
		qp.loop();
		Assert.assertFalse(qp.quit);
		Assert.assertTrue(qp.finished);
		Assert.assertNotNull(qp.exceptionMessage);
	}
	
	/**
	 * test Process when the queue processor is paused.
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testRun() {		
		final QueueProcessor<String> qp = sample();
		qp.successesLog = mockStream();
		qp.failuresLog = mockStream();
		String input = "input";
		qp.inputQueue.offer(input);
		qp.signalEndOfData();
		qp.run();
		Assert.assertFalse(qp.isFinishedAbnormally());		
		Assert.assertEquals(1, qp.processedCount);		
		Assert.assertEquals(1, qp.successesCount);
		Assert.assertEquals(0, qp.failuresCount);
	}
	
	/**
	 * test Process when the queue processor is paused.
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testRun_paused() {
		TransactionManager mockTm = Mockito.mock(TransactionManager.class);
		final QueueProcessor<String> qp = sample();
		qp.successesLog = mockStream();
		qp.failuresLog = mockStream();
		qp.tm=mockTm;		
		qp.inputQueue.offer("A");
		qp.inputQueue.offer("B");		
		qp.pause();
		
		Thread t = new Thread(qp);
		t.start();
		ThreadUtils.sleep(1);				
		Assert.assertEquals(0, qp.processedCount);
		Assert.assertEquals(0, qp.successesCount);
		
		qp.resume();
		
		qp.signalEndOfData();
		ThreadUtils.sleep(3);//Must be 2 or more because the queue repeats the loop in 1sec intervals when paused. 
		Assert.assertFalse(qp.paused);
		Assert.assertFalse(qp.isFinishedAbnormally());
		Assert.assertEquals(2, qp.processedCount);		
		Assert.assertEquals(2, qp.successesCount);
		Assert.assertEquals(0, qp.failuresCount);	
		Assert.assertTrue(qp.isFinished());
	}
	
	/**
	 * test Process when the queue processor is paused.
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testRun_withPauseAndNewOperation() {
		TransactionManager mockTm = Mockito.mock(TransactionManager.class);
		final QueueProcessor<String> qp = sample();
		qp.successesLog = mockStream();
		qp.failuresLog = mockStream();
		qp.tm=mockTm;		
		qp.inputQueue.offer("1");
		qp.inputQueue.offer("2");
		
		Thread t = new Thread(qp);
		t.start();
		ThreadUtils.sleep(1);
		Operation op0 = qp.operation;		
		Operation op1 = new PrintStringOperation();
		qp.setNewOperation(op1);
		ThreadUtils.sleep(1);
		qp.inputQueue.offer("3");
		qp.inputQueue.offer("4");
		qp.signalEndOfData();
		ThreadUtils.sleep(1);
		
		Assert.assertNotSame(op0, qp.operation);
		Assert.assertSame(op1, qp.operation);
		
		Assert.assertTrue(qp.isFinished());
		Assert.assertFalse(qp.isFinishedAbnormally());
		Assert.assertEquals(4, qp.processedCount);		
		Assert.assertEquals(4, qp.successesCount);
	}
	
	/**
	 * test logSuccess. 
	 */
	@Test
	@SuppressWarnings({ "nls" })
	public void testLogSuccess() {		
		QueueProcessor<String> qp = sample();
		qp.successesLog = mockStream();
		qp.logSuccess("Success");
		/*
		 * no assertion.
		 */
	}
	
	/**
	 * test logSuccess. 
	 */
	@Test
	@SuppressWarnings({ "nls" })
	public void testLogFailures() {		
		QueueProcessor<String> qp = sample();		
		qp.failuresLog = mockStream();
		qp.logFailure("Failure", new DataException("For test"));
		/*
		 * no assertion.
		 */
	}
	
	/**
	 * test signalEndOfData(). 
	 */
	@Test
	public void testSignalEndOfData() {		
		QueueProcessor<String> qp = sample();
		qp.signalEndOfData();
		Assert.assertTrue(qp.eod);
	}
	
	/**
	 * test forceQuit(). 
	 */
	@Test
	public void testForceQuit() {		
		QueueProcessor<String> qp = sample();
		qp.forceQuit();
		Assert.assertTrue(qp.quit);
	}
		
	/**
	 * test getProcessedCount(). 
	 */
	@Test
	public void testGetProcessedCount() {		
		QueueProcessor<String> qp = sample();
		long expected = 12;
		qp.processedCount = expected;
		Assert.assertEquals(expected, qp.getProcessedCount());
	}
	
	/**
	 * test getSuccessesCount(). 
	 */
	@Test
	public void testGetSuccessesCount() {		
		QueueProcessor<String> qp = sample();
		long expected = 4;
		qp.successesCount = expected;
		Assert.assertEquals(expected, qp.getSuccessesCount());
	}
	
	/**
	 * test getSuccessesCount(). 
	 */
	@Test
	public void testGetFailuresCount() {		
		QueueProcessor<String> qp = sample();
		long expected = 77;
		qp.failuresCount = expected;
		Assert.assertEquals(expected, qp.getFailuresCount());
	}
	
	/**
	 * test pause(). 
	 */
	@Test
	public void testPause() {		
		QueueProcessor<String> qp = sample();
		qp.paused = false;
		qp.pause();
		Assert.assertTrue(qp.paused);
	}
	
	/**
	 * test pause(). 
	 */
	@Test
	public void testResume() {		
		QueueProcessor<String> qp = sample();
		qp.paused = true;
		qp.resume();
		Assert.assertFalse(qp.paused);		
	}
	
	/**
	 * test pause(). 
	 */
	@Test
	public void testIsPaused() {		
		QueueProcessor<String> qp = sample();
		qp.paused = true;
		Assert.assertTrue(qp.isPaused());
		qp.paused = false;
		Assert.assertFalse(qp.isPaused());
	}
	
	/**
	 * test pause(). 
	 */
	@Test
	public void testHandle() {		
		QueueProcessor<String> qp = sample();
		Exception e = new Exception();
		qp.handle(e);
		Assert.assertNotNull(qp.exceptionMessage);
	}
	
	/**
	 * test isFinishedAbnormally(). 
	 */
	@Test
	public void testIsFinishedAbnormally_true() {		
		QueueProcessor<String> qp = sample();
		qp.exceptionMessage = "Exception"; //$NON-NLS-1$
		Assert.assertTrue(qp.isFinishedAbnormally());
	}	
	
	/**
	 * test isFinishedAbnormally(). 
	 */
	@Test
	public void testIsFinishedAbnormally_false() {		
		QueueProcessor<String> qp = sample();
		qp.exceptionMessage = null;
		Assert.assertFalse(qp.isFinishedAbnormally());
	}	

	/**
	 * test isFinishedAbnormally(). 
	 */
	@Test
	public void testIsFinished() {		
		QueueProcessor<String> qp = sample();
		qp.finished = true;		
		Assert.assertTrue(qp.isFinished());
	}
	
	/**
	 * test getStartTime(). 
	 */
	@Test
	public void testGetStartTime() {		
		QueueProcessor<String> qp = sample();
		qp.startTime = new Date();
		Assert.assertEquals(qp.startTime, qp.getStartTime());
	}	
	
	/**
	 * test getEndTime(). 
	 */
	@Test
	public void testGetEndTime() {		
		QueueProcessor<String> qp = sample();
		qp.endTime = new Date();
		Assert.assertEquals(qp.endTime, qp.getEndTime());
	}	
	
	/**
	 * test getExceptionMessage(). 
	 */
	@Test
	public void testGetExceptionMessage() {		
		QueueProcessor<String> qp = sample();		
		qp.exceptionMessage = "message"; //$NON-NLS-1$
		Assert.assertEquals(qp.exceptionMessage, qp.getExceptionMessage());
	}	
	
	/**
	 * test getName(). 
	 */
	@Test
	public void testGetName() {		
		QueueProcessor<String> qp = sample();
		Assert.assertEquals(qp.name, qp.getName());
	}
	
	/**
	 * test getName(). 
	 */
	@Test
	public void testSetNewOperation() {
		QueueProcessor<String> qp = sample();
		Operation op1 = new PrintStringOperation();
		qp.setNewOperation(op1);
		Assert.assertEquals(qp.newOperation, op1);
	}
	
	/**
	 * test getName(). 
	 */
	@Test
	public void testTidy() {
		QueueProcessor<String> qp = sample();
		Assert.assertNull(qp.newOperation);
		qp.tidy();		
		Assert.assertNotNull(qp.newOperation);
		Assert.assertEquals(qp.operation.getClass(), qp.newOperation.getClass());
	}
	
	/**
	 * test Process.
	 * 
	 * @throws TransactionManagerException 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testPollAndProcess_withTransactionManagerException() throws TransactionManagerException {		
		TransactionManager mockTm = Mockito.mock(TransactionManager.class);
		Mockito.doThrow(CouldNotCommitException.class).when(mockTm).commit();
		NamedPrintStream nps = Mockito.mock(NamedPrintStream.class);
		Mockito.when(nps.getStream()).thenReturn(System.out);
		
		String input = "input";
		QueueProcessor<String> qp = sample();
		qp = Mockito.spy(qp);
		qp.tm = mockTm;
		qp.failuresLog = nps;
		qp.stacktracesLog = nps;
		qp.successesLog = nps;
		
		qp.inputQueue.add(input);		
		qp.pollAndProcess();
		Assert.assertTrue(qp.inputQueue.contains(input));
		Mockito.verify(qp, Mockito.times(1)).tidy();
	}
}
