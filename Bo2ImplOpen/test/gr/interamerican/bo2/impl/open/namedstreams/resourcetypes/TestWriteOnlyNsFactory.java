package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.OutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * test suite for {@link WriteOnlyNsFactory}.
 */
public class TestWriteOnlyNsFactory {

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resource = StreamResourceEnum.FILE;
		ConcreteWriteOnlyNsFactory nsf = new ConcreteWriteOnlyNsFactory(resource);
		Assert.assertEquals(resource, nsf.resourceType);
	}

	/**
	 * test case for {@link WriteOnlyNsFactory#writer(OutputStream, String, Charset, String)}.
	 */
	@Test
	public void testWriter() {
		StreamResource resourceType = StreamResourceEnum.FILE;
		ConcreteWriteOnlyNsFactory nsf = new ConcreteWriteOnlyNsFactory(resourceType);
		OutputStream stream = Mockito.mock(OutputStream.class);
		String name = "foo"; //$NON-NLS-1$
		Charset charset = Charset.defaultCharset();
		String uri = "File://URI"; //$NON-NLS-1$
		NamedPrintStream nbr = nsf.writer(stream, name, charset, uri);
		Assert.assertEquals(resourceType, nbr.getResourceType());
		Assert.assertEquals(charset, nbr.getEncoding());
		Assert.assertEquals(name, nbr.getName());
		Assert.assertEquals(0, nbr.getRecordLength());
		Assert.assertEquals(StreamType.PRINTSTREAM, nbr.getType());
		Assert.assertEquals(stream, nbr.getResource());
		Assert.assertNotNull(nbr.getStream());
		Assert.assertEquals(uri, nbr.getUri());
	}

	/**
	 * test case for {@link WriteOnlyNsFactory#output(OutputStream, String, int, Charset, String)}.
	 */
	@Test
	public void testOutput() {
		StreamResource resourceType = StreamResourceEnum.FILE;
		ConcreteWriteOnlyNsFactory nsf = new ConcreteWriteOnlyNsFactory(resourceType);
		OutputStream stream = Mockito.mock(OutputStream.class);
		String name = "foo"; //$NON-NLS-1$
		int recordLength = 100;
		Charset charset = Charset.defaultCharset();
		String uri = "File://URI"; //$NON-NLS-1$
		NamedOutputStream nis = nsf.output(stream, name, recordLength, charset, uri);
		Assert.assertEquals(resourceType, nis.getResourceType());
		Assert.assertEquals(charset, nis.getEncoding());
		Assert.assertEquals(name, nis.getName());
		Assert.assertEquals(recordLength, nis.getRecordLength());
		Assert.assertEquals(StreamType.OUTPUTSTREAM, nis.getType());
		Assert.assertEquals(stream, nis.getResource());
		Assert.assertEquals(stream, nis.getStream());
		Assert.assertEquals(uri, nis.getUri());
	}

	/**
	 * A factory for creating ConcreteWriteOnlyNs objects.
	 */
	class ConcreteWriteOnlyNsFactory extends WriteOnlyNsFactory {

		/**
		 * default constructor.
		 *
		 * @param resourceType the resource type
		 */
		public ConcreteWriteOnlyNsFactory(StreamResource resourceType) {
			super(resourceType);
		}

		@Override
		protected OutputStream openOutputStream(NamedStreamDefinition def)
				throws CouldNotCreateNamedStreamException {
			OutputStream stream = Mockito.mock(OutputStream.class);
			return stream;
		}
	}
}
