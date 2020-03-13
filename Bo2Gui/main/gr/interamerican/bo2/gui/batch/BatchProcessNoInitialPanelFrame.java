package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.gui.frames.BFrame;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.utils.adapters.vo.Refresh;
import gr.interamerican.bo2.utils.attributes.Refreshable;
import gr.interamerican.bo2.utils.runnables.ConcreteMonitoringOperation;
import gr.interamerican.bo2.utils.runnables.Monitor;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import javax.swing.WindowConstants;

/**
 * BatchProcessFrame without.
 */
public class BatchProcessNoInitialPanelFrame extends BFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The listener. */
	BatchProcessFrameCloseConfirmationWindowListener listener;

	/**
	 * default constructor.
	 *
	 * @param batch the batch
	 * @param monitor the monitor
	 */
	public BatchProcessNoInitialPanelFrame(BatchProcess<?> batch, Monitor<?> monitor) {
		MultiThreadedLongProcessPanel processPanel = new MultiThreadedLongProcessPanel(batch);
		setPanel(processPanel);
		setTitle(batch.getName());
		setPreferredSize(processPanel.getDefaultSize());
		setSize(processPanel.getDefaultSize());
		repaint();
		Refresh<Refreshable> refresh = new Refresh<Refreshable>();
		MonitoringOperation<Refreshable> mo = new ConcreteMonitoringOperation<Refreshable>(refresh);
		mo.setPeriodInterval(1000L);
		monitor.addOperation(mo, processPanel);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		listener = new BatchProcessFrameCloseConfirmationWindowListener();
		addWindowListener(listener);
		listener.setBatchProcess(batch);
	}
}
