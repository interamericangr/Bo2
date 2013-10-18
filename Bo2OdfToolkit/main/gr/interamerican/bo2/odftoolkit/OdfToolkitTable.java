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
package gr.interamerican.bo2.odftoolkit;

import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.dom.style.props.OdfGraphicProperties;
import org.odftoolkit.odfdom.dom.style.props.OdfStyleProperty;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;

/**
 * Implementation of {@link DocumentTable}.
 */
public class OdfToolkitTable implements DocumentTable {
	/**
	 * draw:style-name
	 */
	private static final OdfName STYLENAME = 
 OdfName.newName(OdfDocumentNamespace.DRAW, "style-name"); //$NON-NLS-1$
	
	
			
	/**
	 * Creates a new OdfToolkitTable object. 
	 *
	 * @param owner
	 * @param table
	 */
	public OdfToolkitTable(TextDocument owner, Table table) {
		super();
		this.owner = owner;
		this.table = table;
	}

	/**
	 * Owner document.
	 */
	TextDocument owner;
	
	/**
	 * Table.
	 */
	Table table;

	public void appendRow() throws DocumentEngineException {
		table.appendRow();
		int newRow = table.getRowCount()-1;
		int previousRow = newRow-1;
		int cols = table.getColumnCount();
		for (int col = 0; col < cols; col++) {
			copyStyles(previousRow, col, newRow, col);
		}		
	}

	public void appendColumn() throws DocumentEngineException {
		table.appendColumn();
		int newCol = table.getColumnCount()-1;
		int previousCol = newCol-1;
		int rows = table.getRowCount();
		for (int row = 0; row < rows; row++) {
			copyStyles(row, previousCol, row, newCol);
		}	
	}
	
	/**
	 * Gets the style name of the first paragraph of the cell with the
	 * specified coordinates.
	 * 
	 * @param row
	 *        Row of the specified cell. 
	 * @param col
	 *        Column of the specified cell.
	 *        
	 * @return Returns the name of the first paragraph found in the specified
	 *         cell. If there is no paragraph in the specified cell, or if the
	 *         specified paragraph does not have any style, then returns null.
	 */
	String getStyleOfCellParagraph(int row, int col) {
		Cell cell = table.getCellByPosition(col, row);
		Paragraph p = cell.getParagraphByIndex(0, false);
		if (p==null) {
			return null;
		}
		String style = p.getStyleName();
		return style;
	}
	
	/**
	 * Copies the styles of the from cell to the to cell.
	 * 
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 */
	void copyStyles(int fromRow, int fromCol, int toRow, int toCol) {
		Cell from = table.getCellByPosition(fromCol, fromRow);
		String cellStyle = from.getCellStyleName();
		Cell to = table.getCellByPosition(toCol, toRow);
		to.setCellStyleName(cellStyle);
		
		String styleOfCellParagraph = getStyleOfCellParagraph(fromRow, fromCol);
		if (styleOfCellParagraph!=null) {
			Paragraph p = to.getParagraphByIndex(0, false);
			if (p==null) {
				p=to.addParagraph(StringConstants.EMPTY);
			}
			p.setStyleName(styleOfCellParagraph);
			p.getOdfElement().setStyleName(styleOfCellParagraph);
		}
	}	
	
	/**
	 * Copies the style of the cell in the first column to all other
	 * cells of the table.
	 * 
	 * @param row 
	 *        Row on which the operation takes place.
	 */
	public void copyStyleOfFirstColumnToOthers(int row) {
		int columns = table.getColumnCount();
		for (int col = 1; col < columns; col++) {
			copyStyles(row, 0, row, col);			
		}
	}	

	public void deleteRow(int row) throws DocumentEngineException {
		table.removeRowsByIndex(row, 1);		
	}

	public void deleteColumn(int column) throws DocumentEngineException {
		table.removeColumnsByIndex(column, 1);		
	}

	public void setCell(int row, int column, String value) throws DocumentEngineException {
		checkCellCoordinates(row, column);		
		Cell cell = table.getCellByPosition(column, row);		
		Paragraph p = cell.getParagraphByIndex(0, false);
		if (p==null) {
			p = Paragraph.newParagraph(cell);
		}
		p.setTextContent(value);
	}	
	
