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
package gr.interamerican.bo2.utils.matching;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class TestOrMatchingRule {

	/**
	 * list with rules
	 */
	List<MatchingRule<BeanWith2Fields, BeanWith2Fields>> rulesList = 
		new ArrayList<MatchingRule<BeanWith2Fields, BeanWith2Fields>>();
	
	/**
	 * OrMatchingRule
	 */
	OrMatchingRule<BeanWith2Fields, BeanWith2Fields> orMatchingrule = new OrMatchingRule<BeanWith2Fields, BeanWith2Fields>(rulesList);
	

	/**
	 * test is Match
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsMatch(){
		
		BeanWith2Fields bean1 = new BeanWith2Fields("hello",1);
		BeanWith2Fields bean2 = new BeanWith2Fields("hello",2);
		
		MatchingRule<BeanWith2Fields, BeanWith2Fields> rule1 = 
			new SameTypeEqualPropertyMatchingRule<BeanWith2Fields>
			(BeanWith2Fields.class, "field1"); 
		
		MatchingRule<BeanWith2Fields, BeanWith2Fields> rule2 = 
			new SameTypeEqualPropertyMatchingRule<BeanWith2Fields>
			(BeanWith2Fields.class, "field2"); 
		
		rulesList.add(rule2);
		assertFalse(orMatchingrule.isMatch(bean1, bean2));
		
		rulesList.add(rule1);
		assertTrue(orMatchingrule.isMatch(bean1, bean2));
		
	}
	
}
