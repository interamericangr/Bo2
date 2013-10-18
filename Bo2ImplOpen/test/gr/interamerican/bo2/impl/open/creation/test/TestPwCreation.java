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
package gr.interamerican.bo2.impl.open.creation.test;

import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.samples.archutil.po.User;

import java.io.IOException;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit test for object creation.
 * 
 * This test here, actually tests the reusable test ObjectCreationTest.
 */
@RunWith(Parameterized.class)
public class TestPwCreation extends PersistenceWorkerCreationTest {
	/**
	 * Setup tests.
	 */
	@BeforeClass
	public static void beforeClass() {
		Bo2AnnoUtils.setManagerName(User.class, "LOCALDB"); //$NON-NLS-1$
	}
	
	/**
	 * Creates a new TestCreation object. 
	 *
	 * @param className
	 * @throws ClassNotFoundException
	 */
	public TestPwCreation(String className) throws ClassNotFoundException {
		super(className);
	}

	/**
	 * Test parameters.
	 * @return Returns the test parameters.
	 * @throws IOException 
	 */
	@Parameters
	public static Collection<?> getParameters() throws IOException {		
		String path = "/gr/interamerican/rsrc/TestPwCreation.txt"; //$NON-NLS-1$
		return parameters(path);
	 }	
	
	

}
