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
package gr.interamerican.bo2.jsqlparser.visitors;

import gr.interamerican.bo2.jsqlparser.op.UselessJoinsFinder;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import org.junit.Assert;
import org.junit.Test;


/**
 * test case for {@link RebuildQueryWithOutUselessJoins}
 */
public class TestRebuildQueryWithOutUselessJoins {

	/**
	 * test method for {@link RebuildQueryWithOutUselessJoins#getResult()}
	 * 
	 * @throws SqlParseException
	 * @throws JSQLParserException
	 */
	@Test
	public void testGetResult() throws SqlParseException, JSQLParserException {
		String sql = "select a.a,b.b from a,b,d union all select w from q"; //$NON-NLS-1$
		RebuildQueryWithOutUselessJoins rebuild = new RebuildQueryWithOutUselessJoins();
		rebuild.setUselessJoins(UselessJoinsFinder.INSTANCE.find(sql));
		JSqlParser parser = new CCJSqlParserManager();
		Statement st = parser.parse(new StringReader(sql));
		if (!(st instanceof Select)) {// statement is not select, nothing can be removed
			return;
		}
		Select plain = (Select) st;
		plain.accept(rebuild);
		Assert.assertFalse(rebuild.getResult().contains("d")); //$NON-NLS-1$
		Assert.assertTrue(rebuild.getResult().contains("all")); //$NON-NLS-1$
	}

}
