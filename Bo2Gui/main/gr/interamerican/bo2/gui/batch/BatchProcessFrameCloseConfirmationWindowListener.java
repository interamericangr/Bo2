package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessConstants;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * listener for the frame. When the closing event is sent it will pop up a dialog box asking the
 * user to confirm his selection. If he confirms the system exits with the value given during
 * creation of the {@link BatchProcessFrameCloseConfirmationWindowListener}. If no is selected, the
 * default close
 * operation of the parent frame is executed.
 *
 */
public class BatchProcessFrameCloseConfirmationWindowListener extends WindowAdapter {

	/**
	 * {@link BatchProcess} that is used int the frame.
	 */
	private BatchProcess<?> batchProcess = null;

	/**
	 * Show confirm pane.
	 */
	protected void showConfirmPane() {
		int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", //$NON-NLS-1$
				"Close", JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
				JOptionPane.WARNING_MESSAGE);
		if (i == 0) {
			batchProcess.forceQuit();
			System.exit(BatchProcessConstants.EXIT_VALUE_CLOSED_BY_USER);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		Integer exitValue = BatchProcessConstants.getExitValue(batchProcess);
		if (exitValue != null) {
			System.exit(exitValue.intValue());
		} else {
			showConfirmPane();
		}
	}

	/**
	 * Sets the batch process.
	 *
	 * @param batchProcess the batchProcess to set
	 */
	public void setBatchProcess(BatchProcess<?> batchProcess) {
		this.batchProcess = batchProcess;
	}
}
