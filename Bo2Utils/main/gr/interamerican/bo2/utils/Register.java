package gr.interamerican.bo2.utils;

import java.util.HashMap;

/**
 * Register lets register key-value pairs only once per key.
 * 
 * Trying to re-register the same key, will throw a RuntimeException.
 * 
 * Users can explicitly remove a key from the registry.
 * 
 * Register is VM scoped and it is not thread-safe.
 */
public class Register {
	
	/**
	 * Map.
	 */
	static final HashMap<Object, Object> map = new HashMap<Object, Object>();

	/**
	 * Gets the object associated with the specified key.
	 * 
	 * 
	 * @param key
	 * 
	 * @return Returns the value to which the specified key is mapped,
     *         or {@code null} if the register contains no mapping for the key.
	 */
	public static Object get(Object key) {
		return map.get(key);
	}

	/**
	 * Puts a value in the register.
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(Object key, Object value) {
		Object previous = map.put(key, value);
		if (previous!=null) {
			map.put(key, previous);
			throw new RuntimeException("Key already exists in registry"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Removes a value from the register.
	 * 
	 * @param key
	 */
	public static void remove(Object key) {
		map.remove(key);
	}

}
