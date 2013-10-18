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
package gr.interamerican.bo2.arch.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.samples.providers.EmptyProvider;

import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for {@link ProviderUtils}.
 */
public class TestProviderUtils {
	/**
	 * properties.
	 */
	Properties properties = new Properties();
	
	/**
	 * Unit test for getMandatoryProperty
	 * @throws InitializationException
	 */
	public void testGetMandatoryProperty_ok() throws InitializationException {			
		String expected = "expected"; //$NON-NLS-1$
		String key = "key"; //$NON-NLS-1$	
		properties.setProperty(key, expected);
		String actual = ProviderUtils.getMandatoryProperty(properties, key);
		assertEquals(expected, actual);		
	}
	
	/**
	 * Unit test for getMandatoryProperty
	 * @throws InitializationException
	 */
	@Test(expected=InitializationException.class)
	public void testGetMandatoryProperty_fail() throws InitializationException {
		ProviderUtils.getMandatoryProperty(properties, "foofoo"); //$NON-NLS-1$
	}
	

	
	/**
	 * test GetResourceWrapper
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetResourceWrapper() {
		
		Provider provider = new EmptyProvider();
		assertNull(ProviderUtils.getResourceWrapper("resourceName", SampleWrapper.class, provider));
	}
	
	
	/**
	 * private class to test
	 */
	private class SampleWrapper implements ResourceWrapper{

		public void close() throws DataException {
			// empty
		}
		
	}
}
