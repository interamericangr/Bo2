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
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link NamedBufferedReader}.
 */
@SuppressWarnings("nls")
public class TestNamedBufferedReader {
	
	/**
	 * Object to test.
	 */
	NamedBufferedReader ns;
	
	/**
	 * Tests setup.
	 * @throws FileNotFoundException
	 */
	@Before
	public void before() throws FileNotFoundException {
		String path = UtilityForBo2Test.getTestStreamPath("existingBR.txt");		
		BufferedReader stream = new BufferedReader(new FileReader(path));
		ns = new NamedBufferedReader (StreamResource.FILE, stream, "Nbuf", 0, Bo2UtilsEnvironment.getDefaultTextCharset());
	}
	
	/**
	 * Tests setup.
	 * 
	 * @throws DataException 
	 */
	@After
	public void after() throws DataException {
		if (ns!=null) {
			ns.close();
		}
	}
	

	/**
	 * Unit test for readRecord.
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadRecord() throws DataException {		
		byte[] rec1 = ns.readRecord();
		Assert.assertNotNull(rec1);
		String expectedRecord = UtilityForBo2Test.getRecordOfTestTextFile();
		Assert.assertArrayEquals(expectedRecord.getBytes(), rec1);
		byte[] rec2 = ns.readRecord();
		Assert.assertNotNull(rec2);
	}
	
	/**
	 * Unit test for readString.
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadString() throws DataException {
		String rec1 = ns.readString();
		Assert.assertNotNull(rec1);
		String expectedRecord = UtilityForBo2Test.getRecordOfTestTextFile();
		Assert.assertEquals(expectedRecord, rec1);
	}	
	
	/**
	 * Unit test for writeString.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testWriteString() 
	throws DataException {		
		ns.writeString("not to be written");
	}		
	
	/**
	 * Unit test for writeString.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testWriteRecord() 
	throws DataException {
		ns.writeRecord("not to be written".getBytes());
	}		
	
	
	
	/**
	 * Unit test for find.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testFind() throws DataException {
		ns.find("123".getBytes());			
	}	
	
	
}
