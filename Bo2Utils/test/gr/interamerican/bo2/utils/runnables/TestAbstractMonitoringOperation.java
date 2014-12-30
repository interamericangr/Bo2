package gr.interamerican.bo2.utils.runnables;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbstractMonitoringOperation}.
 */
public class TestAbstractMonitoringOperation {
	
	/**
	 * Creates a sample AbstractMonitoringProcess.
	 * 
	 * @return Returns the sample.
	 */
	AbstractMonitoringOperation<Object> sample() {
		return  new AbstractMonitoringOperation<Object>() {
			@Override
			public void execute(Object a) {/*empty*/};
		};
	}
	
	/**
	 * Test for getPeriodInterval().
	 */
	@Test
	public void testGetPeriodInterval() {
		AbstractMonitoringOperation<Object> amp = sample();
		amp.periodInterval = 100L;
		Assert.assertEquals(amp.periodInterval, amp.getPeriodInterval());
	}
	
	/**
	 * Test for setPeriodInterval(l).
	 */
	@Test
	public void testSetPeriodInterval() {
		AbstractMonitoringOperation<Object> amp = sample();
		long interval = 10L;
		amp.setPeriodInterval(interval);
		Assert.assertEquals(interval, amp.periodInterval);
	}
	
	/**
	 * Test for setIntervalFromProperties(p,s).
	 */
	@Test
	public void testSetIntervalFromProperties() {
		AbstractMonitoringOperation<Object> amp = sample();
		Properties p = new Properties();
		String intervalProperty = "interval"; //$NON-NLS-1$
		long interval = 10L;
		p.setProperty(intervalProperty, Long.toString(interval));		
		amp.setIntervalFromProperties(p, intervalProperty);
		Assert.assertEquals(interval, amp.periodInterval);
	}
	
	/**
	 * Test for isValid().
	 */
	@Test
	public void testIsValid_true() {
		AbstractMonitoringOperation<Object> amp = sample();
		amp.periodInterval = 10L;
		Assert.assertTrue(amp.isValid());
	}
	
	/**
	 * Test for isValid().
	 */
	@Test
	public void testIsValid_false() {
		AbstractMonitoringOperation<Object> amp = sample();
		amp.periodInterval = 0L;
		Assert.assertFalse(amp.isValid());
	}
	
	

}
