package gr.interamerican.bo2.utils.caching;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test LruCache
 */
public class TestLruCache {
	
	/**
	 * test subject
	 */
	LruCache<String, Integer> subject = new LruCache<String, Integer>(3);
	
	/**
	 * test
	 */
	@SuppressWarnings("nls")
	@Test
	public void test() {
		subject.put("a", 1);
		subject.put("b", 2);
		subject.put("c", 3);
		subject.put("d", 4); //a should be removed now
		Assert.assertEquals(3, subject.size());
		Assert.assertFalse(subject.containsKey("a"));
		
		subject.get("b");
		subject.put("a", 1); //c should be removed now
		Assert.assertEquals(3, subject.size());
		Assert.assertFalse(subject.containsKey("c"));
		
		subject.put("e", 5); //d should be removed now
		Assert.assertEquals(3, subject.size());
		Assert.assertFalse(subject.containsKey("d"));
	}

}
