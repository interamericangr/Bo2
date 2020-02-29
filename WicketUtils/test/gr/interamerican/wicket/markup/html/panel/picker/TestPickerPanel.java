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
package gr.interamerican.wicket.markup.html.panel.picker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.serialize.java.JavaSerializer;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.wicket.callback.MockedCallback;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.creators.SampleDataTableCreators;
import gr.interamerican.wicket.samples.flags.AlwaysDownFlag;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link PickerPanel}.
 */
@SuppressWarnings("nls")
public class TestPickerPanel extends WicketTest {
	
	/**
	 * Dummy callback for item selection.
	 */
	private MockedCallback callback = new MockedCallback();

	/**
	 * Creates a sample definition.
	 * 
	 * @return returns a definition.
	 */
	@SuppressWarnings("unchecked")
	PickerPanelDef<BeanWithOrderedFields> createDef() {		
		PickerPanelDef<BeanWithOrderedFields> def = new PickerPanelDefImpl<BeanWithOrderedFields>();
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.radio(5));
		def.setItemSelectedAction(callback);
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		def.setList(BeanCollections.listOfBeanWithOrderedFields());
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Tests creation of {@link PickerPanel}.
	 * 
	 * Also tests that pressing the select button, selects the item. 
	 */	
	@Test
	public void testCreation() {
		PickerPanelDef<BeanWithOrderedFields> def = createDef(); 
		PickerPanel<BeanWithOrderedFields> panel = new PickerPanel<BeanWithOrderedFields>(def);
		tester.startPage(getTestPage(panel));
		
		new JavaSerializer(tester.getApplication().getApplicationKey()).serialize(panel);

		tester.assertComponent(path("tableForm:radioGroup"), RadioGroup.class);
		tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
		tester.assertComponent(path("tableForm:radioGroup:listTable:body:rows:1:cells:4:cell:radioButton"), Radio.class);
		
		String buttonId = path("tableForm:selectButton");		
		tester.assertComponent(buttonId, AjaxButton.class);
		AjaxButton button = (AjaxButton)
			tester.getComponentFromLastRenderedPage(path("tableForm:selectButton"));
		
		@SuppressWarnings("unchecked")
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("tableForm:radioGroup"));
		
		/*
		 * radio button of first row. Holds the first
		 * BeanWithOrderedFields of the List supplied
		 * to the DataTable.
		 */
		@SuppressWarnings("unchecked")
		Radio<BeanWithOrderedFields> radio = (Radio<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path
					("tableForm:radioGroup:listTable:body:rows:1:cells:4:cell:radioButton"));
		assertSame(def.getList().get(0), radio.getModelObject());
		
		/*
		 * Set the first object of the BeanWithOrderedFields List as the 
		 * model object of the RadioGroup. This emulates the user actually
		 * clicking the above Radio button. 
		 */
		BeanWithOrderedFields selection = def.getList().get(0);
		radioGroup.setModelObject(selection);
		
		BeanWithOrderedFields modelOb = radioGroup.getModelObject();
		assertSame(selection,modelOb);

		callback.setExecuted(false);
		
		tester.executeAjaxEvent(button, "onclick");
		
		BeanWithOrderedFields modelOb2 = radioGroup.getModelObject();
		assertSame(modelOb2,modelOb);
		
		assertTrue(callback.isExecuted());
		assertSame(selection, def.getBeanModel().getObject());
	}
	
	/**
	 * def.setDisableUnauthorizedButtons(true);
	 * def.setItemSelectedActionFlag(new AlwaysDownFlag());
	 * 
	 * The expected result is that the pick button will be disabled.
	 */	
	@Test
	public void testPickAction_FlagDown_DisabledUnauthorizedTrue() {
		PickerPanelDef<BeanWithOrderedFields> def = createDef();
		def.setDisableUnauthorizedButtons(true);
		def.setItemSelectedActionFlag(new AlwaysDownFlag());

		tester.startPage(getTestPage(new PickerPanel<BeanWithOrderedFields>(def)));
		
		String buttonId = path("tableForm:selectButton");		
		tester.assertComponent(buttonId, AjaxButton.class);
		AjaxButton button = (AjaxButton)
			tester.getComponentFromLastRenderedPage(path("tableForm:selectButton"));
		
		assertFalse(button.isEnabled());
	}
	
	/**
	 * def.setDisableUnauthorizedButtons(false);
	 * def.setItemSelectedActionFlag(new AlwaysDownFlag());
	 * 
	 * The expected result is that the pick button will be enabled,
	 * but a message will be rendered in the feedback panel when it
	 * is pressed.
	 */	
	@Test
	public void testPickAction_FlagDown_DisabledUnauthorizedFalse() {
		PickerPanelDef<BeanWithOrderedFields> def = createDef();
		def.setDisableUnauthorizedButtons(false);
		def.setItemSelectedActionFlag(new AlwaysDownFlag());
		
		tester.startPage(getTestPage(new PickerPanel<BeanWithOrderedFields>(def)));
		
		String buttonId = path("tableForm:selectButton");
		tester.assertComponent(buttonId, AjaxButton.class);
		AjaxButton button = (AjaxButton)
			tester.getComponentFromLastRenderedPage(path("tableForm:selectButton"));
		assertTrue(button.isEnabled());
		
		String feedbackPanelId = path("feedback");
		tester.assertComponent(feedbackPanelId, FeedbackPanel.class);
		FeedbackPanel feedbackPanel = (FeedbackPanel) tester.getComponentFromLastRenderedPage(feedbackPanelId);
		
		/*
		 * No messages yet.
		 */
		assertEquals(0, feedbackPanel.getFeedbackMessagesModel().getObject().size());
		
		/*
		 * Press the button.
		 */
		callback.setExecuted(false);
		tester.executeAjaxEvent(button, "onclick");
		
		/*
		 * The action was not executed, and the down message of the flag
		 * is registered in the feedback panel.
		 */
		assertFalse(callback.isExecuted());
		assertEquals(1, feedbackPanel.getFeedbackMessagesModel().getObject().size());
		FeedbackMessage fm = feedbackPanel.getFeedbackMessagesModel().getObject().iterator().next();
		assertEquals(AlwaysDownFlag.DOWN_MESSAGE, fm.getMessage().toString());
		
	}
}