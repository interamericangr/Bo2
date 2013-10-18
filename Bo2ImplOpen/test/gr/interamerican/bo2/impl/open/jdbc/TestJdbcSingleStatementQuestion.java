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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Unit test for {@link JdbcSimpleQuestion}.
 */
public class TestJdbcSingleStatementQuestion extends AbstractNonTransactionalProviderTest {
	
	
	/**
	 * Asks for an id.
	 * 
	 * @param question 	  
	 * @param id
	 * 
	 * @return Returns the answer.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 * @throws LogicException
	 */
	Boolean ask(AbstractJdbcSingleStatementQuestionSample question, int id) 
	throws InitializationException, DataException, LogicException {
		question.init(provider);
		question.open();		
		question.setId(id);
		question.ask();
		question.close();
		return question.getAnswer();
	}
	
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
		Boolean answer = ask(new QuestionWithNamedParams(), id);
		assertTrue(answer);
	}
	
	/**
	 * tests the question that uses the annotation.
	 * 
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion_withAnno() 
	throws InitializationException, DataException, LogicException {	
		Integer id = UtilityForBo2Test.getExistingUserId();
		Boolean answer = ask(new QuestionWithAnno(), id);
		assertTrue(answer);
	}	
	
	/**
	 * tests the question that uses the annotation.
	 * 
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion_withoutParams() 
	throws InitializationException, DataException, LogicException {
		Boolean answer = ask(new QuestionWithoutParams(), 0);
		assertTrue(answer);
	}	
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	private abstract class AbstractJdbcSingleStatementQuestionSample 
	extends JdbcSingleStatementQuestion<Boolean> {	
		
		/**
		 * id parameter.
		 */		
		@Parameter private Integer id;
		
		/**
		 * Answer.
		 */
		private Boolean answer;
	

		/**
		 * Assigns a new value to the id.
		 *
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		
		@Override
		protected void work() throws DataException, LogicException {
			answer = false;
			String stmt = sql();
			try {			
				Object[] parms = this.getParamsFromNamedParams();
				ResultSet rs = executePreparedQuery(stmt,parms);
				if (rs.next()) {				
					answer = true;
				}
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}			
		
		@Override
		public Boolean getAnswer() {			
			return answer;
		}
		
	}
	
	
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	@ParametersOrder("id")
	private class QuestionWithAnno 
	extends AbstractJdbcSingleStatementQuestionSample{	
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select 1 from X__X.users where id = :specifiedId"; //$NON-NLS-1$	
	}
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	private class QuestionWithNamedParams 
	extends AbstractJdbcSingleStatementQuestionSample {					
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select 1 from X__X.users where id = :id"; //$NON-NLS-1$
	}
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB") @SuppressWarnings("unused")
	private class QuestionWithoutParams 
	extends AbstractJdbcSingleStatementQuestionSample {					
		/**
		 * sgl.
		 */		
		@Sql private String sql = "select 1 from X__X.users"; //$NON-NLS-1$
	}
	
}
