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
 * Base class for {@link DataTableCreator} implementations.
 * 
 * @param <B> 
 *        Type of row object.
 */
public abstract class AbstractDataTableCreator<B extends Serializable> 
implements DataTableCreator<B> {
	
	/**
	 * Rows per page.
	 */
	protected Integer rowsPerPage;

	/**
	 * Gets the rowsPerPage.
	 *
	 * @return Returns the rowsPerPage
	 */
	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * Assigns a new value to the rowsPerPage.
	 *
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public abstract DataTable<B> createDataTable(String id, List<B> elements);

}
