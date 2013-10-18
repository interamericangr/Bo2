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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.queries.TsEntitiesQueryImpl;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.nf.NfDecimalFormatter;
import gr.interamerican.bo2.utils.meta.formatters.nf.NfObjectFormatter;

import org.junit.Test;

/**
 * Unit test for {@link EntitiesQuery2CsvOperation}.
 */
@SuppressWarnings("nls")
public class TestEntitiesQuery2CsvOperation {
	/**
	 * Properties to extract.
	 */	
	static String[] properties = {
		"code", "name"
	};
	
	/**
	 * Formatters for the properties.
	 */
	@SuppressWarnings("rawtypes")
	static Formatter[] formatters = {
		new NfDecimalFormatter(2),
		new NfObjectFormatter()		
	};
	
	/**
	 * Test for execute.
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testExecute() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {				
				Oper oper = new Oper(properties, formatters);
				oper.init(this.getProvider());
				oper.open();
				oper.execute();
				oper.close();
			}
		}.execute();
	}
	
	/**
	 * Sample implementation to test.
	 */
	@ManagerName("LOCALFS")
	class Oper 
	extends EntitiesQuery2CsvOperation<TypedSelectable<Long>, TsEntitiesQueryImpl> {

		/**
		 * Creates a new Op object.
		 * 
		 * @param properties
		 * @param formatters
		 */
		public Oper(String[] properties, Formatter<?>[] formatters) {
			super(new TsEntitiesQueryImpl(), properties, formatters, "TestEntitiesQuery2CsvOperation.csv");
		}
		
	}

}
