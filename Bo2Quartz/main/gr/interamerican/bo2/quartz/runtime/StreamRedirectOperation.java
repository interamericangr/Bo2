package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Operation to redirect streams.
 */
public class StreamRedirectOperation extends AbstractOperation {

	/** process to monitor. */
	private Process process;
	/**
	 * stream to output process input. If null will output to {@link System#out}
	 */
	private PrintStream outputStream = null;
	
	/**
	 * Gets the process.
	 *
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}
	
	/**
	 * Sets the process.
	 *
	 * @param process the process to set
	 */
	public void setProcess(Process process) {
		this.process = process;
	}
	
	/**
	 * Gets the output stream.
	 *
	 * @return the outputStream
	 */
	public PrintStream getOutputStream() {
		return outputStream;
	}
	
	/**
	 * Sets the output stream.
	 *
	 * @param outputStream the outputStream to set
	 */
	public void setOutputStream(PrintStream outputStream) {
		this.outputStream = outputStream;
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		try {
			PrintStream out = outputStream;
			if (out == null) {
				out = System.out;
			}
			InputStreamReader isr = new InputStreamReader(process.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				out.println(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
