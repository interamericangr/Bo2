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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;
import gr.interamerican.wicket.test.WicketTest;

/**
 * The Class TestPropertiesBasedPickerDataTableCreator.
 */
@Deprecated
public class TestPropertiesBasedPickerDataTableCreator extends WicketTest {

	/**
	 * Returns a sample for the testing.
	 * 
	 * @return Sample
	 */
	@SuppressWarnings("nls")
	List<PropertiesBasedPickerDataTableCreator<ChildBean>> getSample() {
		String[] properties = new String[] { "name", "description", "birthdate" };
		IModel<String> selectColumnLabel = new Model<String>("label");
		List<PropertiesBasedPickerDataTableCreator<ChildBean>> creators = new ArrayList<PropertiesBasedPickerDataTableCreator<ChildBean>>();
		creators.add(
				new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, selectColumnLabel));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, properties,
				selectColumnLabel));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, properties,
				selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, null,
				selectColumnLabel, "name"));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, null));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, properties,
				selectColumnLabel, "name", null));
		creators.add(new PropertiesBasedPickerDataTableCreator<ChildBean>(ChildBean.class, properties, properties,
				selectColumnLabel, "name", new Formatter<?>[] { null, null, null }));
		return creators;
	}

	/**
	 * Test constructors and createDataTable.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreateDataTable() {
		List<ChildBean> elements = new ArrayList<ChildBean>();
		elements.add(new ChildBean());
		for (PropertiesBasedDataTableCreator<ChildBean> creator : getSample()) {
			RadioGroup<BeanWith1Field> group = new RadioGroup<>("group");
			group.add(creator.createDataTable("tested", elements));
			group.setMarkup(Markup.of("<wicket:extend> <table wicket:id=\"tested\" /></wicket:extend>"));
			tester.startComponentInPage(group);
			commonAssertions_noError(BaseWicketTester.StartComponentInPage.class);
		}
	}

	/**
	 * Test CreateColumns<br>
	 * The number of columns creates is as many as the properties passed plus
	 * one for the {@link SingleSelectionColumn}.
	 */
	@Test
	public void testCreateColumns() {
		for (PropertiesBasedPickerDataTableCreator<ChildBean> creator : getSample()) {
			assertEquals(4, creator.createColumns().size());
		}
	}
}