	public void setCell(int row, int column, BusinessDocument doc) 
	throws DocumentEngineException {
		checkCellCoordinates(row, column);		
		Cell cell = table.getCellByPosition(column, row);
		cell.removeContent();
		Paragraph p = cell.addParagraph(StringConstants.EMPTY);
		OdfElement toBeReplaced = p.getOdfElement();
		OdfToolkitTextDocument source = OdfToolkitEngine.safeCast(doc);
		OdfUtils.replaceElementWithContent(toBeReplaced, owner, source.document);		
	}

	public void delete() throws DocumentEngineException {		
		table.remove();
	}
	
	public int getColumnCount() throws DocumentEngineException {
		return table.getColumnCount();
	}
	
	public int getRowCount() throws DocumentEngineException {
		return table.getRowCount();
	}
	
	
	/**
	 * Creates a {@link DocumentEngineException} indicating
	 * an invalid index.
	 * 
	 * @param element
	 *        Type of invalid index (row or column).
	 * @param index
	 *        Invalid index value 
	 * 
	 * @return Returns the exception.
	 */
	DocumentEngineException invalid(String element, int index) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Invalid ", element,
			" index ", Integer.toString(index),
			" for table ", table.getTableName());
		return new DocumentEngineException(msg);
	}
	
	/**
	 * Checks that the specified row and column are included
	 * in the table and if not, throws a DocumentEngineException.
	 * 
	 * @param row
	 * @param column
	 * 
	 * @throws DocumentEngineException 
	 *         If the table has less rows or columns than 
	 *         the specified values.
	 */
	@SuppressWarnings("nls")
	void checkCellCoordinates(int row, int column) 
	throws DocumentEngineException {
		if (row>=table.getRowCount()) {			
			throw invalid("row", row);
		}
		if (column>=table.getColumnCount()) {
			throw invalid("column", column);
		}
	}
	
	public void setCellGraphic(int row, int column, String pictureName) 
	throws DocumentEngineException {
		checkCellCoordinates(row, column);		
		Cell cell = table.getCellByPosition(column, row);		
		double widthMM = cell.getTableColumn().getWidth()-2;
		double heightMM = cell.getTableRow().getHeight()-2;
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		String width = nf.format(widthMM).trim() + "mm"; //$NON-NLS-1$
		String height = nf.format(heightMM).trim() + "mm"; //$NON-NLS-1$
		cell.removeContent();
		
		
		Paragraph.newParagraph(cell);		
		TextPElement pele = 
			OdfElement.findFirstChildNode(TextPElement.class, cell.getOdfElement());
		DrawFrameElement frame = pele.newDrawFrameElement();
		
		
		/*
		 * TODO: Set style of frame with attribute style:wrap=run-through. 
		 */
		
		frame.setSvgHeightAttribute(height); 
        frame.setSvgWidthAttribute(width);
        setPictureStyleWrapRunThrough(frame);
		DrawImageElement image = frame.newDrawImageElement();
		
        image.setXlinkHrefAttribute("Pictures/" + pictureName); //$NON-NLS-1$
        image.setXlinkTypeAttribute("simple"); //$NON-NLS-1$
	}
	
	public void setCellGraphic(int row, int column, String pictureName, byte[] picture, String pictureType)
	throws DocumentEngineException {
		OdfToolkitTextDocument doc = new OdfToolkitTextDocument(owner);
		doc.newPicture(pictureName, picture, pictureType);
		setCellGraphic(row, column, pictureName);
	}
	
	/**
	 * Adds a new graphics style.
	 * 
	 * @param frame
	 * 
	 * @throws DocumentEngineException 
	 */
	@SuppressWarnings("nls")
	void setPictureStyleWrapRunThrough(DrawFrameElement frame) 
	throws DocumentEngineException{		
		try {
			String styleName = "TableGraphic";
			OdfOfficeStyles styles = owner.getDocumentStyles(); 
			OdfStyle style = styles.getStyle(styleName, OdfStyleFamily.Graphic);
			if (style==null) { 
				style = styles.newStyle(styleName, OdfStyleFamily.Graphic);
				style.setStyleNameAttribute(styleName);
				style.setStyleParentStyleNameAttribute("Graphics");
				Map<OdfStyleProperty, String> gp = new HashMap<OdfStyleProperty, String>();
				gp.put(OdfGraphicProperties.Wrap, "run-through");
				style.setProperties(gp);
			}
			frame.setOdfAttributeValue(STYLENAME, styleName);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

}
