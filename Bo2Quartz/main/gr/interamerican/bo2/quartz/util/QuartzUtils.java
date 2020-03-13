package gr.interamerican.bo2.quartz.util;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.quartz.QuartzSchedulerRegistry;
import gr.interamerican.bo2.quartz.QuartzjobDescription;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * utilities for quartz.
 */
public class QuartzUtils {

	/** LOG. */
	public static final Logger LOGGER = LoggerFactory.getLogger(QuartzUtils.class);

	/**
	 * Delimiter for the name elements.
	 */
	private static final String NAME_DELIMITER = StringConstants.PIPE;
	
	/**
	 * Gets the job name.
	 *
	 * @param bean the bean
	 * @return the unique name composed by the properties of quartz description bean
	 */
	private static String getJobName(JobDescription bean) {
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
	 * Generate random quartz job name.
	 *
	 * @param bean the bean
	 * @return a random name that also contains information from the {@link JobDescription}
	 */
	public static String generateRandomQuartzJobName(JobDescription bean) {
		String name = getJobName(bean);
		name += NAME_DELIMITER + Math.random();
		return name;
	}
	
	/**
	 * Gets the job group name.
	 *
	 * @param bean the bean
	 * @return the name of the group that the given job should be.
	 */
	public static String getJobGroupName(JobDescription bean) {
		return getJobGroupName(bean.getOperationClass());
	}

	/**
	 * Gets the job group name.
	 *
	 * @param c the c
	 * @return the name of the group that the given job should be.
	 */
	public static String getJobGroupName(Class<? extends Operation> c) {
		return c.getName();
	}

	/**
	 * Gets the scheduled job keys.
	 *
	 * @param groupName            name of the group to search, If null searches all groups.
	 * @return the set of jobKeys
	 * @throws DataException the data exception
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
	 * Gets the scheduled job names.
	 *
	 * @param groupName            name of the group to search, If null searches all groups.
	 * @return the set of Pairs each containing a group name as left and a job name as right.
	 * @throws DataException the data exception
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
	 * Gets the number of scheduled jobs.
	 *
	 * @param groupName            name of the group to search, If null searches all groups.
	 * @return the number of scheduled jobs. Uses the above.
	 * @throws DataException the data exception
	 */
	public static int getNumberOfScheduledJobs(String groupName) throws DataException {
		return getScheduledJobKeys(groupName).size();
	}

	/**
	 * Gets the maximum number of threads.
	 *
	 * @return maximum number of threads name of the group to search, If null searches all groups.
	 * @throws DataException the data exception
	 */
	public static int getMaximumNumberOfThreads() throws DataException {
		try {
			return QuartzSchedulerRegistry.getScheduler().getMetaData().getThreadPoolSize();
		} catch (SchedulerException e) {
			throw new DataException(e);
		}
	}

	/**
	 * Checks if is job scheduled.
	 *
	 * @param groupName            name of the group to search, If null searches all groups.
	 * @param jobName the job name
	 * @return true if the given job name exists in the scheduler. This means that the job is scheduled AND has not yet finished.
	 * @throws DataException the data exception
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
	 * @param groupName            name of the group that the job is registered. if null searches all groups.
	 * @param jobName the job name
	 * @throws DataException the data exception
	 */
	public static void waitJobToComplete(String groupName, String jobName) throws DataException {
		while (isJobScheduled(groupName, jobName)) {
			ThreadUtils.sleep(1);
		}
	}

	/**
	 * pauses the main thread until the given job has no job scheduled.
	 *
	 * @param groupName            name of the group. if null waits all jobs to finish.
	 * @throws DataException the data exception
	 */
	public static void waitGroupToComplete(String groupName) throws DataException {
		while (getNumberOfScheduledJobs(groupName) > 0) {
			ThreadUtils.sleep(1);
		}
	}

	/**
	 * pauses the main thread until the given job has been completed.
	 *
	 * @param bean            the job to wait
	 * @throws DataException the data exception
	 */
	public static void waitJobToComplete(JobDescription bean) throws DataException {
		waitJobToComplete(getJobGroupName(bean), bean.getJobName());
	}

	/**
	 * Gets the param from job description bean.
	 *
	 * @param bean the bean
	 * @param param the param
	 * @return the param from the bean.
	 */
	public static Object getParamFromJobDescriptionBean(JobDescription bean, String param) {
		Map<String, Object> map = bean.getParameters();
		Object obj = map.get(param);
		return obj;
	}

	/**
	 * Gets the string param from quartz description bean.
	 *
	 * @param bean the bean
	 * @param param the param
	 * @return the param from the bean.
	 */
	public static String getStringParamFromQuartzDescriptionBean(QuartzjobDescription bean,
			String param) {
		String digest = bean.getJobDescriptionDigest();
		String[] tokens = TokenUtils.split(digest, NAME_DELIMITER);
		int i = 0;
		for (i = 0; i < tokens.length; i++) {
			if (tokens[i].equals(param)) {
				break;
			}
		}
		if (i >= (tokens.length - 1)) {
			return null;
		}
		return tokens[i + 1];
	}

	/**
	 * Gets the digest from job description.
	 *
	 * @param bean the bean
	 * @return the digested version of the {@link JobDescription}.
	 */
	public static String getDigestFromJobDescription(JobDescription bean) {
		String digest = StringConstants.EMPTY;
		digest += getJobName(bean);
		digest += NAME_DELIMITER + "Synchronous:" + bean.isSynchronous(); //$NON-NLS-1$
		digest += NAME_DELIMITER + "Transactional:" + !bean.isNonTransactional(); //$NON-NLS-1$
		return digest;
	}
}
