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
package gr.interamerican.bo2.utils.conditions.functional;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Unit test for {@link PropertiesEqual}.
 */
public class TestPropertiesEqual {

	/**
	 * Test method for {@link gr.interamerican.bo2.utils.conditions.functional.PropertiesEqual#PropertiesEqual(Match...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPropertiesEqual() {
		Match<SampleBean2, String> sampleMatch = new Match<SampleBean2, String>(SampleBean2::getField1,"a");
		PropertiesEqual<SampleBean2> tested = new PropertiesEqual<>(sampleMatch);
		assertNotNull(tested.and);
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.utils.conditions.functional.PropertiesEqual#getCondition(gr.interamerican.bo2.utils.conditions.functional.Match)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetCondition() {
		Match<SampleBean2, String> sampleMatch = new Match<SampleBean2, String>(SampleBean2::getField1,"a");
		PropertiesEqual<SampleBean2> tested = new PropertiesEqual<>(sampleMatch);
		
		Condition<SampleBean2> actual = tested.getCondition(sampleMatch);
		assertNotNull(actual);
		assertEquals(PropertyEqualsTo.class, actual.getClass());
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.utils.conditions.functional.PropertiesEqual#check(java.lang.Object)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		Match<SampleBean2, String> sampleMatch = new Match<SampleBean2, String>(SampleBean2::getField1,"a");
		PropertiesEqual<SampleBean2> tested = new PropertiesEqual<>(sampleMatch);

		SampleBean2 sampleBean = new SampleBean2();
		sampleBean.setField1("a");
		assertTrue(tested.check(sampleBean));
	}

}
