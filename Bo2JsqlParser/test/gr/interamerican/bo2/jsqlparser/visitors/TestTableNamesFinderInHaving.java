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
import net.sf.jsqlparser.statement.select.Select;

import org.junit.Assert;
import org.junit.Test;


/**
 * test suite {@link TableNamesFinderInHaving}
 */
public class TestTableNamesFinderInHaving {

	/**
	 * test method for {@link TableNamesFinderInHaving#getHavingTables()}
	 * 
	 * @throws JSQLParserException
	 */
	@Test
	public void testGetHavingTables() throws JSQLParserException {
		String sql = "select a from b having c.a=0"; //$NON-NLS-1$
		JSqlParser parser = new CCJSqlParserManager();
		Statement st = parser.parse(new StringReader(sql));
		Select plain = (Select) st;
		TableNamesFinderInHaving finder = new TableNamesFinderInHaving();
		plain.accept(finder);
		Assert.assertEquals(1, finder.getHavingTables().size());
	}
}
