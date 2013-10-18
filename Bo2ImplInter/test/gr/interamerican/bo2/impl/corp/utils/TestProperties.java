package gr.interamerican.bo2.impl.corp.utils;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * 
 */
public class TestProperties {
	
	
	/**
	 * Implementation to test
	 */
	Properties properties = new Properties();
	
	/**
	 * Path for deployment properties.
	 */
	private static final String TEST_PROPERTIES_PATH = 
		"/gr/interamerican/bo2/impl/corp/utils/test.properties"; //$NON-NLS-1$
	
	
	/**
	 * Test load
	 * @throws IOException
	 */
	@Test
	public void testLoad() throws IOException{
		
		InputStream stream = UtilityForBo2Test.class.getResourceAsStream(TEST_PROPERTIES_PATH);
		properties.load(stream);
		String value = "ελληνικοί χαρακτήρες"; //$NON-NLS-1$
		assertEquals(value,properties.getProperty("VALUE")); //$NON-NLS-1$
	}
	
	
	/**
	 * Test getCharacterEncoding
	 */
	@Test
	public void testGetCharacterEncoding(){
		String expected = System.getProperty("file.encoding").trim(); //$NON-NLS-1$
		String actual = properties.getCharacterEncoding();
		assertEquals(expected,actual);
	}
	
	/**
	 * Test setCharacterEncoding
	 */
	@Test
	public void testSetCharacterEncoding(){
		properties.setCharacterEncoding("greek"); //$NON-NLS-1$
		String expected = "greek"; //$NON-NLS-1$
		String actual = properties.getCharacterEncoding();
		assertEquals(expected,actual);
	}
	
}
