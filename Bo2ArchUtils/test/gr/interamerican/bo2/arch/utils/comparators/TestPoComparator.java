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
package gr.interamerican.bo2.arch.utils.comparators;

import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit tests of {@link PoComparator}
 */
public class TestPoComparator {
	
	/**
	 * Sample instance.
	 */
	private PoComparator<UserKey, User> comparator = new PoComparator<UserKey, User>();

	/**
	 * Tests compareTo
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareTo() {
		User left = null;
		User right = null;
		Assert.assertEquals(0, comparator.compare(left, right));
		
		left = new User(0, "", 0, "");
		Assert.assertTrue(comparator.compare(left, right) > 0);
		
		left = null;
		right = new User(0, "", 0, "");
		Assert.assertTrue(comparator.compare(left, right) < 0);
		
		left = new User(0, "", 0, "");
		Assert.assertEquals(0, comparator.compare(left, right));
		
		right = new User(1, "", 0, "");
		Assert.assertTrue(comparator.compare(left, right) < 0);
	}

}
