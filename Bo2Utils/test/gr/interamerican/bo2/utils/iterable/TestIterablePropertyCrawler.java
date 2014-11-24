package gr.interamerican.bo2.utils.iterable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.samples.bean.BeanWithCollections;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link IterablePropertyCrawler}.
 */
@SuppressWarnings({"unchecked", "nls"})
public class TestIterablePropertyCrawler {
	
	/**
	 * Tests the constructor.
	 */	
	@Test
	public void testConstructor() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		String property = "property"; //$NON-NLS-1$
		
		IterablePropertyCrawler<Object, Object> crawler = 
			new IterablePropertyCrawler<Object, Object>(operation, property);
		Assert.assertEquals(operation, crawler.operation);
		Assert.assertEquals(property, crawler.property);
	}
	
	/**
	 * Tests execute.
	 */	
	@Test
	public void testExecute() {
		Set<String> set = new HashSet<String>();
		set.add("foo");
		set.add("bar");
		BeanWithCollections<String> bean = new BeanWithCollections<String>();
		bean.setSet(set);
		
		String property = "set";
		
		VoidOperation<Object> operation = mock(VoidOperation.class);
		IterablePropertyCrawler<Object, Object> crawler = 
			new IterablePropertyCrawler<Object, Object>(operation, property);
		
		crawler.execute(bean);
		
		for (String string : set) {
			verify(operation,times(1)).execute(string);
		}
		
	}

	

}
