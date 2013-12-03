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
package gr.interamerican.bo2.impl.open.jee;

import gr.interamerican.bo2.impl.open.jee.jdbc.TestJndiConnectionStrategy;
import gr.interamerican.bo2.impl.open.jee.jta.TestJtaTransactionManager;
import gr.interamerican.bo2.impl.open.jee.servlet.TestAbstractBaseLoggingFilter;
import gr.interamerican.bo2.impl.open.jee.servlet.TestHttpServletUtils;
import gr.interamerican.bo2.impl.open.jee.servlet.TestSoapLoggingFilter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open.jee</code>.
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{			
		TestJndiConnectionStrategy.class,
		TestJtaTransactionManager.class,
		TestAbstractBaseLoggingFilter.class,
		TestSoapLoggingFilter.class,
		TestHttpServletUtils.class,
	}
)
public class TestSuiteBo2ImplOpenJee {
	/* empty */
}
