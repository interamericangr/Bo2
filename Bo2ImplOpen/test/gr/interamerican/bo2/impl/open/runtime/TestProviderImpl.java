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
package gr.interamerican.bo2.impl.open.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.proxies.Mocks;
import gr.interamerican.bo2.samples.factories.MockObjectFactoryForResourceWrappers;
import gr.interamerican.bo2.samples.providers.IMockRw1;
import gr.interamerican.bo2.samples.providers.IMockRw2;
import gr.interamerican.bo2.samples.providers.MockTransactionManager;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit test for {@link ProviderImpl}.
 */
public class TestProviderImpl {
	/**
	 * name of ok resourceWrapperManager.
	 */
	private static final String OK = "ok"; //$NON-NLS-1$
	/**
	 * name of problematic resourceWrapperManager.
	 */
	private static final String NOK = "nok"; //$NON-NLS-1$
	
	
	/**
	 * Paths to Mock ResourceManagerWrapper definitions.
	 */
	@SuppressWarnings("nls")
	private static final String[] MOCKPATHS = {
		"/gr/interamerican/rsrc/managers/mock1/of.properties"
	};
	
	/**
	 * Tests the constructor.
	 * @throws InitializationException 
	 */
	@Test	
	public void testConstructor_withMap() throws InitializationException {		
		ProviderImpl prov = mock();
		assertNotNull(prov);
		assertEquals(2, prov.managers.size());
	}	
	
	

    /**
	 * Creates a mock provider.
	 * 
     * @return Returns a new mock ProviderImpl.
     * @throws InitializationException 
	 */
	private ProviderImpl mock() throws InitializationException {
	    Map<String, ObjectFactory> map = new HashMap<String, ObjectFactory>();
		map.put(OK, new MockObjectFactoryForResourceWrappers());		
		map.put(NOK, Mocks.empty(ObjectFactory.class));		
		ProviderImpl prov = new ProviderImpl(map, new HashMap<String, String>(),
				MockTransactionManager.class.getName());    
		return prov;
	}
	
	
	/**
	 * Tests getResource succeed.
	 * @throws InitializationException 
	 */
	@Test	
	public void testGetResource_ok() throws InitializationException {
		ProviderImpl prov = mock();
		ResourceWrapper w = prov.getResource(OK, ResourceWrapper.class);
		assertNotNull(w);
	}
	
	/**
	 * Tests getResource succeed.
	 * @throws InitializationException 
	 */
	@Test	
	public void testGetResource_withAlias() throws InitializationException {
		ProviderImpl prov = mock();
		String alias1 = "alias1"; //$NON-NLS-1$
		String alias2 = "alias2"; //$NON-NLS-1$
		prov.setManagerAlias(alias1, OK);
		prov.setManagerAlias(alias2, OK);
		ResourceWrapper w0 = prov.getResource(OK, ResourceWrapper.class);		
		ResourceWrapper w1 = prov.getResource(alias1, ResourceWrapper.class);
		ResourceWrapper w2 = prov.getResource(alias2, ResourceWrapper.class);
		assertNotNull(w0);
		assertSame(w0, w1);
		assertSame(w0, w2);		
	}
	
	/**
	 * Tests setManagerAlias.
	 * 
	 * @throws InitializationException 
	 */
	@Test	
	public void testSetManagerAlias() throws InitializationException {
		ProviderImpl prov = mock();
		String alias1 = "alias1"; //$NON-NLS-1$		
		prov.setManagerAlias(alias1, OK);
		assertEquals(OK, prov.managerAliases.get(alias1));
	}
	
	/**
	 * Tests getResource when there is no manager with the specified name.
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)	
	public void testGetResource_managerNotFound() throws InitializationException {
		ProviderImpl prov = mock();
		prov.getResource("other", ResourceWrapper.class); //$NON-NLS-1$		
	}
	
	/**
	 * Tests getResource when the manager throws.
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)	
	public void testGetResource_managerThrows() throws InitializationException {
		ProviderImpl prov = mock();
		prov.getResource(NOK, ResourceWrapper.class); 
	}
	
	/**
	 * Tests getTransactionManager.
	 * 
	 * @throws InitializationException 
	 */
	@Test()	
	public void testGetTransactionManager_withTm() throws InitializationException {
		Map<String, ObjectFactory> map = new HashMap<String, ObjectFactory>();
		map.put(OK, new MockObjectFactoryForResourceWrappers());		
		map.put(NOK, Mocks.empty(ObjectFactory.class));		
		ProviderImpl prov = new ProviderImpl(map, new HashMap<String, String>(),
				MockTransactionManager.class.getName());  
		TransactionManager transactionManager = prov.getTransactionManager();
		assertNotNull(transactionManager);
		assertTrue(transactionManager instanceof MockTransactionManager);		
	}
	
	/**
	 * Tests getTransactionManager.
	 * 
	 * @throws InitializationException 
	 */
	@Test()	
	public void testGetTransactionManager_withoutTm() throws InitializationException {
		Map<String, ObjectFactory> map = new HashMap<String, ObjectFactory>();
		map.put(OK, new MockObjectFactoryForResourceWrappers());		
		map.put(NOK, Mocks.empty(ObjectFactory.class));		
		ProviderImpl prov = new ProviderImpl(map,new HashMap<String, String>(), null);  
		TransactionManager transactionManager = prov.getTransactionManager();
		assertNull(transactionManager);		
	}
	
	/**
	 * Tests getResource when two different provider classes must be
	 * implemented by the same class.
	 * 
	 * @throws InitializationException 
	 */
	@Test	
	public void testGetResource_whenTwoProvidersAreTheSameClass() 
	throws InitializationException {	
		 Map<String, ObjectFactory> factories = 
			 ProviderImpl.getFactoriesMapFromProperties(MOCKPATHS);
		ProviderImpl prov = new ProviderImpl(factories, new HashMap<String, String>(), null);
		String managerName = "MOCK1"; //$NON-NLS-1$
		IMockRw1 mock1 = prov.getResource(managerName, IMockRw1.class);
		IMockRw2 mock2 = prov.getResource(managerName, IMockRw2.class);
		assertNotNull(mock1);
		assertNotNull(mock2);
		assertSame(mock1,mock2);		
	}
	
	

}
