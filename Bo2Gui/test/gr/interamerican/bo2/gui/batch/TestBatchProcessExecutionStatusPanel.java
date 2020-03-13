package gr.interamerican.bo2.gui.batch;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.gui.batch.model.BatchProcessExecutionStatusPanelModel;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParm;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;

/**
 * Test for {@link BatchProcessExecutionStatusPanel}
 */
public class TestBatchProcessExecutionStatusPanel {

	/**
	 * Test method for
	 * {@link BatchProcessExecutionStatusPanel#BatchProcessExecutionStatusPanel(BatchProcessExecutionStatusPanelModel)}
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testConstructor() {
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName("sample"); //$NON-NLS-1$
		parms.setInitialThreads(5);
		parms.setOperationClass(PrintStringOperation.class);
		BatchProcess<String> batch = new BatchProcess<String>(parms);
		BatchProcessExecutionStatusPanelModel model = new BatchProcessExecutionStatusPanelModel(batch);
		BatchProcessExecutionStatusPanel panel = new BatchProcessExecutionStatusPanel(model);
		assertNotNull(panel);
		assertNotNull(panel.getModel());
	}

}
