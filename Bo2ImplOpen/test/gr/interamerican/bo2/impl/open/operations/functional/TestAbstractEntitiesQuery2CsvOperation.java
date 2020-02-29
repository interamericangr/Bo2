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
package gr.interamerican.bo2.impl.open.operations.functional;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.bo2.utils.meta.beans.SerializableFunctionWithFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Replace;

/**
 * Tests {@link AbstractEntitiesQuery2CsvOperation}.
 */
public class TestAbstractEntitiesQuery2CsvOperation {

	/**
	 * Test method for {@link AbstractEntitiesQuery2CsvOperation#beforeQuery()}.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testBeforeQuery() throws LogicException, DataException {
		ExportDataSetup<BeanWith3Fields> setup = new ExportDataSetup<>();
		setup.addColumn(BeanWith3Fields::getField1);
		SampleEntitiesQuery2CsvOperation<BeanWith3Fields, EntitiesQuery<BeanWith3Fields>> tested = new SampleEntitiesQuery2CsvOperation<>(
				null, setup);
		tested.beforeQuery();
		assertTrue(tested.lines.isEmpty());
		setup.setFirstRows(Arrays.asList("a row"));
		setup.addColumn(BeanWith3Fields::getField3, "a header");
		tested.beforeQuery();
		assertEquals(2, tested.lines.size());
		assertEquals("a row", tested.lines.get(0));
		assertEquals("null;a header", tested.lines.get(1));
	}

	/**
	 * Test method for {@link AbstractEntitiesQuery2CsvOperation#afterQuery()}.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 */
	@SuppressWarnings("nls")
	@Test
	public void testAfterQuery() throws LogicException, DataException {
		ExportDataSetup<BeanWith3Fields> setup = new ExportDataSetup<>();
		SampleEntitiesQuery2CsvOperation<BeanWith3Fields, EntitiesQuery<BeanWith3Fields>> tested = new SampleEntitiesQuery2CsvOperation<>(
				null, setup);
		tested.afterQuery();
		assertTrue(tested.lines.isEmpty());
		setup.setLastRows(Arrays.asList("a last row"));
		tested.afterQuery();
		assertEquals(1, tested.lines.size());
		assertEquals("a last row", tested.lines.get(0));
	}

	/**
	 * Test method for {@link AbstractEntitiesQuery2CsvOperation#handleRow()}.
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test
	public void testHandleRow() throws LogicException, DataException {
		ExportDataSetup<BeanWith3Fields> setup = new ExportDataSetup<>();
		setup.addColumn(BeanWith3Fields::getField1,StringUtils::firstCapital);
		setup.addColumn(BeanWith3Fields::getField2);
		setup.addColumn(BeanWith3Fields::getField3);
		setup.registerTypeBasedFormatter(Integer.class, new Replace<>("replaced")); //$NON-NLS-1$
		BeanWith3Fields object = new BeanWith3Fields("a field", 444, 22.1); //$NON-NLS-1$
		@SuppressWarnings("unchecked")
		EntitiesQuery<BeanWith3Fields> query = MockUtils.mockEntitiesQuery(EntitiesQuery.class, Arrays.asList(object));
		SampleEntitiesQuery2CsvOperation<BeanWith3Fields, EntitiesQuery<BeanWith3Fields>> tested = new SampleEntitiesQuery2CsvOperation<>(
				query, setup);
		tested.beforeQuery();
		tested.handleRow();
		assertEquals(1, tested.lines.size());
		assertEquals("A field;replaced;22.1", tested.lines.get(0)); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link AbstractEntitiesQuery2CsvOperation#process(Object, SerializableFunctionWithFormatter)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testProcess() {
		ExportDataSetup<BeanWith2Fields> setup = new ExportDataSetup<>();
		SampleEntitiesQuery2CsvOperation<BeanWith2Fields, EntitiesQuery<BeanWith2Fields>> tested = new SampleEntitiesQuery2CsvOperation<>(
				null, setup);
		BeanWith2Fields bean = new BeanWith2Fields("the field", null);
		assertEquals("The field", tested.process(bean,
				new SerializableFunctionWithFormatter<>(BeanWith2Fields::getField1, StringUtils::firstCapital, null)));
		SerializableFunctionWithFormatter<BeanWith2Fields, String> column = new SerializableFunctionWithFormatter<>(BeanWith2Fields::getField1,
				null, null);
		assertEquals("the field", tested.process(bean, column));
		setup.registerTypeBasedFormatter(String.class, new Replace<>("replaced"));
		assertEquals("replaced", tested.process(bean, column));
	}
}