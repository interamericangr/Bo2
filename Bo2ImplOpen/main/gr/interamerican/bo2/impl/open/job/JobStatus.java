package gr.interamerican.bo2.impl.open.job;

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
	 * job have finished. set automatically when job finishes.<br>
	 * Not used by quartz
	 */
	FINISHED,
	/**
	 * job has finished normally. Set by the Operator.<br>
	 * for quartz it is set automatically if the job finishes normally
	 */
	OK,
	/**
	 * job has finished abnormally. Set by the Operator.<br>
	 * for quartz it is set automatically if the job finishes abnormally
	 */
	ERROR
}
