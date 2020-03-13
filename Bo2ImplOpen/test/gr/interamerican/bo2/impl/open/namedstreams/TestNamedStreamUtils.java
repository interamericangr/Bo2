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

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.samples.implopen.mocks.MockNamedStream;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.StringConstants;

/**
 * Unit tests for {@link NamedStreamUtils}.
 */
public class TestNamedStreamUtils {
	/**
	 * Manager.
	 */
	private static final String MANAGER = "LOCALFS"; //$NON-NLS-1$

	/**
	 * Unit test for registerStream().
	 *
	 * @throws InitializationException the initialization exception
	 */
	@Test
	public void testRegisterStream() throws InitializationException {
		String name = "TestNamedStreamUtils.sample_buffered_reader"; //$NON-NLS-1$
		NamedStream<?> ns = new MockNamedStream<>(StreamType.INPUTSTREAM, StreamResourceEnum.FILE, null, name, 5,
				Bo2UtilsEnvironment.get().getDefaultTextCharset(), StringConstants.EMPTY);
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		NamedStreamUtils.registerStream(ns, provider, MANAGER);
		NamedStreamsProvider nsp = provider.getResource(MANAGER, NamedStreamsProvider.class);
		NamedStream<?> nbf = nsp.getStream(name);
		assertEquals(ns,nbf);
	}

	/**
	 * Unit test for getDefaultNamedStream().
	 * @throws InitializationException 
	 */
	@Test
	public void testGetDefaultNamedStream() throws InitializationException {
		String streamName = "TestNamedStreamUtils.sample_named_stream"; //$NON-NLS-1$
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();
		NamedStream<?> sampleStream = new MockNamedStream<>(StreamType.INPUTSTREAM, StreamResourceEnum.FILE, null, streamName, 5, Bo2UtilsEnvironment.get().getDefaultTextCharset(), StringConstants.EMPTY);
		NamedStreamUtils.registerStream(sampleStream, provider, MANAGER);
		
		NamedStream<?> result = NamedStreamUtils.getDefaultNamedStream(provider, streamName);
		assertSame(sampleStream, result);
	}
}