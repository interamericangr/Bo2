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
package gr.interamerican.wicket.components;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;





/**
 * Test suite for package <code>gr.interamerican.wicket.utils</code>.
 * 
 */
@SuppressWarnings("deprecation")
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestBigDecimalTextField.class,
		TestBooleanDdc.class,
		TestCallbackActionBehavior.class,
		TestDoubleTextField.class,
		TestFixedDigitsBigDecimalConverter.class,
		TestFixedDigitsDoubleConverter.class,
		TestPercentageBigDecimalConverter.class,
		TestPercentageBigDecimalTextField.class,
		TestPercentageDoubleConverter.class,
		TestPercentageTextField.class,
		
	
	}
)
public class TestSuiteComponents {
	//empty
}
