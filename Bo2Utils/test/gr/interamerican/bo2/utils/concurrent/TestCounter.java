package gr.interamerican.bo2.utils.concurrent;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Counter}.
 */
public class TestCounter {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Counter c = new Counter();
		Assert.assertEquals(0, c.value);
	}
	
	/**
	 * Tests increase().
	 */
	@Test
	public void testIncrease() {
		Counter c = new Counter();
		c.increase();
		Assert.assertEquals(1, c.value);
		c.increase();
		Assert.assertEquals(2, c.value);
	}
	
	/**
	 * Tests decrease().
	 */
	@Test
	public void testDecrease() {
		Counter c = new Counter();
		c.decrease();
		Assert.assertEquals(-1, c.value);
		c.decrease();
		Assert.assertEquals(-2, c.value);
	}
	
	/**
	 * Tests set(int)
	 */
	@Test
	public void testSet() {
		Counter c = new Counter();
		int value = 15;
		c.set(value);
		Assert.assertEquals(value, c.value);
	}
	
	/**
	 * Tests reset().
	 */
	@Test
	public void testReset() {
		Counter c = new Counter();		
		c.set(25);
		c.reset();
		Assert.assertEquals(0, c.value);
	}



}
