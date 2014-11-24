package gr.interamerican.bo2.utils.iterable;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AddToCollection}.
 */
public class TestAddToCollection {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {		
		Collection<Object> collection = new HashSet<Object>();
		AddToCollection<Object> add = new AddToCollection<Object>(collection);
		Assert.assertEquals(collection, add.collection);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute() {		
		Collection<Object> collection = new HashSet<Object>();		
		AddToCollection<Object> add = new AddToCollection<Object>(collection);
		Object o = new Object();
		add.execute(o);
		Assert.assertTrue(collection.contains(o));
	}

}
