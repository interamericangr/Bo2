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
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.matching.MatchingRule;
import gr.interamerican.bo2.utils.matching.SameTypeEqualPropertyMatchingRule;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test for {@link InstanceOf}.
 */
public class TestMatchingCondition {
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck() {
		String val = "value"; //$NON-NLS-1$
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField1(val);
		BeanWith2Fields b2 = new BeanWith2Fields();
		b2.setField1(val);
		MatchingRule<BeanWith2Fields, BeanWith2Fields> rule = 
			new SameTypeEqualPropertyMatchingRule<BeanWith2Fields>
			(BeanWith2Fields.class, "field1"); //$NON-NLS-1$
		MatchingCondition<BeanWith2Fields, BeanWith2Fields> condition = 
			new MatchingCondition<BeanWith2Fields, BeanWith2Fields>(rule);
		Pair<BeanWith2Fields, BeanWith2Fields> pair = 
			new Pair<BeanWith2Fields, BeanWith2Fields>(b1, b2); 
		Assert.assertTrue(condition.check(pair));
		
	}

}
