package gr.interamerican.bo2.utils.caching;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A cache that will remove the least recently used entry once it
 * reaches its limit.
 * <br/>
 * The implementation relies on LinkedHashMap and the
 * {@link LinkedHashMap#remove(Object)} contract.
 * 
 * @param <K> key 
 * @param <V> value
 * 
 * @see java.util.LinkedHashMap#removeEldestEntry(Map.Entry)
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Maximum number of entries.
	 */
	private final int maxEntries;

	/**
	 * Creates a new LruCache object. 
	 *
	 * @param maxEntries
	 */
    public LruCache(final int maxEntries) {
        super(maxEntries + 1, 1.0f, true); //access-order
        this.maxEntries = maxEntries;
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
    	/*
    	 * Returns true if this LruCache has more entries than the maximum specified.
    	 */
        return super.size() > maxEntries;
    }

}
