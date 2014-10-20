package gr.interamerican.bo2.samples.bean;

public class SomeComparable implements Comparable<SomeComparable> {
	
	/**
	 * a value.
	 */
	private int value;

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(SomeComparable o) {
		if (o==null) {
			return 1;
		}
		if (getValue()<o.getValue()) {
			return -1;
		}
		if (getValue()==o.getValue()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * Prints the specified string.
	 * 
	 * @param s
	 */
	public void print(String s) {
		System.out.println(s);
	}

	/**
	 * Prints the specified integer.
	 * 
	 * @param s
	 */
	public void print(int i) {
		System.out.println(i);
	}
	
	/**
	 * Prints the specified object.
	 * 
	 * @param s
	 */
	public void print(Object o) {
		System.out.println(o);
	}

	
	
	

}
