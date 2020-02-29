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

import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.namedstreams.AbstractStreamBasedQuery;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.utils.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Query that browses an excel sheet.
 */
public class XlsQuery extends AbstractStreamBasedQuery {

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
	 * Workbook.
	 */
	private Workbook workbook;

	/**
	 * formula evaluator.
	 */
	private FormulaEvaluator evaluator;

	/**
	 * Creates a new XlsQuery object.
	 *
	 * @param streamName the stream name
	 * @param sheetIndex the sheet index
	 */
	public XlsQuery(String streamName, int sheetIndex) {
		super();
		setStreamName(streamName);
		this.sheetIndex = sheetIndex;
	}

	/**
	 * Creates a new XlsQuery object.
	 *
	 * @param sheetIndex the sheet index
	 */
	public XlsQuery(int sheetIndex) {
		this(null, sheetIndex);
	}

	/**
	 * Creates a new XlsQuery object.
	 *
	 * @param streamName the stream name
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
			workbook = WorkbookFactory.create(in.getStream());
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			sheet = workbook.getSheetAt(0);
		} catch (IndexOutOfBoundsException e) {
			throw new DataException(e);
		} catch (EncryptedDocumentException e) {
			throw new DataException(e);
		} catch (InvalidFormatException e) {
			throw new DataException(e);
		} catch (IOException e) {
			throw new DataException(e);
		}
	}

	@Override
	public void execute() throws DataException {
		currentRow = null;
	}

	@Override
	public boolean next() throws DataAccessException {
		Row row = null;
		do { //ignore null lines, get the next valid line, if available
			rowIndex++;
			boolean hasNext = rowIndex <= sheet.getLastRowNum();
			if(!hasNext) {
				currentRow = null;
				return false;
			}
			row = sheet.getRow(rowIndex);
		} while (row == null);

		List<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < row.getLastCellNum(); i++) { //always start from first column!!!
			Cell c = row.getCell(i);
			c = evaluator.evaluateInCell(c);
			cells.add(c);
		}
		currentRow = new XlsRow(CollectionUtils.toArray(cells, new Cell[cells.size()]));
		return true;
	}

	@Override
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

	/**
	 * Returns the header row.
	 *
	 * @return the header row.
	 */
	public XlsRow getHeader() {
		Row row = sheet.getRow(0);
		List<Cell> cells = new ArrayList<Cell>();
		for (int colNum = row.getFirstCellNum(); colNum < row.getLastCellNum(); colNum++) {
			Cell cell = row.getCell(colNum);
			cells.add(cell);
		}
		return new XlsRow(CollectionUtils.toArray(cells, new Cell[cells.size()]));
	}

}
