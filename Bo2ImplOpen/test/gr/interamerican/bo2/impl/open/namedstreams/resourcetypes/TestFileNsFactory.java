package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unot tests for {@link FileNsFactory}.
 */
public class TestFileNsFactory {


	/**
	 * Creates a sample NamedStreamDefinition.
	 *
	 * @param type
	 *
	 * @return Returns the NamedStreamDefinition.
	 */
	NamedStreamDefinition sampleDefinition(StreamType type) {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setName("FooStream"); //$NON-NLS-1$
		def.setRecordLength(100);
		def.setResourceType(StreamResource.FILE);
		def.setType(type);
		def.setUri("/tmp/file.txt");// TODO changed to work on linux also, create a folder /tmp on windows
		return def;
	}



	/**
	 * Unit test for create().
	 *
	 * @param type
	 * @throws CouldNotCreateNamedStreamException
	 */
	public void testCreateWithType(StreamType type) throws CouldNotCreateNamedStreamException {
		FileNsFactory factory = new FileNsFactory();
		NamedStreamDefinition def = sampleDefinition(type);
		NamedStream<?> ns = factory.create(def);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = def.getRecordLength();
		if ((type==StreamType.PRINTSTREAM) || (type==StreamType.BUFFEREDREADER)) {
			expectedRecLen = 0;
		}
		Assert.assertEquals(expectedRecLen, ns.getRecordLength());
		Assert.assertEquals(def.getResourceType(), ns.getResourceType());
		Assert.assertEquals(def.getType(), ns.getType());
		Assert.assertNotNull(ns.getStream());
		Assert.assertNotNull(ns.getResource());
		Assert.assertTrue(ns.getResource() instanceof File);
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
	@Test()
	public void testCreate_output() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 */
	@Test()
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
		FileNsFactory factory = new FileNsFactory();
		NamedStreamDefinition def = sampleDefinition(from);
		NamedStream<?> ns1 = factory.create(def);

		int expectedRecLen = def.getRecordLength();
		if ((from==StreamType.PRINTSTREAM) || (from==StreamType.BUFFEREDREADER) ||
				(to==StreamType.PRINTSTREAM) || (to==StreamType.BUFFEREDREADER)) {
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
		Assert.assertEquals(ns1.getUri(), ns2.getUri());
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_OutOut()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_OutPrint()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.PRINTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_OutIn()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.INPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_OutReader()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.BUFFEREDREADER);
	}







	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_PrintOut()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_PrintPrint()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.PRINTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_PrintIn()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.INPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 * @throws CouldNotConvertNamedStreamException
	 */
	@Test()
	public void testConvert_PrintReader()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.BUFFEREDREADER);
	}



}
