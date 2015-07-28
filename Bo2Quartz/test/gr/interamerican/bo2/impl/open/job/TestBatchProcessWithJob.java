package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParm;
import gr.interamerican.bo2.quartz.samples.OperationWithScheduledJob;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.StringsQuery;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Integration test
 */
public class TestBatchProcessWithJob {

	/**
	 * @throws InitializationException
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	@Test
	public void integrationTest() throws InitializationException, DataException, LogicException, UnexpectedException {
		int times = 2000;

		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		EntitiesQuery<String> q = new StringsQuery(times);
		@SuppressWarnings("unchecked")
		BatchProcessParm<String> parms = Factory.create(BatchProcessParm.class);
		parms.setName("TestBatch"); //$NON-NLS-1$
		parms.setQuery(q);
		parms.setInitialThreads(10);
		parms.setFormatter(formatter);
		parms.setOperationClass(OperationWithScheduledJob.class);
		parms.setInputPropertyName("id"); //$NON-NLS-1$
		parms.setPreProcessing(null);
		parms.setPostProcessing(null);
		parms.setBatchProcessInputAsText("textual representation of input"); //$NON-NLS-1$
		BatchProcess<String> batch = new BatchProcess<String>(parms);
		batch.execute();

		QuartzUtils.waitGroupToComplete(null);
		Assert.assertEquals(1999, OperationWithScheduledJob.ScheduledOperation.ctr.get());
	}

}
