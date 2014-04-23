package gr.interamerican.bo2.quartz;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.ext.Session;

import java.util.Map;


/**
 * bean to describe a quartz job.
 */
public interface QuartzJobDescritpionBean {

	/**
	 * @return the operation.
	 */
	Class<? extends Operation> getOperationClass();

	/**
	 * sets the operation to run. (mandatory for the operation that builds the quartz job)
	 * 
	 * @param op
	 */
	void setOperationClass(Class<? extends Operation> op);

	/**
	 * @return the parameters.
	 */
	Map<String, Object> getParameters();

	/**
	 * parameters to be set to the operation.
	 * 
	 * @param params
	 */
	void setParameters(Map<String, Object> params);

	/**
	 * @return the session
	 */
	Session<String, Long> getSession();

	/**
	 * sets the session. (optional)
	 * 
	 * @param session
	 */
	void setSession(Session<String, Long> session);

	/**
	 * @return execution status.
	 */
	JobStatus getExecutionStatus();

	/**
	 * @param status
	 */
	void setExecutionStatus(JobStatus status);

	/**
	 * @return the jobname
	 */
	String getJobName();

	/**
	 * sets the jobname.
	 * 
	 * @param name
	 */
	void setJobName(String name);
}
