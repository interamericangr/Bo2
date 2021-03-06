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
package gr.interamerican.wicket.markup.html.panel.listTable;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.collections.BeanCollections;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.creators.ExportActionCreator;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.creators.SampleDataTableCreators;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link ListTablePanel}.
 */
@SuppressWarnings({ "nls", "unchecked" })
public class TestListTablePanel extends WicketTest {

	/**
		 * 
		 */
	public class DummyCreator implements ExportActionCreator<BeanWithOrderedFields> {

		/**
		 * Version UID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public SerializableRunnable getCreator(ExportDataSetup<BeanWithOrderedFields> input,
				SerializableSupplier<List<BeanWithOrderedFields>> data) {
			return () -> {
				// empty
			};
		}

	}

	/**
	 * Creates a sample definition.
	 * 
	 * @return returns a definition.
	 */
	ListTablePanelDef<BeanWithOrderedFields> createDef() {
		ListTablePanelDef<BeanWithOrderedFields> def = new ListTablePanelDefImpl<BeanWithOrderedFields>();
		def.setList(BeanCollections.listOfBeanWithOrderedFields());
		def.setBackAction(null);
		def.setDataTableCreator(SampleDataTableCreators.empty(5));
		def.setExportActionCreator(new DummyCreator());
		def.setWicketId(TestPage.TEST_ID);
		return def;
	}

	/**
	 * Tests creation of {@link ListTablePanel}.
	 */
	@Test
	public void testCreation() {
		tester.startPage(getTestPage(new ListTablePanel<BeanWithOrderedFields>(createDef())));
		tester.assertComponent(path("tableForm:listTable"), DataTable.class);
		tester.assertVisible(path("tableForm:listTable"));
		DataTable<BeanWithOrderedFields, String> table = (DataTable<BeanWithOrderedFields, String>) tester
				.getComponentFromLastRenderedPage(path("tableForm:listTable"));
		assertEquals(3, table.getColumns().size());
		assertEquals(7, table.getDataProvider().size());
	}
}