package gr.interamerican.bo2.utils.beans;

import java.util.Iterator;
import java.util.Map;

/**
 * Iterator based on a Map.
 * 
 * The SimpleMapIterator does not support the <code>remove()</code>
 * operation.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
class SimpleMapIterator<K,V> implements Iterator<Pair<K, V>> {
	
	
	/**
	 * Instantiates a new simple map iterator.
	 *
	 * @param map the map
	 */
	public SimpleMapIterator(Map<K, V> map) {
		this.iterator = map.entrySet().iterator();
	}
	

	/**
	 * Iterator.
	 */
	Iterator<Map.Entry<K, V>> iterator;
	
	@Override
	public boolean hasNext() {			
		return iterator.hasNext();
	}

	@Override
	public Pair<K, V> next() {
		Map.Entry<K, V> entry = iterator.next();
		return new Pair<K, V>(entry.getKey(), entry.getValue());
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
