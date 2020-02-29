package gr.interamerican.bo2.arch.batch;

/**
 * Status of a running process.
 */
public interface ProcessStatus {

	/**
	 * Indicates if the process has stopped.
	 *
	 * @return Returns true if processing has stopped.
	 */
	boolean isFinished();

	/**
	 * Indicates that the process has quit while processing is still
	 * not finished.
	 *
	 * This could happen in the following cases: <ul><li>The process was forced to quit. </li> <li>
	 * Processing ended abnormally due to an exception. </li></ul>
	 *
	 * @return Returns true if processing has stopped abnormally.
	 */
	boolean isFinishedAbnormally();

	/**
	 * Gets the message of the exception that forced the process to
	 * stop abnormally.
	 *
	 * @return Returns the exception message
	 */
	String getExceptionMessage();
}
