package gr.interamerican.bo2.samples.test;

/**
 * Address.
 */
public class Address {
	
	/**
	 * Empty string.
	 */
	private static final String EMPTY = ""; //$NON-NLS-1$
	
	/**
	 * Empty string.
	 */
	private static final String COMMA = ","; //$NON-NLS-1$
	
	/**
	 * city.
	 */
	String city=EMPTY;
	
	/**
	 * street.
	 */
	String street=EMPTY;
	/**
	 * number.
	 */
	String number=EMPTY;
	

	/**
	 * Gets the city.
	 *
	 * @return Returns the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Assigns a new value to the city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the number.
	 *
	 * @return Returns the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Assigns a new value to the number.
	 *
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}	

	/**
	 * Gets the street.
	 *
	 * @return Returns the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Assigns a new value to the street.
	 *
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Override
	public String toString() {
		return street + COMMA + number + COMMA + city;
	}


}
