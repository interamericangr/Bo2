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

import org.junit.Test;

/**
 * Unit test for {@link NamedStreamDefinition}.
 */
public class TestNamedStreamDefinition {
	
	/**
	 * Unit test for the bean's accessor methods.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAccessors() {
		NamedStreamDefinition def = new NamedStreamDefinition();
		String name = "MyStream";
		StreamType type = StreamType.INPUTSTREAM;
		StreamResource resourceType= StreamResource.FILE;
		String uri = "/home/me/mystream.txt";
		int recordLength = 20;		
		
		def.setName(name);
		assertEquals(name, def.name);				
		def.setType(type);
		assertEquals(type, def.type);		
		def.setResourceType(resourceType);
		assertEquals(resourceType, def.resourceType);		
		def.setUri(uri);
		assertEquals(uri, def.uri);		
		def.setRecordLength(recordLength);
		assertEquals(recordLength, def.recordLength);
		
		assertEquals(def.name, def.getName());
		assertEquals(def.type, def.getType());
		assertEquals(def.resourceType, def.getResourceType());
		assertEquals(def.uri, def.getUri());		
		assertEquals(def.recordLength, def.getRecordLength());		
	}

}
