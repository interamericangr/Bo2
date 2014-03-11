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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.AbstractCallbackAction;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.samples.creators.DataTableCreatorForBeanWithOrderedFields;
import gr.interamerican.wicket.samples.creators.DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes;
import gr.interamerican.wicket.samples.creators.DataTableCreatorForBeanWithOrderedFieldsWithRadios;
import gr.interamerican.wicket.samples.creators.FieldsPanelCreatorForBeanWithOrderedFields;
import gr.interamerican.wicket.test.WicketTest;

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
		
		Assert.assertTrue(errors.get(0).toString().contains("noDoubleHere"));
		System.out.println(errors.get(0));
    	
		tester.assertNoInfoMessage();
		
		tester.assertInvisible(path("resultsPanel"));
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
		
		DataTable<BeanWithOrderedFields> datatable = (DataTable<BeanWithOrderedFields>) 
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
		
		DataTable<BeanWithOrderedFields> datatable = (DataTable<BeanWithOrderedFields>) 
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
		
		DataTable<BeanWithOrderedFields> datatable = (DataTable<BeanWithOrderedFields>) 
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
		
		DataTable<BeanWithOrderedFields> datatable = (DataTable<BeanWithOrderedFields>) 
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
		
		DataTable<BeanWithOrderedFields> datatable = (DataTable<BeanWithOrderedFields>) 
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
		
		tester.executeAjaxEvent(saveButton, "onclick");
		
		assertEquals(list.size(), 8);
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
	 * table creator.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithRadios tableCreator_Pick = createCreator_Pick();
	
	/**
	 * table creator.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes tableCreator_MultiplePick = createCreator_MultiplePick();
	
	/**
	 * table creator.
	 */
	DataTableCreatorForBeanWithOrderedFields tableCreator_List = createCreator_List();
	
	/**
	 * Dummy callback for item selection.
	 */
	DummyCallback pickAction = new DummyCallback();
	
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
	 * Creates the {@link DataTableCreator}.
	 * 
	 * @return Returns a DataTableCreatorForBeanWithOrderedFields.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithRadios createCreator_Pick() {
		DataTableCreatorForBeanWithOrderedFieldsWithRadios cr = 
			new DataTableCreatorForBeanWithOrderedFieldsWithRadios();		
		cr.setRowsPerPage(5);
		return cr;
	}
	
	/**
	 * Creates the {@link DataTableCreator}.
	 * 
	 * @return Returns a DataTableCreatorForBeanWithOrderedFields.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes createCreator_MultiplePick() {
		DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes cr = 
			new DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes();		
		cr.setRowsPerPage(5);
		return cr;
	}
	
	/**
	 * Creates the {@link DataTableCreator}.
	 * 
	 * @return Returns a DataTableCreatorForBeanWithOrderedFields.
	 */
	DataTableCreatorForBeanWithOrderedFields createCreator_List() {
		DataTableCreatorForBeanWithOrderedFields cr = 
			new DataTableCreatorForBeanWithOrderedFields();		
		cr.setRowsPerPage(5);
		return cr;
	}
	
	/**
	 * Creates the {@link SearchFlowPanelDef}.
	 * 
	 * @return a SearchFlowPanel definition.
	 */
	SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> createDef_CrudPick() {
		
		FieldsPanelCreatorForBeanWithOrderedFields panelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		
		SearchFlowPanelDef<BeanWithOrderedFields, BeanWithOrderedFields> def =
			new SearchFlowPanelDefImpl<BeanWithOrderedFields, BeanWithOrderedFields>();
		
		def.setDeleteAction(new DeleteAction());
		def.setUpdateAction(new UpdateAction());
		def.setSaveAction(new SaveAction());
		def.setBeanFieldsPanelCreator(panelCreator);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
				
		def.setQueryAction(new QueryAction());
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		FieldsPanelCreatorForBeanWithOrderedFields criteriaPanelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		def.setCriteriaFieldsPanelCreator(criteriaPanelCreator);
		
		def.setAutoPickSingleResult(false);
		def.setBackAction(null);
		def.setDataTableCreator(tableCreator_Pick);
		def.setPickAction(pickAction);
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
		
		def.setQueryAction(new QueryAction());
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		FieldsPanelCreatorForBeanWithOrderedFields criteriaPanelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		def.setCriteriaFieldsPanelCreator(criteriaPanelCreator);
		
		def.setResultsHidesCriteria(false);
		def.setAllowMultipleSelections(true);
		def.setAutoPickSingleResult(false);
		def.setBackAction(null);
		def.setDataTableCreator(tableCreator_MultiplePick);
		def.setPickAction(new MultipleSelectionsAction());
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
		
		def.setQueryAction(new QueryAction());
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		FieldsPanelCreatorForBeanWithOrderedFields criteriaPanelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		def.setCriteriaFieldsPanelCreator(criteriaPanelCreator);
		
		def.setAutoPickSingleResult(false);
		def.setBackAction(null);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		def.setDataTableCreator(tableCreator_Pick);
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
		
		def.setQueryAction(new QueryAction());
		def.setCriteriaModel(new CompoundPropertyModel<BeanWithOrderedFields>(criterion));
		FieldsPanelCreatorForBeanWithOrderedFields criteriaPanelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		def.setCriteriaFieldsPanelCreator(criteriaPanelCreator);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		
		def.setBackAction(null);
		def.setDataTableCreator(tableCreator_List);
		def.setPickAction(null);
		def.setResultsHidesCriteria(false);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Query action.
	 */
	class QueryAction extends AbstractCallbackAction {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void callBack(AjaxRequestTarget target) {
			target.add(panel);
			query();
		}
		
		public void callBack(AjaxRequestTarget target, Form<?> form) {
			target.add(panel);
			query();
		}
		
		/**
		 * query method.
		 */
		void query() {
			panel.getDefinition().setResults(list);
		}
	}
	
	/**
	 * Query action.
	 */
	abstract class CrudAction extends AbstractCallbackAction {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void callBack(AjaxRequestTarget target) {
			target.add(panel.resultsPanel);
			work();
		}
		
		public void callBack(AjaxRequestTarget target, Form<?> form) {
			target.add(panel.resultsPanel);
			work();
		}
		
		/**
		 * main method method.
		 */
		protected abstract void work();
	}
	
	
	/**
	 * Update action.
	 */
	class SaveAction extends CrudAction {		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void work() {
			BeanWithOrderedFields bwof = panel.getDefinition().getBeanModel().getObject();
			assertNotNull(bwof);
			assertEquals(bwof, userInput);
			/* store the bean to the database here */
		}
	}
	
	/**
	 * Update action.
	 */
	class UpdateAction extends CrudAction {		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void work() {
			BeanWithOrderedFields bwof = panel.getDefinition().getBeanModel().getObject();
			assertNotNull(bwof);
			assertEquals(bwof, userInput);
			/* update the bean to the database here */
		}
	}
	
	/**
	 * Delete action.
	 */
	class DeleteAction extends CrudAction {		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void work() {
			/* code that performs a delete SQL operation */
		}
	}
	
	/**
	 * Multiple selections action.
	 */
	class MultipleSelectionsAction extends CrudAction {		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void work() {
			/* code that gets the selections from the definition and does something */
		}
	}

}
