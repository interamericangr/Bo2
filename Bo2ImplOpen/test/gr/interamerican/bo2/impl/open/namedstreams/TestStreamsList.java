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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.Test;

/**
 * UNit test for {@link StreamsList}.
 */
public class TestStreamsList {
	
	/**
	 * path with properties
	 */
	String path = "/gr/interamerican/bo2/impl/open/namedstreams/StreamsList.properties"; //$NON-NLS-1$
	/**
	 * properties
	 */
	Properties properties = CollectionUtils.readProperties(path);
	/**
	 * list
	 */
	StreamsList list = new StreamsList(properties);
	
	/**
	 * Tests the object.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testStreamsList() {
		
		String[] br = {"A","B","C","D"};
		String[] ps = {"A","B","C"};
		String[] is = {"A"};
		String[] os = {};
		assertArrayEquals(br, list.getBufferedReadersArray());
		assertArrayEquals(ps, list.getPrintStreamsArray());
		assertArrayEquals(is, list.getInputStreamsArray());
		assertArrayEquals(os, list.getOutputStreamsArray());
		
	}

	/**
	 * tests GetBufferedReaders
	 */
	@Test
	public void testGetBufferedReaders(){
		list.setBufferedReaders("bufferedReaders");//$NON-NLS-1$
		assertEquals("bufferedReaders",list.getBufferedReaders());//$NON-NLS-1$
	}
	
	/**
	 * tests GetPrintStreams
	 */
	@Test
	public void testGetPrintStreams(){
		list.setPrintStreams("printStreams");//$NON-NLS-1$
		assertEquals("printStreams",list.getPrintStreams());//$NON-NLS-1$
	}
	
	/**
	 * tests GetInputStreams
	 */
	@Test
	public void testGetInputStreams(){
		list.setInputStreams("inputStreams");//$NON-NLS-1$
		assertEquals("inputStreams",list.getInputStreams());//$NON-NLS-1$
	}
	
	/**
	 * tests GetOutputStreams
	 */
	@Test
	public void testGetOutputStreams(){
		list.setOutputStreams("outputStreams");//$NON-NLS-1$
		assertEquals("outputStreams",list.getOutputStreams());//$NON-NLS-1$
	}
	
	/**
	 * tests GetOutputStreams
	 */
	@Test
	public void testGetPrintStreamsArray_NullValue(){
		list.setPrintStreams(null);
		list.getPrintStreamsArray();
	}
	
}
