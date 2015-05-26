package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;

/**
 * test suite to stress quartz
 */
public class TestStressQuartz {

	static JobScheduler QUARTZ = new QuartzJobSchedulerImpl();

	/**
	 * stress test
	 *
	 * @throws DataException
	 */
	// @Test
	public void testSchedule() throws DataException {
		int batches = 5;
		int batchSize = 5;

		List<Thread> threads = new ArrayList<Thread>();

		for(int i = 0; i < batches; i++) {
			List<JobDescription> list = new ArrayList<JobDescription>();
			for(int j = 0; j < batchSize; j++) {
				JobDescription bean = Factory.create(JobDescription.class);
				bean.setOperationClass(SampleOperation.class);
				list.add(bean);
			}
			Thread t = new Thread(new SchedulerImpl(QUARTZ, list));
			threads.add(t);
			t.start();
		}

		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("==== all jobs scheduled =========");

		int times = batchSize*batches;

		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleOperation.class));
		Assert.assertEquals(times, SampleOperation.counter.get());

		System.out.println("all done");
	}

	static class SchedulerImpl implements Runnable {

		JobScheduler scheduler;
		List<JobDescription> jobs;

		/**
		 * Creates a new TestStressQuartz.Scheduler object.
		 *
		 */
		public SchedulerImpl(JobScheduler scheduler, List<JobDescription> jobs) {
			this.scheduler = scheduler;
			this.jobs = jobs;
		}

		public void run() {
			try {
				doRun();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		void doRun() throws Exception {
			int i = 0;
			for(List<JobDescription> batch : CollectionUtils.partition(jobs, 2)) {
				i += batch.size();
				scheduler.submitJobs(batch);
				ThreadUtils.sleep(NumberUtils.randomInt(2, 3));
			}
		}
	}

	public static class SampleOperation extends AbstractOperation {
		static AtomicInteger counter = new AtomicInteger(0);
		@Override
		public void execute() throws LogicException, DataException {
			counter.incrementAndGet();
			ThreadUtils.sleep(NumberUtils.randomInt(1, 2));
		}
	}
}
