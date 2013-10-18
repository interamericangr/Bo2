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

import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.sql.types.TypeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a question based on one and only one SQL statement.
 * 
 * The SQL statement is defined by annotating a field or a method of
 * the class with the {@link Sql} annotation. The SQL statement should 
 * return only one row that contains a column named "answer".
 * The answer is retrieved from this question. 
 * The question can have as many parameters as required. The values of 
 * the parameters are specified from the fields that are annotated with
 * the {@link Parameter} annotation. There are two alternatives for 
 * defining the order of the parameters. The first alternative is to
 * use the functionality of {@link AbstractJdbcWorker}, namely to define
 * the order in a {@link ParametersOrder} annotation. If the class has 
 * this annotation, then this method will be followed. Alternatively
 * the parameters can be defined as named parameters in the sql statement.
 * In this case, the order of their appearance in the statement is the
 * required order.
 * 
 * @param <A> 
 *        Type of answer.
 */
public abstract class JdbcSimpleQuestion<A> extends JdbcSingleStatementQuestion<A> {
	
	/**
	 * Object that holds the answer.
	 */
	A answer;	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws DataException, LogicException {
		answer = null;
		String stmt = sql();
		try {
			Object[] parms = this.getParamsFromNamedParams();
			ResultSet rs = executePreparedQuery(stmt,parms);
			if (rs.next()) {
				Class<?> answerClass = answerType();
				gr.interamerican.bo2.utils.sql.types.Type<?> sqlBindingType = 
					TypeUtils.getTypeForClass(answerClass);
				Object ob = sqlBindingType.get(rs, "answer"); //$NON-NLS-1$
				answer = (A) ob;
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}	
	
	@Override
	public A getAnswer() {	
		return answer;
	}
	
	/**
	 * Gets the type of answer.
	 * 
	 * @return Returns the type of answer.
	 */
	Class<?> answerType() {
		Class<?> answerType = answerTypes.get(this.getClass());
		if(answerType!=null) {
			return answerType;
		}
		answerType = GenericsUtils.findTypeParameter(this.getClass(), Question.class, null);
		if(answerType!=null) {
			answerTypes.put(this.getClass(), answerType);
		}
		return answerType;
	}
		
}
