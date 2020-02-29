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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * Tests {@link EntitiesQuery2CsvPrintStreamOperation}.
 */
public class TestEntitiesQuery2CsvPrintStreamOperation {

	/**
	 * Test method for
	 * {@link EntitiesQuery2CsvPrintStreamOperation#writeLine(String)}.
	 * 
	 * @throws DataException
	 */
	@Test
	public void testWriteLine() throws DataException {
		PrintStream out = mock(PrintStream.class);
		EntitiesQuery2CsvPrintStreamOperation<String, EntitiesQuery<String>> op = new EntitiesQuery2CsvPrintStreamOperation<>(
				null, null, out);
		op.writeLine("a line"); //$NON-NLS-1$
		verify(out).println(eq("a line")); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link EntitiesQuery2CsvPrintStreamOperation#EntitiesQuery2CsvPrintStreamOperation(EntitiesQuery, ExportDataSetup, PrintStream)}.
	 * @throws DataException 
	 * @throws LogicException 
	 */
	@Test
	public void testEntitiesQuery2CsvPrintStreamOperation() throws LogicException, DataException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(output);
		BeanWith3Fields first = new BeanWith3Fields("first", null, 99.9); //$NON-NLS-1$
		BeanWith3Fields second = new BeanWith3Fields(null, 5, 10.4);
		@SuppressWarnings("unchecked")
		EntitiesQuery<BeanWith3Fields> query = MockUtils.mockEntitiesQuery(EntitiesQuery.class, first, second);
		ExportDataSetup<BeanWith3Fields> setup = new ExportDataSetup<>();
		setup.addColumn(BeanWith3Fields::getField3, NumberUtils::format, "3rd"); //$NON-NLS-1$
		setup.addColumn(BeanWith3Fields::getField1, StringUtils::curlyBrackets);
		setup.addColumn(BeanWith3Fields::getField2, "2nd"); //$NON-NLS-1$
		EntitiesQuery2CsvPrintStreamOperation<BeanWith3Fields, EntitiesQuery<BeanWith3Fields>> op = new EntitiesQuery2CsvPrintStreamOperation<>(
				query, setup , out);
		op.execute();
		String string = output.toString();
		String[] concatSeparated = TokenUtils.split(string, System.lineSeparator(), false);
		assertEquals("3rd;null;2nd",concatSeparated[0]); //$NON-NLS-1$
		assertEquals("99,9;{first};null",concatSeparated[1]); //$NON-NLS-1$
		assertEquals("10,4;{null};5",concatSeparated[2]); //$NON-NLS-1$
	}
}