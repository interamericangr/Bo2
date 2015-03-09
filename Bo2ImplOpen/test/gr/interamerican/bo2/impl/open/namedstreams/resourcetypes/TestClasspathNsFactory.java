package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ClasspathNsFactory}.
 */
public class TestClasspathNsFactory {
	
	/**
	 * Tests <code>openInputStream(def)</code> for a uri.
	 * 
	 * @param uri
	 *        URI to test.
	 *        
	 * @throws CouldNotCreateNamedStreamException
	 */
	void testOpenIS(String uri) throws CouldNotCreateNamedStreamException {		
		ClasspathNsFactory nsf = new ClasspathNsFactory();
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
		ClasspathNsFactory nsf = new ClasspathNsFactory();
		Assert.assertEquals(StreamResource.CLASSPATH, nsf.resourceType);
	}
	
	/**
	 * Tests the <code>openInputStream(def)</code>.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testOpenInputStream_valid() throws CouldNotCreateNamedStreamException {
		String uri = "/gr/interamerican/bo2/impl/open/namedstreams/resourcetypes/ClasspathNsFactory.class"; //$NON-NLS-1$
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
