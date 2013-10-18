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
package gr.interamerican.wicket.markup.html.panel.crud.picker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.AbstractCallbackAction;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.creators.DataTableCreatorForBeanWithOrderedFieldsWithRadios;
import gr.interamerican.wicket.samples.creators.FieldsPanelCreatorForBeanWithOrderedFields;
import gr.interamerican.wicket.test.WicketTest;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CrudPickerPanel}.
 */
@SuppressWarnings({"nls", "unchecked"})
public class TestCrudPickerPanel extends WicketTest {
	
	/**
	 * Error message.
	 */
	private static final String ERROR_MSG = "error";
	
	/**
	 * Panel under testing.
	 */
	private CrudPickerPanel<BeanWithOrderedFields> panel;
	
	/**
	 * Tests setup.
	 */
	@Before
	public void setup() {
		definition = createDef();
		panel = new CrudPickerPanel<BeanWithOrderedFields>(definition) {
			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected BeanWithOrderedFields newBean() {
				return new BeanWithOrderedFields();
			}
		};
	}
	
	/**
	 * Tests creation of {@link CrudPickerPanel}.
	 */	
	@Test
	public void testCreation() {
		tester.startPage(testPageSource(panel));
		
		tester.assertComponent(path("tableForm"), Form.class);
		tester.assertComponent(path("tableForm:radioGroup"), RadioGroup.class);
		tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
		
		tester.assertComponent(path("tableForm:selectButton"), CallbackAjaxButton.class);
		tester.assertComponent(path("tableForm:editButton"), AjaxButton.class);
		tester.assertComponent(path("tableForm:newButton"), AjaxButton.class);
		tester.assertComponent(path("tableForm:deleteButton"), CallbackAjaxButton.class);
		
		tester.assertInvisible(path("beanPanel"));
		tester.assertInvisible(path("tableForm:backButton"));
		
	}
	
	/**
	 * Tests pressing delete button.
	 */
	@Test
	public void testDelete() {		
		tester.startPage(testPageSource(panel));
		
		assertEquals(7, definition.getList().size());
		
		tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	DataTable<BeanWithOrderedFields> table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	assertEquals(7, table.getDataProvider().size());
		
		tester.assertComponent(path("tableForm:radioGroup"), RadioGroup.class);
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>) 
			tester.getComponentFromLastRenderedPage(path("tableForm:radioGroup"));
		radioGroup.setModelObject(definition.getList().get(0));
		
		tester.assertComponent(path("tableForm:deleteButton"), CallbackAjaxButton.class);
		CallbackAjaxButton deleteButton = (CallbackAjaxButton) tester.
			getComponentFromLastRenderedPage(path("tableForm:deleteButton"));
		
		Map<String, String[]> map= tester.getWicketRequest().getParameterMap();	
		map.put("testId:tableForm:radioGroup", new String[]{"radio0"});
    	tester.executeAjaxEvent(deleteButton, "onclick");
    	
    	assertEquals(6, list.size());
    	
