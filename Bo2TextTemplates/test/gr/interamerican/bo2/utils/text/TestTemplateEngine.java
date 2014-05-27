package gr.interamerican.bo2.utils.text;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.TemplateException;
import gr.interamerican.bo2.samples.test.Address;
import gr.interamerican.bo2.samples.test.PersonBean;
import gr.interamerican.bo2.utils.text.TemplateEngine;

/**
 * Unit tests for {@link TemplateEngine}.
 */
public class TestTemplateEngine {
	
	

	/**
	 * Test method for {@link gr.interamerican.bo2.utils.text.TemplateEngine#fill(java.lang.String, java.lang.Object)}.
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFill() throws IOException, TemplateException {
		PersonBean bean = new PersonBean();
		bean.setFirstName("John");
		bean.setLastName("Wayne");
		String occupation = "occupation";
		String actor = "actor";
		bean.getAttributes().put(occupation, actor);
		
		Address address = new Address();
		address.setStreet("10th Avenue");
		address.setCity("New York");
		address.setNumber("36b");
		
		bean.setAddress(address);
		
		String template = 
			"${firstName} ${lastName} is an ${attributes.occupation}. " +
		    "He lives in ${address.city}. His address is ${address}.";
		
		String actual = TemplateEngine.fill(template, bean);
		
		String expected = 
			"John Wayne is an actor. He lives in New York. His address is 10th Avenue,36b,New York.";
		
		Assert.assertEquals(expected, actual);
		
	}

}
