package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

/**
 * generic operation to schedule a job described in the jobdescription.
 */
public class JobOperation extends AbstractOperation {

	/**
	 * job description to schedule.
	 */
	JobDescription jobDescription;

	/**
	 * job scheduler provider
	 */
	private JobSchedulerProvider jsp;

	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		jsp = getProvider().getResource(getManagerName(), JobSchedulerProvider.class);
	}

	@Override
	public void execute() throws LogicException, DataException {
		if (jobDescription != null) {
			jsp.scheduleJob(jobDescription);
		}
	}

	/**
	 * setter.
	 * 
	 * @param jobDescription
	 */
	public void setJobDescription(JobDescription jobDescription) {
		this.jobDescription = jobDescription;
	}
}
