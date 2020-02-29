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
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.BaseWicketTester;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;
import gr.interamerican.wicket.test.WicketTest;

/**
 * The Class TestPropertiesBasedMultipleSelectionsDataTableCreator.
 */
@Deprecated
public class TestPropertiesBasedMultipleSelectionsDataTableCreator extends WicketTest {

	/**
	 * Returns a sample for the testing.
	 * 
	 * @return Sample
	 */
	@SuppressWarnings("nls")
	List<PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>> getSample() {
		IModel<String> selectColumnLabel = new Model<String>("label");
		String[] properties = new String[] { "name", "description", "birthdate" };
		List<PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>> creators = new ArrayList<PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>>();
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel));
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, "name"));
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, properties));
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, properties, "name"));
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, properties, "name", null));
		creators.add(new PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean>(ChildBean.class, properties,
				selectColumnLabel, properties, "name", new Formatter<?>[] { null, null, null }));
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
		for (PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean> creator : getSample()) {
			CheckGroup<ChildBean> group = new CheckGroup<>("group", new ArrayList<>());
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
		for (PropertiesBasedMultipleSelectionsDataTableCreator<ChildBean> creator : getSample()) {
			assertEquals(4, creator.createColumns().size());
		}
	}
}