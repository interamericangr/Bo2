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
package gr.interamerican.bo2.utils.doc;

/**
 * Table.
 */
public interface DocumentTable {
	/**
	 * Adds a row to the end.
	 * 
	 * @throws DocumentEngineException 
	 */
	void appendRow() throws DocumentEngineException;
	
	/**
	 * Ads a column to the right.
	 * 
	 * @throws DocumentEngineException 
	 */
	void appendColumn() throws DocumentEngineException;
	
	/**
	 * Deletes the specified row.
	 * 
	 * @param row
	 *        Index of the row to delete.
	 *        The index of the first row is 0.
	 * 
	 * @throws DocumentEngineException 
	 */
	void deleteRow(int row) throws DocumentEngineException;
	
	/**
	 * Deletes the specified column.
	 * 
	 * @param column
	 * 
	 * @throws DocumentEngineException 
	 */
	void deleteColumn(int column) throws DocumentEngineException;
	
	/**
	 * Sets the contents of the specified cell.
	 * 
	 * @param row
	 *        Row of the cell. Index of the first row is 0.
	 * @param column
	 *        Column of the cell. Index of the first column is 0.
	 * @param value
	 *        Value to put to the cell.
	 *        
	 * @throws DocumentEngineException 
	 */
	void setCell(int row, int column, String value) 
	throws DocumentEngineException;
	
	/**
	 * Puts the content of a {@link BusinessDocument} in the specified
	 * cell.
	 * 
	 * @param row
	 *        Row of the cell. Index of the first row is 0.
	 * @param column
	 *        Column of the cell. Index of the first column is 0.
	 * @param doc
	 *        document who's contents will be inserted in the cell.
	 *        
	 * @throws DocumentEngineException 
	 */
	void setCell(int row, int column, BusinessDocument doc) 
	throws DocumentEngineException;
	
	/**
	 * Puts a picture that already exists in the document in 
	 * the specified cell.
	 * 
	 * @param row
	 *        Row of the cell. Index of the first row is 0.
	 * @param column
	 *        Column of the cell. Index of the first column is 0.
	 * @param pictureName
	 *        name of picture.
	 *        
	 * @throws DocumentEngineException 
	 */
	void setCellGraphic(int row, int column, String pictureName) 
	throws DocumentEngineException;
	
	/**
	 * Puts a picture in the specified cell.
	 * 
	 * @param row
	 *        Row of the cell. Index of the first row is 0.
	 * @param column
	 *        Column of the cell. Index of the first column is 0.
	 * @param pictureName
	 *        name of picture.
	 * @param picture 
	 *        Picture
	 * @param pictureType
	 *        Mime type of the picture. 
	 *        
	 * @throws DocumentEngineException 
	 */
	void setCellGraphic(int row, int column, String pictureName, byte[] picture, String pictureType) 
	throws DocumentEngineException;
	
	/**
	 * Deletes the table from the document.
	 * 
	 * @throws DocumentEngineException 
	 */
	void delete() throws DocumentEngineException;
	
	/**
	 * Gets the column count.
	 * 
	 * @return Returns the count of columns.
	 * 
	 * @throws DocumentEngineException 
	 */
	int getColumnCount() throws DocumentEngineException;
	
	/**
	 * Gets the row count.
	 * 
	 * @return Returns the count of rows.
	 * 
	 * @throws DocumentEngineException 
	 */
	int getRowCount() throws DocumentEngineException;


}
