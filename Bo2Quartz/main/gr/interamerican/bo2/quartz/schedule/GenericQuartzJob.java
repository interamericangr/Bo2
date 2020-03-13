package gr.interamerican.bo2.quartz.schedule;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.jdbc.JdbcConnectionProvider;
import gr.interamerican.bo2.impl.open.jee.jta.J2eeJtaTransactionManager;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentInfoUtility;
import gr.interamerican.bo2.quartz.QuartzConstants;
import gr.interamerican.bo2.quartz.QuartzjobDescription;
import gr.interamerican.bo2.quartz.was.WorkManagerThreadExecutor;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Map;

import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * creates a Job to be executed through quartz scheduler.
 */
public class GenericQuartzJob implements Job {

	/**
	 * logger.
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(GenericQuartzJob.class);

	/**
	 * Provider.
	 */
	Provider provider;

	/**
	 * Throwable on start
	 */
	Throwable t;

	/**
	 * Creates a new GenericQuartzJob object.
	 */
	public GenericQuartzJob() {
		try {
			provider = initialize();
		} catch (Throwable e) {
			e.printStackTrace();
			this.t = e;
		}
	}

	/**
	 * Initializes the resources for this job.
	 * 
	 * Quartz uses a thread pool to instantiate Jobs and executes them in another Thread.
	 * When the application runs inside a JEE container, these threads do not have access
	 * to the JEE context (unless the server allows it, which is not the case for WebSphere).
	 * The only workaround that is available is to use the org.quartz.spi.ThreadExecutor SPI.
	 * This includes the specification of two properties in the quartz configuration, e.g.:
	 * 
	 * <pre>
	 * org.quartz.threadExecutor.class=gr.interamerican.bo2.quartz.was.WorkManagerThreadExecutor
	 * org.quartz.threadExecutor.workManagerName=wm/default
	 * </pre>
	 * 
	 * This ensures that the instantiation of the Job instance is performed in a Thread that is
	 * actually managed by the container. For this reason, we create a {@link Provider} instance
	 * here that is to be used for the actual execution of the Job, which eagerly initializes all
	 * resources that are normally obtained by the container (JTA UserTransction and {@link DataSource}s).
	 * When this Job is executed, the UserTransaction will be used to begin a transaction TX, Connections
	 * will be obtained from the DataSources and implicitly registered to TX and TX will commit or
	 * roll back as appropriate. If the running environment is not a JEE container, then the
	 * Bo2 TransactionManager instance will not be an instance of {@link J2eeJtaTransactionManager}
	 * and the
	 * <br>
	 * In stand alone programs, the Provider will be initialized in the RuntimeCommand that runs on the
	 * thread that executes the job. There is no actual need to initialize any resources here.
	 *
	 * @return Provider instance.
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 * @see WorkManagerThreadExecutor
	 */
	static Provider initialize() throws InitializationException, DataException {
		if(Bo2Session.getProvider() != null) {
			throw new RuntimeException("There is already a Provider instance associated with this thread:" + Thread.currentThread().getName()); //$NON-NLS-1$
		}
		Provider prov = Bo2.getProvider();
		TransactionManager transactionManager = prov.getTransactionManager();
		boolean jeeCtxt = (transactionManager instanceof J2eeJtaTransactionManager);

		if(!jeeCtxt) { //nothing to do; close the provider and return null.
			prov.close();
			return null;
		}

		//initialize all DataSource objects; do not get actual JDBC Connections
		try {
			Bo2Session.setProvider(prov);
			String[] jdbcManagers = Bo2DeploymentInfoUtility.get().getJdbcManagers();
			for (String manager : jdbcManagers) {
				/*
				 * proper setup requires HibernateSessionProvider to be replaced by JdbcConnectionProvider
				 * so this should be enough
				 */
				prov.getResource(manager, JdbcConnectionProvider.class);
			}
			return prov;
		} finally {
			Bo2Session.setProvider(null);
		}

	}

	/**
	 * generates the Operation for the given bean.
	 *
	 * @param bean the bean
	 * @return {@link Operation}
	 */
	Operation generateOperationFromBean(JobDescription bean) {
		Operation op = Factory.create(bean.getOperationClass());
		Map<String, Object> parameterMap = bean.getParameters();
		if (parameterMap != null) {
			for (String param : parameterMap.keySet()) {
				Object p = parameterMap.get(param);
				if (ReflectionUtils.getProperties(op).keySet().contains(param)) {
					ReflectionUtils.setProperty(param, p, op);
					LOGGER.trace("setting " + param + " with value " + p); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
		return op;
	}

	/**
	 * log and re-throw.
	 *
	 * @param e the e
	 * @param bean the bean
	 * @throws JobExecutionException the job execution exception
	 */
	void logMe(Throwable e, QuartzjobDescription bean) throws JobExecutionException {
		String trace = ExceptionUtils.getThrowableStackTrace(e);
		String msg = "Quartz job " + bean.getJobName() + " failed. " + bean.getJobDescriptionDigest(); //$NON-NLS-1$ //$NON-NLS-2$
		LOGGER.warn(msg);
		LOGGER.error(trace);
		bean.setThrowable(e);
		bean.setExecutionStatus(JobStatus.ERROR);
		throw new JobExecutionException(msg + StringConstants.NEWLINE + e);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzjobDescription quartzjobDescription = null;
		try {
			LOGGER.trace("Starting Generic QuartzJob"); //$NON-NLS-1$
			JobDataMap map = context.getJobDetail().getJobDataMap();
			JobDescription bean = (JobDescription) map.get(QuartzConstants.BEAN_PROP);
			quartzjobDescription = (QuartzjobDescription) map.get(QuartzConstants.SCHEDULED_BEAN_PROP);
			if (t != null) {
				throw t;
			}
			Bo2Session.setSession((Session<?, ?>) map.get(QuartzConstants.SESSION_PROP));
			Operation op = generateOperationFromBean(bean);

			RuntimeCommand cmd = null;
			if(provider != null) {
				cmd = new RuntimeCommand(provider, op);
			} else {
				cmd = new RuntimeCommand(op);
			}

			LOGGER.debug("Starting " + bean.getJobName() + " with params " + bean.getParameters()); //$NON-NLS-1$ //$NON-NLS-2$
			quartzjobDescription.setExecutionStatus(JobStatus.RUNNING);
			cmd.execute();
			LOGGER.debug("Ended " + bean.getJobName() + " with params " + bean.getParameters()); //$NON-NLS-1$ //$NON-NLS-2$
			quartzjobDescription.setExecutionStatus(JobStatus.OK);
		} catch (Throwable e) {
			logMe(e, quartzjobDescription);
		}
	}
}