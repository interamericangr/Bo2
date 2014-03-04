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

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.samples.queries.BeanWithOneFieldQuery;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test for {@link QueryAction}.
 */
public class TestQueryAction {
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester =
		new WicketTester(new MockApplicationForWicketBo2());
	
	/**
	 * Unit test for the action.
	 */
	@Test
	public void testAction() {
		RequestCycle rc = RequestCycle.get();
		Bo2WicketRequestCycle.beginRequest(rc);
		
		SearchFlowPanelDef<BeanWith1Field, BeanWith1Field> definition = 
			new SearchFlowPanelDefImpl<BeanWith1Field, BeanWith1Field>();
		
		ServicePanel panel = Mockito.mock(ServicePanel.class);
		definition.setServicePanel(panel);
		BeanWith1Field criterion = new BeanWith1Field(2L);
		Model<BeanWith1Field> model = new Model<BeanWith1Field>(criterion);
		definition.setCriteriaModel(model);
		
		
		QueryAction<BeanWith1Field, BeanWith1Field, BeanWithOneFieldQuery> action =
			new QueryAction<BeanWith1Field, BeanWith1Field, BeanWithOneFieldQuery>
			(definition, BeanWithOneFieldQuery.class);
		
		AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);		
		action.callBack(target);
		List<BeanWith1Field> results = definition.getResults();
		Assert.assertEquals(1, results.size());
		
		Bo2WicketRequestCycle.endRequest(rc);		
	}

}
