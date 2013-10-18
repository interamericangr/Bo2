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
package gr.interamerican.bo2.impl.open.jdbc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Test;

/**
 * Unit test for {@link JdbcSimpleQuestion}.
 */
public class TestJdbcExistsQuestion extends AbstractNonTransactionalProviderTest {

	/**
	 * simpleQuestion to test
	 */
	JdbcExistsQuestionImpl question = new JdbcExistsQuestionImpl();
	
	/**
	 * tests a question
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion_withTrueAnswer() 
	throws InitializationException, DataException, LogicException {		
		Integer id = UtilityForBo2Test.getExistingUserId();
		assertTrue(ask(id));
	}
	
	
	/**
	 * Asks for an id.
	 * 
	 * @param id
	 * 
	 * @return Returns the answer.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws LogicException
	 */
	Boolean ask(int id) 
	throws InitializationException, DataException, LogicException {
		question.init(provider);
		question.open();		
		question.setId(id);
		question.ask();
		question.close();
		return question.getAnswer();
	}
	
	/**
	 * tests a question
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion_withFalseAnswer() 
	throws InitializationException, DataException, LogicException {	
		Integer id = UtilityForBo2Test.getNotExistingUserId();
		assertFalse(ask(id));
	}
	
		
	
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	@ParametersOrder("id")
	private class JdbcExistsQuestionImpl extends JdbcExistsQuestion{	
		/**
		 * id parameter.
		 */
		
		@Parameter private Integer id;
		
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select 1 from X__X.users where id = ?"; //$NON-NLS-1$
		

		/**
		 * Assigns a new value to the id.
		 *
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		
	}
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	private class JdbcExistsQuestionImplWithNamedParams extends JdbcExistsQuestion{	
		/**
		 * id parameter.
		 */
		
		@Parameter private Integer id;
		
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select 1 from X__X.users where id = :id"; //$NON-NLS-1$
		

		/**
		 * Assigns a new value to the id.
		 *
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		
	}
	
}
