/**
 *
 */
package gr.interamerican.bo2.gui.batch;

import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.MultithreadLongProcessExecutionStatus;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessUtility;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.runnables.Monitor;


/**
 * The Class TestBatchProcessNoInitialPanelFrame.
 */
public class TestBatchProcessNoInitialPanelFrame {

	/**
	 * Test method for {@link gr.interamerican.bo2.gui.batch.BatchProcessNoInitialPanelFrame#BatchProcessNoInitialPanelFrame(gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess, gr.interamerican.bo2.utils.runnables.Monitor)}.
	 */
	@Test
	public void testBatchProcessNoInitialPanelFrame() {
		BatchProcess<?> batch = mock(BatchProcess.class);
		Mockito.doReturn(MultithreadLongProcessExecutionStatus.START).when(batch).getExecutionStatus();
		BatchProcessNoInitialPanelFrame batchProcessNoInitialPanelFrame = new BatchProcessNoInitialPanelFrame(batch,
				Mockito.mock(Monitor.class));
		Assert.assertNotNull(batchProcessNoInitialPanelFrame);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		BatchProcess<?> batch;
		String path = "/gr/interamerican/rsrc/batchprocess/PrintStrings.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(path);
		BatchProcessUtility controller = new BatchProcessUtility(Bo2Session.getSession());
		batch = controller.createBatchProcess(properties);
		controller.startBatchProcess(batch);
		Monitor<LongProcess> monitor = controller.createMonitor(batch, properties);
		BatchProcessNoInitialPanelFrame batchProcessNoInitialPanelFrame = new BatchProcessNoInitialPanelFrame(
				batch, monitor);
		batchProcessNoInitialPanelFrame.setVisible(true);
		controller.startMonitor(monitor);
	}
}
