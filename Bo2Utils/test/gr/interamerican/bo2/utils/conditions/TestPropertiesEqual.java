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
package gr.interamerican.bo2.utils.conditions;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.samples.SampleBean2;

/**
 * Unit test for {@link PropertiesEqual}.
 * @deprecated Check {@link gr.interamerican.bo2.utils.conditions.functional.PropertiesEqual}.
 */
@Deprecated
public class TestPropertiesEqual {

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.utils.conditions.PropertiesEqual#PropertiesEqual(java.lang.String[], java.lang.Object[], java.lang.Class)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPropertiesEqual() {
		PropertiesEqual<SampleBean2> tested = new PropertiesEqual<SampleBean2>(new String[] { "field1" }, new String[] { "a" }, SampleBean2.class);
		assertNotNull(tested.and);
		assertEquals(1, tested.and.conditions.size());
		assertEquals(PropertyEqualsTo.class, tested.and.conditions.get(0).getClass());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.bo2.utils.conditions.PropertiesEqual#check(java.lang.Object)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		PropertiesEqual<SampleBean2> tested = new PropertiesEqual<SampleBean2>(new String[] { "field1" }, new String[] { "a" }, SampleBean2.class);
		SampleBean2 sampleBean = new SampleBean2();
		sampleBean.setField1("a");
		assertTrue(tested.check(sampleBean));
	}

}
