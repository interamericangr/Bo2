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
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;

/**
 * Test for {@link PropertiesAreNotNull}.
 */
public class TestPropertiesAreNotNull {

	/**
	 * Test for check.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCheck() {
		@SuppressWarnings("deprecation")
		PropertiesAreNotNull<BeanWith3Fields> condition1 = new PropertiesAreNotNull<BeanWith3Fields>(
				BeanWith3Fields.class, "field1", "field2", "field3");
		PropertiesAreNotNull<BeanWith3Fields> condition2 = new PropertiesAreNotNull<>(BeanWith3Fields::getField1,
				BeanWith3Fields::getField2, BeanWith3Fields::getField3);
		BeanWith3Fields bean = new BeanWith3Fields();
		assertFalse(condition1.check(bean));
		assertFalse(condition2.check(bean));
		bean.setField1("field1");
		assertFalse(condition1.check(bean));
		assertFalse(condition2.check(bean));
		bean.setField2(10);
		assertFalse(condition1.check(bean));
		assertFalse(condition2.check(bean));
		bean.setField3(5d);
		assertTrue(condition1.check(bean));
		assertTrue(condition2.check(bean));
	}
}