package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.utils.SelectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


/**
 * scheduler registry.
 */
public class QuartzSchedulerRegistry {

	/**
	 * singleton factory for scheduler.
	 */
	private static final SchedulerFactory SCHEDULER_FACTORY = new StdSchedulerFactory();
	/**
	 * 
	 */
	private static Scheduler oneScheduler;

	/**
	 * job descriptions that have been scheduled.
	 */
	private static List<JobDescription> scheduledJobDescriptions = new ArrayList<JobDescription>();

	/**
	 * @return the one scheduler.
	 */
	public static Scheduler getScheduler() {
		if (oneScheduler == null) {
			synchronized (QuartzSchedulerRegistry.class) {
				if (oneScheduler == null) {
					try {
						Scheduler s = SCHEDULER_FACTORY.getScheduler();
						if (s == null) {
							throw new RuntimeException("SCHEDULER IS NULL"); //$NON-NLS-1$
						}
						s.start();
						oneScheduler = s;
					} catch (SchedulerException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return oneScheduler;
	}

	/**
	 * appends a collection of {@link JobDescription}s to the list.
	 * 
	 * @param descriptions
	 */
	public static synchronized void appendJobDescription(Collection<JobDescription> descriptions) {
		scheduledJobDescriptions.addAll(descriptions);
	}

	/**
	 * @param status
	 * @return the list of job descriptions that much the given status.
	 */
	public static synchronized List<JobDescription> getJobDescriptionBasedOnStatus(JobStatus status) {
		if (status==null){
			return null;
		}
		List<JobDescription> descriptions = SelectionUtils.selectByProperty("executionStatus", status, //$NON-NLS-1$
				scheduledJobDescriptions, JobDescription.class);
		return descriptions;
	}
}
