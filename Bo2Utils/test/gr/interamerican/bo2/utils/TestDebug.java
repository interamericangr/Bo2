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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests {@link Debug}.
 */
@SuppressWarnings("nls")
public class TestDebug {
	
	/**
	 * before.
	 */
	@Before
	public void before() {
		Debug.setEnabled(true);
	}
	
	/**
	 * Tests get-set 
	 */
	@Test
	public void testSet() {
		Object ob = new Object();
		Debug.set("key", ob);
		assertTrue(Debug.get("key")==ob);		
	}
	
	/**
	 * Tests get-set 
	 */
	@Test
	public void testGet() {	
		assertTrue(Debug.get("nada")==null);
	}
	
	
	/**
	 * Tests counter.
	 */
	@Test
	public void testSetCounter() {	
		assertTrue(Debug.getCounter("counter")==0);
		Debug.setCounter("counter", 2);
		assertTrue(Debug.getCounter("counter")==2);
	}
	
	/**
	 * Tests counter.
	 */
	@Test
	public void testIncreaseCounter() {
		int last = Debug.getCounter("counter1");		
		Debug.increaseCounter("counter1");
		assertTrue(Debug.getCounter("counter1")==last + 1);
	}
	
	/**
	 * Tests get-setActiveModule.
	 */
	@Test
	public void testSetActiveModule() {
		Debug.setActiveModule(this);
		String expected1 = Debug.activeModuleName(this);
		assertEquals(expected1, Debug.getActiveModule());
		Debug.setActiveModule("Nada");
		String expected2 = Debug.activeModuleName("Nada");
		assertEquals(expected2, Debug.getActiveModule());
	}	
	
	/**
	 * Tests get-setActiveModule.
	 */
	@Test
	public void testReSetActiveModule() {
		String initialActiveModule = Debug.getActiveModule();
		Debug.setActiveModule(this);
		String expected1 = Debug.activeModuleName(this);
		Debug.setActiveModule("Nada");				
		Debug.resetActiveModule();
		assertEquals(expected1, Debug.getActiveModule());
		Debug.resetActiveModule();
		assertEquals(initialActiveModule, Debug.getActiveModule());
	}	
	
	/**
	 * test isEnabled
	 */
	@Test
	public void testIsEnabled(){
		Debug.setEnabled(true);
		assertTrue(Debug.isEnabled());
	}

	
	/**
	 * test debug
	 */
	@Test
	public void testDebug(){
		
		Logger target = LoggerFactory.getLogger(Debug.class);
		Debug.debug(target, "msg");
		
	}
}
