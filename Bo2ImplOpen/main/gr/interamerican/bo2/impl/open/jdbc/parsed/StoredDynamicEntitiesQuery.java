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

import gr.interamerican.bo2.arch.EntitiesQuery;

/** 
 * {@link StoredDynamicEntitiesQuery} is a query that has its SQL query
 * statement stored in a resource file and also implements the interface 
 * {@link EntitiesQuery}. Alternatively, the SQL query can be supplied on
 * as a String along with a (unique) id. 
 * <br/>
 * 
 * The type of entity returned by the <code>getEntity()</code> method is created
 * dynamically on runtime and contains the elements of the query columns.
 * Therefore StoredDynamicEntitiesQuery is declared as EntitiesQuery<Object>,
 * however the type of Object is a synthetic class that is created on runtime
 * and contains one field for each column of the row.
 * 
 * The convention for the property names of the synthetic results
 * bean of the {@link StoredDynamicEntitiesQuery} is as follows: The result 
 * set column names are converted to property names by converting all letters
 * to small case and capitalizing each letter that is preceded by an underscore.
 * For example, FIRST_NAME column corresponds to firstName property.
 * 
 */
public class StoredDynamicEntitiesQuery 
extends GenericStoredDynamicEntitiesQuery<Object> {
	
	
	/**
	 * Creates a new StoredDynamicEntitiesQuery object. 
	 *
	 * @param path
	 */
	public StoredDynamicEntitiesQuery(String path) {
		super(path, null);
	}
	
	/**
	 * Creates a new StoredDynamicEntitiesQuery object. 
	 *
	 * @param sql
	 * @param id
	 */
	public StoredDynamicEntitiesQuery(String sql, String id) {
		super(sql, id, null);
	}
	
	
}
