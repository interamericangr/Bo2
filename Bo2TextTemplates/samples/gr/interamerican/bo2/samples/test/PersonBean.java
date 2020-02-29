package gr.interamerican.bo2.samples.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean for testing purpose.
 */
public class PersonBean {
	
	/**
	 * Attributes.
	 */
	Map<String, String> attributes = new HashMap<String, String>();
	
	/**
	 * first name.
	 */
	String firstName;
	
	/**
	 * last name.
	 */
	String lastName;
	
	/**
	 * address.
	 */
	Address address;

	/**
	 * Gets the address.
	 *
	 * @return address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the first name.
	 *
	 * @return fistName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the attributes.
	 *
	 * @return Returns the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Assigns a new value to the attributes.
	 *
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	

}
