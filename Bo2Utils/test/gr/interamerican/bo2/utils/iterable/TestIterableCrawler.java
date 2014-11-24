package gr.interamerican.bo2.utils.iterable;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link IterableCrawler}.
 */
@SuppressWarnings("unchecked")
public class TestIterableCrawler {
	
	/**
	 * Tests the constructor.
	 */	
	@Test
	public void testConstructor() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		IterableCrawler<Object> crawler = new IterableCrawler<Object>(operation);
		Assert.assertEquals(operation, crawler.operation);
	}
	
	/**
	 * Tests execute.
	 */
	@Test
	public void testExecute() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		IterableCrawler<Object> crawler = new IterableCrawler<Object>(operation);
		Collection<Object> collection = new ArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			collection.add(new Object());
		}
		crawler.execute(collection);
		for (Object object : collection) {
			verify(operation, times(1)).execute(object);
		}
	}

	

}
