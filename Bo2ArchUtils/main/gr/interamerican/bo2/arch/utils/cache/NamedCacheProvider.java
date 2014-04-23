package gr.interamerican.bo2.arch.utils.cache;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;

import java.io.Serializable;

/**
 * Utility class for {@link Serializable} classes that need to access a {@link Cache}
 * with its name.
 * 
 * @param <C> Cache code
 * 
 * @see Serializable
 * @see Cache
 */
public class NamedCacheProvider
<C extends Comparable<? super C>> 
implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * cacheName
	 */
	private String cacheName;
	
	/**
	 * Cache.
	 */
	private transient Cache<C> cache;
		
	/**
	 * Creates a new CachedObjectValidator object. 
	 *
	 * @param cacheName
	 */
	public NamedCacheProvider(String cacheName) {
		super();
		this.cacheName = cacheName;
	}
	
	/**
	 * Initializes (if needed) and returns the transient field {@link #cache} 
	 * 
	 * @return returns the {@link Cache} of this AbstractCacheRelatedObjectBoPropertyDescriptor
	 */
	public Cache<C> cache() {
		if(cache == null) {
			cache = CacheRegistry.getRegisteredCache(cacheName);
		}
		return cache;
	}

}
