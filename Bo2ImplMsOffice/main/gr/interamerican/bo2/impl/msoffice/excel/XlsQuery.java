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
package gr.interamerican.bo2.impl.msoffice.excel;

import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.namedstreams.AbstractStreamBasedQuery;
import gr.interamerican.bo2.impl.open.namedstreams.NamedInputStream;

import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Query that browses an excel sheet.
 */
public class XlsQuery 
extends AbstractStreamBasedQuery 
implements Query {
	
	/**
	 * Xls sheet.
	 */
	private Sheet sheet = null;
	
	/**
	 * The current row index.
	 */
	private int rowIndex = 0;
	
	/**
	 * Sheet index.
	 */
	int sheetIndex = 0;
	
	/**
	 * Current row.
	 */
	private XlsRow currentRow = null;
	
	/**
	 * Creates a new XlsQuery object. 
	 *
	 * @param streamName
	 * @param sheetIndex
	 */
	public XlsQuery(String streamName, int sheetIndex) {
		super();
		this.setStreamName(streamName);
		this.sheetIndex = sheetIndex;
	}
	
	/**
	 * Creates a new XlsQuery object. 
	 *
	 * @param sheetIndex
	 */
	public XlsQuery(int sheetIndex) {
		this(null, sheetIndex);
	}
	
	/**
	 * Creates a new XlsQuery object. 
	 *
	 * @param streamName
	 */
	public XlsQuery(String streamName) {
		this(streamName, 0);
	}
	
	/**
	 * Creates a new XlsQuery object. 
	 *
	 */
	public XlsQuery() {
		this(0);
	}
	
	@Override
	public void open() throws DataException {		
		super.open();
		try {
			NamedInputStream in = (NamedInputStream) stream;
			Workbook workbook = Workbook.getWorkbook(in.getStream());
			sheet = workbook.getSheet(sheetIndex);
		} catch (BiffException e) {
			throw new DataException(e);
		} catch (IndexOutOfBoundsException e) {
			throw new DataException(e);
		} catch (IOException e) {
			throw new DataException(e);
		}
	}
	
	public void execute() throws DataException {
		currentRow=null;
	}
	
	public boolean next() throws DataAccessException {
		rowIndex++;
		boolean hasNext = (rowIndex < sheet.getRows());
		if (hasNext) {
			Cell[] cells = sheet.getRow(rowIndex);
			currentRow = new XlsRow(cells);
		} else {
			currentRow = null;			
		}		 
		return hasNext;
	}
	
	public int getRow() throws DataAccessException {
		return rowIndex;
	}
	
	/**
	 * Gets the current excel row.
	 * 
	 * @return Returns the current row.
	 */
	public XlsRow getXlsRow() {		
		return currentRow;
	}
	
}
