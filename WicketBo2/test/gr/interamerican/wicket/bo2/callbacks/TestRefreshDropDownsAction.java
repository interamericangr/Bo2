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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.junit.Test;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.operations.MultipleQueriesOperation;
import gr.interamerican.bo2.test.def.samples.SamplePoForTest;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.wicket.samples.panels.DropDownChoicePanel;
import gr.interamerican.wicket.samples.queries.SampleCodifiedEntitiesQuery;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link RefreshDropDownsAction}
 */
public class TestRefreshDropDownsAction extends WicketTest {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#invoke(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testInvokeAjaxRequestTarget() throws Exception {
		List<SamplePoForTest> resultList = new ArrayList<SamplePoForTest>();
		SamplePoForTest sampleResult = Factory.create(SamplePoForTest.class);
		sampleResult.setId(1);
		sampleResult.setName(StringConstants.FIVE);
		resultList.add(sampleResult);

		DropDownChoicePanel samplePanel = new DropDownChoicePanel("samplePanel");
		Map<String, EntitiesQuery<? extends Codified<?>>> queries = new HashMap<>();
		queries.put("dropdown", MockUtils.mockEntitiesQuery(SampleCodifiedEntitiesQuery.class, resultList));

		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		tested.setPanel(samplePanel);
		tested.setQueries(queries);
		tested.invoke(mock(AjaxRequestTarget.class));

		DropDownChoice<?> component = Utils.cast(samplePanel.get("dropdown"));
		assertEquals(resultList, component.getChoices());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#invoke(org.apache.wicket.ajax.AjaxRequestTarget)}
	 * where both the drop-down choice is null.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	@Test(expected = LogicException.class)
	public void testInvokeAjaxRequestTarget_nullDdc() throws Exception {
		List<SamplePoForTest> resultList = new ArrayList<SamplePoForTest>();
		SamplePoForTest sampleResult = Factory.create(SamplePoForTest.class);
		sampleResult.setId(1);
		sampleResult.setName(StringConstants.FIVE);
		resultList.add(sampleResult);

		DropDownChoicePanel samplePanel = new DropDownChoicePanel("samplePanel");
		Map<String, EntitiesQuery<? extends Codified<?>>> queries = new HashMap<>();
		queries.put("wrong_dropdown", MockUtils.mockEntitiesQuery(SampleCodifiedEntitiesQuery.class, resultList));

		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		tested.setPanel(samplePanel);
		tested.setQueries(queries);
		tested.invoke(mock(AjaxRequestTarget.class));
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#invoke(org.apache.wicket.ajax.AjaxRequestTarget)}
	 * where both the drop-down choices list is null.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	@Test(expected = LogicException.class)
	public void testInvokeAjaxRequestTarget_nullChoiceList() throws Exception {
		DropDownChoicePanel samplePanel = new DropDownChoicePanel("samplePanel");
		Map<String, EntitiesQuery<? extends Codified<?>>> queries = new HashMap<>();
		queries.put("dropdown", MockUtils.mockEntitiesQuery(SampleCodifiedEntitiesQuery.class, new ArrayList<>()));

		Map<String, List<?>> sampleResultMap = new HashMap<>();
		sampleResultMap.put("dropdown", null);

		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		tested.operation = mock(MultipleQueriesOperation.class);
		doReturn(sampleResultMap).when(tested.operation).getResults();

		tested.setPanel(samplePanel);
		tested.setQueries(queries);
		tested.invoke(mock(AjaxRequestTarget.class));
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#setQueries(java.util.Map)}.
	 */
	@Test
	public void testSetQueries() {
		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		Map<String, EntitiesQuery<? extends Codified<?>>> queries = new HashMap<>();
		tested.setQueries(queries);

		assertEquals(queries, tested.operation.getQueries());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#setPanel(org.apache.wicket.markup.html.panel.Panel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetPanel() {
		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		DropDownChoicePanel samplePanel = new DropDownChoicePanel("sample");
		tested.setPanel(samplePanel);

		assertEquals(samplePanel, tested.panel);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.RefreshDropDownsAction#getPanel()}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPanel() {
		RefreshDropDownsAction tested = new RefreshDropDownsAction();
		DropDownChoicePanel samplePanel = new DropDownChoicePanel("sample");
		tested.setPanel(samplePanel);

		assertEquals(samplePanel, tested.getPanel());
	}

}
