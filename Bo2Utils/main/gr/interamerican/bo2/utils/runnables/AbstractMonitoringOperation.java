package gr.interamerican.bo2.utils.runnables;

import gr.interamerican.bo2.utils.NumberUtils;

import java.util.Properties;


/**
 * Abstract base class for {@link MonitoringOperation}s.
 * 
 * @param <T> 
 *        Type of monitored object.
 */
public abstract class AbstractMonitoringOperation<T> 
implements MonitoringOperation<T> {
	
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
	 * @param properties
	 * @param intevalKey
	 */
	protected void setIntervalFromProperties(Properties properties, String intevalKey) {
		String strInterval = properties.getProperty(intevalKey);
		long interval = NumberUtils.string2Int(strInterval);
		setPeriodInterval(interval);
	}

}
