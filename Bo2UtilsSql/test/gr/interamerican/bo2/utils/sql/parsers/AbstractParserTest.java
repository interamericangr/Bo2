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
package gr.interamerican.bo2.utils.sql.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;

import java.util.List;

/**
 * 
 */
public class AbstractParserTest {
	
	/**
	 * Check a column.
	 * 
	 * @param columns
	 *        List with columns.
	 * @param colNo
	 *        Column no.
	 * @param name
	 *        Expected column name.
	 * @param tbCreator
	 *        Expected column table creator.
	 * @param tbName
	 *        Expected column table name.
	 * @param alias 
	 *        Expected column alias.
	 */
	protected void checkColumn(List<Column> columns, int colNo, String name, String tbCreator, String tbName, String alias) {
		List<Column> cols = SelectionUtils.selectByProperty("columnNo", colNo, columns, Column.class); //$NON-NLS-1$
		assertEquals(1, cols.size());		
		Column col = cols.get(0); 
		assertNotNull(col);
		assertEquals(name,col.getName());
		assertEquals(tbCreator,col.getTbCreator());
		assertEquals(tbName,col.getTbName());
		assertEquals(alias,col.getAlias());
	}
	
	/**
	 * Check a column.
	 * 
	 * @param parameters
	 *        List with parameters.
	 * @param name
	 *        expected parameter name.
	 */
	protected void checkParam(List<Parameter> parameters, String name) {
		List<Parameter> params = SelectionUtils.selectByProperty("name", name, parameters, Parameter.class); //$NON-NLS-1$
		assertEquals(1, params.size());
		Parameter parm = params.get(0); 
		assertNotNull(parm);
	}
	
	/**
	 * Asserts whether the two given strings are equal ignoring case.
	 * 
	 * @param expected
	 * @param actual
	 */
	protected void checkStatement(String expected, String actual) {
		assertTrue(actual.trim().equalsIgnoreCase(expected));
	}

}
