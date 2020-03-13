package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedOutputStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

/**
 * Unit tests for {@link ByteNsFactory}.
 */
public class TestByteNsFactory {

	/**
	 * Creates a sample NamedStreamDefinition.
	 *
	 * @param type
	 *            the type
	 * @return Returns the NamedStreamDefinition.
	 */
	NamedStreamDefinition sampleDefinition(StreamType type) {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setName("FooStream"); //$NON-NLS-1$
		def.setRecordLength(100);
		def.setResourceType(StreamResourceEnum.BYTES);
		def.setType(type);
		def.setUri(null);
		return def;
	}

	/**
	 * Unit test for create().
	 *
	 * @param type
	 *            the type
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	public void testCreateWithType(StreamType type) throws CouldNotCreateNamedStreamException {
		ByteNsFactory factory = new ByteNsFactory();
		NamedStreamDefinition def = sampleDefinition(type);
		NamedStream<?> ns = factory.create(def);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = def.getRecordLength();
		if (type == StreamType.PRINTSTREAM || type == StreamType.BUFFEREDREADER) {
			expectedRecLen = 0;
		}
		Assert.assertEquals(expectedRecLen, ns.getRecordLength());
		Assert.assertEquals(def.getResourceType(), ns.getResourceType());
		Assert.assertEquals(def.getType(), ns.getType());
		Assert.assertNotNull(ns.getStream());
		Assert.assertNotNull(ns.getResource());
		Assert.assertTrue(ns.getResource() instanceof ByteArrayOutputStream);
	}

	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	@Test(expected = CouldNotCreateNamedStreamException.class)
	public void testCreate_input() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.INPUTSTREAM);
	}

	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	@Test(expected = CouldNotCreateNamedStreamException.class)
	public void testCreate_reader() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.BUFFEREDREADER);
	}

	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	@Test()
	public void testCreate_output() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 */
	@Test()
	public void testCreate_print() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.PRINTSTREAM);
	}

	/**
	 * Unit test for create().
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	public void testConvertWithTypes(StreamType from, StreamType to)
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		ByteNsFactory factory = new ByteNsFactory();
		NamedStreamDefinition def = sampleDefinition(from);
		NamedStream<?> ns1 = factory.create(def);

		int expectedRecLen = def.getRecordLength();
		if (from == StreamType.PRINTSTREAM || from == StreamType.BUFFEREDREADER || to == StreamType.PRINTSTREAM
				|| to == StreamType.BUFFEREDREADER) {
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
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutOut() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutPrint() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.PRINTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutIn() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.INPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutReader() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.BUFFEREDREADER);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintOut() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.OUTPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
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
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintIn() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.INPUTSTREAM);
	}

	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException
	 *             the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintReader()
			throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.BUFFEREDREADER);
	}

	/**
	 * Unit test for createWithBytes().
	 *
	 * @param type
	 *            the type
	 * @param bytes
	 *            the bytes
	 * @return Returns the NamedStream created.
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws DataOperationNotSupportedException
	 *             the data operation not supported exception
	 */
	public NamedStream<?> testCreateWithBytesAndType(StreamType type, byte[] bytes)
			throws CouldNotCreateNamedStreamException, DataOperationNotSupportedException {

		ByteNsFactory factory = new ByteNsFactory();
		NamedStreamDefinition def = sampleDefinition(type);

		NamedStream<?> ns = factory.createWithBytes(def, bytes);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = def.getRecordLength();
		if (type == StreamType.PRINTSTREAM || type == StreamType.BUFFEREDREADER) {
			expectedRecLen = 0;
		}
		Assert.assertEquals(expectedRecLen, ns.getRecordLength());
		Assert.assertEquals(def.getResourceType(), ns.getResourceType());
		Assert.assertEquals(def.getType(), ns.getType());
		Assert.assertNotNull(ns.getStream());
		Assert.assertNotNull(ns.getResource());
		Assert.assertTrue(ns.getResource() instanceof ByteArrayOutputStream);

		return ns;
	}

	/**
	 * Unit test for createWithBytes(def,bytes).
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws DataOperationNotSupportedException
	 *             the data operation not supported exception
	 * @throws DataException
	 *             the data exception
	 */
	@Test
	public void testCreateWithBytes_Input()
			throws CouldNotCreateNamedStreamException, DataOperationNotSupportedException, DataException {
		StreamType type = StreamType.INPUTSTREAM;
		String foo = "foo"; //$NON-NLS-1$
		byte[] bytes = foo.getBytes();
		NamedInputStream ns = (NamedInputStream) testCreateWithBytesAndType(type, bytes);

		String actual = ns.readString().trim();
		Assert.assertEquals(actual, foo);
	}

	/**
	 * Unit test for createWithBytes(def,bytes).
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws DataOperationNotSupportedException
	 *             the data operation not supported exception
	 */
	@Test
	public void testCreateWithBytes_Reader()
			throws CouldNotCreateNamedStreamException, DataOperationNotSupportedException {
		StreamType type = StreamType.BUFFEREDREADER;
		String foo = "foo"; //$NON-NLS-1$
		byte[] bytes = foo.getBytes();
		NamedBufferedReader ns = (NamedBufferedReader) testCreateWithBytesAndType(type, bytes);
		InitializedByteArrayOutputStream baos = (InitializedByteArrayOutputStream) ns.getResource();
		byte[] bytearray = baos.toByteArray();
		Assert.assertArrayEquals(bytes, bytearray);
	}

	/**
	 * Unit test for createWithBytes(def,bytes).
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws DataOperationNotSupportedException
	 *             the data operation not supported exception
	 */
	@Test
	public void testCreateWithBytes_Output()
			throws CouldNotCreateNamedStreamException, DataOperationNotSupportedException {
		StreamType type = StreamType.OUTPUTSTREAM;
		String foo = "foo"; //$NON-NLS-1$
		byte[] bytes = foo.getBytes();
		NamedOutputStream ns = (NamedOutputStream) testCreateWithBytesAndType(type, bytes);
		InitializedByteArrayOutputStream baos = (InitializedByteArrayOutputStream) ns.getResource();
		byte[] bytearray = baos.toByteArray();
		Assert.assertArrayEquals(bytes, bytearray);
	}

	/**
	 * Unit test for createWithBytes(def,bytes).
	 *
	 * @throws CouldNotCreateNamedStreamException
	 *             the could not create named stream exception
	 * @throws DataOperationNotSupportedException
	 *             the data operation not supported exception
	 */
	@Test
	public void testCreateWithBytes_Print()
			throws CouldNotCreateNamedStreamException, DataOperationNotSupportedException {
		StreamType type = StreamType.PRINTSTREAM;
		String foo = "foo"; //$NON-NLS-1$
		byte[] bytes = foo.getBytes();
		NamedPrintStream ns = (NamedPrintStream) testCreateWithBytesAndType(type, bytes);
		InitializedByteArrayOutputStream baos = (InitializedByteArrayOutputStream) ns.getResource();
		byte[] bytearray = baos.toByteArray();
		Assert.assertArrayEquals(bytes, bytearray);
	}
}