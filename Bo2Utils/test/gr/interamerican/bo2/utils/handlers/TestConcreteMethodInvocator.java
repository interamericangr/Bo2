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
package gr.interamerican.bo2.utils.handlers;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ConcreteMethodInvocator}.
 */
@SuppressWarnings("nls")
public class TestConcreteMethodInvocator {
	
	/**
	 * Foo method.
	 */
	void foo() {/* empty */}
	
	/**
	 * Tests the constructor. 
	 */
	@Test
	public void testConstructor_2() {		
		ConcreteMethodInvocator cmi = new ConcreteMethodInvocator("foo", this);
		Assert.assertNotNull(cmi.handler);
	}
	
	/**
	 * Tests the getArguments(). 
	 */
	@Test
	public void testGetArguments() {		
		ConcreteMethodInvocator cmi = new ConcreteMethodInvocator("foo", this);
		Object[] args = {};
		cmi.arguments = args;
		Assert.assertArrayEquals(cmi.arguments, cmi.getArguments());
	}
	
	/**
	 * Tests the setArguments(args). 
	 */	
	@Test
	public void testSetArguments() {		
		ConcreteMethodInvocator cmi = new ConcreteMethodInvocator("foo", this);
		Object[] args = {};
		cmi.setArguments(args);		
		Assert.assertArrayEquals(args,cmi.arguments);
	}
	
	
	
	
	

}
