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
package gr.interamerican.bo2.utils;

import org.junit.Assert;

/**
 * Assert statement utilitiers.
 */
public class Assertions {
	
	/**
	 * Hidden constructor.
	 */
	private Assertions() {/* empty */}
	
	/**
	 * Assert that big contains little.
	 * 
	 * @param big
	 * @param little
	 */
	public static void assertContains(String big, String little) {		
		String msg = big + "\n does not contain: " + little; //$NON-NLS-1$
		Assert.assertTrue(msg, big.contains(little));
	}
	
	/**
	 * Assert that big contains little.
	 * 
	 * @param big
	 * @param little
	 */
	public static void assertStartsWith(String big, String little) {		
		String msg = big + "\n does not start with: " + little; //$NON-NLS-1$
		Assert.assertTrue(msg, big.startsWith(little));
	}
	
	/**
	 * Assert that big contains little.
	 * 
	 * @param big
	 * @param little
	 */
	public static void assertEndsWith(String big, String little) {		
		String msg = big + "\n does not end with: " + little; //$NON-NLS-1$
		Assert.assertTrue(msg, big.endsWith(little));
	}
	
	/**
	 * Assert that big contains all parts..
	 * 
	 * @param big
	 * @param parts
	 */
	public static void assertContainsAll(String big, String parts[]) {
		for (String string : parts) {
			assertContains(big, string);
		}	
	}
	
	/**
	 * Assert that an exception is not null and has a message.
	 * 
	 * @param ex
	 */
	public static void assertException(Exception ex) {
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}

	

}
