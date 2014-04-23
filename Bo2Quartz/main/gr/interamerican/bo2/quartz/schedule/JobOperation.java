package gr.interamerican.bo2.quartz.schedule;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.quartz.JobStatus;
import gr.interamerican.bo2.quartz.QuartzJobDescritpionBean;
import gr.interamerican.bo2.quartz.QuartzUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Operation to run
 */
public class JobOperation {

	/**
	 * bean to give to the {@link GenericQuartzJob}
	 */
	List<QuartzJobDescritpionBean> quartzJobDescritpionBeans = new ArrayList<QuartzJobDescritpionBean>();
	/**
	 * job names that have been scheduled with the given Operation.
	 */
	Set<String> jobNames = null;
	/**
	 * set to false if you want to skip waiting for the given jobs
	 */
	boolean wait2complete = false;
	/**
	 * throw an exception if either of the spawn processes fails.
	 */
	boolean failOnException = false;

	/**
	 * @return the quartzJobDescritpionBean
	 */
	public List<QuartzJobDescritpionBean> getQuartzJobDescritpionBeans() {
		return quartzJobDescritpionBeans;
	}

	/**
	 * job names that have been scheduled with the given Operation.
	 * 
	 * @return job names.
	 */
	public Set<String> getJobNames() {
		return jobNames;
	}

	/**
	 * set to false if you want the operation not to wait for the Quartz jobs to complete. default false.
	 * 
	 * @param b
	 */
	public void setWait2complete(boolean b) {
		wait2complete = b;
	}

	/**
	 * throw an exception if either of the spawn processes fails. Default false.
	 * 
	 * @param b
	 */
	public void setFailOnException(boolean b) {
		failOnException = b;
	}

	/**
	 * @param beans
	 * @return a set of the current job statuses
	 * @throws DataException
	 */
	Set<JobStatus> testjobStatus(List<QuartzJobDescritpionBean> beans) throws DataException {
		Set<JobStatus> status = new HashSet<JobStatus>();
		for (QuartzJobDescritpionBean bean : beans) {
			if (failOnException && JobStatus.ERROR.equals(bean.getExecutionStatus())) {
				throw new DataException("Job " + bean.getJobName() + " failed to execute " + bean.getParameters()); //$NON-NLS-1$//$NON-NLS-2$
			}
			status.add(bean.getExecutionStatus());
		}
		return status;
	}

	/**
	 * checks whether the given statuses contain running of scheduled jobs.
	 * 
	 * @param statusses
	 * @return whether the given statuses contain running of scheduled jobs.
	 */
	boolean runningScheduledJobs(Set<JobStatus> statusses) {
		if (statusses.contains(JobStatus.RUNNING)) {
			return true;
		}
		if (statusses.contains(JobStatus.SCHEDULED)) {
			return true;
		}
		return false;
	}

	/**
	 * execute the jobs described in {@link QuartzJobDescritpionBean}s
	 * 
	 * @throws LogicException
	 * @throws DataException
	 */
	public void execute() throws LogicException, DataException {
		if (CollectionUtils.isNullOrEmpty(quartzJobDescritpionBeans)) {
			return;
		}
		jobNames = new HashSet<String>();
		for (QuartzJobDescritpionBean bean : quartzJobDescritpionBeans) {
			jobNames.add(QuartzUtils.submitJob(bean));
		}
		while ((wait2complete) && (runningScheduledJobs(testjobStatus(quartzJobDescritpionBeans)))) {
			ThreadUtils.sleep(1);
		}
	}
}
