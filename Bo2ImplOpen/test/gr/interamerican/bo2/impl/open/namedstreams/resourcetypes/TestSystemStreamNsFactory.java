package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.PrintStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link SystemStreamNsFactory}.
 */
@SuppressWarnings("nls")
public class TestSystemStreamNsFactory {
	
	/**
	 * Unit test for create().
	 * 
	 * @param uri 
	 * 
	 * @return Returns the NamedStream.
	 * 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	public NamedStream<?> testCreate(String uri) throws CouldNotCreateNamedStreamException {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setName("FooStream"); //$NON-NLS-1$
		def.setRecordLength(100);
		def.setResourceType(StreamResourceEnum.SYSTEM);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(uri); 
		
		SystemStreamNsFactory factory = new SystemStreamNsFactory();
		
		NamedStream<?> ns = factory.create(def);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = 0;
		Assert.assertEquals(expectedRecLen, ns.getRecordLength());
		Assert.assertEquals(def.getResourceType(), ns.getResourceType());
		Assert.assertEquals(def.getType(), ns.getType());
		Assert.assertNotNull(ns.getStream());		
		Assert.assertNotNull(ns.getResource());
		Assert.assertTrue(ns.getResource() instanceof PrintStream);
		
		return ns;
	}

	/**
	 * Unit test for create().
	 * 
	 * @param type 
	 * @throws CouldNotCreateNamedStreamException 
	 */	
	@Test
	public void testCreate_sysout() throws CouldNotCreateNamedStreamException {
		testCreate("sysout");
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @param type 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testCreate_syserr() throws CouldNotCreateNamedStreamException {
		testCreate("syserr");
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @param type 
	 * @throws CouldNotCreateNamedStreamException 
	 */	
	@Test(expected=CouldNotCreateNamedStreamException.class)
	public void testCreate_invalid() throws CouldNotCreateNamedStreamException {
		testCreate("/home/temp/foo");
	}
	
	/**
	 * Unit test for create().
	 * 
	 * @param type 
	 * @throws CouldNotConvertNamedStreamException 
	 * @throws CouldNotCreateNamedStreamException 
	 */	
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert() 
	throws CouldNotConvertNamedStreamException, CouldNotCreateNamedStreamException {
		NamedStream<?> ns = testCreate("sysout");
		SystemStreamNsFactory factory = new SystemStreamNsFactory();
		factory.convert(ns, StreamType.PRINTSTREAM, "NewFoo");		
	}
	

}
