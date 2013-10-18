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
package gr.interamerican.bo2.impl.open.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestConditionChecker {
	
	
	/**
	 * contditionChecker to test
	 */
	private ConditionChecker<String> checker = new ConditionChecker<String>();
	
	/**
	 * message to test
	 */
	private static final String message = "message"; //$NON-NLS-1$
	
	
	/**
	 * Test addControl
	 */
	@Test
	public void testAddControl(){
		checker.addControl(message, true);
	}

	/**
	 * Test check
	 */
	@Test
	public void testCheck(){
		checker.addControl("message1", true); //$NON-NLS-1$
		checker.addControl("message2", true); //$NON-NLS-1$
		checker.addControl("message3", false); //$NON-NLS-1$
        assertEquals(2,checker.check().size());
		
	}
	
}
