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
package gr.interamerican.bo2.utils.adapters.trans;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.IsNotNull;

/**
 * Unit test for {@link Sequence}.
 */
public class TestSequence {

	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		BeanWith2Fields bean = new BeanWith2Fields();		
		bean.setField1("value"); //$NON-NLS-1$
		bean.setField2(2);
		Condition<String> notNull = new IsNotNull<String>();
		ConditionAdapter<String> notNullResult = new ConditionAdapter<String>(notNull);
		Transformation<BeanWith2Fields, String> t = BeanWith2Fields::getField1;
		Sequence<BeanWith2Fields, Boolean> sequence = 
			new Sequence<BeanWith2Fields, Boolean>(t, notNullResult);
		Boolean val = sequence.execute(bean);
		Assert.assertTrue(val);
	}
}