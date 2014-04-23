package gr.interamerican.bo2.quartz;

/**
 * status of the job.
 */
public enum JobStatus {
	/**
	 * job is scheduled, first status of newly created job.
	 */
	SCHEDULED,
	/**
	 * job is running. first set automatically when a job is invoked by the Operator.
	 */
	RUNNING,
	/**
	 * job have finished. set automatically when job finishes.
	 */
	FINISHED,
	/**
	 * job has finished normally. Set by the Operator.
	 */
	OK,
	/**
	 * job has finished abnormally. Set by the Operator.
	 */
	ERROR
}
