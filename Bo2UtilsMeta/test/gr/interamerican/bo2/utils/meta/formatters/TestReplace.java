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
package gr.interamerican.bo2.utils.meta.formatters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Replace}.
 */
public class TestReplace {
	/**
	 * Constant.
	 */
	String constant = "C"; //$NON-NLS-1$
	/**
	 * Object to test.
	 */
	Replace<Object> replace = new Replace<Object>(constant);
	
	/**
	 * Tests format.
	 */
	@Test
	public void testFormat() {
		Assert.assertEquals(constant, replace.format(new Object()));
	}

}
