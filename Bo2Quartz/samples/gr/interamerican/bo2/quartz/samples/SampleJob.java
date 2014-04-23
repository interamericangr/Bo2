package gr.interamerican.bo2.quartz.samples;

import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * sample job for the tests.
 */
public class SampleJob implements Job {

	public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
		ThreadUtils.sleep(10);
		System.out.println("TestJob run successfully..."); //$NON-NLS-1$
	}
}
