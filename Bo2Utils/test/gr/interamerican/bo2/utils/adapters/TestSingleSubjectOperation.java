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

import static org.mockito.Mockito.*;

import org.junit.Test;

import org.junit.Assert;
/**
 * Tests {@link SingleSubjectOperation}.
 */
public class TestSingleSubjectOperation {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked")
		VoidOperation<Object> v = mock(VoidOperation.class);
		Object o = new Object();
		SingleSubjectOperation<Object> cmd = 
			new SingleSubjectOperation<Object>(v,o);
		Assert.assertEquals(o, cmd.subject);
		Assert.assertEquals(v, cmd.operation);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testExecute() {
		@SuppressWarnings("unchecked")
		VoidOperation<Object> v = mock(VoidOperation.class);
		Object o = new Object();
		SingleSubjectOperation<Object> cmd = 
			new SingleSubjectOperation<Object>(v,o);
		cmd.execute();
		verify(v,times(1)).execute(o);
	}


}
