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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link MultiFormatter}.
 */
public class TestMultiFormatter {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		MultiFormatter mf = new MultiFormatter();
		Assert.assertNotNull(mf.formatters);
		Assert.assertNotNull(mf.formatters.select(new Object()));
	}
	
	/**
	 * Tests format(o).
	 */
	@Test
	public void testFormat() {
		MultiFormatter mf = new MultiFormatter();
		String s = mf.format(new Object());
		Assert.assertNotNull(s);		
	}

}
