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

import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link NamedStreamsManagerImpl}.
 */
public class TestNamedStreamsManagerImpl {

	/**
	 * Object to test.
	 */
	NamedStreamsManagerImpl manager;

	/**
	 * Setup tests.
	 */
	@Before
	public void setup() {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		manager = new NamedStreamsManagerImpl(p);
	}

	/**
	 * Opens a NamedStream.
	 *
	 * @param resourceType
	 * @param uri
	 *
	 *
	 * @return returns the NamedPrintStream.
	 * @throws InitializationException
	 */
	private NamedPrintStream open(StreamResource resourceType, String uri)
			throws InitializationException {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("TestNamedStreamsManagerImpl_open"); //$NON-NLS-1$
		def.setRecordLength(0);
		def.setResourceType(resourceType);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(uri);
		return (NamedPrintStream) manager.open(def);
	}

	/**
	 * test case
	 * {@link NamedStreamsManagerImpl#createNameStreamDefinitionWithMandatoryOptions(String, String, StreamType, StreamResource)}
	 */
	@Test
	public void testCreateNameStreamDefinitionWithMandatoryOptions() {
		NamedStreamsManagerImpl impl = new NamedStreamsManagerImpl(new Properties());
		NamedStreamDefinition nsp=impl.createNameStreamDefinitionWithMandatoryOptions(StringConstants.ONE, StringConstants.TWO, StreamType.PRINTSTREAM, StreamResource.FILE);
		Assert.assertEquals(StringConstants.ONE, nsp.getName());
		Assert.assertEquals(StringConstants.TWO, nsp.getUri());
		Assert.assertEquals(StreamType.PRINTSTREAM, nsp.getType());
		Assert.assertEquals(StreamResource.FILE, nsp.getResourceType());
	}
}
