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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link GetPoKey}.
 */
public class TestGetPoKey {
	
	/**
	 * Unit test.
	 */
	@Test
	public void testExecute() {
		User user = new User();
		user.setId(2);
		GetPoKey get = new GetPoKey();
		Object expected = user.getKey();
		Object actual = get.execute(user);
		Assert.assertEquals(expected, actual);
		Assert.assertNull(get.execute(null));
		Assert.assertNull(get.execute("the string")); //$NON-NLS-1$
	}
	
	
	

}
