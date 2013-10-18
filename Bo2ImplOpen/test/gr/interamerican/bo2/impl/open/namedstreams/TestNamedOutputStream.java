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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link NamedOutputStream}.
 */
@SuppressWarnings("nls")
public class TestNamedOutputStream {
	
	/**
	 * Object to test.
	 */
	NamedOutputStream ns;
	
	
	
	/**
	 * Tests setup.
	 * 
	 * @throws DataException 
	 */
	@Before
	public void after() throws DataException {
		if (ns!=null) {
			ns.close();
		}
	}
	
	/**
	 * Unit test for readRecord.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testReadRecord() throws FileNotFoundException, DataException {
		String path = UtilityForBo2Test.getTestStreamPath("TestNamedOutputStream.1.txt");		
		File file = new File(path);
		FileOutputStream stream = new FileOutputStream(file);
		ns = new NamedOutputStream (StreamResource.FILE, stream, "Nout", 20, file, Bo2UtilsEnvironment.getDefaultTextCharset());
		@SuppressWarnings("unused") byte[] rec1 = ns.readRecord();	
	}
	
	/**
	 * Unit test for readString.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testReadString() throws FileNotFoundException, DataException {
		String path = UtilityForBo2Test.getTestStreamPath("TestNamedOutputStream.2.txt");		
		File file = new File(path);
		FileOutputStream stream = new FileOutputStream(file);
		ns = new NamedOutputStream (StreamResource.FILE, stream, "Nout", 20, file, Bo2UtilsEnvironment.getDefaultTextCharset());
		@SuppressWarnings("unused") String rec1 = ns.readString();	
	}	
	
	/**
	 * Unit test for writeString.
	 * @throws DataException
	 * @throws IOException 
	 */	
	@Test()
	public void testWriteString() throws DataException, IOException {
		String path = UtilityForBo2Test.getTestStreamPath("TestNamedOutputStream.3.txt");		
		File file = new File(path);
		FileOutputStream stream = new FileOutputStream(file);
		ns = new NamedOutputStream (StreamResource.FILE, stream, "Nout", 20, file, Bo2UtilsEnvironment.getDefaultTextCharset());
		String string = "write this";
		ns.writeString(string);
		ns.close();
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		String actual = br.readLine();
		Assert.assertEquals(string, actual);
		br.close();		
	}		
	
	/**
	 * Unit test for writeRecord.
	 * @throws DataException
	 * @throws IOException 
	 */	
	@Test()
	public void testWriteRecord() throws DataException, IOException {
		String path = UtilityForBo2Test.getTestStreamPath("TestNamedOutputStream.4.txt");		
		File file = new File(path);
		FileOutputStream stream = new FileOutputStream(file);
		ns = new NamedOutputStream (StreamResource.FILE, stream, "Nout", 20, file, Bo2UtilsEnvironment.getDefaultTextCharset());
		String string = "write this";
		ns.writeRecord(string.getBytes());
		ns.close();
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		String actual = br.readLine();
		Assert.assertEquals(string, actual);
		br.close();		
		
	}	
	
	/**
	 * Unit test for readString.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testFind() throws FileNotFoundException, DataException {
		String path = UtilityForBo2Test.getTestStreamPath("TestNamedOutputStream.5.txt");		
		File file = new File(path);
		FileOutputStream stream = new FileOutputStream(file);
		ns = new NamedOutputStream (StreamResource.FILE, stream, "Nout", 20, file, Bo2UtilsEnvironment.getDefaultTextCharset());
		@SuppressWarnings("unused") String rec1 = ns.readString();
		ns.find("123".getBytes());
	}	
	
}
