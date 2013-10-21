/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.namedstreams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.implopen.mocks.MockAbstractNamedStreamsManager;
import gr.interamerican.bo2.samples.implopen.mocks.MockFailingAbstractNamedStreamsManager;
import gr.interamerican.bo2.samples.implopen.mocks.MockNamedStream;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractNamedStreamsManager}
 *
 */
@SuppressWarnings("nls")
public class TestAbstractNamedStreamsManager {
	
	/**
	 * Clear the registry.
	 */
	@Before @After
	public void cleanupRegistry() {
		SharedNamedStreamsRegistry.name2stream.clear();
		SharedNamedStreamsRegistry.stream2name.clear();
		SharedNamedStreamsRegistry.providersAccessingStream.clear();
	}
	
	
	/**
	 * Unit test for getDefinition()
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefinition_succeed() throws InitializationException {
		Properties properties = new Properties();
		String path = "/home/path/file.txt";
		StreamType type = StreamType.INPUTSTREAM;
		String resourceType = "File";
		int len = 100;	
		String defStr = StringUtils.concatSeparated
			(StringConstants.COMMA, path, type.toString(), resourceType, Integer.toString(len));
		String name = "SAMPLE";
		properties.setProperty(name, defStr);		
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);
		
		NamedStreamDefinition def = impl.getDefinition(name);
		assertNotNull(def);
		assertEquals(name, def.getName());
		assertEquals(path, def.getUri());
		assertEquals(type, def.getType());
		assertEquals(Bo2UtilsEnvironment.getDefaultTextCharset(), def.getEncoding());
		assertEquals(0, def.getRecordLength());
		
	}
	
	/**
	 * Unit test for getDefinition()
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)
	public void testGetDefinition_failWithNoPath() throws InitializationException {
		Properties properties = new Properties();		
		String name = "SAMPLE";
		properties.setProperty(name, StringConstants.EMPTY);		
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);		
		@SuppressWarnings("unused")
		NamedStreamDefinition def = impl.getDefinition(name);
	}
	
	/**
	 * Unit test for getDefinition()
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)	
	public void testGetDefinition_failWithNoType() throws InitializationException {
		Properties properties = new Properties();		
		String name = "SAMPLE";
		properties.setProperty(name, "/home/folder/path.txt");		
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);		
		@SuppressWarnings("unused")
		NamedStreamDefinition def = impl.getDefinition(name);
	}
	
	/**
	 * Unit test for getDefinition()
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)	
	public void testGetDefinition_failWithNoResourceType() throws InitializationException {
		Properties properties = new Properties();		
		String name = "SAMPLE";
		properties.setProperty(name, "/home/folder/path.txt,Inputstream");		
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);		
		@SuppressWarnings("unused")
		NamedStreamDefinition def = impl.getDefinition(name);
	}
	
	/**
	 * Tests getStream() with the stream not defined in the properties file.
	 * 
	 * @throws InitializationException
	 */
	@Test(expected=InitializationException.class)
	public void testGetStream_failStreamNotDefined() throws InitializationException {
		Properties properties = new Properties();
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);
		@SuppressWarnings("unused")
		NamedStream<?> ns = impl.getStream("NOFILE");
	}	

	/**
	 * Tests getStream() fail due to failure on stream open.
	 * 
	 * @throws InitializationException
	 */
	@Test(expected=InitializationException.class)
	public void testGetStream_failOnOpen() throws InitializationException {
		Properties properties = new Properties();		
		String name = "SAMPLE";
		String defStr = "/home/path/file,notype,0";
		properties.setProperty(name, defStr);
		AbstractNamedStreamsManager impl = new MockFailingAbstractNamedStreamsManager(properties);		 
		@SuppressWarnings("unused")
		NamedStream<?> ns = impl.getStream(name);
	}	
	
	/**
	 * Tests getStream() fail due to failure on stream open.
	 * 
	 * @throws InitializationException
	 */
	@Test()
	public void testGetStream_suceed() throws InitializationException {
		Properties properties = UtilityForBo2Test.getLocalFsProperties();
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);
		String name = "SAMPLE";
		String defStr = "sysout,PrintStream,system,0";
		properties.setProperty(name, defStr);		
		NamedStream<?> ns = impl.getStream(name);
		Assert.assertNotNull(ns);
	}	
	
	/**
	 * Tests getSharedNamedStream()
	 * 
	 * @throws InitializationException
	 */
	@Test()
	public void testGetSharedStream_suceed() throws InitializationException {
		Properties properties = UtilityForBo2Test.getLocalFsProperties();
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);
		String name = "SAMPLE";
		String defStr = "sysout,PrintStream,system,0";
		properties.setProperty(name, defStr);		
		NamedStream<?> ns = impl.getSharedStream(name);
		Assert.assertNotNull(ns);
		Assert.assertEquals(ns, SharedNamedStreamsRegistry.name2stream.get(name));
		Assert.assertEquals(name, SharedNamedStreamsRegistry.stream2name.get(ns));
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.size()==1);
	}	
	
	/**
	 * Tests getStream() with due to unknown stream type.
	 * 
	 * @throws InitializationException
	 */
	@Test(expected=InitializationException.class)
	public void testGetStream_failWithUnknownType() throws InitializationException {
		Properties properties = new Properties();		
		String name = "SAMPLE";
		String defStr = "/home/path/file,notype,0";
		properties.setProperty(name, defStr);
		AbstractNamedStreamsManager impl = new MockAbstractNamedStreamsManager(properties);
		@SuppressWarnings("unused")
		NamedStream<?> ns = impl.getStream(name);
	}	
	
	/**
	 * tests register
	 * @throws InitializationException 
	 */
	@Test
	public void testRegister() throws InitializationException{
		Properties properties = new Properties();
		AbstractNamedStreamsManager impl = 
			new MockAbstractNamedStreamsManager(properties);
		NamedStream<PrintStream> ns = 
			new MockNamedStream<PrintStream>(StreamType.PRINTSTREAM, StreamResource.SYSTEM, System.out, "Sysout_alt", 0, Bo2UtilsEnvironment.getDefaultTextCharset());
		impl.registerStream(ns);
		NamedStream<?> actual = impl.getStream(ns.getName());
		assertSame(ns,actual);
	}
	
	/**
	 * tests register
	 * @throws InitializationException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testRegisterSharedStream() throws InitializationException, InterruptedException{
		Properties properties = new Properties();
		final AbstractNamedStreamsManager impl = 
			new MockAbstractNamedStreamsManager(properties);
		
		final String name = "Sysout_alt";
		NamedStream<PrintStream> ns = 
			new MockNamedStream<PrintStream>(StreamType.PRINTSTREAM, StreamResource.SYSTEM, System.out, name, 0, Bo2UtilsEnvironment.getDefaultTextCharset());
		impl.registerSharedStream(ns);
		NamedStream<?> actual = impl.getSharedStream(ns.getName());
		
		Thread test = new Thread(new Runnable() {
			@Override public void run() {
				try {
					Assert.assertNotNull(impl.getSharedStream(name));
				} catch (InitializationException e) {
					Assert.fail("unexpected failure");
				}
			}
		});
		test.start();
		test.join();
		
		assertSame(ns,actual);
		Assert.assertTrue(SharedNamedStreamsRegistry.name2stream.containsKey(name));
		Assert.assertTrue(SharedNamedStreamsRegistry.stream2name.size()==1);
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.size()==1);
	}
	
	
	/**
	 * Tests openStream with print stream.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@Test()
	public void testClose() throws InitializationException, DataException {
		Properties p = new Properties();
		String name = "name";
		String defStr = "/home/path/file,printstream,File";
		p.setProperty(name, defStr);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		
		NamedStream<?> ns = man.getStream(name);
		Assert.assertTrue(man.streams.containsKey(name));
		Assert.assertNotNull(ns);
		
		NamedStream<?> sharedNs = man.getSharedStream(name);
		Assert.assertTrue(SharedNamedStreamsRegistry.name2stream.containsKey(name));
		Assert.assertTrue(SharedNamedStreamsRegistry.stream2name.size()==1);
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.size()==1);
		Assert.assertNotNull(sharedNs);
		
		man.close();
		Assert.assertTrue(man.streams.isEmpty());
		
		Assert.assertTrue(SharedNamedStreamsRegistry.name2stream.isEmpty());
		Assert.assertTrue(SharedNamedStreamsRegistry.stream2name.isEmpty());
		Assert.assertTrue(SharedNamedStreamsRegistry.providersAccessingStream.isEmpty());
	}
	
	/**
	 * Tests openStream with print stream.
	 * 
	 */
	@Test()
	public void testInvalid() {		
		InitializationException ex = 
			AbstractNamedStreamsManager.invalid("Wrong type X", "Stream");
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenFileStream_print() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String workdir = p.getProperty("workDir");
		String path = workdir + "TestOpenFileStream.print.txt";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("FilePrintStream");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.FILE);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(path);
		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedPrintStream ns = (NamedPrintStream) man.openFileStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenFileStream_output() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String workdir = p.getProperty("workDir");
		String path = workdir + "TestOpenFileStream.output.txt";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("FileOutputStream");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.FILE);
		def.setType(StreamType.OUTPUTSTREAM);
		def.setUri(path);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedOutputStream ns = (NamedOutputStream) man.openFileStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenFileStream_input() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String workdir = p.getProperty("workDir");
		String path = workdir + "existing.txt";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("Existing.1");
		def.setRecordLength(10);
		def.setResourceType(StreamResource.FILE);
		def.setType(StreamType.INPUTSTREAM);
		def.setUri(path);
		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedInputStream ns = (NamedInputStream) man.openFileStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpenFileStream_fileNotFound() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("FileNotFound");
		def.setRecordLength(10);
		def.setResourceType(StreamResource.FILE);
		def.setType(StreamType.INPUTSTREAM);
		def.setUri("/com/foo/bar/xxx");
		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedInputStream ns = (NamedInputStream) man.openFileStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenFileStream_reader() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String workdir = p.getProperty("workDir");
		String path = workdir + "existing.txt";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("Existing.2");
		def.setRecordLength(10);
		def.setResourceType(StreamResource.FILE);
		def.setType(StreamType.BUFFEREDREADER);
		def.setUri(path);
		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedBufferedReader ns = (NamedBufferedReader) man.openFileStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenClasspathStream_input() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();		
		String path = "/gr/interamerican/bo2/deployparms/deployment.properties";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("ClasspathInputstream");
		def.setRecordLength(100);
		def.setResourceType(StreamResource.CLASSPATH);
		def.setType(StreamType.INPUTSTREAM);
		def.setUri(path);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedInputStream ns = (NamedInputStream) man.openClasspathStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenClasspathStream_reader() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();		
		String path = "/gr/interamerican/bo2/deployparms/deployment.properties";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("ClasspathReader");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.CLASSPATH);
		def.setType(StreamType.BUFFEREDREADER);
		def.setUri(path);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		NamedBufferedReader ns = (NamedBufferedReader) man.openClasspathStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openClasspathStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpenClasspathStream_output() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();		
		String path = "/gr/interamerican/bo2/deployparms/deployment.properties";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("ClasspathFail1");
		def.setRecordLength(10);
		def.setResourceType(StreamResource.CLASSPATH);
		def.setType(StreamType.OUTPUTSTREAM);
		def.setUri(path);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		man.openClasspathStream(def);
	}
	
	/**
	 * Tests openClasspathStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpenClasspathStream_print() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();		
		String path = "/gr/interamerican/bo2/deployparms/deployment.properties";
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("ClasspathFail2");
		def.setRecordLength(10);
		def.setResourceType(StreamResource.CLASSPATH);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(path);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		man.openClasspathStream(def);
	}
	
	/**
	 * Opens a System Stream.
	 * 
	 * @param stream
	 * 
	 * @return returns the NamedPrintStream.
	 * @throws InitializationException 
	 */
	private NamedPrintStream openSystemStream(String stream) throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("Sys");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.SYSTEM);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(stream);
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		return man.openSystemStream(def);
	}
	
	
	/**
	 * Tests openStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenSystemStream_sysout() throws InitializationException {	
		NamedPrintStream ns = openSystemStream("SysOut");
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenSystemStream_syserr() throws InitializationException {	
		NamedPrintStream ns = openSystemStream("SysErr");
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpenSystemStream_fail() throws InitializationException {	
		openSystemStream("/home/foo/text.txt");
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenInMemoryStream_print() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("MemoryPrintStream");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.BYTES);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri("M");		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);		
		NamedPrintStream ns = (NamedPrintStream) man.openInMemoryStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test()
	public void testOpenInMemoryStream_out() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("MemoryPrintStream");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.BYTES);
		def.setType(StreamType.OUTPUTSTREAM);
		def.setUri("M");		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);		
		NamedOutputStream ns = (NamedOutputStream) man.openInMemoryStream(def);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Tests openFileStream with print stream.
	 * @throws InitializationException 
	 * 
	 */
	@Test(expected=InitializationException.class)
	public void testOpenInMemoryStream_invalid() throws InitializationException {		
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("MemoryPrintStream");
		def.setRecordLength(0);
		def.setResourceType(StreamResource.BYTES);
		def.setType(StreamType.INPUTSTREAM);
		def.setUri("M");		
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);		
		man.openInMemoryStream(def);
	}
	
	/**
	 * Tests handleOptionalDefinitionElement
	 */
	@Test
	public void testHandleOptionalDefinitionElement() {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		AbstractNamedStreamsManager man = new MockAbstractNamedStreamsManager(p);
		
		NamedStreamDefinition nsd = Factory.create(NamedStreamDefinition.class);
		Charset utf8 = Charset.forName("UTF-8");
		String attribute = AbstractNamedStreamsManager.ENCODING_PREFIX + utf8.name();
		man.handleOptionalDefinitionElement(nsd, attribute);
		assertEquals(utf8, nsd.getEncoding());
		
		nsd = Factory.create(NamedStreamDefinition.class);
		int recordLength = 101;
		attribute = AbstractNamedStreamsManager.RECORD_LENGTH_PREFIX + String.valueOf(recordLength);
		man.handleOptionalDefinitionElement(nsd, attribute);
		assertEquals(recordLength, nsd.getRecordLength());
	}
	
}
