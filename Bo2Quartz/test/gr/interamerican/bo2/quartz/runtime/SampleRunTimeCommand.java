package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.SystemUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

/**
 * The Class SampleRunTimeCommand.
 */
public class SampleRunTimeCommand extends AbstractBo2RuntimeCmd {

	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		ThreadUtils.sleep(2);
		System.out.println(this.getClass().getName());
		System.out.println(SystemUtils.maxMemory());
		System.out.println(SystemUtils.totalMemory());
		System.out.println(SystemUtils.maxMemory());
	}
}
