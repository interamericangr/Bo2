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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.utils.adapters.TestSuiteArchUtilsAdapters;
import gr.interamerican.bo2.arch.utils.beans.TestSuiteBo2ArchUtilsBeans;
import gr.interamerican.bo2.arch.utils.collections.TestSuiteBo2ArchUtilsCollections;
import gr.interamerican.bo2.arch.utils.comparators.TestSuiteBo2ArchUtilsComparators;
import gr.interamerican.bo2.arch.utils.copiers.TestSuiteBo2ArchUtilsCopiers;
import gr.interamerican.bo2.arch.utils.ext.TestSuiteBo2ArchUtilsExt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestCacheRegistry.class,
		TestSuiteBo2ArchUtilsBeans.class,
		TestSuiteBo2ArchUtilsCollections.class,
		TestBo2ArchStringUtils.class,
		TestCacheUtils.class,
		TestProviderUtils.class,
		TestMockUtils.class,
		TestMoneyUtils.class,
		TestSuiteArchUtilsAdapters.class,
		TestSuiteBo2ArchUtilsCopiers.class,
		TestSuiteBo2ArchUtilsComparators.class,
		TestSuiteBo2ArchUtilsExt.class,
		TestBo2ArchReflectionUtils.class,
		TestBo2ExceptionUtils.class,
	}
)
public class TestSuiteBo2ArchUtils {
	/* empty */
}
