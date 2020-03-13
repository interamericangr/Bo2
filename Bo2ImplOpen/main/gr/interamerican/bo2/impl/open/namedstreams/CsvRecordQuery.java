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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.records.Record;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.utils.CopyRecordUtils;

import java.nio.charset.Charset;

/**
 * NamedStreamBasicQuery that fetches CSV records.
 */
public class CsvRecordQuery
extends NamedStreamBasicQuery
implements EntitiesQuery<CsvRecord> {

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
		this(columnCount, separator, null);
	}

	/**
	 * Creates a new CsvRecordQuery object.
	 *
	 * @param columnCount the column count
	 * @param separator the separator
	 * @param streamName the stream name
	 */
	public CsvRecordQuery(int columnCount, char separator, String streamName) {
		this.columnCount = columnCount;
		this.separator = separator;
		if (streamName!=null){
			this.streamName = streamName;
		}
	}

	@Override
	protected Record emptyRecord(Charset charset) {
		CsvRecord csvRecord = new CsvRecord(columnCount, separator);
		csvRecord.setCharset(charset);
		return csvRecord;
	}

	@Override
	public CsvRecord getEntity() throws DataAccessException {
		return CopyRecordUtils.copyCsvRecord((CsvRecord) getRecord());
	}
}