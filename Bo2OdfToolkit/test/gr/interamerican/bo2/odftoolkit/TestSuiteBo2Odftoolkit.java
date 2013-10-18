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
package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.odftoolkit.pdf.TestJodPdfEngine;
import gr.interamerican.bo2.odftoolkit.span.TestSuiteBo2OdftoolkitSpan;
import gr.interamerican.bo2.odftoolkit.utils.TestSuiteBo2OdftoolkitUtils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestOdfToolkitEngine.class,
		TestOdfToolkitTextDocument.class,
		TestOdfToolkitTable.class,        
		TestJodPdfEngine.class,
		TestSuiteBo2OdftoolkitUtils.class,	
		TestSuiteBo2OdftoolkitSpan.class,
	}
)
public class TestSuiteBo2Odftoolkit {
	/* empty */
}
