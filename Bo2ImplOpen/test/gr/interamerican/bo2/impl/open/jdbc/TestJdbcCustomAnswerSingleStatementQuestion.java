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

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import org.junit.Test;

/**
 * Test for {@link JdbcCustomAnswerSingleStatementQuestion}.
 */
public class TestJdbcCustomAnswerSingleStatementQuestion extends AbstractNonTransactionalProviderTest {
	
	/**
	 * tests the question that uses named parameters.
	 * 
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion_withNamedParams() 
	throws InitializationException, DataException, LogicException {	
		Integer id = UtilityForBo2Test.getExistingUserId();
		JdbcCustomAnswerQuestionSample question = new JdbcCustomAnswerQuestionSample();
		question.init(provider);
		question.open();		
		question.id = id;
		question.ask();
		Boolean answer = question.getAnswer();
		question.close();
		
		assertTrue(answer);
		
	}
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB")
	private class JdbcCustomAnswerQuestionSample 
	extends JdbcCustomAnswerSingleStatementQuestion<Boolean> {	
		
		/**
		 * id parameter.
		 */		
		@Parameter Integer id;
		
		/**
		 * sgl.
		 */		
		@Sql private static final String sql = "select 1 from X__X.users where id = :id"; //$NON-NLS-1$	
		
		@Override
		protected Boolean createAnswer() throws DataAccessException {
			return getBoolean(1); 
		}
		
		@Override
		public Boolean getAnswer() {
			if(super.getAnswer()==null) {
				return false;
			}
			return super.getAnswer();
		}
		
	}
	
}
