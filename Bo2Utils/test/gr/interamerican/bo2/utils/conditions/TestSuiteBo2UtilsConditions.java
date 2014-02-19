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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for package <code>gr.interamerican.bo2.utils.comparators</code>.
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{			
		TestIsNull.class,
		TestIsNotNull.class,
		TestEqualityCondition.class,
		TestEqualsIgnoreCaseCondition.class,
		TestGreaterThan.class,
		TestGreaterThanOrEqual.class,
		TestLessThan.class,
		TestLessThanOrEqual.class,
		TestEqualTo.class,
		TestTrue.class,
		TestFalse.class,
		TestAnd.class,
		TestOr.class,
		TestNot.class,		
		TestInstanceOf.class,
		TestConditionOnTransformation.class,
		TestConditionOnProperty.class,
		TestMatchingCondition.class,
		TestExistsIn.class,
		TestIsWithinRange.class,
		TestPropertiesAreNotNull.class,
		TestPropertyEqualsTo.class,
		TestPropertyExistsIn.class,
		TestPropertyIsWithinRange.class,
		TestRangeContainsValue.class,
		TestValueIsWithinRangeDefinedByProperties.class,
		TestStringNotEmpty.class,
		TestNotInitialized.class,
		TestArrayHasOnlyNulls.class,
		TestComparisonCondition.class,		
		TestInvokeMethodCondition.class,
	}
)
public class TestSuiteBo2UtilsConditions { 
	/* empty */
}
