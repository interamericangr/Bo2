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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.FunctionalSortableDataProvider;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@AjaxLink AjaxLinkPickColumn}
 */
public class TestAjaxLinkPickColumn extends WicketTest {

	/**
	 * Test method for {@AjaxLink AjaxLinkPickColumn#AjaxLinkPickColumn(IModel,
	 * IModel, PickAction, Behavior...)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAjaxLinkPickColumnIModelOfStringEmbeddedSerializableConsumerOfT() {
		AjaxLinkPickColumn<SampleBean2, String> tested = new AjaxLinkPickColumn<>(Model.of("text"), Model.of("display"),
				(t, a) -> System.out.println(a.getField1()));
		assertNotNull(tested);
		assertEquals("text", tested.getDisplayModel().getObject());
	}

	/**
	 * Test method for {@AjaxLink AjaxLinkPickColumn#populateItem(Item, String,
	 * IModel)}.
	 */
	@Test
	public void testPopulateItem() {
		startAndTestComponent(DefaultDataTable.class, Markup.table);
	}

	@SuppressWarnings("nls")
	@Override
	protected Component initializeComponent(String wicketId) {
		AjaxLinkPickColumn<SampleBean2, SerializableComparableFunction<SampleBean2, ?>> tested = new AjaxLinkPickColumn<>(
				Model.of("text"), Model.of("display"), (t, a) -> System.out.println(a.getField1()));
		return new DefaultDataTable<>(wicketId, Arrays.asList(tested),
				new FunctionalSortableDataProvider<>(Arrays.asList(new SampleBean2())), 10);
	}
}