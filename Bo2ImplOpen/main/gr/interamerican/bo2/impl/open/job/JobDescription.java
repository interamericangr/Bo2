package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.arch.Operation;

import java.util.Map;

/**
 * Description of a job that is scheduled. This includes:
 * <li/> the class of an {@link Operation}
 * <li/> the operation named parameters
 * <li/> whether the job should be executed synchronously, i.e. the application
 *       should wait for it to finish. By default jobs are asynchronous.
 */
public interface JobDescription {

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
	 * @param synchronous
	 */
	void setSynchronous(boolean synchronous);

}