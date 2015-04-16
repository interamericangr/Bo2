package gr.interamerican.bo2.quartz.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

/**
 *
 */
public class SampleRunTimeCommand extends AbstractBo2RuntimeCmd {

	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		ThreadUtils.sleep(1);
		System.out.println(this.getClass().getName());
	}
}