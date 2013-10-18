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
package gr.interamerican.bo2.gui.components;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link BPanel}.
 */
public class TestBPanelForMap {
	
	/**
	 * Creates a map with two entries, with keys field1 and field2.
	 * 
	 * @return Returns the map.
	 */
	@SuppressWarnings("nls")
	static Map<Object, Object> mapWithTwoEntries() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("field1", "field1");
		map.put("field2", "field2");
		return map;
	}
	
	/**
	 * Field0, field1
	 */
	static final String[] FIRSTFIELDS = { "field0", "field1" }; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_FirstFieldsAndMore() {
		Map<Object, Object> map = mapWithTwoEntries();		
		BPanelForMap<Map<Object, Object>> sample = 
			new BPanelForMap<Map<Object, Object>>(map,FIRSTFIELDS,false);
		Assert.assertEquals(map,sample.getModel());
		Assert.assertArrayEquals(FIRSTFIELDS,sample.propertyNames);
		Assert.assertFalse(sample.onlyPredefined);
		Assert.assertEquals(6, sample.getComponentCount());		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_FirstFieldsOnly() {
		Map<Object, Object> map = mapWithTwoEntries();		
		BPanelForMap<Map<Object, Object>> sample = 
			new BPanelForMap<Map<Object, Object>>(map,FIRSTFIELDS,true);
		Assert.assertEquals(map,sample.getModel());
		Assert.assertArrayEquals(FIRSTFIELDS,sample.propertyNames);
		Assert.assertTrue(sample.onlyPredefined);
		Assert.assertEquals(4, sample.getComponentCount());		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_FirstFieldsWithEmptyMap() {
		Map<Object, Object> map = new HashMap<Object, Object>();	
		BPanelForMap<Map<Object, Object>> sample = 
			new BPanelForMap<Map<Object, Object>>(map,FIRSTFIELDS,false);
		Assert.assertEquals(map,sample.getModel());
		Assert.assertArrayEquals(FIRSTFIELDS,sample.propertyNames);
		Assert.assertFalse(sample.onlyPredefined);
		Assert.assertEquals(4, sample.getComponentCount());		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_NoFirstFields() {
		Map<Object, Object> map = mapWithTwoEntries();
		BPanelForMap<Map<Object, Object>> sample = 
			new BPanelForMap<Map<Object, Object>>(map,null,false);
		Assert.assertEquals(map,sample.getModel());
		Assert.assertEquals(0,sample.propertyNames.length);
		Assert.assertFalse(sample.onlyPredefined);
		Assert.assertEquals(4, sample.getComponentCount());		
	}
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_NoFirstFieldsEmptyMap() {
		Map<Object, Object> map = new HashMap<Object, Object>();	
		BPanelForMap<Map<Object, Object>> sample = 
			new BPanelForMap<Map<Object, Object>>(map,null,false);
		Assert.assertEquals(map,sample.getModel());
		Assert.assertEquals(0,sample.propertyNames.length);
		Assert.assertFalse(sample.onlyPredefined);
		Assert.assertEquals(0, sample.getComponentCount());		
	}
	
	
}
