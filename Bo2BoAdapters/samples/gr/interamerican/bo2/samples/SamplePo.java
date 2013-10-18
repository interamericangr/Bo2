package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.arch.PersistentObject;

/**
 * 
 */
public interface SamplePo extends  PersistentObject<Integer> {

	/**
	 * Gets the id.
	 *
	 * @return Returns the id
	 */
	public Integer getId();

	/**
	 * Assigns a new value to the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id);

	/**
	 * Gets the string1.
	 *
	 * @return Returns the string1
	 */
	public String getString1();

	/**
	 * Assigns a new value to the string1.
	 *
	 * @param string1 the string1 to set
	 */
	public void setString1(String string1);

	/**
	 * Gets the string2.
	 *
	 * @return Returns the string2
	 */
	public String getString2();

	/**
	 * Assigns a new value to the string2.
	 *
	 * @param string2 the string2 to set
	 */
	public void setString2(String string2);

}