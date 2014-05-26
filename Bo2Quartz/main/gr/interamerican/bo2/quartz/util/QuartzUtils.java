package gr.interamerican.bo2.quartz.util;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.quartz.QuartzSchedulerRegistry;
import gr.interamerican.bo2.quartz.QuartzjobDescription;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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
	public static String getJobName(JobDescription bean) {
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
	public static String getJobGroupName(JobDescription bean) {
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
	 * @return the set of Pairs each containing a group name as left and a job name as right.
	 * @throws DataException
	 */
	public static Set<Pair<String, String>> getScheduledJobNames(String groupName) throws DataException {
		Set<Pair<String, String>> names = new HashSet<Pair<String, String>>();
		Set<JobKey> s = getScheduledJobKeys(groupName);
		for (JobKey key : s) {
			names.add(new Pair<String, String>(key.getGroup(), key.getName()));
		}
		return names;
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
	 * pauses the main thread until the given job has been completed.
	 * 
	 * @param groupName
	 *            name of the group that the job is registered. if null searches all groups.
	 * @param jobName
	 * @throws DataException
	 */
	public static void waitJobToComplete(String groupName, String jobName) throws DataException {
		while (isJobScheduled(groupName, jobName)) {
			ThreadUtils.sleep(1);
		}
	}

	/**
	 * pauses the main thread until the given job has no job scheduled.
	 * 
	 * @param groupName
	 *            name of the group. if null waits all jobs to finish.
	 * @throws DataException
	 */
	public static void waitGroupToComplete(String groupName) throws DataException {
		while (getNumberOfScheduledJobs(groupName) > 0) {
			ThreadUtils.sleep(1);
		}
	}

	/**
	 * pauses the main thread until the given job has been completed.
	 * 
	 * @param bean
	 *            the job to wait
	 * 
	 * @throws DataException
	 */
	public static void waitJobToComplete(JobDescription bean) throws DataException {
		waitJobToComplete(getJobGroupName(bean), getJobName(bean));
	}

	/**
	 * @param bean
	 * @param param
	 * @return the param from the bean.
	 */
	public static Object getParamFromQuartzDescriptionBean(QuartzjobDescription bean, String param) {
		Map<String, Object> map = bean.getParameters();
		Object obj = map.get(param);
		return obj;
	}
}
