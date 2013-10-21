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
package gr.interamerican.bo2.utils.beans;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link PairWithComparableLeft}.
 */
public class TestPairWithComparableLeft {
	
	/**
	 * Test compareTo
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareTo() {
		PairWithComparableLeft<String, Object> one = new PairWithComparableLeft<String, Object>(null, null);
		PairWithComparableLeft<String, Object> two = new PairWithComparableLeft<String, Object>(null, null);
		Assert.assertEquals(one.compareTo(null), 1);
		Assert.assertEquals(one.compareTo(two), 0);
		one.setLeft("1");
		Assert.assertEquals(one.compareTo(two), 1);
		two.setLeft("1");
		Assert.assertEquals(one.compareTo(two), 0);
		two.setLeft("0");
		Assert.assertEquals(one.compareTo(two), 1);
	}

}
