package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.adapters.VoidOperationAsLogicOperation;
import gr.interamerican.bo2.utils.adapters.vo.MethodBasedVoidOperation;
import gr.interamerican.bo2.utils.iterable.IterableCrawler;
import gr.interamerican.bo2.utils.iterable.IterableNestedPropertyCrawler;

import java.util.Collection;

/**
 * Method invocation utility bean for use inside Bo2 operations.
 */
public class Invoker {
	
	/**
	 * Owner object.
	 */
	Object owner;
	
	/**
	 * Creates a new {@link Invoker}.
	 * 
	 * @param owner
	 *        Owner of the invoker.
	 */
	public Invoker(Object owner) {
		super();
		this.owner = owner;
	}

	/**
	 * Invokes a method of the owner object on each element 
	 * of the specified collection.
	 * 
	 * @param method
	 *        Name of a public or declared method of the owner class.	         
	 * @param collection
	 *        Collection that contains the elements.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void invokeOnCollection(String method, Collection<?> collection) 
	throws DataException, LogicException {
		MethodBasedVoidOperation mbvo = new MethodBasedVoidOperation(method, owner);
		IterableCrawler crawler = new IterableCrawler(mbvo);		
		VoidOperationAsLogicOperation logic = new VoidOperationAsLogicOperation(crawler);
		logic.setInput(collection);
		logic.execute();
	}
	
	/**
	 * Invokes a method of the owner object on each element 
	 * of the collection that is derrived as a nested property
	 * of the specified object.
	 * 
	 * @param method
	 *        Name of a public or declared method of the owner class.	         
	 * @param object
	 *        Object that has the collection.
	 * @param property
	 *        String that specified the property or nested property
	 *        of the specified object that returns the collection.        
	 * 
	 * @throws DataException
	 * @throws LogicException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void invokeOnCollection(String method, Object object, String property) 
	throws DataException, LogicException {
		MethodBasedVoidOperation mbvo = new MethodBasedVoidOperation(method, owner);
		IterableNestedPropertyCrawler  crawler = new IterableNestedPropertyCrawler(mbvo, property);
		VoidOperationAsLogicOperation logic = new VoidOperationAsLogicOperation(crawler);
		logic.setInput(object);
		logic.execute();
	}

}
