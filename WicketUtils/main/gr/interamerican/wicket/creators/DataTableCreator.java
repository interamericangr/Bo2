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
package gr.interamerican.wicket.creators;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

/**
 * Class that creates a {@link DataTable}.
 * 
 * @param <B> 
 *        Type of entity presented in the data table.
 */
public interface DataTableCreator<B extends Serializable> extends Serializable {
	
	/**
	 * Creates the {@link DataTable}.
	 * 
	 * @param id 
	 *        Wicket id of the table.
	 * @param elements 
	 *        Elements to put in the table.
	 *	  
	 * @return Returns the {@link DataTable}
	 */
	public DataTable<B> createDataTable(String id, List<B> elements);
	
}
