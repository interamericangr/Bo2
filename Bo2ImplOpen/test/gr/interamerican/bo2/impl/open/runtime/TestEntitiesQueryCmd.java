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
package gr.interamerican.bo2.impl.open.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.StringsQuery1000;

import org.junit.Test;

/**
 * Unit tests for {@link EntitiesQueryCmd}.
 */
public class TestEntitiesQueryCmd {

	/**
	 * Test method for {@link EntitiesQueryCmd#EntitiesQueryCmd(Class)}.
	 */
	@Test
	public void testEntitiesQueryCmd() {
		EntitiesQueryCmd<StringsQuery1000, String> queryCmd = new EntitiesQueryCmd<StringsQuery1000, String>(
				StringsQuery1000.class);
		assertNotNull(queryCmd);
		assertNotNull(queryCmd.getQuery());
	}

	/**
	 * Test method for EntitiesQueryCmd#getResults()} .
	 * 
	 * @throws UnexpectedException
	 * @throws DataException
	 * @throws LogicException
	 */
	@Test
	public void testGetResults() throws LogicException, DataException, UnexpectedException {
		EntitiesQueryCmd<StringsQuery1000, String> queryCmd = new EntitiesQueryCmd<StringsQuery1000, String>(
				StringsQuery1000.class);
		assertEquals(queryCmd.getResults().size(), 1000);
	}
}