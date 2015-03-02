package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Operation to redirect streams.
 */
public class StreamRedirectOperation extends AbstractOperation {

	/**
	 * process to monitor
	 */
	private Process process;
	/**
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}
	/**
	 * @param process the process to set
	 */
	public void setProcess(Process process) {
		this.process = process;
	}
	@Override
	public void execute() throws LogicException, DataException {
		try {
			InputStreamReader isr = new InputStreamReader(process.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
