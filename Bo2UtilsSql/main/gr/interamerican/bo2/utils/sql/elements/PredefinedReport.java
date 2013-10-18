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
package gr.interamerican.bo2.utils.sql.elements;

/**
 * PredefinedReport is a report that is based on a dynamic query.
 */
public class PredefinedReport {
	/**
	 * SQL query statement.
	 */
	String sql;
	
	/**
	 * Columns of the report.
	 */
	Column[] columns;
	
	/**
	 * Report parameters.
	 */
	Parameter[] parameters;
	
	/**
	 * Report description
	 */
	String description;
	
	/**
	 * Report title.
	 */
	String title;

	/**
	 * Unique identifier of the report.
	 */
	String uniqueId;

	/**
	 * Gets the sql.
	 *
	 * @return Returns the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * Assigns a new value to the sql.
	 *
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * Gets the columns.
	 *
	 * @return Returns the columns
	 */
	public Column[] getColumns() {
		return columns;
	}

	/**
	 * Assigns a new value to the columns.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return Returns the parameters
	 */
	public Parameter[] getParameters() {
		return parameters;
	}

	/**
	 * Assigns a new value to the parameters.
	 *
	 * @param parameters the parameters to set
	 */
	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the description.
	 *
	 * @return Returns the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Assigns a new value to the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the title.
	 *
	 * @return Returns the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Assigns a new value to the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the uniqueId.
	 *
	 * @return Returns the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Assigns a new value to the uniqueId.
	 *
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
