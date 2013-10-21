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
package gr.interamerican.bo2.impl.open.streams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link TestPollingStreamsProviderImpl}.
 *
 */
public class TestPollingStreamsProviderImpl {
	/**
	 * Creates a new {@link PollingStreamsProviderImpl} to test.
	 * 
	 * @return Returns a new instance.
	 */
	PollingStreamsProviderImpl newInstance() {
		Properties p = new Properties();
		String path = UtilityForBo2Test.getStreamTestWorkDirectory();
		p.setProperty(PollingStreamsProviderImpl.WORK_DIR, path);
		p.setProperty(PollingStreamsProviderImpl.MAX_TRIES, "3"); //$NON-NLS-1$
		p.setProperty(PollingStreamsProviderImpl.INTERVAL, "1000"); //$NON-NLS-1$
		return new PollingStreamsProviderImpl(p);		
	}
	
	/**
	 * Tests the execution of getOutputStream.
	 * 
	 * Creates an OutputStream, writes some data in it, then 
	 * Creates an InputStream and reads the data.
	 * @throws DataException 
	 */
	@Test(expected=DataOperationNotSupportedException.class)
	public void testGetOutputStream_withoutException() 
	throws DataException {
		String filename = "TestPollStreamsProviderImpl.testfile1.txt"; //$NON-NLS-1$
		newInstance().getOutputStream(filename);
	}
		
	/**
	 * Tests the successful execution of getOutputStream.
	 * 
	 * Creates an OutputStream, writes some data in it, then 
	 * Creates an InputStream and reads the data.
	 * @throws DataException
	 */
	@Test
	public void testInputStream_withoutException() 
	throws DataException {
		String filename = "existing.txt"; //$NON-NLS-1$
		InputStream stream = newInstance().getInputStream(filename);
		Assert.assertNotNull(stream);
	}
	
	/**
	 * Tests the failure of an output stream creation.
	 * 
	 * @throws DataException
	 */
	@Test(expected=DataException.class)
	public void testGetInputStream_withException() throws DataException {
		PollingStreamsProviderImpl impl = new PollingStreamsProviderImpl(); 
		String filename = "foo.bar"; //$NON-NLS-1$
		impl.getInputStream(filename);
	}
}
