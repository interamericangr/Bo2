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
package gr.interamerican.bo2.arch.utils.adapters;

import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ResetModificationRecord}.
 */
public class TestResetModificationRecord {
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute() {			
		ResetModificationRecord<User> reset = 
			new ResetModificationRecord<User>();
		User user = new User();
		reset.execute(user);
		Assert.assertNull(user.getLastModified());
		Assert.assertNull(user.getLastModifiedBy());		
	}
	
	

}
