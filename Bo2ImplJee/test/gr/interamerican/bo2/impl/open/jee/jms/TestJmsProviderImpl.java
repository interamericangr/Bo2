package gr.interamerican.bo2.impl.open.jee.jms;

import gr.interamerican.bo2.arch.exceptions.InitializationException;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link JmsProviderImpl} tests.
 */
public class TestJmsProviderImpl {
	
	/**
	 * test getAttributes.
	 *
	 * @throws InitializationException the initialization exception
	 */
	@SuppressWarnings("nls")
	@Test(expected=InitializationException.class)
	public void testGetAttributes_empty() throws InitializationException {
		JmsProviderImpl subject = new JmsProviderImpl(new Properties());
		subject.getAttributes("name");
	}
	
	/**
	 * test getAttributes.
	 *
	 * @throws InitializationException the initialization exception
	 */
	@SuppressWarnings("nls")
	@Test(expected=InitializationException.class)
	public void testGetAttributes_invalid() throws InitializationException {
		Properties p = new Properties();
		p.setProperty("name", "1,2,3");
		JmsProviderImpl subject = new JmsProviderImpl(p);
		subject.getAttributes("name");
	}
	
	/**
	 * test getAttributes.
	 *
	 * @throws InitializationException the initialization exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetAttributes_valid() throws InitializationException {
		Properties p = new Properties();
		p.setProperty("name", "1,2");
		JmsProviderImpl subject = new JmsProviderImpl(p);
		String[] attributes = subject.getAttributes("name");
		
		Assert.assertTrue(attributes.length==2);
	}

}
