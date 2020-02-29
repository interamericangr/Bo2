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

import java.util.Date;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;
import gr.interamerican.bo2.utils.meta.formatters.DateFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link FunctionalColumn}.
 */
public class TestFunctionalColumn extends WicketTest {

	/**
	 * Test method for {@link FunctionalColumn#FunctionalColumn(IModel, SerializableComparableFunction, Formatter)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFunctionalColumn() {
		DateFormatter formatter = new DateFormatter("ddMMyyyy");
		IModel<String> displayModel = new Model<>("display");
		FunctionalColumn<Bean1, Date> tested = new FunctionalColumn<>(displayModel, Bean1::getRenewalDate, formatter);
		assertEquals("display", tested.getHeader());
		Bean1 bean = new Bean1();
		assertNull(tested.getFunction().apply(bean));
		assertNull(tested.getSortProperty().apply(bean));
		bean.setRenewalDate(DateUtils.today());
		assertEquals(DateUtils.today(), tested.getFunction().apply(bean));
		assertEquals(DateUtils.today(), tested.getSortProperty().apply(bean));
		assertEquals(formatter, tested.getFormatter());
	}

	/**
	 * Test method for {@link FunctionalColumn#getDataModel(IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetDataModel() {
		Bean1 bean = new Bean1();
		Model<Bean1> rowModel = new Model<Bean1>(bean);
		// with formatter
		FunctionalColumn<Bean1, Date> tested = new FunctionalColumn<>(Model.of("we"), Bean1::getRenewalDate, t -> "ignoredTheInput");
		assertNull(tested.getDataModel(rowModel).getObject());
		bean.setRenewalDate(DateUtils.today());
		assertEquals("ignoredTheInput",tested.getDataModel(rowModel).getObject());
		// no formatter
		tested = new FunctionalColumn<>(Model.of("we"), Bean1::getRenewalDate, null);
		bean.setRenewalDate(null);
		assertNull(tested.getDataModel(rowModel).getObject());
		bean.setRenewalDate(DateUtils.today());
		assertEquals(DateUtils.today(),tested.getDataModel(rowModel).getObject());
		// integration
		bean.setRenewalDate(null);
		Item<ICellPopulator<Bean1>> item = new Item<>("wee", 0);
		tested.populateItem(item, "weeee", rowModel);
		assertEquals("",((Label)item.get("weeee")).getDefaultModelObjectAsString());
		bean.setRenewalDate(DateUtils.today());
		tested.populateItem(item, "another", rowModel);
		assertNotEquals(DateUtils.today().toString(),((Label)item.get("another")).getDefaultModelObjectAsString());
	}
}