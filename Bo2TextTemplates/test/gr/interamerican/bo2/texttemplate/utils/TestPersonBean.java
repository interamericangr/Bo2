package gr.interamerican.bo2.texttemplate.utils;

/**
 * Bean for testing purpose.
 */
public class TestPersonBean {
	
	/**
	 * 
	 */
	String firstName;
	
	/**
	 * 
	 */
	String lastName;
	
	/**
	 * 
	 */
	TestAddress address;

	/**
	 * 
	 * @return address
	 */
	public TestAddress getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(TestAddress address) {
		this.address = address;
	}

	/**
	 * 
	 * @return fistName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

}
