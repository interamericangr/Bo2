package gr.interamerican.bo2.samples.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class MapOwner {
	
	/**
	 * Map with strings.
	 */
	private Map<String, String> stringsMap = new HashMap<String, String>();

	/**
	 * Gets the stringsMap.
	 *
	 * @return Returns the stringsMap
	 */
	public Map<String, String> getStringsMap() {
		return stringsMap;
	}

	/**
	 * Assigns a new value to the stringsMap.
	 *
	 * @param stringsMap the stringsMap to set
	 */
	public void setStringsMap(Map<String, String> stringsMap) {
		this.stringsMap = stringsMap;
	}

}
