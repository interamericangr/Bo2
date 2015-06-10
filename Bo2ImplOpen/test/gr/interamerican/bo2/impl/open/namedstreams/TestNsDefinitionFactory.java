package gr.interamerican.bo2.impl.open.namedstreams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Test;

/**
 * Test for {@link NsDefinitionFactory}.
 */
public class TestNsDefinitionFactory {
	
	/**
	 * Unit test for getDefinition()
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate() throws InitializationException {
		Properties properties = new Properties();
		String path = "/home/path/file.txt";
		StreamType type = StreamType.INPUTSTREAM;
		StreamResource resource = StreamResourceEnum.FILE;
		int len = 100;	
		String defStr = StringUtils.concatSeparated
			(StringConstants.COMMA, path, type.toString(), resource.toString(), Integer.toString(len));
		String name = "SAMPLE";
		properties.setProperty(name, defStr);
		
		NsDefinitionFactory factory = new NsDefinitionFactory();
		NamedStreamDefinition def = factory.create(name, properties);
		
		
		assertNotNull(def);
		assertEquals(name, def.getName());
		assertEquals(path, def.getUri());
		assertEquals(type, def.getType());
		assertEquals(Bo2UtilsEnvironment.getDefaultTextCharset(), def.getEncoding());
		assertEquals(0, def.getRecordLength());
		assertEquals(resource, def.getResourceType());
		
	}
	
	
	/**
	 * Tests handleOptionalDefinitionElement
	 */
	@Test
	public void testHandleOptionalDefinitionElement_reclen() {
		NsDefinitionFactory factory = new NsDefinitionFactory();
		NamedStreamDefinition nsd = new NamedStreamDefinition();
		int recordLength = 101;
		String attribute = NamedStreamDefinition.RECORD_LENGTH_PREFIX + String.valueOf(recordLength);
		factory.handleOptionalDefinitionElement(nsd, attribute);
		assertEquals(recordLength, nsd.getRecordLength());
	}
	
	/**
	 * Tests handleOptionalDefinitionElement
	 */
	@Test
	public void testHandleOptionalDefinitionElement_encoding() {
		NsDefinitionFactory factory = new NsDefinitionFactory();
		NamedStreamDefinition nsd = new NamedStreamDefinition();
		Charset utf8 = Charset.forName("UTF-8"); //$NON-NLS-1$
		String attribute = NamedStreamDefinition.ENCODING_PREFIX + utf8.name();
		factory.handleOptionalDefinitionElement(nsd, attribute);
		assertEquals(utf8, nsd.getEncoding());
	}

	

}
