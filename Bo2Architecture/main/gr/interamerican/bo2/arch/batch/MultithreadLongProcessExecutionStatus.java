package gr.interamerican.bo2.arch.batch;

/**
 * enumeration to indicate the execution status of the given BatchProcess
 */
public enum MultithreadLongProcessExecutionStatus {
	/**
	 * start
	 */
	START,
	/**
	 * preprocess is executing
	 */
	PREPROCESS_EXECUTION,
	/**
	 * main query is executing
	 */
	MAIN_QUERY_EXECUTION,
	/**
	 * QPROCESSORS executing
	 */
	QPROCESSORS_EXECUTION,
	/**
	 * post process executing
	 */
	POST_PROCESS,
	/**
	 * end
	 */
	END;
}
