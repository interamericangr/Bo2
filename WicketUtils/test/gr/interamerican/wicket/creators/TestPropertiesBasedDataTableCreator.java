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

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.junit.Test;

import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link PropertiesBasedDataTableCreator}.
 */
@Deprecated
public class TestPropertiesBasedDataTableCreator extends WicketTest {

	/**
	 * Returns a sample for the testing.
	 * 
	 * @return Sample
	 */
	@SuppressWarnings("nls")
	List<PropertiesBasedDataTableCreator<ChildBean>> getSample() {
		String[] properties = new String[0];
		List<PropertiesBasedDataTableCreator<ChildBean>> creators = new ArrayList<PropertiesBasedDataTableCreator<ChildBean>>();
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties));
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, "name"));
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties));
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties, "name"));
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties, null));
		creators.add(
				new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties, "name", null));
		creators.add(
				new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties, "name", null));
		creators.add(new PropertiesBasedDataTableCreator<ChildBean>(ChildBean.class, properties, properties, "name",
				new Formatter<?>[] { null, null, null }));
		return creators;
	}

	/**
	 * Test constructors and createDataTable.
	 */
	@Test
	public void testCreateDataTable() {
		List<ChildBean> elements = new ArrayList<ChildBean>();
		elements.add(new ChildBean());
		for (PropertiesBasedDataTableCreator<ChildBean> creator : getSample()) {
			tester.startPage(new TestPage(creator.createDataTable(TestPage.TEST_ID, elements), Markup.table));
			commonAssertions_noError(TestPage.class);
			tester.assertComponent(subjectPath(), DataTable.class);
		}
	}

	/**
	 * Test CreateColumns.<br>
	 * Number of columns is equal to that the bean in use has.
	 */
	@Test
	public void testCreateColumns() {
		for (PropertiesBasedDataTableCreator<ChildBean> creator : getSample()) {
			assertEquals(3, creator.createColumns().size());
		}
	}
}