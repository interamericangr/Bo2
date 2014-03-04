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
package gr.interamerican.wicket.markup.html.panel.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.samples.creators.FieldsPanelCreatorForBeanWithOrderedFields;
import gr.interamerican.wicket.samples.panels.BeanWithOrderedFieldsFormPanel;
import gr.interamerican.wicket.test.WicketTest;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link SingleBeanPanel}.
 */
@SuppressWarnings({"nls","unchecked"})
public class TestSingleBeanPanel extends WicketTest {
	
	/**
	 * Tests creation and execution of {@link SingleBeanPanel}.
	 */	
	@Test
	public void testCreation() {		
		tester.assertComponent(path("beanForm"), Form.class);
		tester.assertVisible(path("beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("beanForm:executeButton"));
		CallbackAjaxButton queryButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("beanForm:executeButton"));
		assertNotNull(queryButton);
		
		tester.assertComponent(path("beanForm:clearButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("beanForm:clearButton"));
		CallbackAjaxButton clearButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("beanForm:clearButton"));
		assertNotNull(clearButton);
		
		tester.assertComponent(path("beanForm:beanFormFieldsPanel"), BeanWithOrderedFieldsFormPanel.class);
		tester.assertVisible(path("beanForm:beanFormFieldsPanel"));
		BeanWithOrderedFieldsFormPanel bwofPanel = (BeanWithOrderedFieldsFormPanel) 
			tester.getComponentFromLastRenderedPage(path("beanForm:beanFormFieldsPanel"));
		assertNotNull(bwofPanel);
		
		tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		tester.assertNoInfoMessage();    	
    	tester.assertNoErrorMessage();
    	
    	
	}

	/**
	 * Tests submitting the form.
	 */	
	@Test
	public void testFormSubmit() {		
		tester.assertComponent(path("beanForm"), Form.class);
		tester.assertVisible(path("beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("beanForm:executeButton"));
		CallbackAjaxButton queryButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("beanForm:executeButton"));
		assertNotNull(queryButton);
		
		tester.assertComponent(path("beanForm:beanFormFieldsPanel"), BeanWithOrderedFieldsFormPanel.class);
		tester.assertVisible(path("beanForm:beanFormFieldsPanel"));
		BeanWithOrderedFieldsFormPanel bwofPanel = (BeanWithOrderedFieldsFormPanel) 
			tester.getComponentFromLastRenderedPage(path("beanForm:beanFormFieldsPanel"));
		assertNotNull(bwofPanel);
		
		tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		assertEquals(bean, form.getModelObject());
		assertEquals(bean, bwofPanel.getDefaultModelObject());
		
		BeanWithOrderedFields userInput = new BeanWithOrderedFields("11", "22", 33, 44L, 5.5d);
		FormTester formTester = tester.newFormTester(path("beanForm"));
		formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(userInput.getThird()));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", new DecimalFormat().format((userInput.getFifth())));
		
		callback.setExecuted(false);
		
    	formTester.submit();
    	tester.executeAjaxEvent(queryButton, "onclick");
    	
    	assertTrue(callback.isExecuted());
    	
    	BeanWithOrderedFields formBean = form.getModelObject();
    	BeanWithOrderedFields panelBean = (BeanWithOrderedFields) bwofPanel.getDefaultModelObject();
    	
    	assertEquals(formBean.getFirst(), userInput.getFirst());
    	assertEquals(formBean.getSecond(), userInput.getSecond());
    	assertEquals(formBean.getThird(), userInput.getThird());
    	assertEquals(formBean.getFourth(), userInput.getFourth());
    	assertEquals(formBean.getFifth(), userInput.getFifth());
    	
    	assertEquals(formBean.getFirst(), panelBean.getFirst());
    	assertEquals(formBean.getSecond(), panelBean.getSecond());
    	assertEquals(formBean.getThird(), panelBean.getThird());
    	assertEquals(formBean.getFourth(), panelBean.getFourth());
    	assertEquals(formBean.getFifth(), panelBean.getFifth());
    	
    	tester.assertNoInfoMessage();    	
    	tester.assertNoErrorMessage();
    }
	
	/**
	 * Tests clearing the form.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testClearForm() {
		tester.assertComponent(path("beanForm"), Form.class);
		tester.assertVisible(path("beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("beanForm:clearButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("beanForm:clearButton"));
		CallbackAjaxButton clearButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("beanForm:clearButton"));
		assertNotNull(clearButton);
		
		assertEquals(bean, form.getModelObject());
		
		tester.assertComponent(path("beanForm:beanFormFieldsPanel:first"), TextField.class);
		TextField firstFld = (TextField) tester.getComponentFromLastRenderedPage(path("beanForm:beanFormFieldsPanel:first"));
		assertNotNull(firstFld.getDefaultModelObject());
		
		tester.executeAjaxEvent(clearButton, "onclick");
		
		BeanWithOrderedFields cleared = form.getModelObject();
		assertNull(cleared.getFirst());
		assertNull(cleared.getSecond());
		assertNull(cleared.getThird());
		assertNull(cleared.getFourth());
		assertNull(cleared.getFifth());
		
		firstFld = (TextField) tester.getComponentFromLastRenderedPage(path("beanForm:beanFormFieldsPanel:first"));
		assertNull(firstFld.getDefaultModelObject());
	}
	
	/**
	 * Tests submitting the form with invalid input.
	 * The feedback panel should be rendered with an error message.
	 * The model objects remain unchanged.
	 * The callback action is not executed.
	 */	
	@Test
	public void testFormSubmit_InvalidUserInput() {		
		tester.assertComponent(path("beanForm"), Form.class);
		tester.assertVisible(path("beanForm"));
		Form<BeanWithOrderedFields> form = (Form<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("beanForm"));
		assertNotNull(form);
		
		tester.assertComponent(path("beanForm:executeButton"), CallbackAjaxButton.class);
		tester.assertVisible(path("beanForm:executeButton"));
		CallbackAjaxButton queryButton = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("beanForm:executeButton"));
		assertNotNull(queryButton);
		
		tester.assertComponent(path("beanForm:beanFormFieldsPanel"), BeanWithOrderedFieldsFormPanel.class);
		tester.assertVisible(path("beanForm:beanFormFieldsPanel"));
		BeanWithOrderedFieldsFormPanel bwofPanel = (BeanWithOrderedFieldsFormPanel) 
			tester.getComponentFromLastRenderedPage(path("beanForm:beanFormFieldsPanel"));
		assertNotNull(bwofPanel);
		
		assertEquals(bean, form.getModelObject());
		assertEquals(bean, bwofPanel.getDefaultModelObject());
		
		String notdouble = "noDoubleHere";
		BeanWithOrderedFields userInput = new BeanWithOrderedFields("11", "22", 33, 44L, 5.5d);
		FormTester formTester = tester.newFormTester(path("beanForm"));
		formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(userInput.getThird()));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", notdouble); //invalid user input
		
    	formTester.submit();
    	tester.executeAjaxEvent(queryButton, "onclick");
    	
    	tester.assertComponent(path("feedback"), FeedbackPanel.class);
		tester.assertVisible(path("feedback"));
		FeedbackPanel feedbackPanel = (FeedbackPanel) 
			tester.getComponentFromLastRenderedPage(path("feedback"));
		assertNotNull(feedbackPanel);
		
		tester.assertNoInfoMessage();
		List<Serializable> errors = tester.getMessages(FeedbackMessage.ERROR);
		Assert.assertFalse(errors.isEmpty());
		
		Assert.assertTrue(errors.get(0).toString().contains("noDoubleHere"));
		System.out.println(errors.get(0));
		
    	BeanWithOrderedFields formBean = form.getModelObject();
    	BeanWithOrderedFields panelBean = (BeanWithOrderedFields) bwofPanel.getDefaultModelObject();
    	
    	assertEquals(bean, formBean);
    	assertEquals(bean, panelBean);
    	
    }
	
