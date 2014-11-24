package gr.interamerican.bo2.utils.iterable;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * {@link IterablePropertyCrawler} applies a {@link VoidOperation} to each
 * element of an {@link Iterable} that is calculated by a property of
 * an object.
 *
 * @param <A>
 *        Type of object being processed by this {@link VoidOperation}.
 * @param <T>
 *        Type of elements in the iterable property. 
 */
public class IterablePropertyCrawler<A,T>
implements VoidOperation<A> {
	
	/**
	 * Void operation to apply to all elements of the iterable.
	 */
	VoidOperation<T> operation;
	
	/**
	 * Property name that gets the iterable.
	 */
	String property;
	
	/**
	 * Crawler.
	 */
	IterableCrawler<T> crawler;
	
	/**
	 * Creates a new IterablePropertyCrawler object.
	 * 
	 * @param operation
	 *        Operation to execute on each element of the iterable
	 *        property.
	 * @param property
	 *        name of the iterable property.      
	 */
	public IterablePropertyCrawler(VoidOperation<T> operation, String property) {
		super();
		this.operation = operation;
		this.property = property;	
		this.crawler = new IterableCrawler<T>(operation);
	}

	@Override
	public void execute(A a) {
		Object col = ReflectionUtils.getProperty(property, a);
		if (col!=null) {
			Iterable<T> iterable = Utils.cast(col);
			crawler.execute(iterable);
		}
	}

}
