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

import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotDelistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotEnlistException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.NoTransactionManagerException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.ProviderCreationException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.exceptions.WebServiceException;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.PdfEngineException;
import gr.interamerican.bo2.utils.ftp.FtpException;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * Unit test for the creation of Throwables.
 * 
 * The same test tests the Throwables declared in the bo2 architecture.
 */
@RunWith(Parameterized.class)
public class TestThrowableCreationTest extends ThrowableCreationTest {
	
	/**
	 * Creates a new TestCreation object. 
	 *
	 * @param className
	 * @throws ClassNotFoundException
	 */
	public TestThrowableCreationTest(String className) throws ClassNotFoundException {
		super(className);
	}

	/**
	 * Test parameters.
	 * @return Returns the test parameters.
	 * 
	 */
	@Parameters
	public static Collection<?> getParameters() {
		String[][] classNames = {
			/* Bo2Architecture */	
			{CouldNotBeginException.class.getName()},
			{CouldNotCommitException.class.getName()},
			{CouldNotRollbackException.class.getName()},
			{CouldNotEnlistException.class.getName()},
			{CouldNotDelistException.class.getName()},			
			{DataAccessException.class.getName()},
			{DataException.class.getName()},
			{DataOperationNotSupportedException.class.getName()},
			{InitializationException.class.getName()},
			{LogicException.class.getName()},
			{NoTransactionManagerException.class.getName()},
			{PoNotFoundException.class.getName()},
			{ProviderCreationException.class.getName()},
			{RuleException.class.getName()},
			{UnexpectedException.class.getName()},
			{WebServiceException.class.getName()},
			
			/* Bo2Utils */
			{FtpException.class.getName()},
			
			/* Bo2Creation */
			{ClassCreationException.class.getName()},
			
			/* Bo2UtilsSql */
			{SqlParseException.class.getName()},
			
			/* Bo2UtilsDoc */
			{DocumentEngineException.class.getName()},
			{PdfEngineException.class.getName()},
			
		};
		return Arrays.asList(classNames);
		
	 }	
	
	

}
