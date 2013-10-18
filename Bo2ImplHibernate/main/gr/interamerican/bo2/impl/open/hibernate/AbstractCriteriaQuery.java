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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.EntitiesQuery;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 * {@link AbstractCriteriaQuery} is an implementation of
 * {@link EntitiesQuery} based on hibernate.
 * 
 * @param <P> Type of root entity.
 */
public abstract class AbstractCriteriaQuery<P> 
extends AbstractHibernateQuery<P> {
	
	/**
	 * Query results.
	 */
	private List<?> results;
	
	@Override
	protected Iterator<?> createIterator() {
		this.results = null;
		Criteria criteria = session.createCriteria(rootEntity());
		Criterion[] crit = getCriteria();
		if (crit != null) {
			for (Criterion criterion : crit) {
				criteria.add(criterion);
			}
		}
		
		Order[] orders = getOrders();
		if(orders != null) {
			for(Order order : orders) {
				criteria.addOrder(order);
			}
		}
		
		this.results = criteria.list();
		return results.iterator();
	}
	
	/**
	 * Gets the criteria used for the query.
	 * 
	 * @return Returns the criteria for the query.
	 */
	protected abstract Criterion[] getCriteria();
	
	/**
	 * Gets the result order preferences for the query.
	 * 
	 * The default implementation returns an empty array. If a specific order
	 * needs to be specified, then the method has to be overriden.
	 * 
	 * @return Returns the result order preferences for the query
	 */
	protected Order[] getOrders() {
		Order[] orders = new Order[0];
		return orders;
	}
	
	/**
	 * Gets the type of objects this query should search for.
	 * 
	 * @return Returns the type of objects this query should search for. 
	 */
	protected abstract Class<? extends P> rootEntity();

}
