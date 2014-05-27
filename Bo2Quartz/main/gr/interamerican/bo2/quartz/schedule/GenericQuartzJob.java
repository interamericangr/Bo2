package gr.interamerican.bo2.quartz.schedule;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobStatus;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.quartz.QuartzConstants;
import gr.interamerican.bo2.quartz.QuartzjobDescription;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.Map;

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
	Logger LOGGER = LoggerFactory.getLogger(GenericQuartzJob.class);

	/**
	 * generates the Operation for the given bean.
	 * 
	 * @param bean
	 * @return {@link Operation}
	 */
	Operation generateOperationFromBean(JobDescription bean) {
		Operation op = Factory.create(bean.getOperationClass());
		Map<String, Object> parameterMap = bean.getParameters();
		if (parameterMap != null) {
			for (String param : parameterMap.keySet()) {
				Object p = parameterMap.get(param);
				ReflectionUtils.setProperty(param, p, op);
				LOGGER.trace("setting " + param + " with value " + p); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return op;
	}

	/**
	 * log and re-throw.
	 * 
	 * @param e
	 * @param bean
	 * @throws JobExecutionException
	 */
	void logMe(Throwable e, QuartzjobDescription bean) throws JobExecutionException {
		String trace = ExceptionUtils.getThrowableStackTrace(e);
		String msg = "Quartz job " + bean.getJobName() + " failed. " + bean.getParameters(); //$NON-NLS-1$ //$NON-NLS-2$
		LOGGER.warn(msg);
		LOGGER.error(trace);
		bean.setExecutionStatus(JobStatus.ERROR);
		bean.setThrowable(e);
		throw new JobExecutionException(msg + StringConstants.NEWLINE + e);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("Starting Generic QuartzJob"); //$NON-NLS-1$
		JobDataMap map = context.getJobDetail().getJobDataMap();
		QuartzjobDescription bean = (QuartzjobDescription) map.get(QuartzConstants.BEAN_PROP);
		Bo2Session.setSession((Session<?, ?>) map.get(QuartzConstants.SESSION_PROP));
		Operation op = generateOperationFromBean(bean);
		RuntimeCommand cmd = null;
		String depl = Bo2.getDefaultDeployment().getDeploymentBean().getPathToSecondaryBatchDeployment();
		if (!StringUtils.isNullOrBlank(depl)) {
			cmd = new RuntimeCommand(depl, op);
		} else {
			cmd = new RuntimeCommand(op);
		}
		LOGGER.debug("Starting " + bean.getJobName() + " with params " + bean.getParameters()); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			bean.setExecutionStatus(JobStatus.RUNNING);
			cmd.execute();
		} catch (DataException e) {
			logMe(e, bean);
		} catch (LogicException e) {
			logMe(e, bean);
		} catch (UnexpectedException e) {
			logMe(e, bean);
		} catch (Error e) {
			logMe(e, bean);
		}
		LOGGER.debug("Ended " + bean.getJobName() + " with params " + bean.getParameters()); //$NON-NLS-1$ //$NON-NLS-2$
		bean.setExecutionStatus(JobStatus.OK);
	}
}
