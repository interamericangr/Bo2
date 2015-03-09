package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;

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
		Assert.assertEquals(StreamResource.HTTP, nsf.resourceType);
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

}
