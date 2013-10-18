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
package gr.interamerican.bo2.samples.sql;

import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock {@link SqlParser}.
 * 
 * This class is necessary, because the tests not only
 * need a mock SqlParser, but also a class implementing
 * SqlParser.
 */
public class MockParser implements SqlParser {

	public ArrayList<Parameter> getParameters(String sql) throws SqlParseException {		
		return null;
	}
	
	public String removeParameter(String parameter, String sql)
	throws SqlParseException {
		return null;
	}
	
	public List<Column> getColumns(String sql) throws SqlParseException {
		return null;
	}
	
	public String removeUselessJoins(String sql) throws SqlParseException {
		return null;
	}

}
