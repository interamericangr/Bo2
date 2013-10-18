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
package gr.interamerican.bo2.impl.open.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.doc.MockDocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngine;

import java.util.Properties;

import org.junit.Test;

/**
 * 
 */
public class TestDocumentEngineProviderImpl {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testGetDocumentEngine() {		
		DocumentEngineProviderImpl impl = new DocumentEngineProviderImpl(new MockDocumentEngine());
		assertEquals(impl.documentEngine, impl.getDocumentEngine());
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withEngine() {
		DocumentEngine p = new MockDocumentEngine();
		DocumentEngineProviderImpl impl = new DocumentEngineProviderImpl(p);
		assertNotNull(impl);
		assertEquals(impl.documentEngine, p);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withClassName() {
		String className = MockDocumentEngine.class.getName();
		DocumentEngineProviderImpl impl = new DocumentEngineProviderImpl(className);
		assertNotNull(impl);
		assertNotNull(impl.documentEngine);
		assertTrue(impl.documentEngine instanceof MockDocumentEngine);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withProperties() {
		Properties properties = new Properties();
		properties.setProperty("documentEngineClass", MockDocumentEngine.class.getName()); //$NON-NLS-1$
		properties.setProperty("host", "localhost");
		properties.setProperty("port", "8100");
		DocumentEngineProviderImpl impl = new DocumentEngineProviderImpl(properties);
		assertNotNull(impl);
		assertNotNull(impl.documentEngine);
		assertTrue(impl.documentEngine instanceof MockDocumentEngine);
	}

}
