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

import static org.junit.Assert.assertSame;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link AbstractEventHandler}.
 */
public class TestAbstractEventHandler {
	
	/**
	 * Creates a sample AbstractEventHandler object.
	 * 
	 * @return Returns a AbstractEventHandler. 
	 */
	@SuppressWarnings("serial")
	AbstractEventHandler<Object> sample() {
		return new AbstractEventHandler<Object>(Mockito.mock(ExceptionHandler.class)) {/*empty*/};
	}
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testSetCaller() {
		Object caller = new Object();
		AbstractEventHandler<Object> aeh = sample();
		aeh.setCaller(caller);
		Assert.assertEquals(caller, aeh.caller);
	}
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testGetCaller() {
		Object caller = new Object();
		AbstractEventHandler<Object> aeh = sample();
		aeh.caller = caller;
		Assert.assertEquals(caller, aeh.getCaller());
	}
	
	/**
	 * Unit test for getHandlerParameters.
	 */
	@Test
	public void testGetHandlerParameters() {
		AbstractEventHandler<Object> aeh = sample();
		Map<Class<?>, Object> hp = aeh.getHandlerParameters();
		Assert.assertNotNull(hp);		
	}
	
	/**
	 * Tests setHandlerParameter.
	 */
	@Test
	public void testSetHandlerParameter() {		
		AbstractEventHandler<Object> aeh = sample();
		Integer i = 5;
		aeh.setHandlerParameter(Integer.class, i);
		Object actual = aeh.handlerParameters.get(Integer.class);
		assertSame(i, actual);		
	}
	
	/**
	 * Tests getHandlerParameter.
	 */
	@Test
	public void testGetHandlerParameter() {		
		AbstractEventHandler<Object> aeh = sample();
		Integer i = 5;
		aeh.setHandlerParameter(Integer.class, i);
		Integer actual = aeh.getHandlerParameter(Integer.class);
		assertSame(i, actual);	
	}
	

}
