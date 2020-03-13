package gr.interamerican.bo2.utils;

import org.slf4j.MDC;

/**
 * Constants used for logging facilities.
 * 
 * @see MDC
 */
public final class LoggingConstants {
	
	/**
	 * Hidden constructor.
	 */
	private LoggingConstants() {/*empty*/}
	
	/** userId MDC key. */
	public static final String MDC_USER_ID = "userid"; //$NON-NLS-1$
	
	/** callback method MDC key. */
	public static final String MDC_CALLBACKMETHOD = "callback"; //$NON-NLS-1$
	
	/** uuid MDC key. */
	public static final String MDC_UUID = "uuid"; //$NON-NLS-1$

}
