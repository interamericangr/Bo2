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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Converts a {@link CsvRecord} to a String that is created by
 * the tags that represent a row.
 */
public class CsvRecord2RowTags 
implements Transformation<CsvRecord, String> {
	/**
	 * Count of CSV record columns.
	 */
	int columnCount;
	
	/**
	 * Tag for row start.
	 */
	String startRowTag;
	
	/**
	 * Tag for row start.
	 */
	String endRowTag;
	
	/**
	 * Tag for cell start.
	 */
	String startCellTag;
	
	/**
	 * Tag for cell end.
	 */
	String endCellTag;
	
	

	/**
	 * Creates a new CsvRecord2RowTags object. 
	 *
	 * @param columnCount
	 *        Count of CSV record columns.
	 * @param rowTag 
	 *        Tag for the row.
	 * @param cellTag 
	 *        Tag for the cell.
	 */
	public CsvRecord2RowTags(int columnCount, String rowTag, String cellTag) {
		super();
		this.columnCount = columnCount;
		this.startRowTag = StringUtils.xmlStartTag(rowTag);
		this.endRowTag = StringUtils.xmlEndTag(rowTag);
		this.startCellTag = StringUtils.xmlStartTag(cellTag);
		this.endCellTag = StringUtils.xmlEndTag(cellTag);
	}
	
	/**
	 * Creates a new CsvRecord2RowTags object for the HTML dialect. 
	 *
	 * @param columnCount
	 */
	@SuppressWarnings("nls")
	public CsvRecord2RowTags(int columnCount) {
		this(columnCount,"tr","td");
	}

	public String execute(CsvRecord a) {
		StringBuilder sb = new StringBuilder();
		sb.append(startRowTag);
		for (int i = 0; i < columnCount; i++) {
			sb.append(startCellTag);
			sb.append(a.getString(i));
			sb.append(endCellTag);
		}
		sb.append(endRowTag);
		return sb.toString();
	}
	
	

}
