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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.List;

/**
 * QueryWrapperQuestion is a question backed by an {@link EntitiesQuery}.<br/>
 * 
 * The answer is a list that contains the entities fetched by the query.
 * 
 * 
 * @param <T> 
 */
public class QueryWrapperQuestion<T>
extends AbstractBaseWorker
implements Question<List<T>> {
	
	/**
	 * Query
	 */
	@Child EntitiesQuery<T> query;
	
	/**
	 * Answer.
	 */
	List<T> answer;
	

	/**
	 * Creates a new QueryWrapperQuestion object. 
	 *
	 * @param query
	 */
	public QueryWrapperQuestion(EntitiesQuery<T> query) {
		super();
		this.query = query;
	}
		
	@Override
	public void ask() throws DataException, LogicException {
		query.execute();
		answer = WorkerUtils.queryResultsAsList(query);		
	}
	
	@Override
	public List<T> getAnswer() {
		return answer;
	}
	

}
