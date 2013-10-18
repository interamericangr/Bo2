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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;

import java.lang.reflect.Type;

import org.junit.Test;

/**
 * Unit test for {@link JdbcSimpleQuestion}.
 */
public class TestJdbcSimpleQuestion extends AbstractNonTransactionalProviderTest {

	/**
	 * simpleQuestion to test
	 */
	JdbcSimpleQuestionImpl question = new JdbcSimpleQuestionImpl();
	
	/**
	 * tests a question
	 * @throws InitializationException 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecuteQuestion() 
	throws InitializationException, DataException, LogicException {	
		question.init(provider);
		question.open();
		question.ask();
		question.close();
		Integer ans = question.getAnswer();
		assertNotNull(ans);
	}
	
	/**
	 * Unit test for answer type.
	 */
	@Test
	public void testAnswerType() {
		JdbcSimpleQuestionImpl impl = new JdbcSimpleQuestionImpl();
		Type type = impl.answerType();
		assertEquals(Integer.class, type);
	}
	
	
	
	/**
	 * implementation to test
	 */
	@ManagerName("LOCALDB")
	private class JdbcSimpleQuestionImpl extends JdbcSimpleQuestion<Integer>{		
		/**
		 * sgl.
		 */		
		@Sql @SuppressWarnings("unused")
		private String sql = "select MAX(id) as answer from test.users"; //$NON-NLS-1$
		
	}
	
}
