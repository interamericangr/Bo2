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


import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Unit test of JdbcQuery.
 * 
 *
 */
public class TestJdbcQuestion extends AbstractNonTransactionalProviderTest  {
	
	
	/**
	 * tests a question
	 * @throws DataException 
	 * @throws InitializationException 
	 * @throws LogicException 
	 */
	@Test
	public void testExecuteQuestion() 	
	throws DataException, InitializationException, LogicException {		
		JdbcQuestionImpl question = new JdbcQuestionImpl();
		question.init(provider);
		question.open();
		question.setCode(1);
		question.ask();
		question.close();		
	}
	
	
	/**
	 * Simple Implementation of JdbcQuestion
	 * 
	 *
	 */
	@ManagerName("LOCALDB")
	private static class JdbcQuestionImpl extends JdbcQuestion<Object> {
		
		/**
		 * sgl
		 */
		private String stmt = "select * from TEST.users where ID = ?"; //$NON-NLS-1$

		/**
		 * Results
		 */
		ResultSet rs;
		
		/**
		 * id
		 */
		private Integer code = 0;
		
		/**
		 * Sets the id
		 * @param c
		 */
		public void setCode(Integer c){
			code=c;
		}

		@Override
		protected void work()
				throws gr.interamerican.bo2.arch.exceptions.DataException,
				gr.interamerican.bo2.arch.exceptions.LogicException {
			try {
				Object[] params = new Object[1];
				params[0] = code;
			    rs = executePreparedQuery(stmt,params);

			} catch (SQLException e) {
				throw new gr.interamerican.bo2.arch.exceptions.DataException(e);
			}
			
		}

		@Override
		public Object getAnswer() {
			return rs;
		}


	
	}
	
	
	


	

	
	

		

}
