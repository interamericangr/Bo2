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
import gr.interamerican.bo2.arch.ext.CriteriaDependent;

/**
 * {@link CriteriaDependent} question that wraps an
 * {@link EntitiesQuery}.
 * 
 * @param <T> 
 *        Type of elements in the answer.
 * @param <C> 
 *        Type of criteria.
 * 
 */
public class CriteriaQueryWrapperQuestion<T,C> 
extends QueryWrapperQuestion<T> 
implements CriteriaDependent<C> {
	
	/**
	 * Criteria.
	 */
	C criteria;

	/**
	 * Creates a new CriteriaQueryWrapperQuestion object.
	 * 
	 * @param query
	 *        Query wrapped by this question. 
	 * @param <Q>
	 *        Type of the query. The query must implement both
	 *        interfaces {@link EntitiesQuery} and {@link CriteriaDependent}.
	 */
	public <Q extends EntitiesQuery<T> & CriteriaDependent<C>>
	CriteriaQueryWrapperQuestion(Q query) {
		super(query);		
	}

	public C getCriteria() {
		return criteria;
	}

	public void setCriteria(C criteria) {
		this.criteria = criteria;
		@SuppressWarnings("unchecked")
		CriteriaDependent<C> q = (CriteriaDependent<C>) query;
		q.setCriteria(criteria);
	}

}
