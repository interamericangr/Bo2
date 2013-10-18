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
package gr.interamerican.bo2.creation.proxies;

import static gr.interamerican.bo2.utils.ReflectionUtils.getMethodByUniqueName;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link MethodInvocation}.
 */
public class TestMethodInvocation {
	
	/**
	 * Tests successfull().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSuccessful() {
		Method m = getMethodByUniqueName("getField1", BeanWith2Fields.class);
		Object[] args = {};
		String retValue = "1";
		MethodInvocation mi = MethodInvocation.successful(m, args, retValue);
		Assert.assertNotNull(mi);
	}
	
	/**
	 * Tests successfull().
	 */
	@Test
	public void testThrown() {
		Method m = getMethodByUniqueName("getField1", BeanWith2Fields.class); //$NON-NLS-1$
		Object[] args = {};
		NullPointerException ne = new NullPointerException();
		MethodInvocation mi = MethodInvocation.thrown(m, args, ne);
		Assert.assertNotNull(mi);		
	}
	
	/**
	 * Tests getArgs().
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetArgs() {
		Method m = getMethodByUniqueName("setField1", BeanWith2Fields.class);
		Object[] args = {"1"};		
		MethodInvocation mi = MethodInvocation.successful(m, args, null);
		Assert.assertArrayEquals(mi.args, mi.getArgs());
	}
	
	/**
	 * Tests isMatch().
	 */
	@Test
	@SuppressWarnings("nls")	
	public void testIsMatch_true() {
		Method m = getMethodByUniqueName("setField1", BeanWith2Fields.class);
		Object[] args = {"1"};		
		MethodInvocation mi = MethodInvocation.successful(m, args, null);
		boolean match = mi.isMatch("setField1", args);
		Assert.assertTrue(match);
	}
	
	/**
	 * Tests isMatch().
	 */
	@Test
	@SuppressWarnings("nls")	
	public void testIsMatch_false() {
		Method m = getMethodByUniqueName("setField1", BeanWith2Fields.class);
		Object[] args = {"1"};		
		MethodInvocation mi = MethodInvocation.successful(m, args, null);
		Object[] notMatchingArgs = {2,2};
		boolean match = mi.isMatch("setField1", notMatchingArgs);
		Assert.assertFalse(match);
	}
	
	

}
