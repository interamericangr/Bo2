package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link HttpNsFactory}.
 */
public class TestHttpNsFactory {
	
	/**
	 * Tests <code>openInputStream(def)</code> for a uri.
	 * 
	 * @param uri
	 *        URI to test.
	 *        
	 * @throws CouldNotCreateNamedStreamException
	 */
	void testOpenIS(String uri) throws CouldNotCreateNamedStreamException {		
		HttpNsFactory nsf = new HttpNsFactory();
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setUri(uri);
		InputStream stream = nsf.openInputStream(def);
		Assert.assertNotNull(stream);
	}	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {		
		HttpNsFactory nsf = new HttpNsFactory();
		Assert.assertEquals(StreamResourceEnum.HTTP, nsf.resourceType);
	}
	
	/**
	 * Tests the <code>openInputStream(def)</code>.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testOpenInputStream_valid() throws CouldNotCreateNamedStreamException {
		//String uri = "http://code.google.com/p/bo2/"; //$NON-NLS-1$
		String uri = "https://ino.interamerican.gr"; //$NON-NLS-1$
		testOpenIS(uri);
	}	
	
	/**
	 * Tests the <code>openInputStream(def)</code>.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test(expected=CouldNotCreateNamedStreamException.class)
	public void testOpenInputStream_exception() throws CouldNotCreateNamedStreamException {		
		String uri = "foo.bar.foo"; //$NON-NLS-1$
		testOpenIS(uri);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Creates a sample NamedStreamDefinition.
	 * 
	 * @return Returns the NamedStreamDefinition.
	 */
	NamedStreamDefinition sampleDefinition(StreamType type) {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setName("FooStream");
		def.setRecordLength(100);
		def.setResourceType(StreamResourceEnum.HTTP);
		def.setType(type);
		def.setUri("https://ino.interamerican.gr");
		return def;
	}
	
	
	
	/**
	 * Unit test for create().
	 * 
	 * @param type 
	 * @throws CouldNotCreateNamedStreamException 
	 */	
	public void testCreateWithType(StreamType type) throws CouldNotCreateNamedStreamException {
		HttpNsFactory factory = new HttpNsFactory();
		NamedStreamDefinition def = sampleDefinition(type);
		NamedStream<?> ns = factory.create(def);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = def.getRecordLength();
		if (type==StreamType.BUFFEREDREADER) {
			expectedRecLen = 0;
		}
		Assert.assertEquals(expectedRecLen, ns.getRecordLength());
		Assert.assertEquals(def.getResourceType(), ns.getResourceType());
		Assert.assertEquals(def.getType(), ns.getType());
		Assert.assertNotNull(ns.getStream());		
		Assert.assertNotNull(ns.getResource());
		Assert.assertTrue(ns.getResource() instanceof InputStream);
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test()
	public void testCreate_input() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test()
	public void testCreate_reader() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.BUFFEREDREADER);
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test(expected=CouldNotCreateNamedStreamException.class)
	public void testCreate_output() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test(expected=CouldNotCreateNamedStreamException.class)
	public void testCreate_print() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.PRINTSTREAM);
	}
	
	
	/**
	 * Unit test for create().
	 * 
	 * @param from
	 * @param to
	 *  
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */	
	public void testConvertWithTypes(StreamType from, StreamType to) 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		HttpNsFactory factory = new HttpNsFactory();
		NamedStreamDefinition def = sampleDefinition(from);
		NamedStream<?> ns1 = factory.create(def);
		
		int expectedRecLen = def.getRecordLength();
		if (from==StreamType.BUFFEREDREADER || to==StreamType.BUFFEREDREADER) {
			expectedRecLen = 0;
		}

		String newName = "New Name"; //$NON-NLS-1$
		NamedStream<?> ns2 = factory.convert(ns1, to, newName);
		
		Assert.assertNotNull(ns2);
		Assert.assertEquals(newName, ns2.getName());
		Assert.assertEquals(ns1.getEncoding(), ns2.getEncoding());
		Assert.assertEquals(expectedRecLen, ns2.getRecordLength());
		Assert.assertEquals(to, ns2.getType());
		Assert.assertEquals(ns1.getResourceType(), ns2.getResourceType());
		Assert.assertEquals(ns1.getResource(), ns2.getResource());
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert_InOut() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.INPUTSTREAM, StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert_InPrint() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.INPUTSTREAM, StreamType.PRINTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test()
	public void testConvert_InIn() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.INPUTSTREAM, StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test()
	public void testConvert_InReader() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.INPUTSTREAM, StreamType.BUFFEREDREADER);
	}
	
	
	
	
	
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert_ReaderOut() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.BUFFEREDREADER, StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert_ReaderPrint() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.BUFFEREDREADER, StreamType.PRINTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test()
	public void testConvert_ReaderIn() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.BUFFEREDREADER, StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test()
	public void testConvert_ReaderReader() 
	throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.BUFFEREDREADER, StreamType.BUFFEREDREADER);
	}

}
