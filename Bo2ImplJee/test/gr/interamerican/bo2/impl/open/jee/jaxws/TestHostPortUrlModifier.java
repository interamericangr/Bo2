package gr.interamerican.bo2.impl.open.jee.jaxws;


import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link HostPortUrlModifier}
 */
public class TestHostPortUrlModifier {
	
	/**
	 * test execute
	 * @throws MalformedURLException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute() throws MalformedURLException {
		HostPortUrlModifier mod = new HostPortUrlModifier("127.0.0.1:8080");
		Assert.assertEquals("127.0.0.1", mod.host);
		Assert.assertEquals(8080, mod.port);
		
		URL u = new URL("http://host/file");
		String expected = "http://127.0.0.1:8080/file";
		String actual = mod.execute(u).toExternalForm();
		Assert.assertEquals(expected, actual);
		
		//no port
		mod = new HostPortUrlModifier("127.0.0.1");
		Assert.assertEquals("127.0.0.1", mod.host);
		Assert.assertEquals(80, mod.port);
		
		expected = "http://127.0.0.1:80/file";
		actual = mod.execute(u).toExternalForm();
		Assert.assertEquals(expected, actual);
		
	}

}
