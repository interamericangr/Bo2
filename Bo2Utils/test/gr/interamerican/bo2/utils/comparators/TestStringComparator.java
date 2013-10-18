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
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestStringComparator {

	/**
	 * tests compare
	 */
	@Test
	public void testCompare(){
		
		StringComparator impl = new StringComparator();
		String str1 = "test"; //$NON-NLS-1$
		String str2 = "test"; //$NON-NLS-1$
		assertEquals(impl.compare(str1, str2),0);
		
		String str3 = null;
		assertEquals(impl.compare(str3, str1),-1);
		assertEquals(impl.compare(str1, str3),1);
		
		Integer int1 = 1;
		String  int2 = "1"; //$NON-NLS-1$
		assertEquals(impl.compare(int1, int2),0);
	}
	
	
}
