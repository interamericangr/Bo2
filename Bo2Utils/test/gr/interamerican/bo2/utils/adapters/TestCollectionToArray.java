package gr.interamerican.bo2.utils.adapters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link CollectionToArray}.
 */
public class TestCollectionToArray {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Object[] array = {};
		CollectionToArray<Object> c2a = new CollectionToArray<Object>(array);
		Assert.assertSame(array, c2a.sample);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute() {
		List<Object> list = new ArrayList<Object>();
		Object[] expecteds = {new Object(), new Object(), new Object()};
		list.add(expecteds[0]);
		list.add(expecteds[1]);
		list.add(expecteds[2]);
		
		CollectionToArray<Object> c2a = new CollectionToArray<Object>(new Object[0]);
		Object[] actuals = c2a.execute(list);		
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	

}
