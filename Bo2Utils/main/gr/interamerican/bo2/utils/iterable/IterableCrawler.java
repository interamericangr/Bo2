package gr.interamerican.bo2.utils.iterable;

import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * {@link IterableCrawler} executes an {@link VoidOperation}
 * on each element of an {@link Iterable}.
 *
 * @param <T>
 *        Type of iterator elements.
 */
public class IterableCrawler<T> 
implements VoidOperation<Iterable<T>>{
	
	/**
	 * Operation.
	 */
	VoidOperation<T> operation;

	
	/**
	 * Creates a new IteratorProcessor.
	 * 
	 * @param iterable        
	 * @param operation
	 */
	public IterableCrawler(VoidOperation<T> operation) {
		super();		
		this.operation = operation;
	}	
	
	@Override
	public void execute(Iterable<T> a) {
		for (T t : a) {
			operation.execute(t);
		}
	}
	
	

}
