package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobScheduler;
import gr.interamerican.bo2.impl.open.job.JobSchedulerProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.quartz.util.QuartzUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.SystemUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.quartz.SchedulerException;

/**
 * test suite to stress quartz.
 */
@SuppressWarnings("all")
public class TestStressQuartz {

	/** The Constant PAYLOAD_SIZE. */
	static final int PAYLOAD_SIZE = 1 * 1024 * 1024;
	
	/** The Constant BATCHES. */
	static final int BATCHES = 20;
	
	/** The Constant BATCHES_SIZE. */
	static final int BATCHES_SIZE = 20;
	
	/** The quartz. */
	static JobScheduler QUARTZ = new QuartzJobSchedulerImpl();
	
	/** The all jobs. */
	static List<JobDescription> ALL_JOBS = new ArrayList<JobDescription>();

	/**
	 * stress test.
	 *
	 * @throws DataException the data exception
	 */
	public void testSchedule() throws DataException {
		System.out.println(SystemUtils.permgenSize());

		int batches = BATCHES;
		int batchSize = BATCHES_SIZE;

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < batches; i++) {
			List<JobDescription> list = new ArrayList<JobDescription>();
			for (int j = 0; j < batchSize; j++) {
				JobDescription bean = Factory.create(JobDescription.class);
				bean.setOperationClass(SampleOperation.class);
				list.add(bean);
			}
			Thread t = new Thread(new Dispatcher(QUARTZ, list));
			threads.add(t);
			t.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("========= all jobs scheduled =========");
		
		Assert.assertTrue(checkJobsScheduleStatus());
		
		int times = batchSize * batches;

		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleOperation.class));
		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleScheduledOperation.class));
		Assert.assertEquals(times, SampleOperation.counter.get());
		Assert.assertEquals(times, SampleScheduledOperation.counter.get());

		Assert.assertFalse(checkJobsScheduleStatus());
		System.out.println("all done");
		System.out.println(SystemUtils.permgenSize());
		
		try {
			QuartzSchedulerRegistry.shutdown();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Check jobs schedule status.
	 *
	 * @return true, if successful
	 * @throws DataException the data exception
	 */
	boolean checkJobsScheduleStatus() throws DataException {
		boolean atLeastOneJobAtScheduledStatus = false;
		for(JobDescription jobDescription : ALL_JOBS) {
			if(QuartzUtils.isJobScheduled(QuartzUtils.getJobGroupName(jobDescription), jobDescription.getJobName())) {
				atLeastOneJobAtScheduledStatus = true;
			}
		}
		return atLeastOneJobAtScheduledStatus;
	}

	/**
	 * The Class Dispatcher.
	 */
	static class Dispatcher implements Runnable {

		/** The scheduler. */
		JobScheduler scheduler;
		
		/** The jobs. */
		List<JobDescription> jobs;

		/**
		 * Creates a new TestStressQuartz.Scheduler object.
		 *
		 * @param scheduler the scheduler
		 * @param jobs the jobs
		 */
		public Dispatcher(JobScheduler scheduler, List<JobDescription> jobs) {
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

		/**
		 * Do run.
		 *
		 * @throws Exception the exception
		 */
		void doRun() throws Exception {
			int i = 0;
			for (List<JobDescription> batch : CollectionUtils.partition(jobs, 2)) {
				i += batch.size();
				scheduler.submitJobs(batch);
				ThreadUtils.sleep(NumberUtils.randomInt(2, 3));
			}
		}
	}

	/**
	 * The Class SampleScheduledOperation.
	 */
	public static class SampleScheduledOperation extends AbstractOperation {

		/** The counter. */
		static AtomicInteger counter = new AtomicInteger(0);

		/** The payload. */
		byte[] payload;

		@Override
		public void execute() throws LogicException, DataException {
			ThreadUtils.sleep(NumberUtils.randomInt(1, 3));
			counter.incrementAndGet();
		}
		
		/**
		 * Sets the payload.
		 *
		 * @param payload the new payload
		 */
		public void setPayload(byte[] payload) {
			this.payload = payload;
		}

	}

	/**
	 * The Class SampleOperation.
	 */
	@ManagerName("LOCALDB")
	public static class SampleOperation extends AbstractOperation {
		
		/** The counter. */
		static AtomicInteger counter = new AtomicInteger(0);

		/** The jsp. */
		JobSchedulerProvider jsp;

		@Override
		public void init(Provider parent) throws InitializationException {
			super.init(parent);
			jsp = getResource(JobSchedulerProvider.class);
		}

		@Override
		public void execute() throws LogicException, DataException {
			counter.incrementAndGet();
			ThreadUtils.sleep(NumberUtils.randomInt(1, 2));
			JobDescription job = Factory.create(JobDescription.class);
			job.setOperationClass(SampleScheduledOperation.class);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("payload", new byte[PAYLOAD_SIZE]);
			job.setParameters(parameters);
			jsp.scheduleJob(job);
			ALL_JOBS.add(job);
		}

	}
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws DataException the data exception
	 */
	public static void main(String[] args) throws DataException {
		System.setProperty("org.quartz.properties", "org/quartz/bo2/quartz.properties");
		new TestStressQuartz().testSchedule();
	}
	
}
