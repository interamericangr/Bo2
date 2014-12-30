package gr.interamerican.bo2.utils.runnables;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link AbstractMonitoringProcess}.
 */
public class TestAbstractMonitoringProcess {
	
	/**
	 * Creates a sample AbstractMonitoringProcess.
	 * 
	 * @return Returns the sample.
	 */
	AbstractMonitoringProcess<Object> sample() {
		return  new AbstractMonitoringProcess<Object>() {
			@Override
			public void execute(Object a) {/*empty*/};
		};
	}
	
	/**
	 * Test for getPeriodInterval().
	 */
	@Test
	public void testGetPeriodInterval() {
		AbstractMonitoringProcess<Object> amp = sample();
		amp.periodInterval = 100L;
		Assert.assertEquals(amp.periodInterval, amp.getPeriodInterval());
	}
	
	/**
	 * Test for setPeriodInterval(l).
	 */
	@Test
	public void testSetPeriodInterval() {
		AbstractMonitoringProcess<Object> amp = sample();
		long interval = 10L;
		amp.setPeriodInterval(interval);
		Assert.assertEquals(interval, amp.periodInterval);
	}
	
	/**
	 * Test for setIntervalFromProperties(p,s).
	 */
	@Test
	public void testSetIntervalFromProperties() {
		AbstractMonitoringProcess<Object> amp = sample();
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
		AbstractMonitoringProcess<Object> amp = sample();
		amp.periodInterval = 10L;
		Assert.assertTrue(amp.isValid());
	}
	
	/**
	 * Test for isValid().
	 */
	@Test
	public void testIsValid_false() {
		AbstractMonitoringProcess<Object> amp = sample();
		amp.periodInterval = 0L;
		Assert.assertFalse(amp.isValid());
	}
	
	

}
