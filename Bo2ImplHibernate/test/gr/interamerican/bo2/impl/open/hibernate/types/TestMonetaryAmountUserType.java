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
package gr.interamerican.bo2.impl.open.hibernate.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.io.Serializable;
import java.math.BigDecimal;

import org.junit.Test;

/**
 * 
 */
public class TestMonetaryAmountUserType {

	/**
	 * MonetaryAmountUserType to test
	 */
	MonetaryAmountUserType userType = new MonetaryAmountUserType();
	
	
	/**
	 * test returned class 
	 */
	@Test
	public void testSqlTypes(){
		assertTrue(userType.sqlTypes().length>0);
	}
	
	/**
	 * test returned class 
	 */
	@Test
	public void testReturnedClass(){
		assertEquals(MoneyImpl.class,userType.returnedClass());
	}
	
	/**
	 * tests IsMutable
	 */
	@Test
	public void testIsMutable(){
		assertFalse(userType.isMutable());
	}
	
	/**
	 * tests DeepCopy
	 */
	@Test
	public void testDeepCopy(){
		BigDecimal bd = new BigDecimal(1);
		assertEquals(bd,userType.deepCopy(bd));
	}
	
	
	/**
	 * tests Disassemble
	 */
	@Test
	public void testDisassemble(){
		BigDecimal bd = new BigDecimal(1);
		assertEquals(bd,userType.disassemble(bd));
	}
	
	/**
	 * tests Assemble
	 */
	@Test
	public void testAssemble(){
	    Cache cache = new Cache();
		String owner = "owner"; //$NON-NLS-1$
		assertEquals(cache,userType.assemble(cache, owner));
	}
	
	/**
	 * tests Replace
	 */
	@Test
	public void testReplace(){
		BigDecimal original = new BigDecimal(1);
		BigDecimal target = new BigDecimal(2);
		String owner = "owner"; //$NON-NLS-1$
		assertEquals(original,userType.replace(original,target,owner));
	}
	
	/**
	 * tests Replace
	 */
	@Test
	public void testEquals(){
		BigDecimal bd1 = new BigDecimal(1);
		BigDecimal bd2 = new BigDecimal(1);
		assertTrue(userType.equals(bd1, bd2));
		
		BigDecimal bd3 = new BigDecimal(2);
		assertFalse(userType.equals(bd1, bd3));
	}
	
	
	/**
	 * tests has code
	 */
	@Test
	public void testHashCode(){
		String test = "test"; //$NON-NLS-1$
		userType.hashCode(test);
	}
	
	/**
	 * cache to test
	 */
	public class Cache implements Serializable{

		/**
		 * uid.
		 */
		private static final long serialVersionUID = 7132757500200169995L;
	 //empty	
	}
}
