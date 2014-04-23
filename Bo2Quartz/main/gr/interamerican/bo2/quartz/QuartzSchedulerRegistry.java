package gr.interamerican.bo2.quartz;

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

}
