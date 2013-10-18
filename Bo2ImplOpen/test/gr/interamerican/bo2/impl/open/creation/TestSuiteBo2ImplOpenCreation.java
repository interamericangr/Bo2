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
package gr.interamerican.bo2.impl.open.creation;

import gr.interamerican.bo2.impl.open.creation.templatebean.TestSuiteBo2ImplOpenCreationTemplatebean;
import gr.interamerican.bo2.impl.open.creation.test.TestSuiteBo2ImplOpenCreationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Test suite for package <code>gr.interamerican.bo2.impl.open.creation</code>.
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
	
		TestImpl4Abstract.class,
		TestImpl4Interfaces.class,
		
		TestAbstractBasePo.class,	
		TestPersistenceWorkerFactoryImpl.class,
		TestFactory.class,
		
		TestSuiteBo2ImplOpenCreationTemplatebean.class,		
		TestSuiteBo2ImplOpenCreationTest.class,
	}
)
public class TestSuiteBo2ImplOpenCreation {
	/* empty */
}
