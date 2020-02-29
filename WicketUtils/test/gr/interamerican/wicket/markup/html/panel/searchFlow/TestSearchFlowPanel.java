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
package gr.interamerican.wicket.markup.html.panel.searchFlow;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.samples.creators.SampleDataTableCreators;
import gr.interamerican.wicket.samples.panels.BeanWithOrderedFieldsFormPanel;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link SearchFlowPanelDef}.
 */
@SuppressWarnings({"nls", "unchecked"})
public class TestSearchFlowPanel extends WicketTest {
	
	/**
	 * The panel under testing.
	 */
	private SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields> panel;
	
	/**
	 * Tests creation of {@link SearchFlowPanel} that has a 
	 * {@link SingleBeanPanel} and a {@link PickerPanel}.
	 * 
	 * Also resultsHidesCriteria = true
	 */	
	@Test
	public void testCreation_Pick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_Pick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		tester.assertNoInfoMessage();
		tester.assertNoErrorMessage();
	}
	
	/**
	 * Tests creation of {@link SearchFlowPanel} that has a 
	 * {@link SingleBeanPanel} and a {@link MultipleSelectionsPanel}.
	 * 
	 * Also resultsHidesCriteria = true
	 */	
	@Test
	public void testCreation_MultiplePick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_MultiplePick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		tester.assertNoInfoMessage();
		tester.assertNoErrorMessage();
	}
	
	/**
	 * Tests submitting the criteria panel form with invalid input.
	 * Feedback panel has the error message.
	 * Results panel is not shown.
	 */
	@Test
	public void testShowResults_ErrorUserInput() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_Pick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		String notdouble = "noDoubleHere";
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
		formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(userInput.getThird()));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", notdouble); //invalid user input
		
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	List<Serializable> errors = tester.getMessages(FeedbackMessage.ERROR);
		Assert.assertFalse(errors.isEmpty());
		
		Assert.assertTrue(errors.get(0).toString().contains("fifth"));
		System.out.println(errors.get(0));
    	
		tester.assertNoInfoMessage();
		
		tester.assertInvisible(path("resultsPanel"));
	}
	
	/**
	 * Tests submitting the criteria panel form.
	 * 
	 * Results panel is shown as a {@link CrudPickerPanel}. Only View button available
	 */
	@Test
	public void testShowResults_View() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> sfpDef = createDef_View();
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(sfpDef);
		tester.startPage(getTestPage(panel));
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
    	
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), CrudPickerPanel.class);
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
				tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);
		tester.assertComponent(path("resultsPanel:tableForm:viewButton"), AjaxButton.class);
		tester.assertComponent(path("resultsPanel:tableForm:radioGroup"), RadioGroup.class);
		
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>) tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup"));
    	radioGroup.setModelObject(list.get(0));
    	Map<String, String[]> map= tester.getRequest().getParameterMap();	
		map.put(path("resultsPanel:tableForm:radioGroup"), new String[]{"radio0"});
    	
		AjaxButton viewButton = (AjaxButton) tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:viewButton"));
		tester.executeAjaxEvent(viewButton, "onclick");
		
		tester.assertComponent(path("resultsPanel:beanPanel"), SingleBeanPanel.class);
		tester.assertComponent(path("resultsPanel:beanPanel:beanForm:beanFormFieldsPanel"), BeanWithOrderedFieldsFormPanel.class);
	}
	/**
	 * Tests submitting the criteria panel form.
	 * 
	 * Results panel is shown as a {@link PickerPanel}.
	 * 
	 * Check that the panel shows the results and pick one.
	 * 
	 * Then go back to criteria.
	 * 
	 * Note that resultsHidesCriteria = true
	 */
	@Test
	public void testShowResults_Pick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_Pick);
		tester.startPage(getTestPage(panel));

		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), PickerPanel.class);
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);
		tester.assertComponent(path("resultsPanel:tableForm:selectButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("resultsPanel:tableForm:selectButton"));
		tester.assertComponent(path("resultsPanel:tableForm:radioGroup"), RadioGroup.class);
		tester.assertComponent(path("resultsPanel:tableForm:radioGroup:listTable"), DataTable.class);
		
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup"));
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
    	CallbackAjaxButton pickButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:selectButton"));
    	
    	pickAction.setExecuted(false);
    	/*
    	 * simulate picking the first radio button and submitting.
    	 */
    	radioGroup.setModelObject(list.get(0));
    	Map<String, String[]> map= tester.getRequest().getParameterMap();	
		map.put("testId:tableForm:radioGroup", new String[]{"radio0"});
    	tester.executeAjaxEvent(pickButton, "onclick");
    	
    	assertTrue(pickAction.isExecuted());
    	assertEquals(list.get(0), definition_Pick.getBeanModel().getObject());
		
	}
	
	/**
	 * Tests submitting the criteria panel form.
	 * 
	 * Results panel is shown as a {@link MultipleSelectionsPanel}.
	 * 
	 * Check that the panel shows the results and pick two.
	 * 
	 * Then go back to criteria.
	 * 
	 * Note that resultsHidesCriteria = true
	 */
	@Test
	public void testShowResults_MultiplePick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_MultiplePick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), MultipleSelectionsPanel.class);
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);
		tester.assertComponent(path("resultsPanel:tableForm:checkGroup"), CheckGroup.class);
		tester.assertComponent(path("resultsPanel:tableForm:checkGroup:listTable"), DataTable.class);
		
		CheckGroup<BeanWithOrderedFields> checkGroup = (CheckGroup<BeanWithOrderedFields>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:checkGroup"));
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:checkGroup:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
		CallbackAjaxButton pickButton = (CallbackAjaxButton) 
		tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:selectButton"));
	
		pickAction.setExecuted(false);
		/*
		 * simulate picking the first two radio buttons and submitting.
		 */
		List<BeanWithOrderedFields> selections = new ArrayList<BeanWithOrderedFields>();
		selections.add(list.get(0));
		selections.add(list.get(1));
		checkGroup.setModelObject(selections);
		Map<String, String[]> map= tester.getRequest().getParameterMap();	
		map.put("testId:tableForm:checkGroup", new String[]{"check0", "check1"});
		tester.executeAjaxEvent(pickButton, "onclick");
		
		assertEquals(selections, definition_MultiplePick.getSelectionsModel().getObject());
		
	}
	
	/**
	 * Tests creation of {@link SearchFlowPanel} that has a 
	 * {@link SingleBeanPanel} and a {@link PickerPanel}.
	 * 
	 * Also resultsHidesCriteria = false
	 */	
	@Test
	public void testCreation_List() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_List);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		tester.assertNoInfoMessage();
		tester.assertNoErrorMessage();
	}
	
	/**
	 * Tests submitting the criteria panel form.
	 * 
	 * Results panel is shown as a {@link ListTablePanel}.
	 * 
	 * Check that the panel shows the results.
	 * 
	 * Check that both panels appear simultaneously.
	 * 
	 * Check that the back button of the results panel is not shown.
	 * 
	 * Note that resultsHidesCriteria = false
	 */
	@Test
	public void testShowResults_List() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_List);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), ListTablePanel.class);
		
		tester.assertVisible(path("resultsPanel"));
		tester.assertVisible(path("criteriaPanel"));
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);		
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
		tester.assertInvisible(path("resultsPanel:tableForm:backButton"));

	}
	
	/**
	 * Tests creation of the {@link SearchFlowPanel} with a
	 * {@link CrudPickerPanel} as the results panel.
	 * 
	 * Show the results.
	 * 
	 * Note that resultsHidesCriteria = true
	 */
	@Test
	public void testCreation_ShowResults_CrudPick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_CrudPick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), CrudPickerPanel.class);
		
		tester.assertVisible(path("resultsPanel"));
		tester.assertInvisible(path("criteriaPanel"));
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);		
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
		tester.assertVisible(path("resultsPanel:tableForm:backButton"));
		
		tester.assertComponent(path("resultsPanel:tableForm:deleteButton"), CallbackAjaxButton.class);	
		tester.assertComponent(path("resultsPanel:tableForm:editButton"), AjaxButton.class);
		tester.assertComponent(path("resultsPanel:tableForm:newButton"), AjaxButton.class);
		tester.assertComponent(path("resultsPanel:tableForm:selectButton"), CallbackAjaxButton.class);
	}
	
	/**
	 * Tests creation of the {@link SearchFlowPanel} with a
	 * {@link CrudPickerPanel} as the results panel.
	 * 
	 * Show the results.
	 * 
	 * Press new.
	 * 
	 * Then save.
	 * 
	 * Press new.
	 * 
	 * Then go back.
	 * 
	 * Note that resultsHidesCriteria = true
	 */
	@Test
	public void testCrud_CrudPick() {
		panel = new SearchFlowPanel<BeanWithOrderedFields, BeanWithOrderedFields>(definition_CrudPick);
		tester.startPage(getTestPage(panel));
		
		tester.assertComponent(path("criteriaPanel"), SingleBeanPanel.class);
		tester.assertVisible(path("criteriaPanel"));
		SingleBeanPanel<BeanWithOrderedFields> SingleBeanPanel = (SingleBeanPanel<BeanWithOrderedFields>)  
			tester.getComponentFromLastRenderedPage(path("criteriaPanel"));
		assertNotNull(SingleBeanPanel);
		
		tester.assertInvisible(path("resultsPanel"));
		
		tester.assertComponent(path("criteriaPanel:beanForm"), Form.class);
		tester.assertVisible(path("criteriaPanel:beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("criteriaPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("criteriaPanel:beanForm:executeButton"));
		CallbackAjaxButton executeButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("criteriaPanel:beanForm:executeButton"));
		assertNotNull(executeButton);
		
		FormTester formTester = tester.newFormTester(path("criteriaPanel:beanForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(executeButton, "onclick");
		
    	tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		
		/* the new results panel is rendered */
		tester.assertComponent(path("resultsPanel"), CrudPickerPanel.class);
		
		tester.assertVisible(path("resultsPanel"));
		tester.assertInvisible(path("criteriaPanel"));
		
		tester.assertComponent(path("resultsPanel:tableForm"), Form.class);		
		
		DataTable<BeanWithOrderedFields,String> datatable = (DataTable<BeanWithOrderedFields,String>) 
			tester.getComponentFromLastRenderedPage(path("resultsPanel:tableForm:radioGroup:listTable"));
		assertEquals(list.size(), datatable.getDataProvider().size());
		
		tester.assertVisible(path("resultsPanel:tableForm:backButton"));
		
		/* press new and add a new bean */
		tester.assertInvisible(path("resultsPanel:beanPanel"));
		
		tester.assertComponent(path("resultsPanel:tableForm:newButton"), AjaxButton.class);
		AjaxButton newButton = (AjaxButton) tester.
			getComponentFromLastRenderedPage(path("resultsPanel:tableForm:newButton"));
		
		formTester = tester.newFormTester(path("resultsPanel:tableForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(newButton, "onclick");
    	
    	tester.assertComponent(path("resultsPanel:beanPanel:beanForm:executeButton"), CallbackAjaxButton.class);
    	CallbackAjaxButton saveButton = (CallbackAjaxButton) tester.
    		getComponentFromLastRenderedPage(path("resultsPanel:beanPanel:beanForm:executeButton"));
    	
    	formTester = tester.newFormTester(path("resultsPanel:beanPanel:beanForm"));
    	formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(userInput.getThird()));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", String.valueOf(userInput.getFifth()));
		formTester.submit();
		
		assertEquals(list.size(), 7);
		assertEquals(7, ((CrudPickerPanel<?>)tester.getComponentFromLastRenderedPage(path("resultsPanel"))).getDefinition().getList().size());
		assertEquals(7, definition_CrudPick.getResults().size());
		
		tester.executeAjaxEvent(saveButton, "onclick");
		
		assertEquals(list.size(), 8);
		assertEquals(8, ((CrudPickerPanel<?>)tester.getComponentFromLastRenderedPage(path("resultsPanel"))).getDefinition().getList().size());
		assertEquals(8, definition_CrudPick.getResults().size());
		tester.assertInvisible(path("resultsPanel:beanPanel"));
		
		/* press new and then back */
		tester.assertInvisible(path("resultsPanel:beanPanel"));
		
		tester.assertComponent(path("resultsPanel:tableForm:newButton"), AjaxButton.class);
		newButton = (AjaxButton) tester.
			getComponentFromLastRenderedPage(path("resultsPanel:tableForm:newButton"));
		
		formTester = tester.newFormTester(path("resultsPanel:tableForm"));
    	formTester.submit();
    	tester.executeAjaxEvent(newButton, "onclick");
    	
    	tester.assertComponent(path("resultsPanel:beanPanel:beanForm:backButton"), CallbackAjaxButton.class);
    	
    	CallbackAjaxButton backButton = (CallbackAjaxButton) tester.
			getComponentFromLastRenderedPage(path("resultsPanel:beanPanel:beanForm:backButton"));
    	
    	tester.executeAjaxEvent(backButton, "onclick");
    	
    	tester.assertInvisible(path("resultsPanel:beanPanel"));
	}
	
	/**********************************************************
	 * SAMPLE SETUP GOES ON BELOW THIS POINT...
	 **********************************************************/
	
	/**
	 * criterion.
	 */
	BeanWithOrderedFields criterion = new BeanWithOrderedFields("","",0,0L,0.0);
	
	/**
	 * user input.
	 */
	BeanWithOrderedFields userInput = new BeanWithOrderedFields("44","44",44,44L,44.4);
	
	/**
	 * Dummy callback for item selection.
	 */
	MockedCallback pickAction = new MockedCallback();
	
	/**
	 * Panel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> definition_Pick = createDef_Pick();
	
	/**
	 * Panel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> definition_MultiplePick = createDef_MultiplePick();
	
	/**
	 * Panel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> definition_List = createDef_List();
	
	/**
	 * Panel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> definition_CrudPick = createDef_CrudPick();
	
	/**
	 * list with results.
	 */
	List<BeanWithOrderedFields> list = BeanCollections.listOfBeanWithOrderedFields();

	/**
	 * Creates the {@link SearchFlowPanelDef}.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_CrudPick() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		
		def.setDeleteAction(this::delete);
		def.setUpdateAction(this::update);
		def.setSaveAction(this::save);
		def.setBeanFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
				
		def.setQueryAction(this::query);
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		def.setCriteriaFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.radio(5));
		def.setPickAction(pickAction);
		def.setResultsHidesCriteria(true);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Creates the {@link SearchFlowPanelDef} with no CRUD actions and viewEnabled=true.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_View() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		def.setViewEnabled(true);
		def.setBeanFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
				
		def.setQueryAction(this::query);
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		def.setCriteriaFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.radio(5));
		def.setResultsHidesCriteria(true);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Creates the {@link SearchFlowPanelDef}.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_MultiplePick() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		def.setQueryAction(this::query);
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		def.setCriteriaFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setResultsHidesCriteria(false);
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.checkBoxes(5));
		def.setMultiplePickAction(this::pick);
		def.setResultsHidesCriteria(false);
		def.setSelectionsModel(new Model<ArrayList<BeanWithOrderedFields>>());
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Creates the {@link SearchFlowPanelDef}.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_Pick() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		def.setQueryAction(this::query);
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		def.setCriteriaFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBackAction(null);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		def.setDataTableCreator(SampleDataTableCreators.radio(5));
		def.setPickAction(pickAction);
		def.setResultsHidesCriteria(false);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Creates the {@link SearchFlowPanelDef}.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_List() {
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		def.setQueryAction(this::query);
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		def.setCriteriaFieldsPanelCreator(PanelCreator.getCompoundCreator(BeanWithOrderedFieldsFormPanel::new));
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.empty(5));
		def.setPickAction(null);
		def.setResultsHidesCriteria(false);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * query action
	 * 
	 * @param bean
	 * @return result
	 */
	List<BeanWithOrderedFields> query(@SuppressWarnings("unused") BeanWithOrderedFields bean) {
		return list;
	}

	/**
	 * save action
	 * @param bwof
	 * @return saved bean
	 */
	BeanWithOrderedFields save(BeanWithOrderedFields bwof) {
		assertNotNull(bwof);
		assertEquals(bwof, userInput);
		/* store the bean to the database here */
		return bwof;
	}

	/**
	 * update action
	 * @param bwof
	 * @return updated bean
	 */
	BeanWithOrderedFields update(BeanWithOrderedFields bwof) {
		assertNotNull(bwof);
		assertEquals(bwof, userInput);
		/* update the bean to the database here */
		return bwof;
	}

	/**
	 * delete action
	 * @param bwof
	 */
	void delete(BeanWithOrderedFields bwof) {
		assertNotNull(bwof);
		/* code that performs a delete SQL operation */
	}

	/**
	 * pick action
	 * 
	 * @param target
	 * @param beans
	 */
	void pick(AjaxRequestTarget target, List<BeanWithOrderedFields> beans) {
		assertNotNull(target);
		assertNotNull(beans);
	}
}