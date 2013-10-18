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
package gr.interamerican.wicket.bo2.factories.meta;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 */

/**
 * Test suite for package <code>gr.interamerican.wicket.bo2.factories.meta</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestBigDecimalBoPDComponentFactory.class,
		TestBooleanBoPDComponentFactory.class,
		TestCachedEntryBoPDComponentFactory.class,
		TestDateBoPDComponentFactory.class,
		TestDoubleBoPDComponentFactory.class,
		TestFloatBoPDComponentFactory.class,
		TestIntegerBoPDComponentFactory.class,
		TestLongBoPDComponentFactory.class,
		TestStringBoPDComponentFactory.class,
		TestTranslatableBoPDWrapperComponentFactory.class,
		TestBoPDTypeBasedFactorySelection.class,
		TestGenericBoPDComponentFactory.class,
		TestCachedEntryOwnerBoPDComponentFactory.class,
		TestPalleteCachedEntriesForBoPDComponentFactory.class,
		TestSelectableBoPDComponentFactory.class,
		TestMoneyBoPDComponentFactory.class,
		TestMultipleChoiceCachedEntryBoPDFactory.class,
		TestSelectionBoPDComponentFactory.class
	}
)
public class TestSuiteComponentFactories {
	/* empty */
}
