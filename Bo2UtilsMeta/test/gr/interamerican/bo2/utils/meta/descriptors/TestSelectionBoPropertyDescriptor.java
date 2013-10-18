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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Unit test for {@link SelectionBoPropertyDescriptor}.
 */
public class TestSelectionBoPropertyDescriptor {
	
	/**
	 * SelectionBoPropertyDescriptor
	 */
	private SelectionBoPropertyDescriptor<Integer> selectDesc = new SelectionBoPropertyDescriptor<Integer>();
	
	/**
	 * Test getValues
	 */
	@Test
	public void testGetValues(){
		selectDesc.getValues();
	}
	
	/**
	 * Test setValues
	 */
	@Test
	public void testsetValues() {
		Set<Integer> values = new HashSet<Integer>(Arrays.asList(3,10,24));
		selectDesc.setValues(values);
	}
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test		
	public void testParse() throws ParseException{
		Set<Integer> values = new HashSet<Integer>(Arrays.asList(3,10,24));
        selectDesc.setValues(values);
   	    assertEquals((Integer)3,selectDesc.parse("3")); //$NON-NLS-1$
   	    assertEquals((Integer)24,selectDesc.parse("24")); //$NON-NLS-1$
   	    assertNull(selectDesc.parse("25")); //$NON-NLS-1$
	}	

}
