package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.utils.SelectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;


/**
 * scheduler registry.
 */
public class QuartzSchedulerRegistry {

	/**
	 * Scheduler
	 */
	private static Scheduler oneScheduler;

	/**
	 * job descriptions that have been scheduled.
	 */
	private static List<QuartzjobDescription> scheduledJobDescriptions = new ArrayList<QuartzjobDescription>();

	/**
	 * @return the one scheduler.
	 */
	public static Scheduler getScheduler() {
		if (oneScheduler == null) {
			synchronized (QuartzSchedulerRegistry.class) {
				if (oneScheduler == null) {
					try {
						Scheduler s = new StdSchedulerFactory().getScheduler();
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
	 * shutdowns the scheduler.
	 * 
	 * @throws SchedulerException
	 */
	public static void shutdown() throws SchedulerException {
		if((oneScheduler!=null) && oneScheduler.isStarted()) {
			oneScheduler.shutdown();
		}
	}

	/**
	 * appends a collection of {@link JobDescription}s to the list.
	 * 
	 * @param descriptions
	 */
	public static synchronized void appendJobDescription(Collection<QuartzjobDescription> descriptions) {
		scheduledJobDescriptions.addAll(descriptions);
	}

	/**
	 * appends a collection of {@link JobDescription}s to the list.
	 * 
	 * @param descriptions
	 */
	public static synchronized void appendJobDescription(QuartzjobDescription descriptions) {
		scheduledJobDescriptions.add(descriptions);
	}

	/**
	 * @param status
	 * @return the list of job descriptions that much the given status. If status == null returns all descriptions
	 */
	public static synchronized List<QuartzjobDescription> getJobDescriptionBasedOnStatus(JobStatus status) {
		if (status==null){
			return scheduledJobDescriptions;
		}
		List<QuartzjobDescription> descriptions = SelectionUtils.selectByProperty("executionStatus", status, //$NON-NLS-1$
				scheduledJobDescriptions, QuartzjobDescription.class);
		return descriptions;
	}

	/**
	 * clears the list of scheduled job descriptions.
	 */
	public static synchronized void clearScheduledJobDescriptions() {
		scheduledJobDescriptions.clear();
	}
}
