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
package gr.interamerican.bo2.impl.open.parse;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.Properties;

/**
 * Implementation of {@link ParserProvider}.
 */
public class ParserProviderImpl 
implements ParserProvider {

	/**
	 * Parser.
	 */
	SqlParser parser;

	/**
	 * Creates a new ParserProviderImpl object. 
	 *
	 * @param parser
	 *        Parser for the new object.
	 */
	public ParserProviderImpl(SqlParser parser) {
		super();
		this.parser = parser;
	}
	
	/**
	 * 
	 * Creates a new ParserProviderImpl object. 
	 *
	 * @param parserClass
	 *        Class of the parser.
	 */
	public ParserProviderImpl(String parserClass) {
		super();
		parser = ReflectionUtils.newInstance(parserClass);
	}
	
	/**
	 * 
	 * Creates a new ParserProviderImpl object. 
	 *
	 * @param properties
	 *        Initialization properties. of the parser.
	 */
	public ParserProviderImpl(Properties properties) {
		super();
		String parserClass = CollectionUtils.getMandatoryProperty(properties, "parserClass"); //$NON-NLS-1$
		parser = ReflectionUtils.newInstance(parserClass);
	}


	public SqlParser getParser() {
		return parser;
	}
	
	public void close() throws DataException {
		/* empty */
	}

}
