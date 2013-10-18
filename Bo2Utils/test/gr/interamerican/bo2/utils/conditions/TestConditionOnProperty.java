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

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestConditionOnProperty {
	
	/**
	 * Tests check()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		EqualsIgnoreCaseCondition equals = new EqualsIgnoreCaseCondition("vava");
		ConditionOnProperty<BeanWith2Fields> conditionOnProperty = 
			new ConditionOnProperty<BeanWith2Fields>("field1", BeanWith2Fields.class, equals);
		BeanWith2Fields bean = new BeanWith2Fields();
		bean.setField1("vava");
		Assert.assertTrue(conditionOnProperty.check(bean));
	}
	
	/**
	 * Tests if the constructor throws a runtime exception 
	 * for an invalid property.
	 */
	@SuppressWarnings({ "nls", "unused" })
	@Test(expected=RuntimeException.class)
	public void testConstructor_withInvalidProperty() {
		EqualsIgnoreCaseCondition equals = new EqualsIgnoreCaseCondition("vava");
		ConditionOnProperty<BeanWith2Fields> conditionOnProperty = 
			new ConditionOnProperty<BeanWith2Fields>
			("nosuchfield", BeanWith2Fields.class, equals);
	}

}
