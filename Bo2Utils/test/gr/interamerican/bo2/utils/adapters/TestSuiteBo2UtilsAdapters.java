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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.adapters.date.TestSuiteBo2UtilsAdaptersDate;
import gr.interamerican.bo2.utils.adapters.number.TestSuiteBo2UtilsAdaptersNumber;
import gr.interamerican.bo2.utils.adapters.string.TestSuiteBo2UtilsAdaptersString;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;




/**
 * Test suite for package <code>gr.interamerican.bo2.utils.adapters</code>.
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{	
		TestNumberCoverter.class,
		TestGetTheSame.class,
		TestConditionAdapter.class,
		TestFilter.class,
		TestGetProperty.class,
		TestGetProperties.class,
		TestSequence.class,
		TestGetRangeFromProperties.class,
		TestToString.class,
		TestPropertyValuesChecker.class,
		TestPropertyValuesNotNullChecker.class,
		TestCreate.class,
		TestMultipleStepsCreate.class,
		TestCopyFromProperties.class,
		TestReplaceWith.class,
		TestPeriodicCommand.class,
		TestSingleSubjectOperation.class,
		TestCopyFromBeans.class,
		TestCollectionToArray.class,
		TestRefresh.class,
		TestSuiteBo2UtilsAdaptersDate.class,
		TestSuiteBo2UtilsAdaptersNumber.class,
		TestSuiteBo2UtilsAdaptersString.class,
	}
)
public class TestSuiteBo2UtilsAdapters {
	/* empty */
}
