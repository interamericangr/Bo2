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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.samples.creators.DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.ITestPageSource;
import org.junit.Test;

/**
 * Unit tests for {@link MultipleSelectionsPanel}
 */
@SuppressWarnings("nls")
public class TestMultipleSelectionsPanel extends WicketTest {
	
	/**
	 * Dummy callback for item selection.
	 */
	private DummyCallback callback = new DummyCallback();
	
	/**
	 * table creator.
	 */
	private DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes creator = createCreator();
	
	/**
	 * Creates the {@link DataTableCreator}.
	 * 
	 * @return Returns a DataTableCreatorForBeanWithOrderedFields.
	 */
	DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes createCreator() {
		DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes cr = 
			new DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes();		
		cr.setRowsPerPage(2);
		return cr;
	}
	
	/**
	 * Creates a sample definition.
	 * 
	 * @return returns a definition.
	 */
	MultipleSelectionsPanelDef<BeanWithOrderedFields> createDef() {		
		MultipleSelectionsPanelDef<BeanWithOrderedFields> def = 
			new MultipleSelectionsPanelDefImpl<BeanWithOrderedFields>();
		def.setBackAction(null);
		def.setDataTableCreator(creator);
		def.setItemsSelectedAction(callback);
		def.setSelectionsModel(new Model<ArrayList<BeanWithOrderedFields>>());
		def.setList(BeanCollections.listOfBeanWithOrderedFields());
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}
	
	/**
	 * Creates a page source
	 * 
	 * @param def
	 * @return Returns a page source.
	 */
	@SuppressWarnings("serial")
	ITestPageSource pageSource(final MultipleSelectionsPanelDef<BeanWithOrderedFields> def) {		
		return new ITestPageSource() {
			public Page getTestPage() {
				MultipleSelectionsPanel<BeanWithOrderedFields> panel = 
					new MultipleSelectionsPanel<BeanWithOrderedFields>(def);
				return new TestPage(panel);
			}
		};
	}
	
	/**
	 * Tests creation of {@link MultipleSelectionsPanel}.
	 * 
	 * Also tests that pressing the select button, selects the selected items. 
	 */	
	@Test
	public void testCreation() {
		MultipleSelectionsPanelDef<BeanWithOrderedFields> def = createDef(); 
		tester.startPage(pageSource(def));
		
		tester.assertComponent(path("tableForm:checkGroup"), CheckGroup.class);
		tester.assertComponent(path("tableForm:checkGroup:listTable"), DataTable.class);
		tester.assertComponent(path("tableForm:checkGroup:listTable:body:rows:1:cells:4:cell:checkBox"), Check.class);
		
		String buttonId = path("tableForm:selectButton");		
		tester.assertComponent(buttonId, AjaxButton.class);
		AjaxButton button = (AjaxButton)
			tester.getComponentFromLastRenderedPage(path("tableForm:selectButton"));
		
		@SuppressWarnings("unchecked")
		CheckGroup<BeanWithOrderedFields> checkGroup = (CheckGroup<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path("tableForm:checkGroup"));
		
		/*
		 * radio button of first row. Holds the first
		 * BeanWithOrderedFields of the List supplied
		 * to the DataTable.
		 */
		@SuppressWarnings("unchecked")
		Check<BeanWithOrderedFields> check1 = (Check<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path
					("tableForm:checkGroup:listTable:body:rows:1:cells:4:cell:checkBox"));
		assertSame(def.getList().get(0), check1.getModelObject());
		
		@SuppressWarnings("unchecked")
		Check<BeanWithOrderedFields> check2 = (Check<BeanWithOrderedFields>)
			tester.getComponentFromLastRenderedPage(path
				("tableForm:checkGroup:listTable:body:rows:2:cells:4:cell:checkBox"));
		assertSame(def.getList().get(1), check2.getModelObject());
		
		/*
		 * Set the first and second object of the BeanWithOrderedFields List as the 
		 * model object of the RadioGroup. This emulates the user actually having
		 * clicked the above check boxes. 
		 */
		List<BeanWithOrderedFields> selections = new ArrayList<BeanWithOrderedFields>();
		selections.add(check1.getModelObject());
		selections.add(check2.getModelObject());
		checkGroup.setModelObject(selections);
		
		callback.setExecuted(false);
		
		/*
		 * We insert the values in the request parameters manually, because
		 * the formTester.submit() will assign new ids in the path of the
		 * radio buttons and, as a result, the AjaxEvent will fail to locate
		 * the correct radio and retrieve the model. So, normally we would do:
		 * 
		 * FormTester formTester = tester.newFormTester(path("tableForm"));
		 * formTester.select("radioGroup", 0);
     	 * formTester.submit();
     	 * 
     	 * TODO: is there a way to have the ajaxevent submit the form maybe?
     	 * Having to do this in two steps is a bit counter-intuitive.
		 * 
		 * The following line is the equivalent of having clicked the first
		 * row radio. "radio" is the wicket:id of the radio buttons and 0 stands
		 * for the first row.
		 */
		Map<String, String[]> map= tester.getWicketRequest().getParameterMap();	
		map.put("testId:tableForm:checkGroup", new String[]{"check0", "check1"});

		tester.executeAjaxEvent(button, "onclick");
		
		assertTrue(callback.isExecuted());
		assertSame(selections, def.getSelectionsModel().getObject());
	}

}
