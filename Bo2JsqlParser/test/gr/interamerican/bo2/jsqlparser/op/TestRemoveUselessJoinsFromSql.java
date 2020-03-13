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
package gr.interamerican.bo2.jsqlparser.op;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.jsqlparser.GenericParser;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.sql.SqlProcessor;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;
import gr.interamerican.bo2.utils.sql.parsers.ParserUtils;


/**
 * test suite for {@link RemoveUselessJoinsFromSql}.
 */
public class TestRemoveUselessJoinsFromSql {

	/**
	 * test case 1.
	 *
	 * @throws SqlParseException the sql parse exception
	 */
	@Test
	public void test() throws SqlParseException {
		String sql = "select a.a from a JOIN b ON b.a = a.a AND b.b = 5"; //$NON-NLS-1$
		Assert.assertEquals(SqlProcessor.normalizeSql(RemoveUselessJoinsFromSql.INSTANCE.remove(sql)),
				SqlProcessor.normalizeSql(sql));
	}

	/**
	 * test case 2.
	 *
	 * @throws SqlParseException the sql parse exception
	 */
	@Test
	public void testTableJoin() throws SqlParseException {
		String sql = "select a.a from a JOIN (select b.a as a from b where b.c=5) as c"; //$NON-NLS-1$
		assertEquals(sql, RemoveUselessJoinsFromSql.INSTANCE.remove(sql));
	}

	/**
	 * test case 3.
	 *
	 * @throws SqlParseException the sql parse exception
	 */
	@Test
	public void testJoinWithSelect() throws SqlParseException {
		String sql = "select a.a from a JOIN (select b.a as a from b join d on b.c=d.c where b.c=5) as c"; //$NON-NLS-1$
		assertEquals(sql, RemoveUselessJoinsFromSql.INSTANCE.remove(sql));
	}

	/**
	 * test case 4, generic load from file.
	 *
	 * @throws SqlParseException the sql parse exception
	 */
	@Test
	public void testFromFile() throws SqlParseException {
		// TODO : create a complex sql script on existing test db and test it
		String sql = StreamUtils.getStringFromResourceFile("/gr/interamerican/bo2/jsqlparser/op/test.sql"); //$NON-NLS-1$
		System.out.println(SqlProcessor.normalizeSql(sql));
		String s = ParserUtils.removeNullParameters(new GenericParser(), null, sql);
		System.out.println(s);
	}
}