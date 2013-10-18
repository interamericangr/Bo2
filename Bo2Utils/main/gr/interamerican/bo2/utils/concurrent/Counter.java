package gr.interamerican.bo2.utils.concurrent;

/**
 * Thread safe counter.
 */
public class Counter {
	
	/**
	 * Value.
	 */
	int value = 0;
	
	/**
	 * Sets the value of the counter.
	 * 
	 * @param value
	 */
	public synchronized void set(int value) {
		this.value = value;
	}
	
	/**
	 * Sets the value of the counter to zero.
	 */
	public synchronized void reset() {
		this.value = 0;
	}
	
	/**
	 * Increases the value by 1 and returns the new value.
	 * 
	 * @return Returns the value of the counter.
	 */
	public synchronized int increase() {
		return ++value;		
	}
	
	/**
	 * Increases the value by 1 and returns the new value.
	 * 
	 * @return Returns the value of the counter.
	 */
	public synchronized int decrease() {
		return --value;		
	}


}
