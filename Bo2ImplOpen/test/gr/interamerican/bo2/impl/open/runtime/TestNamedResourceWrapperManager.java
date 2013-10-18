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
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.proxies.Mocks;
import gr.interamerican.bo2.samples.factories.MockObjectFactoryForResourceWrappers;

import org.junit.Test;

/**
 * Unit test for {@link NamedResourceWrapperManager}.
 */
@SuppressWarnings("unused")
public class TestNamedResourceWrapperManager {
	
	/**
	 * Tests that the constructor creates a valid object.
	 */
	@Test	
	public void testConstructor() {
		ObjectFactory factory = Mocks.empty(ObjectFactory.class);
		String name = "name"; //$NON-NLS-1$
		NamedResourceWrapperManager manager = 
			new NamedResourceWrapperManager(name, factory);
		assertEquals(name, manager.name);
		assertEquals(factory, manager.factory);
	}	
	
	/**
	 * Tests createResourceWrapper
	 * 
	 * @throws InitializationException 
	 */
	@Test	
	public void testCreateResourceWrapper() throws InitializationException {
		NamedResourceWrapperManager manager = 
			new NamedResourceWrapperManager("name", new MockObjectFactoryForResourceWrappers()); //$NON-NLS-1$
		ResourceWrapper rw = manager.createResourceWrapper(ResourceWrapper.class);
		assertNotNull(rw);
	}	
	
	/**
	 * Tests createResourceWrapper throwing an InitializationException
	 * when it fails to create a ResourceWrapper.
	 * 
	 * @throws InitializationException 
	 */
	@Test(expected=InitializationException.class)	
	public void testCreateResourceWrapper_fail() throws InitializationException {
		NamedResourceWrapperManager manager = 
			new NamedResourceWrapperManager("name", Mocks.empty(ObjectFactory.class)); //$NON-NLS-1$
		ResourceWrapper rw = manager.createResourceWrapper(ResourceWrapper.class);
	}
	
	/**
	 * Tests createResourceWrapper
	 * 
	 * @throws InitializationException 
	 */
	@Test	
	public void testGetResource() throws InitializationException {
		NamedResourceWrapperManager manager = 
			new NamedResourceWrapperManager("name", new MockObjectFactoryForResourceWrappers()); //$NON-NLS-1$
		ResourceWrapper rw = manager.getResource(ResourceWrapper.class);
		assertNotNull(rw);
		ResourceWrapper rw2 = manager.getResource(ResourceWrapper.class);
		assertSame(rw, rw2);
	}	

	

	
	
	
	
	
	

}
