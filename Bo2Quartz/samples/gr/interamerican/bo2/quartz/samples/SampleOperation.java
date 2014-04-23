package gr.interamerican.bo2.quartz.samples;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;


/**
 * sample operation for the tests
 */
public class SampleOperation extends AbstractOperation implements Operation {

	/**
	 * time to wait
	 */
	int waitTime = 10;

	/**
	 * @param s
	 *            setter for wait time.
	 */
	public void setWaitTime(int s) {
		waitTime = s;
	}

	/**
	 * @return wait time.
	 */
	public int getWaitTime() {
		return waitTime;
	}
	@Override
	public void execute() throws LogicException, DataException {
		ThreadUtils.sleep(waitTime);
		System.out.println("Job Done"); //$NON-NLS-1$
	}
}
