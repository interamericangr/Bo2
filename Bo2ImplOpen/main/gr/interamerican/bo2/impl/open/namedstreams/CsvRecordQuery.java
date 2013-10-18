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
package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.records.Record;
import gr.interamerican.bo2.impl.open.records.CsvRecord;

/**
 * NamedStreamBasicQuery that fetches CSV records.
 */
public class CsvRecordQuery 
extends NamedStreamBasicQuery {
	
	/**
	 * Column count.
	 */
	int columnCount = 1;
	
	/**
	 * Column separator.
	 */
	char separator = ';';
	
	/**
	 * Creates a new CsvRecordQuery object. 
	 *
	 * @param columnCount
	 *        Count of columns.
	 * @param separator 
	 *        Column separator.
	 */
	public CsvRecordQuery(int columnCount, char separator) {
		super();
		this.columnCount = columnCount;
		this.separator = separator;
		initializeRecord();
	}

	@Override
	protected Record emptyRecord() {
		return new CsvRecord(columnCount, separator);
	}

}
