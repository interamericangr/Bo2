package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.utils.doc.BusinessDocument;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestMetaUtils {
	
	/**
	 * Test for insertAt().
	 * @throws Exception 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testGetProperty() throws Exception {
		OdfToolkitEngine engine = new OdfToolkitEngine();
		String templatePath = ResourceUtils.inputPath("DocTestGetProperty.odt");
		String propertyName = "Table1.minLength";		
		BusinessDocument template = engine.openDocument(templatePath);				
		Object expected = "30";
		Object actual = template.getProperty(propertyName);
		Assert.assertEquals(expected, actual);

	}

}
