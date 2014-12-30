package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.LongProcessLauncher;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.runnables.Monitor;

import java.util.Properties;

/**
 * Console launcher for a batch process.
 */
public class ConsoleLauncher implements LongProcessLauncher {

	@Override
	public void launch(Properties properties) {
		BatchProcessUtility controller = new BatchProcessUtility(Bo2Session.getSession());
		BatchProcess<?> batch = controller.createBatchProcess(properties);
		controller.startBatchProcess(batch);
		Monitor<LongProcess> monitor = controller.createMonitor(batch, properties);
		controller.startMonitor(monitor);
	}

}
