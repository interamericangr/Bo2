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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.io.PrintStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AbstractNamedStream}.
 */
@SuppressWarnings("nls")
public class TestAbstractNamedStream {
	
	/**
	 * Creates a mock object.
	 * @return Returns the mock.
	 */
	private static AbstractNamedStream<PrintStream> mock() {
		PrintStream stream = System.out;
		AbstractNamedStream<PrintStream> mo = 
			new AbstractNamedStream<PrintStream>
			(StreamType.PRINTSTREAM,StreamResource.SYSTEM,stream,"Mock",10, stream, Bo2UtilsEnvironment.getDefaultTextCharset()) {
				public byte[] readRecord() 
				throws DataException, DataOperationNotSupportedException {
					return null;
				}
				public String readString() 
				throws DataException, DataOperationNotSupportedException {
					return null;
				}
				public void writeRecord(byte[] record) 
				throws DataException, DataOperationNotSupportedException {
					/* empty */				
				}
				public void writeString(String string) 
				throws DataException, DataOperationNotSupportedException {
					/* empty */
				}
				public boolean find(byte[] key) 
				throws DataException, DataOperationNotSupportedException {
					return false;
				}
				public void close() 
				throws DataException, DataOperationNotSupportedException {
					/* empty */					
				}
		};
		return mo;
	}
	
	/**
	 * Tests getStream()
	 */
	@Test
	public void testGetStream() {
		AbstractNamedStream<PrintStream> ns = mock();
		Assert.assertEquals(ns.stream, ns.getStream());
	}
		
	/**
	 * Tests getName()
	 */	
	@Test
	public void testGetName() {
		AbstractNamedStream<PrintStream> ns = mock();
		Assert.assertEquals(ns.name, ns.getName());
	}
	
	/**
	 * Tests getRecordLength()
	 */	
	@Test
	public void testGetRecordLength() {
		AbstractNamedStream<PrintStream> ns = mock();
		Assert.assertEquals(ns.recordLength, ns.getRecordLength());
	}
	
	/**
	 * Tests getType()
	 */	
	@Test
	public void testGetType() {
		AbstractNamedStream<PrintStream> ns = mock();
		Assert.assertEquals(ns.streamType, ns.getType());
	}
	
	/**
	 * Tests getResourceType()
	 */	
	@Test
	public void testGetResourceType() {
		AbstractNamedStream<PrintStream> ns = mock();
		Assert.assertEquals(ns.resourceType, ns.getResourceType());
	}
	
	/**
	 * Tests getResourceType()
	 */	
	@Test(expected=RuntimeException.class)
	public void testValidate() {
		AbstractNamedStream<PrintStream> ns = mock();
		ns.streamType = StreamType.BUFFEREDREADER;
		ns.validate();
	}
		
	

}
