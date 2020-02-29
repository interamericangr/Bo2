/**
 *
 */
package gr.interamerican.bo2.impl.open.runtime.monitor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessConstants;

import java.security.Permission;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * The Class TestLongProcessExitValueMonitor.
 */
public class TestLongProcessExitValueMonitor {

	/**
	 * Test method for {@link gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessExitValueMonitor#execute(gr.interamerican.bo2.arch.batch.LongProcess)}.
	 */
	@Test
	public void testExecute() {
		LongProcessExitValueMonitor monitor = new LongProcessExitValueMonitor();
		LongProcess lp = mock(LongProcess.class);
		Mockito.when(lp.isFinished()).thenReturn(false);
		Mockito.when(lp.isFinishedAbnormally()).thenReturn(false);
		monitor.execute(lp);
		verify(lp, times(1)).isFinished();
		verify(lp, times(1)).isFinishedAbnormally();
		Mockito.when(lp.isFinished()).thenReturn(true);
		Mockito.when(lp.isFinishedAbnormally()).thenReturn(false);
		Mockito.when(lp.getFailuresCount()).thenReturn(0L);
		try {
			monitor.execute(lp);
		} catch (ExitException e) {
			Assert.assertEquals("Exit status", 0, e.status); //$NON-NLS-1$
		}
		Mockito.when(lp.isFinished()).thenReturn(true);
		Mockito.when(lp.isFinishedAbnormally()).thenReturn(false);
		Mockito.when(lp.getFailuresCount()).thenReturn(2L);
		try {
			monitor.execute(lp);
		} catch (ExitException e) {
			Assert.assertEquals(
					"Exit status", BatchProcessConstants.EXIT_VALUE_WITH_THREAD_FAILURES, e.status); //$NON-NLS-1$
		}
		Mockito.when(lp.isFinished()).thenReturn(false);
		Mockito.when(lp.isFinishedAbnormally()).thenReturn(true);
		try {
			monitor.execute(lp);
		} catch (ExitException e) {
			Assert.assertEquals("Exit status", -1, e.status); //$NON-NLS-1$
		}
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessExitValueMonitor#beModified(java.util.Properties)}.
	 */
	@Test
	public void testBeModified() {
		LongProcessExitValueMonitor monitor = new LongProcessExitValueMonitor();
		Properties p = new Properties();
		long interval = 1000L;
		p.setProperty(MonitoringConstants.EXIT_INTERVAL, Long.toString(interval));
		monitor.beModified(p);
		Assert.assertEquals(interval, monitor.getPeriodInterval());
	}

	/**
	 * The Class ExitException.
	 */
	protected static class ExitException extends SecurityException {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The status. */
		public final int status;

		/**
		 * Instantiates a new exit exception.
		 *
		 * @param status the status
		 */
		public ExitException(int status) {
			super("There is no escape!"); //$NON-NLS-1$
			this.status = status;
		}
	}

	/**
	 * The Class NoExitSecurityManager.
	 */
	private static class NoExitSecurityManager extends SecurityManager {

		@Override
		public void checkPermission(Permission perm) {
			// allow anything.
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
			// allow anything.
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}
	}

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		System.setSecurityManager(new NoExitSecurityManager());
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {
		System.setSecurityManager(null); // or save and restore original
	}
}
