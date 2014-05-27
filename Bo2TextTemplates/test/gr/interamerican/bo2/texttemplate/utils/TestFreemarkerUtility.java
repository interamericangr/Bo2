package gr.interamerican.bo2.texttemplate.utils;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.TemplateException;

/**
 * 
 */
public class TestFreemarkerUtility {
	
	/**
	 * 
	 */
	String fisrtName = "James"; //$NON-NLS-1$
	/**
	 * 
	 */
	String lastName = "Gosling"; //$NON-NLS-1$
	/**
	 * 
	 */
	String addressStr = "Doiranis 240"; //$NON-NLS-1$

	/**
	 * Test method for {@link gr.interamerican.bo2.texttemplate.utils.FreemarkerUtility#fix(java.lang.String, java.lang.Object)}.
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	@Test
	public void testFix() throws IOException, TemplateException {
		
		String template = "Hello ${firstName}, ${lastName}. Your address is ${address.street}"; //$NON-NLS-1$
		TestPersonBean bean = new TestPersonBean();
		bean.setFirstName(fisrtName);
		bean.setLastName(lastName);
		TestAddress address = new TestAddress();
		address.setStreet(addressStr);
		bean.setAddress(address);
		
		String parsedText = FreemarkerUtility.fix(template, bean);
		
		Assert.assertTrue(parsedText.contains(fisrtName));
		Assert.assertTrue(parsedText.contains(lastName));
		Assert.assertTrue(parsedText.contains(addressStr));
		
	}

}
