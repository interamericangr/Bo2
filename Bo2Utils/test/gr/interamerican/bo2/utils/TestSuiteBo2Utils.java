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
package gr.interamerican.bo2.utils;

import gr.interamerican.bo2.utils.adapters.TestSuiteBo2UtilsAdapters;
import gr.interamerican.bo2.utils.beans.TestSuiteBo2UtilsBeans;
import gr.interamerican.bo2.utils.comparators.TestSuiteBo2UtilsComparators;
import gr.interamerican.bo2.utils.concurrent.TestSuiteBo2UtilsConcurrent;
import gr.interamerican.bo2.utils.conditions.TestSuiteBo2UtilsConditions;
import gr.interamerican.bo2.utils.enums.TestSuiteBo2UtilsEnums;
import gr.interamerican.bo2.utils.ftp.TestSuiteBo2UtilsFtp;
import gr.interamerican.bo2.utils.handlers.TestSuiteBo2UtilsHandlers;
import gr.interamerican.bo2.utils.mail.TestSuiteBo2UtilsMail;
import gr.interamerican.bo2.utils.matching.TestSuiteMatching;
import gr.interamerican.bo2.utils.reflect.TestSuiteBo2UtilsReflect;
import gr.interamerican.bo2.utils.reflect.analyze.TestAbstractObjectStructureAnalyzer;
import gr.interamerican.bo2.utils.reflect.analyze.TestSuiteBo2UtilsReflectAnalyze;
import gr.interamerican.bo2.utils.runnables.TestSuiteBo2UtilsRunnables;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;




/**
 * Test suite for package <code>gr.interamerican.bo2.utils</code>.
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{			
		TestStringUtils.class,
		TestTokenUtils.class,
		TestDateUtils.class,
		TestNumberUtils.class,
		TestArrayUtils.class,
		TestUtils.class,
		TestCollectionUtils.class,
		TestSelectionUtils.class,		
		TestMatchingUtils.class,
		TestDebug.class,		
		TestDefaults.class,
		TestExceptionUtils.class,
		TestTemplateUtils.class,
		TestStreamUtils.class,
		TestReflectionUtils.class,
		TestJavaBeanUtils.class,
		TestCopyPropertiesForReflectionUtils.class,
		TestCopyPropertiesForJavaBeanUtils.class,
		TestGenericsUtils.class,
		TestVariableDefinitionFactory.class,
		TestConditionUtils.class,
		TestAdapterUtils.class,
		TestSystemUtils.class,
		TestRegexUtils.class,
		TestTimeUtils.class,
		TestEnhancedProperties.class,
		TestAbstractObjectStructureAnalyzer.class,
		TestIllegalCharacterFilter.class,
		TestDependencyGraphs.class,
		TestSuiteBo2UtilsAdapters.class,
		TestSuiteBo2UtilsConditions.class,
		TestSuiteBo2UtilsComparators.class,
		TestSuiteBo2UtilsBeans.class,		
		TestSuiteMatching.class,
		TestSuiteBo2UtilsReflectAnalyze.class,	
		TestSuiteBo2UtilsEnums.class,
		TestSuiteBo2UtilsReflect.class,
		TestSuiteBo2UtilsMail.class,
		TestSuiteBo2UtilsConcurrent.class,
		TestSuiteBo2UtilsHandlers.class,
		TestSuiteBo2UtilsFtp.class,
		TestSuiteBo2UtilsRunnables.class,
	}
)
public class TestSuiteBo2Utils {
	/* empty */
}
