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
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.impl.open.operations.MultipleQueriesOperation;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.def.PanelDependent;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Callback action that refreshes the dropdowns of a panel.
 * 
 * This action takes as input a map that contains a 
 */
public class RefreshDropDownsAction 
extends Bo2WicketBlock 
implements PanelDependent {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Operation that executes the queries.
	 */
	MultipleQueriesOperation<Codified<?>> operation = new MultipleQueriesOperation<Codified<?>>();

	/**
	 * {@link Panel} that contains the components to refresh.
	 */
	Panel panel;

	@Override
	public void work() 
	throws InitializationException, DataException, LogicException {
		operation.init(this.getProvider());
		operation.open();
		if(panel!=null) {
			operation.setCriteria(panel.getDefaultModelObject());
		}
		operation.execute();
		operation.close();
		refreshDropDowns();
		AjaxRequestTarget target = getHandlerParameter(AjaxRequestTarget.class);
		target.addComponent(panel);
	}
	
	/**
	 * Refreshes the drop downs with the lists of the results.
	 * 
	 * @param <M>
	 *        Type of choice. 
	 * 
	 * @throws LogicException 
	 * 
	 */
	private <M extends Codified<?>> void refreshDropDowns() throws LogicException {
		Map<String, List<? extends Codified<?>>> results = operation.getResults();
		for(String key :results.keySet()){
			@SuppressWarnings("unchecked") DropDownChoice<M> dropDownChoice = (DropDownChoice<M>) 
				SelfDrawnUtils.getComponentFromSelfDrawnPanel(panel,key);
			if (dropDownChoice==null) {
				@SuppressWarnings("nls") String msg = "DropDownChoice with name " + key + " not found";
				throw new LogicException(msg);
			}
			@SuppressWarnings("unchecked") List<M> choices = (List<M>) results.get(key);		
			if (choices==null) {
				@SuppressWarnings("nls") String msg = "Query with name " + key + " not found";
				throw new LogicException(msg);
			}
			dropDownChoice.setChoices(choices);
		}
	}

	/**
	 * Assigns a new value to the queries.
	 *
	 * @param queries the queries to set
	 */
	public void setQueries(Map<String, EntitiesQuery<? extends Codified<?>>> queries) {
		operation.setQueries(queries);
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public Panel getPanel() {
		return panel;
	}
	
}
