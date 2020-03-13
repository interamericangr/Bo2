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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.bo2.utils.meta.formatters.nf.NfDecimalFormatter;

/**
 * Unit test for {@link EntitiesQuery2CsvOperation}.
 */
@SuppressWarnings("nls")
public class TestEntitiesQuery2CsvOperation {

	/**
	 * Test for execute.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExecute() throws Exception {
		new AbstractBo2RuntimeCmd() {
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				ExportDataSetup<TypedSelectable<Long>> setup = new ExportDataSetup<>();
				setup.addColumn(TypedSelectable::getCode, new NfDecimalFormatter(2));
				setup.addColumn(TypedSelectable::getName);
				EntitiesQuery2CsvOperation<TypedSelectable<Long>, TsEntitiesQueryImpl> oper = new EntitiesQuery2CsvOperation<>(
						new TsEntitiesQueryImpl(), setup, "TestEntitiesQuery2CsvOperation.csv");
				oper.setManagerName("LOCALFS");
				open(oper);
				oper.execute();
			}
		}.execute();
	}

	/**
	 * Test method for {@link EntitiesQuery2CsvOperation#init(Provider)}.
	 * @throws InitializationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testInit() throws InitializationException {
		ExportDataSetup<BigDecimal> setup = new ExportDataSetup<>();
		EntitiesQuery2CsvOperation<BigDecimal, EntitiesQuery<BigDecimal>> tested = new EntitiesQuery2CsvOperation<>(
				null, setup, "stream");
		assertEquals(tested.setup, setup);
		tested.setManagerName("foo");
		Provider provider = mock(Provider.class);
		NamedStreamsProvider nsp = mock(NamedStreamsProvider.class);
		when(provider.getResource("foo", NamedStreamsProvider.class)).thenReturn(nsp);
		NamedStream notShared = mock(NamedStream.class);
		when(nsp.getStream(eq("stream"))).thenReturn(notShared);
		NamedStream shared = mock(NamedStream.class);
		when(nsp.getSharedStream(eq("stream"))).thenReturn(shared);
		tested.init(provider);
		assertSame(notShared, tested.out);
		tested.setUseSharedStream(true);
		tested.init(provider);
		assertSame(shared, tested.out);
	}

	/**
	 * Test method for {@link EntitiesQuery2CsvOperation#writeLine(String)}.
	 * 
	 * @throws DataException 
	 */
	@Test
	public void testWriteLine() throws DataException {
		EntitiesQuery2CsvOperation<BigDecimal, EntitiesQuery<BigDecimal>> tested = new EntitiesQuery2CsvOperation<>(
				null, null, null);
		tested.out = mock(NamedStream.class);
		tested.writeLine("a line");
		verify(tested.out).writeString(eq("a line"));
	}
}