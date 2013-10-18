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

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link TypedSelectableComparator}
 */
public class TestTypedSelectableComparator {
	
	/**
	 * Tests compare().
	 */
	@Test
	public void testCompare() {
		TypedSelectableComparator tsc = new TypedSelectableComparator();
		TypedSelectable<Long> ts1 = new TypedSelectableImpl<Long>();
		ts1.setCode(1l);
		TypedSelectable<Long> ts2 = new TypedSelectableImpl<Long>();
		ts2.setCode(2l);
		Assert.assertTrue(tsc.compare(ts1, ts2)<0);
		Assert.assertTrue(tsc.compare(ts1, ts1)==0);
		Assert.assertTrue(tsc.compare(ts2, ts1)>0);
	}
	
	

}
