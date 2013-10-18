package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Timer}.
 */
public class TestTimer {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testCreator() {		
		Timer t = new Timer();
		Assert.assertNotNull(t.start);		
	}
	
	/**
	 * Tests reset.
	 */
	@Test
	public void testReset() {		
		Timer t = new Timer();		
		ThreadUtils.sleepMillis(5);
		Date oldStart = t.start;
		t.reset();
		Date newStart = t.start;
		Assert.assertNotEquals(oldStart, newStart);
	}

	/**
	 * Tests reset.
	 */
	@Test
	public void testGet() {		
		Timer t = new Timer();
		ThreadUtils.sleepMillis(500);
		long millis = t.get();
		long dev = Math.abs(millis - 500);
		boolean ok = dev<5;
		Assert.assertTrue(ok);		
	}


}
