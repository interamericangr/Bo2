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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import gr.interamerican.bo2.utils.StreamUtils;

/**
 * {@link DynamicJdbcQuery} that has its base SQL statement read
 * from a resource file.
 * 
 * @param <C> 
 *        Type of criteria.
 */
public class StoredJdbcQuery<C> extends DynamicJdbcQuery<C>{

	/**
	 * SQL statement.
	 */
	String statement;
	
	@Override
	public final String baseSql() {		
		return statement;
	}

	/**
	 * Creates a new StoredJdbcQuery object. 
	 * 
	 * @param path 
	 *        Path to the resource file containing the SQL statement. 
	 */
	public StoredJdbcQuery(String path) {
		super();			
		statement = StreamUtils.getStringFromResourceFile(path);
	}

}
