package gr.interamerican.bo2.utils.iterable;

import java.util.Collection;

import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * {@link VoidOperation} that adds an element to a Collection.
 *
 * @param <T>
 */
public class AddToCollection<T> implements VoidOperation<T> {
	
	/**
	 * Collection.
	 */
	Collection<T> collection;
	
	/**
	 * Creates a new AddToCollection object.
	 * 
	 * @param collection
	 */
	public AddToCollection(Collection<T> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public void execute(T a) {
		collection.add(a);		
	}

}
