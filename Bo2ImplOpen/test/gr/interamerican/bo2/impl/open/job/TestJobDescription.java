package gr.interamerican.bo2.impl.open.job;

import gr.interamerican.bo2.impl.open.creation.Factory;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestJobDescription {
	
	/**
	 * 
	 */
	@Test
	public void testSynchronous() {
		JobDescription subject = Factory.create(JobDescription.class);
		Assert.assertFalse(subject.isSynchronous());
		subject.setSynchronous(true);
		Assert.assertTrue(subject.isSynchronous());
	}

}
