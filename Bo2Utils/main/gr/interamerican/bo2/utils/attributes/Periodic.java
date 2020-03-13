package gr.interamerican.bo2.utils.attributes;

/**
 * An object that has a cycle interval.
 */
public interface Periodic {
	
	/**
	 * Gets the period interval in milliseconds.
	 * 
	 * @return Returns the period interval.
	 */
	public long getPeriodInterval();
	
	/**
	 * Sets the periodinterval.
	 *
	 * @param periodInterval the new period interval
	 */
	public void setPeriodInterval(long periodInterval);

}
