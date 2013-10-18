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
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Test for {@link AddElementToCollectionI}.
 */
public class TestAddElementToCollectionI {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		String property = "third"; //$NON-NLS-1$
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();
		BeanWithOrderedFields b = new BeanWithOrderedFields();		
		AddElementToCollectionI<BeanWithOrderedFields> add =
			new AddElementToCollectionI<BeanWithOrderedFields>(set, b, property);
		assertEquals(property, add.indexPropertyName);
		assertEquals(set, add.collection);
		assertEquals(b, add.element);		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testWork() {
		String property = "third"; //$NON-NLS-1$
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();
		BeanWithOrderedFields b = new BeanWithOrderedFields();
		b.setThird(null);
		AddElementToCollectionI<BeanWithOrderedFields> add =
			new AddElementToCollectionI<BeanWithOrderedFields>(set, b, property);
		add.work();		
		assertTrue(set.contains(b));
		assertEquals(Integer.valueOf(1), b.getThird());
	}
	
	
	
	


}
