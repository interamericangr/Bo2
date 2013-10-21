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
package gr.interamerican.bo2.utils.sql;

import gr.interamerican.bo2.utils.StringConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SqlProcessor}.
 */
public class TestSqlProcessor {

	/**
	 * Test removeDoubleHyphenSqlComments()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveDoubleHyphenSqlComments() {
		String sql = "--lala";
		String expected = StringConstants.SPACE;
		check(sql, expected);
		
		sql = "--lala\nselect";
		expected = " \nselect";
		check(sql, expected);
		
		sql = "\nPeter\n--lala\nselect";
		expected = "\nPeter\n \nselect";
		check(sql, expected);
		
		sql = "\nPeter\n--lala\nselect --comment";
		expected = "\nPeter\n \nselect  ";
		check(sql, expected);
	}
	
	/**
	 * Checks
	 * @param sql
	 * @param expected
	 */
	private void check(String sql, String expected) {
		String actual = SqlProcessor.removeDoubleHyphenSqlComments(sql);
		Assert.assertEquals(expected, actual);
	}
	
}
