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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.test.def.posamples.Invoice;

import org.junit.Test;

/**
 * Unit tests for {@link JdbcDetachStrategy}.
 */
public class TestJdbcDetachStrategy {
	
	/**
	 * Unit tests for empty methods.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testJdbcDetachStrategy() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				Invoice i = Factory.create(Invoice.class);
				JdbcDetachStrategy.INSTANCE.detach(i, getProvider());
				JdbcDetachStrategy.INSTANCE.reattach(i, getProvider());
				JdbcDetachStrategy.INSTANCE.markExplicitSave(i, getProvider());
			}
		}.execute();
		
	}

}
