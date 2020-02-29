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

import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.def.NamedFieldsContainer;
import gr.interamerican.bo2.arch.records.IndexedFieldsRecord;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstract Query for browsing a sequential file.
 * 
 * Concrete sub-classes must define the query record.
 * This is done by implementing the <code>emtyRecord()</code>
 * method.
 */
public abstract class NamedStreamQuery 
extends NamedStreamBasicQuery 
implements Query, NamedFieldsContainer {
	
	/**
	 * Creates a new NamedStreamQuery object. 
	 *
	 */
	public NamedStreamQuery() {
		super();
	}

	/**
	 * Current record.
	 */
	@SuppressWarnings("unchecked")
	private IndexedFieldsRecord<String> indexedRecord = 
		(IndexedFieldsRecord<String>) this.getRecord();

	@Override
	public byte[] getBytes(String field) {
		return indexedRecord.getBytes(field);
	}

	@Override
	public double getDouble(String field) {
		return indexedRecord.getDouble(field);
	}

	@Override
	public int getInt(String field) {
		return indexedRecord.getInt(field);
	}

	@Override
	public long getLong(String field) {
		return indexedRecord.getLong(field);
	}

	@Override
	public boolean getBoolean(String field) {
		return indexedRecord.getBoolean(field);
	}

	@Override
	public Date getDate(String field) {
		return indexedRecord.getDate(field);
	}

	@Override
	public Calendar getCalendar(String field) {
		return indexedRecord.getCalendar(field);
	}

	@Override
	public BigDecimal getBigDecimal(String field) {
		return indexedRecord.getBigDecimal(field);
	}

	@Override
	public String getString(String field) {
		return indexedRecord.getString(field);
	}

	@Override
	public short getShort(String field) {
		return indexedRecord.getShort(field);
	}

	@Override
	public float getFloat(String field) {
		return indexedRecord.getFloat(field);
	}

	@Override
	public byte getByte(String field) {
		return indexedRecord.getByte(field);
	}

	@Override
	public Object getObject(String field) {
		return indexedRecord.getObject(field);
	}
}