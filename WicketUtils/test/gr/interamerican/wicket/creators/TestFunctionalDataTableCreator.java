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
package gr.interamerican.wicket.creators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.BooleanFormatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.ExportableColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.MultipleSelectionsColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link FunctionalDataTableCreator}.
 */
public class TestFunctionalDataTableCreator extends WicketTest {
	/**
	 * Test for method {@link FunctionalDataTableCreator#empty()} 
	 */
	@Test
	public void testEmpty() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		assertNotNull(tested);
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#picker()}
	 */
	@Test
	public void testPicker() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.picker();
		assertNotNull(tested);
		assertEquals(1,tested.columns.size());
		assertEquals(SingleSelectionColumn.class,tested.columns.get(0).getClass());
	}

	/**
	 * Test for method {@link FunctionalDataTableCreator#multiple()}
	 */
	@Test
	public void testMultiple() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.multiple();
		assertNotNull(tested);
		assertEquals(1,tested.columns.size());
		assertEquals(MultipleSelectionsColumn.class,tested.columns.get(0).getClass());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#add(gr.interamerican.bo2.utils.functions.SerializableComparableFunction, org.apache.wicket.model.IModel)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAdd_SingleFunction() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested = tested.add(a -> a + 1, "test");
		assertEquals(1,tested.columns.size());
		assertEquals(FunctionalColumn.class, tested.columns.get(0).getClass());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#add(gr.interamerican.bo2.utils.functions.SerializableComparableFunction, org.apache.wicket.model.IModel)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAdd_twoFunctions() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested = tested.add(Integer::longValue, Long::intValue, Model.of("test"));
		assertEquals(1,tested.columns.size());
		assertEquals(FunctionalColumn.class, tested.columns.get(0).getClass());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#add(gr.interamerican.bo2.utils.functions.SerializableComparableFunction, org.apache.wicket.model.IModel)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAdd_threeFunctions() {
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested = tested.add(Integer::longValue, Long::intValue, Integer::longValue, Model.of("test"));
		assertEquals(1,tested.columns.size());
		assertEquals(FunctionalColumn.class, tested.columns.get(0).getClass());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#createDataTable(String, java.util.List)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateDataTable(){
		List<Integer> elements = new ArrayList<>();
		elements.add(1);
		elements.add(2);
		elements.add(3);

		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested.setDisablePaging(true);
		
		DataTable<Integer, SerializableComparableFunction<Integer, ?>> actual = tested.createDataTable("test", elements);
		assertEquals(DefaultDataTable.class,actual.getClass());
		assertEquals(0,actual.getColumns().size());
		assertEquals(elements.size(),actual.getRowCount());
		
		tested.setDisablePaging(false);
		tested.setRowsPerPage(2);
		actual = tested.createDataTable("test", elements);
		assertEquals(2L, actual.getPageCount());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#setRowsPerPage(Integer)}
	 */
	@Test
	public void testSetRowsPerPage(){
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested = tested.setRowsPerPage(2);
		assertEquals(2, tested.rowsPerPage.intValue());
	}
	
	/**
	 * Test for method {@link FunctionalDataTableCreator#setDisablePaging(Boolean)}
	 */
	@Test
	public void testSetDisablePage(){
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		tested = tested.setDisablePaging(true);
		assertTrue(tested.disablePaging);
	}
	
	/**
	 * Test method for {@link FunctionalDataTableCreator#setDefaultSort(SerializableComparableFunction)}
	 */
	@Test
	public void testSetDefaultSort(){
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		SerializableComparableFunction<Integer, Long> sortFunc = Integer::longValue;
		tested.setDefaultSort(sortFunc);
		assertEquals(sortFunc, tested.defaultSort);
	}

	/**
	 * Test method for {@link FunctionalDataTableCreator#setModification(SerializableUnaryOperator)}.
	 */
	@Test
	public void testSetModification(){
		FunctionalDataTableCreator<Integer> tested = FunctionalDataTableCreator.empty();
		long random = Math.abs(new Random().nextLong());
		SerializableUnaryOperator<DefaultDataTable<Integer, SerializableComparableFunction<Integer, ?>>> modification = m -> {
			m.setItemsPerPage(random);
			return m;
		};
		tested.setModification(modification);
		assertEquals(modification, tested.modification);
		assertEquals(random, tested.createDataTable("foo", Arrays.asList(4,6,6)).getItemsPerPage()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link FunctionalDataTableCreator#addExtraColumn(org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddExtraColumn(){
		FunctionalDataTableCreator<String> tested = FunctionalDataTableCreator.empty();
		FunctionalColumn<String, Boolean> column = new FunctionalColumn<String,Boolean>(Model.of("testColumn"), Boolean::valueOf, new BooleanFormatter());
		tested = tested.addExtraColumn(column);
		assertEquals(1,tested.columns.size());
		assertEquals(column,tested.columns.get(0));
	}

	/**
	 * Test method for {@link FunctionalDataTableCreator#addExtraColumns(org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn...)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddExtraColumns(){
		FunctionalDataTableCreator<String> tested = FunctionalDataTableCreator.empty();
		
		FunctionalColumn<String, Boolean> boolColumn = new FunctionalColumn<String,Boolean>(Model.of("testColumn"), Boolean::valueOf, new BooleanFormatter());
		FunctionalColumn<String, Boolean> secBoolColumn = new FunctionalColumn<String,Boolean>(Model.of("testColumn"), Boolean::valueOf, new BooleanFormatter());
		tested = tested.addExtraColumns(boolColumn,secBoolColumn);
		assertEquals(2,tested.columns.size());
		assertEquals(boolColumn,tested.columns.get(0));
		assertEquals(secBoolColumn,tested.columns.get(1));
	}

	/**
	 * Test method for
	 * {@link FunctionalDataTableCreator#addSelfDrawnColumns(BusinessObjectDescriptor)}
	 */
	@Test
	public void testAddSelfDrawnColumns() {
		FunctionalDataTableCreator<Bean1> tested = FunctionalDataTableCreator.empty();
		BusinessObjectDescriptor<Bean1> descriptor = new Bean1descriptor();
		tested = tested.addSelfDrawnColumns(descriptor);
		assertEquals(9, tested.columns.size());
	}

	/**
	 * Test method for
	 * {@link FunctionalDataTableCreator#addSelfDrawnColumn(gr.interamerican.bo2.utils.attributes.Named)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAddSelfDrawnColumn() {
		FunctionalDataTableCreator<Bean1> tested = FunctionalDataTableCreator.empty();
		FloatBoPropertyDescriptor descriptor = new FloatBoPropertyDescriptor();
		descriptor.setName("childBean.description");
		descriptor.setLabel("the label");
		tested.addSelfDrawnColumn(descriptor);
		assertEquals(1, tested.columns.size());
		@SuppressWarnings("unchecked")
		ExportableColumn<Bean1, ?, String> column = (ExportableColumn<Bean1, ?, String>) tested.columns.get(0);
		Bean1 bean = null;
		assertNull(column.getFunction().apply(bean));
		bean = new Bean1();
		assertNull(column.getFunction().apply(bean));
		ChildBean childBean = new ChildBean();
		bean.setChildBean(childBean);
		assertNull(column.getFunction().apply(bean));
		childBean.setDescription("a description");
		assertEquals("a description", column.getFunction().apply(bean));
		assertEquals("the label", column.getHeader());
	}
}
