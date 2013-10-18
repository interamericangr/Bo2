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
package gr.interamerican.bo2.samples.implopen.po;


import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.archutil.po.User;

import org.junit.Test;

/**
 * Unit test for {@link User}.
 * 
 * User is a class that is used by other tests,
 * for this reason it is included in the tests.
 *
 */
@SuppressWarnings("nls")
public class TestUser {
	



	/**
	 * tests  the accessor methods work well.
	 */
	@Test
	public void testIdAccessors() {
		User user=new User();
		Integer id=200;
		user.setId(id);
		assertEquals(user.getId(), id);
	}
	
	/**
	 * tests  the accessor methods work well.
	 */
	@Test
	public void testUsridAccessors() {
		User user=new User();
		String usrid="some user"; 
		user.setUsrid(usrid);
		assertEquals(user.getUsrid(), usrid);
	}
	
	/**
	 * tests  the accessor methods work well.
	 */
	@Test
	public void testNameAccessors() {
		User user=new User();
		String name="some user"; 
		user.setName(name);
		assertEquals(user.getName(), name);
	}
	
	/**
	 * tests  the accessor methods work well.
	 */
	@Test
	public void testRoleIdAccessors() {
		User user=new User();
		int roleid=200;
		user.setRoleId(roleid);
		assertEquals(user.getRoleId(), roleid);
	}
	

	
	
	
	
	
}
