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
package gr.interamerican.bo2.utils.meta.formatters.nf;



import gr.interamerican.bo2.utils.adapters.trans.ToString;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestNfTransformationBasedFormatter.
 */
public class TestNfTransformationBasedFormatter {
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {		
		ToString ts = new ToString();
		NfTransformationBasedFormatter<Object> f = new NfTransformationBasedFormatter<Object>(ts);
		Assert.assertNotNull(f.formatter);
	}
	
}
