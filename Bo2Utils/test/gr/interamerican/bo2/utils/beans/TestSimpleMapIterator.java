package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link SimpleMapIterator}.
 */
@SuppressWarnings("nls")
public class TestSimpleMapIterator {
	
	

	/**
	 * Tests iterator
	 */	
	@Test
	public void testConstructor() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		SimpleMapIterator<String, Integer> iterator = 
			new SimpleMapIterator<String, Integer>(map);
		assertNotNull(iterator.iterator);
	}


	/**
	 * Test for hasNext().
	 */	
	@Test
	public void testHasNext_false() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		SimpleMapIterator<String, Integer> iterator = 
			new SimpleMapIterator<String, Integer>(map);		
		assertFalse(iterator.hasNext());
	}
	
	/**
	 * Test for hasNext().
	 */	
	@Test
	public void testHasNext_true() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ena", new Integer(1));		
		SimpleMapIterator<String, Integer> iterator = 
			new SimpleMapIterator<String, Integer>(map);		
		assertTrue(iterator.hasNext());
	}
	
	
	/**
	 * Tests iterator
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIterator_next() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ena", new Integer(1));
		map.put("dyo", new Integer(2));
		map.put("tria", new Integer(3));
		SimpleMapIterator<String, Integer> iterator = 
			new SimpleMapIterator<String, Integer>(map);
		
		for (int i = 0; i < 3; i++) {
			Pair<String, Integer> pair = iterator.next();
			String left = pair.getLeft();
			Integer right = pair.getRight();
			Assert.assertEquals(right, map.get(left));
		}
		
	}
	
	/**
	 * Tests iterator
	 */	
	@Test(expected=UnsupportedOperationException.class)
	public void testIterator_remove() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ena", new Integer(1));
		map.put("dyo", new Integer(2));
		map.put("tria", new Integer(3));
		SimpleMapIterator<String, Integer> iterator = 
			new SimpleMapIterator<String, Integer>(map);
		iterator.next();
		iterator.remove();			
		
	}

}
