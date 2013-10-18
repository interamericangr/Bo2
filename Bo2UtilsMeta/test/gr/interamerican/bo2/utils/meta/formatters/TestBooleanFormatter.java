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
 * 
 */
public class TestBooleanFormatter {
	/**
	 * Yes for true.
	 */
	String yes = "yes"; //$NON-NLS-1$
	/**
	 * No for false.
	 */
	String no = "no"; //$NON-NLS-1$
	/**
	 * Formatter to test.
	 */
	BooleanFormatter formatter = new BooleanFormatter(yes, no);
	
	/**
	 * test.
	 */
	@Test
	public void testFormat() {
		Assert.assertEquals(yes, formatter.format(true));
		Assert.assertEquals(no, formatter.format(false));
	}

}
