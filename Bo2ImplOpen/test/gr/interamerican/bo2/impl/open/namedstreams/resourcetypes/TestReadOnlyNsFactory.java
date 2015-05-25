package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unot tests for {@link ReadOnlyNsFactory}.
 */
public class TestReadOnlyNsFactory {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resource = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resource);
		Assert.assertEquals(resource, nsf.resourceType);
	}
	
	/**
	 * Test for <code>input(stream, name, recordLength, charset)</code>
	 */
	@Test
	public void testInput() {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		InputStream stream = Mockito.mock(InputStream.class);
		String name = "foo"; //$NON-NLS-1$
		int recordLength = 100;
		Charset charset = Charset.defaultCharset();
		String uri = "File://URI"; //$NON-NLS-1$
		NamedInputStream nis = nsf.input(stream, name, recordLength, charset, uri);		
		Assert.assertEquals(resourceType, nis.getResourceType());
		Assert.assertEquals(charset, nis.getEncoding());
		Assert.assertEquals(name, nis.getName());
		Assert.assertEquals(recordLength, nis.getRecordLength());
		Assert.assertEquals(StreamType.INPUTSTREAM, nis.getType());
		Assert.assertEquals(stream, nis.getResource());
		Assert.assertEquals(stream, nis.getStream());
		Assert.assertEquals(uri, nis.getUri());
	}
	
	/**
	 * Test for <code>reader(stream, name, charset)</code>.
	 * 
	 * @throws IOException 
	 */
	@Test
	public void testReader() throws IOException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		InputStream stream = Mockito.mock(InputStream.class);
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		String uri = "File://URI"; //$NON-NLS-1$
		NamedBufferedReader nbr = nsf.reader(stream, name, charset,uri);		
		Assert.assertEquals(resourceType, nbr.getResourceType());
		Assert.assertEquals(charset, nbr.getEncoding());
		Assert.assertEquals(name, nbr.getName());
		Assert.assertEquals(0, nbr.getRecordLength());
		Assert.assertEquals(StreamType.BUFFEREDREADER, nbr.getType());
		Assert.assertEquals(stream, nbr.getResource());
		Assert.assertNotNull(nbr.getStream());
		Assert.assertEquals(uri, nbr.getUri());
	}
	
	/**
	 * Test for <code>createNs(stream, def)</code>.
	 * 
	 * @throws IOException 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testCreateNs_reader() throws IOException, CouldNotCreateNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		InputStream stream = Mockito.mock(InputStream.class);
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;
		StreamType type = StreamType.BUFFEREDREADER;
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName(name);
		def.setEncoding(charset);
		def.setRecordLength(recordLength);
		def.setResourceType(resourceType);		
		def.setType(type);
				
		NamedStream<?> ns = nsf.createNs(stream, def);
		
		Assert.assertTrue(ns instanceof NamedBufferedReader);
		NamedBufferedReader nbr = (NamedBufferedReader) ns;		
		Assert.assertEquals(resourceType, nbr.getResourceType());
		Assert.assertEquals(charset, nbr.getEncoding());
		Assert.assertEquals(name, nbr.getName());
		Assert.assertEquals(0, nbr.getRecordLength());
		Assert.assertEquals(type, nbr.getType());
		Assert.assertEquals(stream, nbr.getResource());
		Assert.assertNotNull(nbr.getStream());	
	}
	
	/**
	 * Test for <code>createNs(stream, def)</code>.
	 * 
	 * @throws IOException 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testCreateNs_input() throws IOException, CouldNotCreateNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		InputStream stream = Mockito.mock(InputStream.class);
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;
		StreamType type = StreamType.INPUTSTREAM;
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName(name);
		def.setEncoding(charset);
		def.setRecordLength(recordLength);
		def.setResourceType(resourceType);		
		def.setType(type);
				
		NamedStream<?> ns = nsf.createNs(stream, def);
		
		Assert.assertTrue(ns instanceof NamedInputStream);
		NamedInputStream nis = (NamedInputStream) ns;		
		Assert.assertEquals(resourceType, nis.getResourceType());
		Assert.assertEquals(charset, nis.getEncoding());
		Assert.assertEquals(name, nis.getName());
		Assert.assertEquals(recordLength, nis.getRecordLength());
		Assert.assertEquals(type, nis.getType());
		Assert.assertEquals(stream, nis.getResource());
		Assert.assertEquals(stream, nis.getStream());
	}
	
	/**
	 * Test for <code>createNs(stream, def)</code>.
	 * 
	 * @throws IOException 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test(expected=CouldNotCreateNamedStreamException.class)
	public void testCreateNs_invalid() throws IOException, CouldNotCreateNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		InputStream stream = Mockito.mock(InputStream.class);
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;
		StreamType type = StreamType.PRINTSTREAM;
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName(name);
		def.setEncoding(charset);
		def.setRecordLength(recordLength);
		def.setResourceType(resourceType);		
		def.setType(type);				
		nsf.createNs(stream, def);
	}
	
	/**
	 * Test for <code>createNs(def)</code>.
	 * 
	 * @throws IOException 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testCreateNs_def() throws IOException, CouldNotCreateNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;
		StreamType type = StreamType.INPUTSTREAM;
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName(name);
		def.setEncoding(charset);
		def.setRecordLength(recordLength);
		def.setResourceType(resourceType);		
		def.setType(type);				
		NamedStream<?> ns = nsf.createNs(def);
		
		Assert.assertTrue(ns instanceof NamedInputStream);
		NamedInputStream nis = (NamedInputStream) ns;		
		Assert.assertEquals(resourceType, nis.getResourceType());
		Assert.assertEquals(charset, nis.getEncoding());
		Assert.assertEquals(name, nis.getName());
		Assert.assertEquals(recordLength, nis.getRecordLength());
		Assert.assertEquals(type, nis.getType());
		Assert.assertNotNull(nis.getStream());
		Assert.assertEquals(nis.getStream(), nis.getResource());		
	}
	
	/**
	 * Test for <code>create(def)</code>.
	 * 
	 * @throws IOException 
	 * @throws CouldNotCreateNamedStreamException 
	 */
	@Test
	public void testCreate() throws CouldNotCreateNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		
		String name = "foo"; //$NON-NLS-1$		
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;
		StreamType type = StreamType.INPUTSTREAM;
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName(name);
		def.setEncoding(charset);
		def.setRecordLength(recordLength);
		def.setResourceType(resourceType);		
		def.setType(type);				
		NamedStream<?> ns = nsf.create(def);
		
		Assert.assertTrue(ns instanceof NamedInputStream);
		NamedInputStream nis = (NamedInputStream) ns;		
		Assert.assertEquals(resourceType, nis.getResourceType());
		Assert.assertEquals(charset, nis.getEncoding());
		Assert.assertEquals(name, nis.getName());
		Assert.assertEquals(recordLength, nis.getRecordLength());
		Assert.assertEquals(type, nis.getType());
		Assert.assertNotNull(nis.getStream());
		Assert.assertEquals(nis.getStream(), nis.getResource());		
	}
	
	/**
	 * Test for <code>convert(ns1, newType, newName)</code>. 
	 * 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test
	public void testConvert_valid() throws CouldNotConvertNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);
		
		String name = "foo"; //$NON-NLS-1$
		String uri = "bar"; //$NON-NLS-1$
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;		
		InputStream stream = Mockito.mock(InputStream.class);		
		NamedStream<?> ns1 = new NamedInputStream(resourceType, stream, name, recordLength, stream, charset, uri);
		
		String newName = "bar"; //$NON-NLS-1$
		StreamType newType = StreamType.BUFFEREDREADER;
				
		NamedStream<?> ns2 = nsf.convert(ns1, newType, newName);
		
		Assert.assertTrue(ns2 instanceof NamedBufferedReader);
		NamedBufferedReader nbr = (NamedBufferedReader) ns2;		
		Assert.assertEquals(resourceType, nbr.getResourceType());
		Assert.assertEquals(charset, nbr.getEncoding());
		Assert.assertEquals(newName, nbr.getName());
		Assert.assertEquals(0, nbr.getRecordLength());
		Assert.assertEquals(newType, nbr.getType());
		Assert.assertNotNull(nbr.getStream());				
		Assert.assertTrue(nbr.getResource() instanceof InputStream);
		Assert.assertEquals(uri, nbr.getUri());
	}
	
	
	/**
	 * Test for <code>convert(ns1, newType, newName)</code>. 
	 * 
	 * @throws CouldNotConvertNamedStreamException 
	 */
	@Test(expected=CouldNotConvertNamedStreamException.class)
	public void testConvert_invalid() throws CouldNotConvertNamedStreamException {
		StreamResource resourceType = StreamResource.FILE;
		ConcreteReadOnlyNsFactory nsf = new ConcreteReadOnlyNsFactory(resourceType);		
		String name = "foo"; //$NON-NLS-1$
		String uri = "bar"; //$NON-NLS-1$
		Charset charset = Charset.defaultCharset();
		int recordLength = 100;		
		InputStream stream = Mockito.mock(InputStream.class);		
		NamedStream<?> ns1 = new NamedInputStream(resourceType, stream, name, recordLength, stream, charset,uri);		
		String newName = "bar"; //$NON-NLS-1$
		StreamType newType = StreamType.PRINTSTREAM;
		nsf.convert(ns1, newType, newName);
		
	}
	
	
	
	
	
	
	
	
	/**
	 * Concrete implementation of ReadOnlyNsFactory.
	 */
	class ConcreteReadOnlyNsFactory extends ReadOnlyNsFactory {
		public ConcreteReadOnlyNsFactory(StreamResource resourceType) {
			super(resourceType);
		}

		@Override
		protected InputStream openInputStream(NamedStreamDefinition def)
		throws CouldNotCreateNamedStreamException {
			InputStream stream = Mockito.mock(InputStream.class);
			return stream;
		}		
	}

}
