package gr.interamerican.bo2.utils.enums;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Collection;

/**
 * Type of an index property.
 */
public enum IndexPropertyType {
	/**
	 * Integer or int.
	 */
	INTEGER(Integer.class, int.class) {
		@Override
		public <T> void addNext(Collection<T> collection, T nextElement, String indexPropertyName) {
			CollectionUtils.addNextI(collection, nextElement, indexPropertyName);
		}
	},
	
	/**
	 * Long or long.
	 */
	LONG(Long.class, long.class) {
		@Override
		public <T> void addNext(Collection<T> collection, T nextElement, String indexPropertyName) {
			CollectionUtils.addNextL(collection, nextElement, indexPropertyName);			
		}
	},
	
	/**
	 * Short or short.
	 */
	SHORT(Short.class, short.class) {
		@Override
		public <T> void addNext(Collection<T> collection, T nextElement, String indexPropertyName) {
			CollectionUtils.addNextS(collection, nextElement, indexPropertyName);
		}
	};
	
	
	
	/**
	 * Compatible index property types.
	 */
	private Class<?>[] compatibleTypes;
	
	/**
	 * Finds if a class is compatible.
	 * 
	 * @param clazz
	 * 
	 * @return Returns true if the class is compatible.
	 */
	boolean isCompatible(Class<?> clazz) {
		for (Class<?> compatible : compatibleTypes) {
			if (compatible.isAssignableFrom(clazz)) {
				return true;
			}			
		}
		return false;
	}
	
	
	/**
	 * Creates a new IndexPropertyType object. 
	 *
	 * @param compatibleTypes
	 */
	private IndexPropertyType(Class<?>... compatibleTypes) {
		this.compatibleTypes = compatibleTypes;
	}
















	/**
	 * Adds the next element in a collection.
	 * 
	 * 
	 * @param collection
	 *            Collection in which the next element is being added.
	 * @param nextElement
	 *            Next element that will be added in the collection.
	 * @param indexPropertyName
	 *            Name of the property that defines the index.
	 * @param <T>
	 *            Type of elements.
	 *
	 */	
	public abstract <T> void addNext(Collection<T> collection, T nextElement, String indexPropertyName);
	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Gets the {@link IndexPropertyType} that is compatible with the
	 * specified type.
	 *   
	 * @param clazz
	 * 
	 * @return Returns the correct IndexPropertyType, or null if their is 
	 *         no compatible type.
	 */
	public static IndexPropertyType getIndexPropertyTypeFor(Class<?> clazz) {
		for (IndexPropertyType type : values()) {
			if (type.isCompatible(clazz)) {
				return type;
			}
		}
		 return null;
	 }
	
	

}
