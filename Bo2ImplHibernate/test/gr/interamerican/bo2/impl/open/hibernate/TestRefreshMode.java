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
package gr.interamerican.bo2.impl.open.hibernate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link RefreshMode}.
 */
public class TestRefreshMode {
	
	/**
	 * Test setRefreshMode.
	 */
	@Test
	public void testSetRefreshMode() {
		Class<?> persistentClass = Object.class;
		RefreshMode mode = Mockito.mock(RefreshMode.class);
		RefreshMode.setRefreshMode(persistentClass, mode);
		RefreshMode actual = RefreshMode.modes.get(persistentClass);
		Assert.assertEquals(mode, actual);
		RefreshMode.modes.remove(persistentClass);		
	}
	
	/**
	 * Test getRefreshMode.
	 */
	@Test
	public void testGetRefreshMode() {
		Class<?> persistentClass = Object.class;
		RefreshMode mode = Mockito.mock(RefreshMode.class);
		RefreshMode.setRefreshMode(persistentClass, mode);
		RefreshMode.modes.put(persistentClass,mode);
		RefreshMode actual = RefreshMode.getRefreshMode(persistentClass);
		Assert.assertEquals(mode, actual);
		RefreshMode.modes.remove(persistentClass);		
	}
	
	/**
	 * Test setDefaulthMode.
	 */
	@Test
	public void testSetDefaultMode() {
		RefreshMode old = RefreshMode.defaultMode;	//record normal state.	
		RefreshMode mode = Mockito.mock(RefreshMode.class);		
		RefreshMode.setDefaultMode(mode);
		Assert.assertEquals(mode, RefreshMode.defaultMode);		
		RefreshMode.defaultMode = old; //restore state.
	}
	
	/**
	 * Test getDefaulthMode.
	 */
	@Test
	public void testGetDefaultMode() {		
		Assert.assertEquals(RefreshMode.defaultMode, RefreshMode.getDefaultMode());
	}

}
