package gr.interamerican.bo2.utils.iterable;

import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * {@link IterableNestedPropertyCrawler} provides the functionality
 * of {@link IterablePropertyCrawler} for nested properties.
 *
 * @param <A>
 *        Type of object being processed by this {@link VoidOperation}.
 * @param <T>
 *        Type of elements in the iterable property. 
 */
public class IterableNestedPropertyCrawler<A,T>
implements VoidOperation<A> {
	
	/**
	 * operation.
	 */
	VoidOperation<A> operation;
	
	
	/**
	 * Creates a new IterablePropertyCrawler object.
	 * 
	 * @param operation
	 *        Operation to execute on each element of the iterable
	 *        property.
	 * @param property
	 *        name of the iterable property.      
	 */
	IterableNestedPropertyCrawler(VoidOperation<T> op, String[] properties) {
		super();
		int length = properties.length;
		switch (length) {
		case 0:
			String msg = "Null or empty property"; //$NON-NLS-1$
			throw new RuntimeException(msg);
			
		case 1:			
			this.operation = new IterablePropertyCrawler<A, T>(op, properties[0]);
			break;

		default:
			String[] restProperties = ArrayUtils.right(properties, 1);
			this.operation = new IterableNestedPropertyCrawler<A, T>(op, restProperties);
			break;			
		}
	}	
	
	/**
	 * Creates a new IterablePropertyCrawler object.
	 * 
	 * @param operation
	 *        Operation to execute on each element of the iterable
	 *        property.
	 * @param nested 
	 *        name of the nested property.      
	 */
	public IterableNestedPropertyCrawler(VoidOperation<T> operation, String nested) {
		this(operation, TokenUtils.split(nested, StringConstants.DOT));		
	}

	@Override
	public void execute(A a) {
		operation.execute(a);
	}

}
