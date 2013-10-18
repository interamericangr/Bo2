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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * 
 */
public class TestSelectableBoPropertyDescriptor {

	
	/**
	 * SelectableBoPropertyDescriptor
	 */
	SelectableBoPropertyDescriptor<String> descriptor = new SelectableBoPropertyDescriptor<String>();
	
	
	/**
	 * test SetChoices
	 */
	@Test
	public void testSetChoices(){
		Set<Selectable<String>> choices = new HashSet<Selectable<String>>();
		descriptor.setChoices(choices);
		assertEquals(descriptor.choices,choices);
	}
	
	/**
	 * test getChoices
	 */
	@Test
	public void testGetChoices(){
		Set<Selectable<String>> choices = new HashSet<Selectable<String>>();
		descriptor.setChoices(choices);
		assertEquals(descriptor.getChoices(),choices);
	}
	
	/**
	 * test parse()
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
		assertNull(descriptor.parse(null));
		Selectable<Long> expected = new TypedSelectableImpl<Long>();
		expected.setCode(1L);
		assertEquals(expected, descriptor.parse("1"));
	}
	
	/**
	 * test format()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat() {
		assertNull(descriptor.format(null));
		Selectable<String> sample = new TypedSelectableImpl<String>();
		sample.setCode("1");
		assertEquals("1", descriptor.format(sample));
	}
}