	/**
	 * Dummy callback for item selection.
	 */
	private DummyCallback callback = new DummyCallback();
	
	/**
	 * criterion.
	 */
	BeanWithOrderedFields bean = new BeanWithOrderedFields("1","2",3,4L,5.0);
	
	/**
	 * panel definition 
	 */
	SingleBeanPanelDef<BeanWithOrderedFields> definition = createDef();
	
	/**
	 * Tests setup.
	 */
	@SuppressWarnings("serial")
	@Before
	public void setupTests() {
		definition = createDef();
		SingleBeanPanel<BeanWithOrderedFields> panel = 
			new SingleBeanPanel<BeanWithOrderedFields>(definition) {
			@Override protected BeanWithOrderedFields newBean() {
				return new BeanWithOrderedFields();
			}
		};
		tester.startPage(getTestPage(panel));
	}
	
	/**
	 * Creates a sample definition.
	 * 
	 * @return returns a definition.
	 */
	SingleBeanPanelDef<BeanWithOrderedFields> createDef() {
		
		FieldsPanelCreatorForBeanWithOrderedFields creator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
				
		SingleBeanPanelDef<BeanWithOrderedFields> def = 
			new SingleBeanPanelDefImpl<BeanWithOrderedFields>();		

		def.setBackAction(null);
		def.setBeanFieldsPanelCreator(creator);
		def.setBeanAction(callback);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(bean));
		def.setShowClearButton(true);
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	

}
