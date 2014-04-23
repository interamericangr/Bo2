package gr.interamerican.bo2.quartz;

import static org.quartz.JobBuilder.newJob;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.quartz.schedule.GenericQuartzJob;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;


/**
 * utilities for quartz
 */
public class QuartzUtils {

	/**
	 * Delimiter for the name elements.
	 */
	private static final String NAME_DELIMITER = StringConstants.PIPE;
	/**
	 * @param bean
	 * @return the unique name composed by the properties of quartz description bean
	 */
	public static String getJobName(QuartzJobDescritpionBean bean) {
		String name = StringConstants.EMPTY;
		name += bean.getOperationClass().getName();
		Map<String, Object> map = bean.getParameters();
		if (map != null) {
			List<String> sortedKeys = new ArrayList<String>(map.keySet());
			Collections.sort(sortedKeys);
			for (String key : sortedKeys) {
				name += NAME_DELIMITER + key + NAME_DELIMITER + map.get(key);
			}
		}
		return name;
	}

	/**
	 * @param bean
	 * @return the name of the group that the given job should be.
	 */
	public static String getJobGroupName(QuartzJobDescritpionBean bean) {
		return getJobGroupName(bean.getOperationClass());
	}

	/**
	 * @param c
	 * @return the name of the group that the given job should be.
	 */
	public static String getJobGroupName(Class<? extends Operation> c) {
		return c.getName();
	}

	/**
	 * @param groupName
	 *            name of the group to search, If null searches all groups.
	 * @return the set of jobKeys
	 * @throws DataException
	 */
	public static Set<JobKey> getScheduledJobKeys(String groupName) throws DataException {
		Scheduler scheduler = QuartzSchedulerRegistry.getScheduler();
		if (groupName != null) {
			try {
				return scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
			} catch (SchedulerException e) {
				throw new DataException(e);
			}
		}
		Set<JobKey> jobKeys = new HashSet<JobKey>();
		try {
			for (String gName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(gName))) {
					jobKeys.add(jobKey);
				}
			}
		} catch (SchedulerException e) {
			throw new DataException(e);
		}
		return jobKeys;
	}

	/**
	 * @param groupName
	 *            name of the group to search, If null searches all groups.
	 * @return the number of scheduled jobs. Uses the above.
	 * @throws DataException
	 */
	public static int getNumberOfScheduledJobs(String groupName) throws DataException {
		return getScheduledJobKeys(groupName).size();
	}

	/**
	 * @return maximum number of threads name of the group to search, If null searches all groups.
	 * @throws DataException
	 */
	public static int getMaximumNumberOfThreads() throws DataException {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("quartz.properties")); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DataException(e);
		} catch (NullPointerException e) {
			return -1;
		}
		String prio = prop.getProperty("org.quartz.threadPool.threadCount"); //$NON-NLS-1$
		return NumberUtils.string2Int(prio);
	}

	/**
	 * @param groupName
	 *            name of the group to search, If null searches all groups.
	 * @param jobName
	 * @return true if the given job name exists in the scheduler.
	 * @throws DataException
	 */
	public static boolean isJobScheduled(String groupName, String jobName) throws DataException {
		Set<JobKey> jobKeys = getScheduledJobKeys(groupName);
		for (JobKey key : jobKeys) {
			if (key.getName().equals(jobName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * method to submit {@link QuartzJobDescritpionBean} as {@link GenericQuartzJob}
	 * 
	 * @param bean
	 * @return the job name scheduled.
	 * @throws DataException
	 */
	public static String submitJob(QuartzJobDescritpionBean bean) throws DataException {
		Scheduler scheduler = QuartzSchedulerRegistry.getScheduler();
		bean.setExecutionStatus(JobStatus.SCHEDULED);
		JobDataMap map = new JobDataMap();
		map.put(QuartzConstants.BEAN_PROP, bean);
		map.put(QuartzConstants.SESSION_PROP, Bo2Session.getSession());
		String jobName = QuartzUtils.getJobName(bean);
		String groupName = QuartzUtils.getJobGroupName(bean);
		JobDetail job = newJob().withIdentity(jobName, groupName).withDescription(groupName)
				.usingJobData(map).ofType(GenericQuartzJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startNow().build();
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			throw new DataException(e);
		}
		bean.setJobName(jobName);
		return jobName;
	}
}
