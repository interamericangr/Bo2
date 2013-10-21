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

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.select.Select;

import org.junit.Assert;
import org.junit.Test;


/**
 * test case for {@link TableNamesFinderInJoin}, {@link TableNamesFinderInSelectElements},
 * {@link TableNamesFinderInWhereClause}
 */
public class TestTableNamesFinder {

	/**
	 * @throws JSQLParserException
	 */
	@Test
	public void testGetTableNames() throws JSQLParserException {
		String sql = "select a.a,b.b from a,b,c,d join e on e.a=a.a join f on f.a=a.a where d.a=a.a"; //$NON-NLS-1$
		JSqlParser parser = new CCJSqlParserManager();
		Statement st = parser.parse(new StringReader(sql));
		Select plain = (Select) st;
		TableNamesFinderInSelectElements selectFinder = new TableNamesFinderInSelectElements();
		plain.accept((StatementVisitor) selectFinder);
		Assert.assertTrue(selectFinder.getTableNames().contains("a")); //$NON-NLS-1$
		Assert.assertTrue(selectFinder.getTableNames().contains("b")); //$NON-NLS-1$
		Assert.assertFalse(selectFinder.getTableNames().contains("c")); //$NON-NLS-1$
		Assert.assertFalse(selectFinder.getTableNames().contains("d")); //$NON-NLS-1$
		TableNamesFinderInWhereClause whereFinder = new TableNamesFinderInWhereClause();
		plain.accept(whereFinder);
		Assert.assertTrue(whereFinder.getTableNames().contains("d")); //$NON-NLS-1$
		Assert.assertTrue(whereFinder.getTableNames().contains("a")); //$NON-NLS-1$
		Assert.assertFalse(whereFinder.getTableNames().contains("c")); //$NON-NLS-1$
		Assert.assertFalse(whereFinder.getTableNames().contains("b")); //$NON-NLS-1$
		TableNamesFinderInJoin joinFinder = new TableNamesFinderInJoin();
		plain.accept(joinFinder);
		Assert.assertEquals(5, joinFinder.getJoinTables().size());
	}
}
