package gr.interamerican.bo2.utils.iterable;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.samples.bean.BeanWithCollections;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link IterableNestedPropertyCrawler}.
 */
@SuppressWarnings("unchecked")
public class TestIterableNestedPropertyCrawler {
	
	/**
	 * Tests the constructor_error.
	 */	
	@Test(expected=RuntimeException.class)
	public void testConstructor_error() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		String property = null; 		
		@SuppressWarnings("unused")
		IterableNestedPropertyCrawler<Object, Object> crawler = 
			new IterableNestedPropertyCrawler<Object, Object>(operation, property);
	}
	
	/**
	 * Tests the constructor_level1.
	 */	
	@Test
	public void testConstructor_level1() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		String property = "property"; //$NON-NLS-1$
		IterableNestedPropertyCrawler<Object, Object> crawler = 
			new IterableNestedPropertyCrawler<Object, Object>(operation, property);
		
		VoidOperation<Object> op = crawler.operation;		
		Assert.assertTrue(op instanceof IterablePropertyCrawler);
		
		
		IterablePropertyCrawler<Object, Object> ipc = Utils.cast(op);
		Assert.assertEquals(operation, ipc.operation);
		Assert.assertEquals(property, ipc.property);
	}
	
	/**
	 * Tests the constructor_level2.
	 */	
	@Test
	public void testConstructor_level2() {
		VoidOperation<Object> operation = mock(VoidOperation.class);
		String property = "property1.property2"; //$NON-NLS-1$
		IterableNestedPropertyCrawler<Object, Object> crawler = 
			new IterableNestedPropertyCrawler<Object, Object>(operation, property);
		VoidOperation<Object> op = crawler.operation;
		Assert.assertTrue(op instanceof IterableNestedPropertyCrawler);
	}
	
	
	
	
	/**
	 * Tests execute.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() {
		Set<String> set1 = new HashSet<String>();
		set1.add("foo");
		set1.add("bar");
		BeanWithCollections<String> bean1 = new BeanWithCollections<String>();
		bean1.setSet(set1);
		
		Set<String> set2 = new HashSet<String>();
		set2.add("xoo");
		set2.add("xar");
		BeanWithCollections<String> bean2 = new BeanWithCollections<String>();
		bean2.setSet(set2);
		
		BeanWithCollections<BeanWithCollections<String>> beanWithBeans =
			new BeanWithCollections<BeanWithCollections<String>>();
		List<BeanWithCollections<String>> list = new ArrayList<BeanWithCollections<String>>();
		list.add(bean1);
		list.add(bean2);
		
		BeanWithCollections<BeanWithCollections<BeanWithCollections<String>>> bean =
			new BeanWithCollections<BeanWithCollections<BeanWithCollections<String>>>();
		
		List<BeanWithCollections<BeanWithCollections<String>>> listWithBeans =
			new ArrayList<BeanWithCollections<BeanWithCollections<String>>>();
		listWithBeans.add(beanWithBeans);
		
		String property = "list.list.set";
		
		VoidOperation<String> operation = mock(VoidOperation.class);
		IterableNestedPropertyCrawler<Object, String> crawler = 
			new IterableNestedPropertyCrawler<Object, String>(operation, property);
		
		crawler.execute(bean);
	}

	

}