    	tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	
    	assertEquals(6, table.getDataProvider().size());
	}
	
	/**
	 * Tests pressing the new button and storing
	 * a new bean.
	 */
	@Test
	public void testNew() {		
		tester.startPage(testPageSource(panel));
		
		assertEquals(7, definition.getList().size());
		
		tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	DataTable<BeanWithOrderedFields> table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	assertEquals(7, table.getDataProvider().size());
		
		tester.assertComponent(path("tableForm:radioGroup"), RadioGroup.class);
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>) 
			tester.getComponentFromLastRenderedPage(path("tableForm:radioGroup"));
		radioGroup.setModelObject(definition.getList().get(0));
		
		tester.assertComponent(path("tableForm:newButton"), AjaxButton.class);
		AjaxButton newButton = (AjaxButton) tester.
			getComponentFromLastRenderedPage(path("tableForm:newButton"));
		
    	tester.executeAjaxEvent(newButton, "onclick");
    	
    	tester.assertComponent(path("beanPanel:beanForm"), Form.class);
    	tester.assertComponent(path("beanPanel:beanForm:beanFormFieldsPanel"), Panel.class);
    	tester.assertComponent(path("beanPanel:beanForm:executeButton"), CallbackAjaxButton.class);
    	
    	CallbackAjaxButton execButton = (CallbackAjaxButton) tester.
			getComponentFromLastRenderedPage(path("beanPanel:beanForm:executeButton"));
    	
    	userInput = new BeanWithOrderedFields("11", "22", 33, 44L, 5.5d);
		FormTester formTester = tester.newFormTester(path("beanPanel:beanForm"));
		formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(userInput.getThird()));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", String.valueOf(userInput.getFifth()));
		formTester.submit();
    	tester.executeAjaxEvent(execButton, "onclick");
    	
    	tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	
    	assertEquals(8, table.getDataProvider().size());
    	
    	tester.assertInvisible(path("beanPanel"));
	}
	
	/**
	 * Tests pressing the new button and then canceling
	 * the insertion of the new bean.
	 */
	@Test
	public void testNewThenBack() {
		tester.startPage(testPageSource(panel));
		
		tester.assertComponent(path("tableForm:newButton"), AjaxButton.class);
		AjaxButton newButton = (AjaxButton) tester.
			getComponentFromLastRenderedPage(path("tableForm:newButton"));
		
    	tester.executeAjaxEvent(newButton, "onclick");
    	
    	tester.assertComponent(path("beanPanel:beanForm:backButton"), CallbackAjaxButton.class);
    	CallbackAjaxButton backButton = (CallbackAjaxButton) tester.
    		getComponentFromLastRenderedPage(path("beanPanel:beanForm:backButton"));
    	
    	tester.executeAjaxEvent(backButton, "onclick");
    	
    	tester.assertInvisible(path("beanPanel"));
    	
	}
		
	/**
	 * Tests pressing the update button and updating a bean.
	 */
	@Test
	public void testUpdate() {
		tester.startPage(testPageSource(panel));
		
		assertEquals(7, definition.getList().size());
		
		tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	DataTable<BeanWithOrderedFields> table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	assertEquals(7, table.getDataProvider().size());
		
		tester.assertComponent(path("tableForm:radioGroup"), RadioGroup.class);
		RadioGroup<BeanWithOrderedFields> radioGroup = (RadioGroup<BeanWithOrderedFields>) 
			tester.getComponentFromLastRenderedPage(path("tableForm:radioGroup"));
		radioGroup.setModelObject(definition.getList().get(0));
		
		tester.assertComponent(path("tableForm:editButton"), AjaxButton.class);
		AjaxButton editButton = (AjaxButton) tester.
			getComponentFromLastRenderedPage(path("tableForm:editButton"));
		
		Map<String, String[]> map= tester.getWicketRequest().getParameterMap();	
		map.put("testId:tableForm:radioGroup", new String[]{"radio0"});
    	tester.executeAjaxEvent(editButton, "onclick");
    	
    	tester.assertVisible(path("beanPanel:beanForm:beanFormFieldsPanel"));
    	Panel beanPanel = (Panel) tester.
    		getComponentFromLastRenderedPage(path("beanPanel:beanForm:beanFormFieldsPanel"));
    	assertEquals(definition.getList().get(0), beanPanel.getDefaultModelObject());
    	
    	/*
    	 * Will leave first two fields intact and modify the third
    	 */
    	userInput = (BeanWithOrderedFields) beanPanel.getDefaultModelObject();
		FormTester formTester = tester.newFormTester(path("beanPanel:beanForm"));
		formTester.setValue("beanFormFieldsPanel:first", String.valueOf(userInput.getFirst()));
		formTester.setValue("beanFormFieldsPanel:second", String.valueOf(userInput.getSecond()));
		formTester.setValue("beanFormFieldsPanel:third", String.valueOf(editInt));
		formTester.setValue("beanFormFieldsPanel:fourth", String.valueOf(userInput.getFourth()));
		formTester.setValue("beanFormFieldsPanel:fifth", String.valueOf(userInput.getFifth()));
		formTester.submit();
		
		Integer changed = ((BeanWithOrderedFields) beanPanel.getDefaultModelObject()).getThird();
		assertEquals(changed, editInt);
		
		tester.assertComponent(path("beanPanel:beanForm:executeButton"), CallbackAjaxButton.class);
		CallbackAjaxButton updateButton = (CallbackAjaxButton) tester.
			getComponentFromLastRenderedPage(path("beanPanel:beanForm:executeButton"));
		
		tester.executeAjaxEvent(updateButton, "onclick");
    	
    	tester.assertComponent(path("tableForm:radioGroup:listTable"), DataTable.class);
    	table = (DataTable<BeanWithOrderedFields>) tester.
			getComponentFromLastRenderedPage(path("tableForm:radioGroup:listTable"));
    	assertEquals(7, table.getDataProvider().size());
    	
    	tester.assertInvisible(path("beanPanel"));
	}
	
	
	
	
	
	/**********************************************************
	 * SAMPLE SETUP GOES ON BELOW THIS POINT...
	 **********************************************************/
	
	/**
	 * Integer for editing a bean. 
	 */
	private static final Integer editInt = 333;
	
	/**
	 * sample bean.
	 */
	BeanWithOrderedFields bean = new BeanWithOrderedFields("","",0,0L,0.0);
	
	/**
	 * user input.
	 */
	BeanWithOrderedFields userInput = new BeanWithOrderedFields("","",0,0L,0.0);
	
	/**
	 * table creator.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithRadios tableCreator = createCreator();
	
	/**
	 * Panel definition.
	 */
	CrudPickerPanelDef<BeanWithOrderedFields> definition;
	
	/**
	 * list with results.
	 */
	List<BeanWithOrderedFields> list = BeanCollections.listOfBeanWithOrderedFields();
	
	/**
	 * Creates the {@link DataTableCreator}.
	 * 
	 * @return Returns a DataTableCreatorForBeanWithOrderedFields.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithRadios createCreator() {
		DataTableCreatorForBeanWithOrderedFieldsWithRadios cr = 
			new DataTableCreatorForBeanWithOrderedFieldsWithRadios();		
		cr.setRowsPerPage(5);
		return cr;
	}
	
	/**
	 * Creates the {@link CrudPickerPanelDef}.
	 * 
	 * @return a CrudPickerPanelDef definition.
	 */
	CrudPickerPanelDef<BeanWithOrderedFields> createDef() {
		
		FieldsPanelCreatorForBeanWithOrderedFields panelCreator = 
			new FieldsPanelCreatorForBeanWithOrderedFields();
		
		CrudPickerPanelDef<BeanWithOrderedFields> def =
			new CrudPickerPanelDefImpl<BeanWithOrderedFields>();
		
		def.setBackAction(null);
		def.setList(list);
		def.setBeanFieldsPanelCreator(panelCreator);
		def.setDataTableCreator(tableCreator);
		def.setDeleteAction(new DeleteAction());
		def.setItemSelectedAction(new PickAction());
		def.setBeanModel(new CompoundPropertyModel<BeanWithOrderedFields>(new BeanWithOrderedFields()));
		def.setSaveAction(new SaveAction());
		def.setUpdateAction(new UpdateAction());
		def.setWicketId(TestPage.TEST_ID);
		
		return def;
	}
	
	
	/**
	 * Abstract action.
	 */
	abstract class AbstractAction extends AbstractCallbackAction {
		
		public void callBack(AjaxRequestTarget target) {
			target.addComponent(panel);
			work();
		}
		
		public void callBack(AjaxRequestTarget target, Form<?> form) {
			target.addComponent(panel);
			work();
		}
		
		/**
		 * update method.
		 */
		abstract protected void work();
		
	}
	
	
	/**
	 * Save action.
	 */
	class SaveAction extends AbstractAction {
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
	class UpdateAction extends AbstractAction {		
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
	class DeleteAction extends AbstractAction {
		@Override 
		protected void work() {
			/* delete from the database here */
		}
	}
	
	/**
	 * Delete action that simulates that a rule exception is thrown
	 * somewhere in its work(). This will normally be caught by the
	 * wicket block performing the action and registered to the
	 * calling Component as an error.
	 */
	class PickAction extends AbstractAction {
		@Override
		protected void work() {
			getCaller().error(ERROR_MSG);
		}
	}

}
