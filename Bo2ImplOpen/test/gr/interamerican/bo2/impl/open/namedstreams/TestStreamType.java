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

import static gr.interamerican.bo2.impl.open.namedstreams.StreamType.BUFFEREDREADER;
import static gr.interamerican.bo2.impl.open.namedstreams.StreamType.INPUTSTREAM;
import static gr.interamerican.bo2.impl.open.namedstreams.StreamType.PRINTSTREAM;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link StreamType}.
 */
public class TestStreamType {
	
	/**
	 * Tests isOk(object)
	 */
	@Test
	public void testIsOk() {
		InputStream in = new ByteArrayInputStream(new byte[1]);
		Assert.assertTrue(INPUTSTREAM.isOk(in));		
		OutputStream out = new ByteArrayOutputStream();
		Assert.assertTrue(INPUTSTREAM.isOk(in));
		BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
		Assert.assertTrue(BUFFEREDREADER.isOk(bfr));		
		PrintStream ps = new PrintStream(out);
		Assert.assertTrue(PRINTSTREAM.isOk(ps));
	}
	
}
