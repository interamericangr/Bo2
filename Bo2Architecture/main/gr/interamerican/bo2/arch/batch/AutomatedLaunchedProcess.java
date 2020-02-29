package gr.interamerican.bo2.arch.batch;

/**
 * A process that was launched through a launcher and waits not user input to execute.
 */
public interface AutomatedLaunchedProcess {

	/**
	 * with this method, the caller thread waits for operation thread to finish and returns the
	 * appropriate exit value.
	 *
	 * @return exit value. null something is terribly wrong
	 */
	Integer waitForThreadAndGetProcessExitValue();

}
