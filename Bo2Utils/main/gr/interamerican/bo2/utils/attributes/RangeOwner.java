package gr.interamerican.bo2.utils.attributes;

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Interface for an object that owns a {@link Range}.
 *
 * @param <T>
 */
public interface RangeOwner<T extends Comparable<? super T>> {
	
	/**
	 * Gets the range owned by the specified obejct.
	 * 
	 * @return Returns the Range owned by the specified date.
	 */
	public Range<T> getRange();

}
