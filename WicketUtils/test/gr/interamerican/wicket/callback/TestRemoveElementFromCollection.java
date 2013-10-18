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
package gr.interamerican.wicket.callback;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Test for {@link RemoveElementFromCollection}.
 */
public class TestRemoveElementFromCollection {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {		
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();		
		BeanWithOrderedFields b = new BeanWithOrderedFields();
		RemoveElementFromCollection<BeanWithOrderedFields> remove =
			new RemoveElementFromCollection<BeanWithOrderedFields>(set, b);		
		assertEquals(set, remove.collection);
		assertEquals(b, remove.element);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testWork() {		
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();
		BeanWithOrderedFields b = new BeanWithOrderedFields();
		set.add(b);		
		RemoveElementFromCollection<BeanWithOrderedFields> remove =
			new RemoveElementFromCollection<BeanWithOrderedFields>(set, b);		
		remove.work();		
		assertFalse(set.contains(b));
		
	}
	
	
	
	


}
