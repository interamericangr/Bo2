package gr.interamerican.bo2.utils.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WORK IN PROGRESS
 *
 * Services can be unreliable. This utility is meant to protect applications
 * that consume services that are known not to work 24/7 and are not mandatory
 * i.e. the application has a built-in workaround.
 * <br>
 * Indicates the status of an unreliable service. If a service call fails for
 * <code>maxConsecutiveFailures</code> consecutive calls, the service status is 
 * marked as down for the next <code>downTimeOut</code> ms.
 * <br>
 * 
 * Usage:
 * <br>
 * Create a singleton instance of this class for each unreliable service client.
 * On this instance:
 * <ul>
 * <li>Whenever a successful call is made, {@link #resetFailCount()} must be called.
 * <li>Service call failures call {@link #setDown()}.
 * <li>Before calling the service consult {@link #isDown()} result.
 * </ul>
 */
public class UnreliableServiceStatus {
	
	/**
	 * Logger.
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(UnreliableServiceStatus.class);
	
	/** name. */
	String name;
	
	/**
	 * Down timeout.
	 */
	long downTimeOut;
	
	/**
	 * Max consecutive failures.
	 */
	long maxConsecutiveFailures;
	
	/**
	 * Consecutive failures.
	 */
	int failCount = 0;
	
	/**
	 * Is currently down.
	 */
	boolean down = false;
	
	/** When was the last down period started?. */
	long lastDownOn = 0;
	
	/**
	 * Creates a new UnreliableServiceStatus object. 
	 *
	 * @param name
	 *        Name of this instance.
	 * @param downTimeOut
	 *        How much time (ms) should we skip the service after <code>maxConsecutiveFailures</code>?
	 * @param maxConsecutiveFailures
	 *        For how many consecutive failures do we keep trying to call the service?
	 */
	private UnreliableServiceStatus(String name, long downTimeOut, long maxConsecutiveFailures) {
		super();
		this.name = name;
		this.downTimeOut = downTimeOut;
		this.maxConsecutiveFailures = maxConsecutiveFailures;
	}
	
	/**
	 * Gets the down.
	 *
	 * @return Returns the down
	 */
	@SuppressWarnings("nls")
	public synchronized boolean isDown() {
		if(down) {
			long downtime = System.currentTimeMillis() - lastDownOn;
			LOGGER.info("Down for " + downtime + " ms");
			if(downtime < downTimeOut) {
				return true;
			}
			LOGGER.info("Max downtime period exceeded, resetting availability");
			down = false;
		}
		return down;
	}

	/**
	 * Indicate that the YSAE service is down. When this is called,
	 * {@link #isDown()} will return true for {@link #downTimeOut} ms. 
	 */
	@SuppressWarnings("nls")
	public synchronized void setDown() {
		failCount++;
		LOGGER.info(failCount + " consecutive failures reached");
		if(failCount == maxConsecutiveFailures) {
			LOGGER.info("Reached max consecutive failures count, unavailable for " + downTimeOut + " ms");
			this.down = true;
			this.lastDownOn = System.currentTimeMillis(); //set to now
			failCount=0;
		}
	}
	
	/**
	 * Resets the failCount on successful service call. 
	 */
	@SuppressWarnings("nls")
	synchronized void resetFailCount() {
		LOGGER.info("Reset consecutive failures count");
		failCount=0;
	}
	
	/**
	 * Factory method.
	 *
	 * @param name the name
	 * @param downTimeOut the down time out
	 * @param maxConsecutiveFailures the max consecutive failures
	 * @return Instance of this class.
	 */
	public static UnreliableServiceStatus get(String name, long downTimeOut, long maxConsecutiveFailures) {
		return new UnreliableServiceStatus(name, downTimeOut, maxConsecutiveFailures);
	}
}
