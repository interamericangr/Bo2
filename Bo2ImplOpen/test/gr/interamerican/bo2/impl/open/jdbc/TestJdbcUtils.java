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

import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;

import org.junit.Test;

/**
 * Unit Test of {@link JdbcUtils}.
 */
public class TestJdbcUtils {

	/**
	 * Test method for {@link JdbcUtils#getParameterNamesArrays(Supplier, Supplier)}.
	 */
	@Test
	public void testGetParameterNamesArrays() {
		Object[] parms = JdbcUtils.getParameterNamesArrays(this::getParams, this::getSql);
		assertEquals(2, parms.length);
		assertEquals("one", parms[0]); //$NON-NLS-1$
		assertEquals("other", parms[1]); //$NON-NLS-1$
		parms = JdbcUtils.getParameterNamesArrays(this::getEmptyParams, this::getSql);
		assertEquals(3, parms.length);
		assertEquals("id", parms[0]); //$NON-NLS-1$
		assertEquals("other.id", parms[1]); //$NON-NLS-1$
		assertEquals("yetAnother", parms[2]); //$NON-NLS-1$
	}

	/**
	 * Returns empty parameters
	 * 
	 * @return An Array with Empty parameters
	 */
	String[] getEmptyParams() {
		return null;
	}

	/**
	 * Returns empty parameters
	 * 
	 * @return An Array with Empty parameters
	 */
	@SuppressWarnings("nls")
	String[] getParams() {
		return new String[] { "one", "other" };
	}

	/**
	 * Gets an sql for this test
	 * 
	 * @return an sql
	 */
	String getSql() {
		return "select * from X__X.foo where id = :id and otherId = :other.id and yetAnother = :yetAnother "; //$NON-NLS-1$
	}
}