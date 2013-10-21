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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link NamedStreamUtils}.
 */
public class TestNamedStreamUtils {
	/**
	 * Manager.
	 */
	private static final String MANAGER = "LOCALFS"; //$NON-NLS-1$
	
	/**
	 * Unit test for registerInputStream().
	 * 
	 * @throws InitializationException
	 */
	@Test
	public void testRegisterStream() throws InitializationException {
		byte[] bytes = new byte[10000];
		String name = "TestNamedStreamUtils.sample_buffered_reader"; //$NON-NLS-1$
		NamedStream<?> ns = NamedStreamFactory.reader(bytes, name, Bo2UtilsEnvironment.getDefaultTextCharset());
		Provider provider = Bo2.getDeployment(UtilityForBo2Test.BATCH_NO_TRAN).getProvider();		
		NamedStreamUtils.registerStream(ns, provider, MANAGER);
		NamedStreamsProvider nsp = provider.getResource(MANAGER, NamedStreamsProvider.class);
		NamedStream<?> nbf = nsp.getStream(name);
		Assert.assertEquals(ns,nbf);
	}
	
	

}
