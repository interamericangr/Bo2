package gr.interamerican.bo2.gui.batch;

import java.util.Properties;

import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.runtime.concurrent.AbstractAutomatedLongProcessLauncher;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessUtility;

/**
 * same as {@link GuiLauncher} but does not show the initial frame for parameter selection (uses
 * default values).
 */
public class GuiNoInitialPanelLauncher extends AbstractAutomatedLongProcessLauncher {

	@Override
	public void launch(Properties properties) {

		BatchProcessUtility controller = new BatchProcessUtility(Bo2Session.getSession());
		creatAndStartBatchProcessThread(properties, controller);
		monitor = controller.createMonitor(batch, properties, extraMonitoringOperation);
		BatchProcessNoInitialPanelFrame batchProcessNoInitialPanelFrame = new BatchProcessNoInitialPanelFrame(batch, monitor);
		batchProcessNoInitialPanelFrame.setVisible(true);
		monitorThread = controller.startMonitor(monitor);
	}
}