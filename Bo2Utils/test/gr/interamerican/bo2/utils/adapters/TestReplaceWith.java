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
package gr.interamerican.bo2.utils.adapters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ReplaceWith}.
 */
public class TestReplaceWith {
	
	/**
	 * Tests execute when it replaces.
	 */
	@Test
	public void testConstructor() {
		Object what = new Object();
		Object with = new Object();		
		ReplaceWith<Object> replace = new ReplaceWith<Object>(what, with);
		Assert.assertEquals(with, replace.with);
		Assert.assertEquals(what, replace.what);
	}
	
	/**
	 * Tests execute when it replaces.
	 */
	@Test
	public void testExecute_notReplacing() {
		Object old = new Object();
		Object with = new Object();		
		ReplaceWith<Object> replace = new ReplaceWith<Object>(old, with);
		
		Object actual = replace.execute(old);
		Assert.assertEquals(with, actual);
	}
	
	/**
	 * Tests execute when it does not replace.
	 */
	@Test
	public void testExecute_replacing() {
		Object old = new Object();
		Object with = new Object();		
		Object other = new Object();
		ReplaceWith<Object> replace = new ReplaceWith<Object>(other, with);
		
		Object actual = replace.execute(old);
		Assert.assertEquals(old, actual);
	}

}
