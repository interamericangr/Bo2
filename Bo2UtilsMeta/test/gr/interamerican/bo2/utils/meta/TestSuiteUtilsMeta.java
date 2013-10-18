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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.utils.meta.convert.TestSuiteUtilsMetaConvert;
import gr.interamerican.bo2.utils.meta.descriptors.TestSuiteUtilsMetaDescriptors;
import gr.interamerican.bo2.utils.meta.exceptions.TestSuiteUtilsMetaExceptions;
import gr.interamerican.bo2.utils.meta.expressions.TestSuiteExpressions;
import gr.interamerican.bo2.utils.meta.factories.TestSuiteUtilsMetaFactories;
import gr.interamerican.bo2.utils.meta.formatters.TestSuiteUtilsMetaFormatters;
import gr.interamerican.bo2.utils.meta.parsers.TestSuiteUtilsMetaParsers;
import gr.interamerican.bo2.utils.meta.transformations.TestSuiteUtilsMetaTransformations;
import gr.interamerican.bo2.utils.meta.validators.TestSuiteUtilsMetaValidators;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open.po.collections</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestBasicBusinessObjectDescriptor.class,
		TestMediator.class,
		TestMeta.class,
		TestConversionUtils.class,
		TestDescriptorUtils.class,
		TestValidators.class,
		TestSuiteUtilsMetaDescriptors.class,
		TestSuiteUtilsMetaParsers.class,
		TestSuiteUtilsMetaValidators.class,
		TestSuiteUtilsMetaExceptions.class,
		TestSuiteUtilsMetaFormatters.class,
		TestSuiteUtilsMetaConvert.class,
		TestSuiteUtilsMetaTransformations.class,
		TestSuiteUtilsMetaFactories.class,
		TestSuiteExpressions.class
	}
)
public class TestSuiteUtilsMeta {
	/* empty */
}
