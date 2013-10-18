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
package gr.interamerican.bo2.utils.sql.types;

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
		TestAbstractType.class,
		TestBigDecimalType.class,
		TestBytesType.class,
		TestByteType.class,
		TestCalendarType.class,
		TestCharacterType.class,
		TestDateType.class,
		TestTimeType.class,
		TestDoubleType.class,
		TestFloatType.class,
		TestIntegerType.class,
		TestLongType.class,
		TestNonQuotedType.class,
		TestObjectType.class,
		TestQuotedType.class,
		TestShortType.class,
		TestStringType.class,
		TestTimestampType.class,
		TestTypeUtils.class
	}
)
public class TestSuiteBo2UtilsSqlTypes {
	/* empty */
}
