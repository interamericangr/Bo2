package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.Operation;

import java.io.Serializable;
import java.util.Map;

/**
 * Description of a job that is scheduled. This includes:
 * <ul>
 * <li> the class of an {@link Operation} </li>
 * <li> the operation named parameters  </li>
 * <li> whether the job should be executed synchronously, i.e. the application
 *       should wait for it to finish. By default jobs are asynchronous. </li>
 * <li> whether the job triggering is nonTransactional. By default it is
 *       transactional, i.e. the job is fired only if the unit of work in
 *       which it was scheduled commits. </li>
 *       </ul>
 */
public interface JobDescription extends Serializable {
	
	/**
	 * Gets the job name.
	 *
	 * @return the jobName assigned during the creation.
	 */
	String getJobName();

	/**
	 * Gets the operationClass.
	 *
	 * @return Returns the operationClass
	 */
	Class<? extends Operation> getOperationClass();

	/**
	 * Assigns a new value to the operationClass.
	 *
	 * @param operationClass the operationClass to set
	 */
	void setOperationClass(Class<? extends Operation> operationClass);

	/**
	 * Gets the parameters.
	 *
	 * @return Returns the parameters
	 */
	Map<String, Object> getParameters();

	/**
	 * Assigns a new value to the parameters.
	 *
	 * @param parameters the parameters to set
	 */
	void setParameters(Map<String, Object> parameters);

	/**
	 * Gets the synchronous.
	 *
	 * @return synchronous
	 */
	boolean isSynchronous();

	/**
	 * Assigns a new value to the synchronous.
	 *
	 * @param synchronous the new synchronous
	 */
	void setSynchronous(boolean synchronous);

	/**
	 * Gets the nonTransactional.
	 *
	 * @return nonTransactionals
	 */
	boolean isNonTransactional();

	/**
	 * Assigns a new value to the nonTransactional.
	 *
	 * @param nonTransactional the new non transactional
	 */
	void setNonTransactional(boolean nonTransactional);

}