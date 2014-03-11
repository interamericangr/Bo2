package gr.interamerican.bo2.samples;

import interamerican.architecture.PersistentObject;

/**
 * Sample legacy po.
 */
public class SampleLegacyPo implements PersistentObject {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id.
	 */
	private Integer id;
	/**
	 * field.
	 */
	private String string1;
	/**
	 * field.
	 */
	private String string2;
	/**
	 * Gets the id.
	 *
	 * @return Returns the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Assigns a new value to the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Gets the string1.
	 *
	 * @return Returns the string1
	 */
	public String getString1() {
		return string1;
	}
	/**
	 * Assigns a new value to the string1.
	 *
	 * @param string1 the string1 to set
	 */
	public void setString1(String string1) {
		this.string1 = string1;
	}
	/**
	 * Gets the string2.
	 *
	 * @return Returns the string2
	 */
	public String getString2() {
		return string2;
	}
	/**
	 * Assigns a new value to the string2.
	 *
	 * @param string2 the string2 to set
	 */
	public void setString2(String string2) {
		this.string2 = string2;
	}


}
