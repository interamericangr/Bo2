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

import gr.interamerican.bo2.utils.sql.types.Type;

/**
 * Metadata of a column from a database table.
 * 
 */
public class Column {
	/**
	 * column alias.
	 */
	 String alias;
	/**
	 * column name.
	 */
	 String name;
	/**
	 * table name.
	 */
	 String tbName;
	/**
	 * table qualifier.
	 */
	 String tbCreator;
	/**
	 * column no.
	 */
	 Integer columnNo;
	/**
	 * column type.
	 */
	 Type<?> columnType;
	/**
	 * Column length.
	 */
	 Integer length;
	/**
	 * scale.
	 */
	 Integer scale;
	/**
	 * column label.
	 */
	 String label;
	/**
	 * column comments.
	 */
	 String remarks;
	
	/**
	 * Gets the alias.
	 *
	 * @return Returns the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * Assigns a new value to the alias.
	 *
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the tbName.
	 *
	 * @return Returns the tbName
	 */
	public String getTbName() {
		return tbName;
	}
	/**
	 * Assigns a new value to the tbName.
	 *
	 * @param tbName the tbName to set
	 */
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}
	/**
	 * Gets the tbCreator.
	 *
	 * @return Returns the tbCreator
	 */
	public String getTbCreator() {
		return tbCreator;
	}
	/**
	 * Assigns a new value to the tbCreator.
	 *
	 * @param tbCreator the tbCreator to set
	 */
	public void setTbCreator(String tbCreator) {
		this.tbCreator = tbCreator;
	}
	/**
	 * Gets the columnNo.
	 *
	 * @return Returns the columnNo
	 */
	public Integer getColumnNo() {
		return columnNo;
	}
	/**
	 * Assigns a new value to the columnNo.
	 *
	 * @param columnNo the columnNo to set
	 */
	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}
	/**
	 * Gets the columnType.
	 *
	 * @return Returns the columnType
	 */
	public Type<?> getColumnType() {
		return columnType;
	}
	/**
	 * Assigns a new value to the columnType.
	 *
	 * @param columnType the columnType to set
	 */
	public void setColumnType(Type<?> columnType) {
		this.columnType = columnType;
	}
	/**
	 * Gets the length.
	 *
	 * @return Returns the length
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * Assigns a new value to the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}
	/**
	 * Gets the scale.
	 *
	 * @return Returns the scale
	 */
	public Integer getScale() {
		return scale;
	}
	/**
	 * Assigns a new value to the scale.
	 *
	 * @param scale the scale to set
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	/**
	 * Gets the label.
	 *
	 * @return Returns the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Assigns a new value to the label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the remarks.
	 *
	 * @return Returns the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * Assigns a new value to the remarks.
	 *
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
