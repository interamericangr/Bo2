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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanel;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Query action for search action of a {@link SearchFlowPanel}.
 * 
 * @param <C> 
 *        Type of criteria bean.
 * @param <B>
 *        Type of result bean.
 * @param <Q> 
 *        Type of query.
 */
public class QueryAction
<C extends Serializable, 
 B extends Serializable,
 Q extends EntitiesQuery<B> & CriteriaDependent<C>> 
extends Bo2WicketBlock {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<C, B> definition;
	
	/**
	 * Query.
	 */
	Class<Q> queryClass;
	

	
	/**
	 * Creates a new QueryAction object. 
	 *
	 * @param definition
	 *        Definition of the {@link SearchFlowPanel}.
	 * @param queryClass 
	 *        Class of query.
	 */
	public QueryAction(SearchFlowPanelDef<C, B> definition, Class<Q> queryClass) {
		super();
		this.definition = definition;
		this.queryClass = queryClass;
	}

	@Override
	public void work() 
	throws InitializationException, DataException, LogicException {
		AjaxRequestTarget target = getHandlerParameter(AjaxRequestTarget.class);
		target.add(definition.getServicePanel());
		Q query = Bo2WicketRequestCycle.open(queryClass);
		C criteria = definition.getCriteriaModel().getObject();
		query.setCriteria(criteria);
		query.execute();
		List<B> results = WorkerUtils.queryResultsAsList(query);
		definition.setResults(results);
	}

}

