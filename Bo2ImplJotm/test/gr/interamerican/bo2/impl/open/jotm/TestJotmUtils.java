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
package gr.interamerican.bo2.impl.open.jotm;

import static org.mockito.Mockito.mock;
import gr.interamerican.bo2.arch.TransactionManager;

import org.junit.Test;

/**
 * 
 */
public class TestJotmUtils {
	
	/**
	 * Test validateTm(tm)
	 */
	@Test
	public void testValidateTm_valid() {
		TransactionManager tm = mock(JotmTransactionManager.class);
		JotmUtils.validateTm(tm);
		/* no assertion */
	}
	
	/**
	 * Test validateTm(tm) when tm==null.
	 */
	@Test(expected=RuntimeException.class)
	public void testValidateTm_null() {		
		JotmUtils.validateTm(null);
		/* no assertion, just expecting exception */
	}
	
	/**
	 * Test validateTm(tm) when tm==null.
	 */
	@Test(expected=RuntimeException.class)
	public void testValidateTm_notValid() {	
		TransactionManager tm = mock(TransactionManager.class);
		JotmUtils.validateTm(tm);
		/* no assertion, just expecting exception */
	}

}
