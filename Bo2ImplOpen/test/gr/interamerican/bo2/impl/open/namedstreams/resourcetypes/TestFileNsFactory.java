package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import static gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition.*;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.utils.SystemUtils;

/**
 * Unit tests for {@link FileNsFactory}.
 * 
 */
public class TestFileNsFactory {
	
	/**
	 * 
	 */
	@Rule
	public TemporaryFolder tempDirectory = new TemporaryFolder();
	
	/**
	 * Creates a sample NamedStreamDefinition.
	 *
	 * @param type the type
	 * @return Returns the NamedStreamDefinition.
	 */
	@SuppressWarnings("finally")
	NamedStreamDefinition sampleDefinition(StreamType type) {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setEncoding(Charset.defaultCharset());
		def.setName("FooStream"); //$NON-NLS-1$
		def.setRecordLength(100);
		def.setResourceType(StreamResourceEnum.FILE);
		def.setType(type);
		try {
			File tempFile = tempDirectory.newFile();
			def.setUri(tempFile.getPath());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		return def;
	}
	}
	
	/**
	 * Unit test for create().
	 *
	 * @param type the type
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */	
	public void testCreateWithType(StreamType type) throws CouldNotCreateNamedStreamException {
		FileNsFactory factory = new FileNsFactory();
		NamedStreamDefinition def = sampleDefinition(type);
		NamedStream<?> ns = factory.create(def);
		Assert.assertNotNull(ns);
		Assert.assertEquals(def.getName(), ns.getName());
		Assert.assertEquals(def.getEncoding(), ns.getEncoding());
		int expectedRecLen = def.getRecordLength();
		if (type==StreamType.PRINTSTREAM || type==StreamType.BUFFEREDREADER) {
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
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test()
	public void testCreate_input() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test()
	public void testCreate_reader() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.BUFFEREDREADER);
	}
	
	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test()
	public void testCreate_output() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for create().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 */
	@Test()
	public void testCreate_print() throws CouldNotCreateNamedStreamException {
		testCreateWithType(StreamType.PRINTSTREAM);
	}
	
	/**
	 * Unit test for create().
	 *
	 * @param from the from
	 * @param to the to
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */	
	public void testConvertWithTypes(StreamType from, StreamType to) throws CouldNotCreateNamedStreamException,
			CouldNotConvertNamedStreamException {
		FileNsFactory factory = new FileNsFactory();
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
		Assert.assertEquals(ns1.getUri(), ns2.getUri());
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutOut() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutPrint() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.PRINTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutIn() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_OutReader() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.OUTPUTSTREAM, StreamType.BUFFEREDREADER);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintOut() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.OUTPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintPrint() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.PRINTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintIn() throws CouldNotCreateNamedStreamException, CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.INPUTSTREAM);
	}
	
	/**
	 * Unit test for convert().
	 *
	 * @throws CouldNotCreateNamedStreamException the could not create named stream exception
	 * @throws CouldNotConvertNamedStreamException the could not convert named stream exception
	 */
	@Test()
	public void testConvert_PrintReader() throws CouldNotCreateNamedStreamException,
			CouldNotConvertNamedStreamException {
		testConvertWithTypes(StreamType.PRINTSTREAM, StreamType.BUFFEREDREADER);
	}	
	
	/**
	 * Tests currentTimestamp.
	 */
	@Test
	public void testCurrentTimestamp() {
		FileNsFactory factory = new FileNsFactory();
		String tmstmp = factory.currentTimestamp();
		Assert.assertEquals(tmstmp.length(), 14);
	}
	
	/**
	 * Tests currentDate.
	 */
	@Test
	public void testCurrentDate() {
		FileNsFactory factory = new FileNsFactory();
		String date = factory.currentDate();
		Assert.assertEquals(date.length(), 8);
	}
	
	/**
	 * Tests fileUri.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFileUri() {
		FileNsFactory factory = new FileNsFactory();
		
		String definitionUri = "/foo/<DATE>/bar-<TIMESTAMP>.txt";
		String result = factory.fileUriModification(definitionUri);
		Assert.assertFalse(result.contains(DATE));
		Assert.assertFalse(result.contains(TIMESTAMP));
		if (SystemUtils.isWindows()) {
			Assert.assertTrue(result.startsWith("C:"));
		}
	}	
	
}
