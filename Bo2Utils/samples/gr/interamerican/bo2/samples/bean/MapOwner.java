package gr.interamerican.bo2.samples.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that owns a Map.
 * 
 * @param <K> 
 * @param <V> 
 */
public class MapOwner<K,V> {
	
	/**
	 * Map with strings.
	 */
	private Map<K, V> map = new HashMap<K, V>();

	/**
	 * Gets the stringsMap.
	 *
	 * @return Returns the stringsMap
	 */
	public Map<K, V> getMap() {
		return map;
	}

	/**
	 * Assigns a new value to the map.
	 *
	 * @param map 
	 *        the map to set
	 */
	public void setMap(Map<K, V> map) {
		this.map = map;
	}

}
