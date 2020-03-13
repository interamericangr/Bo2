package gr.interamerican.bo2.impl.open.runtime.monitor;



/**
 * constants for the monitoring operations.
 */
public class MonitoringConstants {

	/**
	 * Prefix for properties defining a monitoring class.
	 */
	public static final String MONITOR_KEY = "monitor"; //$NON-NLS-1$

	/*
	 * gr.interamerican.bo2.impl.open.runtime.monitor.Tidy
	 */
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String TIDY_INTERVAL = "tidyInterval"; //$NON-NLS-1$
	/*
	 * gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessSysout
	 */
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String SYSOUT_INTERVAL = "sysoutInterval"; //$NON-NLS-1$
	/*
	 * gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessMail
	 */
	/**
	 * Property key for monitoringMailRecipients.
	 */
	public static final String MONITOR_MESSAGE_RECIPIENTS = "monitoringMailRecipients"; //$NON-NLS-1$
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String MONITOR_MESSAGE_INTERVAL = "monitoringMailInterval"; //$NON-NLS-1$
	/**
	 * Property key for monitoringMailFrom.
	 */
	public static final String MONITOR_MESSAGE_FROM = "monitoringMailFrom"; //$NON-NLS-1$
	/*
	 * gr.interamerican.bo2.impl.open.runtime.monitor.BatchProcessExitValueMonitor
	 */
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions in milliseconds.
	 */
	public static final String EXIT_INTERVAL = "exitInterval"; //$NON-NLS-1$
}
