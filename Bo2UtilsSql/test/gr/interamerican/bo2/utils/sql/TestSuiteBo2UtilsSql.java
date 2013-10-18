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
package gr.interamerican.bo2.utils.sql;

import gr.interamerican.bo2.utils.sql.elements.TestSuiteBo2UtilsSqlElements;
import gr.interamerican.bo2.utils.sql.parsers.TestSuiteBo2UtilsSqlParsers;
import gr.interamerican.bo2.utils.sql.types.TestSuiteBo2UtilsSqlTypes;

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
		TestSqlUtils.class,
		TestVariableDefinitionFactory.class,
		TestSuiteBo2UtilsSqlTypes.class,
		TestSuiteBo2UtilsSqlParsers.class,
		TestSuiteBo2UtilsSqlElements.class,
		TestPsMetadataUtils.class,
	}
)
public class TestSuiteBo2UtilsSql {
	/* empty */
}
