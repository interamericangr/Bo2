package gr.interamerican.bo2.utils.runnables;

import gr.interamerican.bo2.utils.NumberUtils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract base class for {@link MonitoringOperation}s.
 *
 * @param <T>
 *        Type of monitored object.
 */
public abstract class AbstractMonitoringOperation<T>
implements MonitoringOperation<T> {

	/**
	 * Default logger for monitoring operations.
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractMonitoringOperation.class);
	/**
	 * Period interval.
	 */
	long periodInterval;

	@Override
	public long getPeriodInterval() {
		return periodInterval;
	}

	@Override
	public void setPeriodInterval(long periodInterval) {
		this.periodInterval = periodInterval;
	}

	@Override
	public boolean isValid() {
		return getPeriodInterval()>0;
	}

	/**
	 * Sets the interval.
	 *
	 * @param properties the properties
	 * @param intevalKey the inteval key
	 */
	protected void setIntervalFromProperties(Properties properties, String intevalKey) {
		String strInterval = properties.getProperty(intevalKey);
		long interval = NumberUtils.string2Int(strInterval);
		setPeriodInterval(interval);
		LOGGER.info(this.getClass().getName() + " setting period inteval to " + interval); //$NON-NLS-1$
	}

}
