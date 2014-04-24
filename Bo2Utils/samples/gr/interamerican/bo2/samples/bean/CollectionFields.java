package gr.interamerican.bo2.samples.bean;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class CollectionFields {
	/**
	 * List
	 */
	@SuppressWarnings("rawtypes")
	List rawList;
	/**
	 * List of String
	 */
	List<String> stringList;
	/**
	 * String
	 */
	String string;
	/**
	 * Collection of (Set of String)
	 */
	Collection<Set<String>> setsCollection;

	/**
	 * Gets the rawList.
	 *
	 * @return Returns the rawList
	 */
	@SuppressWarnings("rawtypes")
	public List getRawList() {
		return rawList;
	}
	/**
	 * Assigns a new value to the rawList.
	 *
	 * @param rawList the rawList to set
	 */
	@SuppressWarnings("rawtypes")
	public void setRawList(List rawList) {
		this.rawList = rawList;
	}

	/**
	 * Assigns a new value to the stringList.
	 *
	 * @param stringList the stringList to set
	 */
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	/**
	 * Gets the stringList.
	 *
	 * @return Returns the stringList
	 */
	public List<String> getStringList() {
		return stringList;
	}

	/**
	 * Gets the string.
	 *
	 * @return Returns the string
	 */
	public String getString() {
		return string;
	}
	/**
	 * Assigns a new value to the string.
	 *
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}
	/**
	 * Gets the setsCollection.
	 *
	 * @return Returns the setsCollection
	 */
	public Collection<Set<String>> getSetsCollection() {
		return setsCollection;
	}
	/**
	 * Assigns a new value to the setsCollection.
	 *
	 * @param setsCollection the setsCollection to set
	 */
	public void setSetsCollection(Collection<Set<String>> setsCollection) {
		this.setsCollection = setsCollection;
	}
}