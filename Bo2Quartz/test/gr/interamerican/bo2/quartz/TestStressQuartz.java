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

/**
 * test suite to stress quartz
 */
@SuppressWarnings("all")
public class TestStressQuartz {

	static final int PAYLOAD_SIZE = 1 * 1024 * 1024;
	static final int BATCHES = 200;
	static final int BATCHES_SIZE = 20;
	
	static JobScheduler QUARTZ = new QuartzJobSchedulerImpl();

	/**
	 * stress test
	 * 
	 * @throws DataException
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
			Thread t = new Thread(new SchedulerImpl(QUARTZ, list));
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

		int times = batchSize * batches;

		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleOperation.class));
		QuartzUtils.waitGroupToComplete(QuartzUtils.getJobGroupName(SampleScheduledOperation.class));
		Assert.assertEquals(times, SampleOperation.counter.get());
		Assert.assertEquals(times, SampleScheduledOperation.counter.get());

		System.out.println("all done");
		System.out.println(SystemUtils.permgenSize());
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
			for (List<JobDescription> batch : CollectionUtils.partition(jobs, 2)) {
				i += batch.size();
				scheduler.submitJobs(batch);
				ThreadUtils.sleep(NumberUtils.randomInt(2, 3));
			}
		}
	}

	public static class SampleScheduledOperation extends AbstractOperation {

		static AtomicInteger counter = new AtomicInteger(0);

		byte[] payload;

		@Override
		public void execute() throws LogicException, DataException {
			ThreadUtils.sleep(NumberUtils.randomInt(1, 3));
			counter.incrementAndGet();
		}
		
		public void setPayload(byte[] payload) {
			this.payload = payload;
		}

	}

	@ManagerName("LOCALDB")
	public static class SampleOperation extends AbstractOperation {
		static AtomicInteger counter = new AtomicInteger(0);

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
		}

	}
	
	
	public static void main(String[] args) throws DataException {
		new TestStressQuartz().testSchedule();
	}
	
}
