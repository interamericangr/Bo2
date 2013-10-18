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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestTypeBasedSelection {

	/**
	 * TypeBasedSelection
	 */
	TypeBasedSelection<String> selection;
	
	/**
	 * Tests setup.
	 */
	@Before
	public void setup() {
		selection = new TypeBasedSelection<String>();
	}
	
	/**
	 * tests RegisterSelection
	 */
	@Test
	public void testRegisterSelection(){
		String value = "value"; //$NON-NLS-1$
		selection.registerSelection(IBeanWithIdAndNameImpl.class, value);
		assertTrue(selection.selections.size()==1);
	}
	
	/**
	 * tests SelectionForType
	 */
	@Test
	public void testSelectionForType(){
		String value = "value"; //$NON-NLS-1$
		selection.registerSelection(IBeanWithIdAndName.class, value);
		assertEquals(value,selection.selectionForType(IBeanWithIdAndName.class));
		assertEquals(value,selection.selectionForType(IBeanWithIdAndNameImpl.class));
		assertNull(selection.selectionForType(Object.class));
	}
	
	/**
	 * tests Select for null value
	 */
	@Test
	public void testSelect_nullValue(){
        assertNull(selection.select(null));
	}
	
	/**
	 * tests Select for a object that is not the same class 
	 */
	@Test
	public void testSelect_returningNull(){
		String value = "value"; //$NON-NLS-1$
		selection.registerSelection(IBeanWithIdAndNameImpl.class, value);
		assertNull(selection.select(new Object()));
	}
	
	/**
	 * tests Select for a object that is not the same class 
	 */
	@Test
	public void testSelect_findingValue(){
		String value = "value"; //$NON-NLS-1$
		selection.registerSelection(String.class, value);
		assertEquals(value,selection.select(value));
	}
	
	
	
	/**
	 * tests Select
	 */
	@Test
	public void testSelect_WhenItLearnTheSubTypes(){
		String value = "value"; //$NON-NLS-1$
		selection.registerSelection(Number.class, value);
		assertTrue(selection.selections.containsKey(Number.class));
		assertFalse(selection.selections.containsKey(Integer.class));
		Integer i = 2;
		assertEquals(value,selection.select(i));
		assertTrue(selection.selections.containsKey(Integer.class));
	}
	
}
