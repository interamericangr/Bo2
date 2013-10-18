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
package gr.interamerican.bo2.creation.util;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CodeGenerationUtilities}.
 */
public class TestMethodsUtilities {
	
	
	
	/**
	 * Unit test for compareTo(clazz).
	 */
	@Test
	public void testCompareTo() {
		Method mComparable = MethodsUtilities.compareTo(Comparable.class);
		Assert.assertNotNull(mComparable);
		
		Method mString = MethodsUtilities.compareTo(String.class);
		Assert.assertNotNull(mString);
		
		Method mSerializable = MethodsUtilities.compareTo(Serializable.class);
		Assert.assertNull(mSerializable);
		
	}
	
	/**
	 * Unit test for isEquals(method).
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@Test
	public void testIsEqual() throws SecurityException, NoSuchMethodException {
		Method m = Object.class.getMethod("equals", Object.class); //$NON-NLS-1$
		Assert.assertTrue(MethodsUtilities.isEquals(m));
	}
	
	/**
	 *Unit test for isHashcode(method).
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@Test
	public void testIsHashCode() throws SecurityException, NoSuchMethodException {
		Method m = Object.class.getMethod("hashCode"); //$NON-NLS-1$
		Assert.assertTrue(MethodsUtilities.isHashCode(m));
	}
	
	
	

}
