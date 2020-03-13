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

import gr.interamerican.bo2.odftoolkit.barcode.BarCodeFactory;
import gr.interamerican.bo2.odftoolkit.barcode.BarCodeType;
import gr.interamerican.bo2.odftoolkit.barcode.BarcodeTableUtils;
import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.utils.IllegalCharacterFilter;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.krysalis.barcode4j.HumanReadablePlacement;
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
	
	
	/** draw:style-name. */
	private static final OdfName STYLENAME = 
        OdfName.newName(OdfDocumentNamespace.DRAW, "style-name"); //$NON-NLS-1$
	
	
			
	/**
	 * Creates a new OdfToolkitTable object. 
	 *
	 * @param owner the owner
	 * @param table the table
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

	@Override
	public void appendRow() throws DocumentEngineException {
		table.appendRow();
		int newRow = table.getRowCount()-1;
		int previousRow = newRow-1;
		int cols = table.getColumnCount();
		for (int col = 0; col < cols; col++) {
			copyStyles(previousRow, col, newRow, col);
		}		
	}

	@Override
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
	 * @param fromRow the from row
	 * @param fromCol the from col
	 * @param toRow the to row
	 * @param toCol the to col
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

	@Override
	public void deleteRow(int row) throws DocumentEngineException {
		table.removeRowsByIndex(row, 1);		
	}

	@Override
	public void deleteColumn(int column) throws DocumentEngineException {
		table.removeColumnsByIndex(column, 1);		
	}

	@Override
	public void setCell(int row, int column, String value) throws DocumentEngineException {
		checkCellCoordinates(row, column);		
		Cell cell = table.getCellByPosition(column, row);		
		Paragraph p = cell.getParagraphByIndex(0, false);
		if (p==null) {
			p = Paragraph.newParagraph(cell);
		}
		String txt = IllegalCharacterFilter.SINGLETON.filter(value);
		p.setTextContent(txt);
	}	
	
	@Override
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

	@Override
	public void delete() throws DocumentEngineException {		
		table.remove();
	}
	
	@Override
	public int getColumnCount() throws DocumentEngineException {
		return table.getColumnCount();
	}
	
	@Override
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
	 * @param row the row
	 * @param column the column
	 * @throws DocumentEngineException         If the table has less rows or columns than 
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
	
	@Override
	public void setCellGraphic(int row, int column, String pictureName) 
	throws DocumentEngineException {
		checkCellCoordinates(row, column);		
		Cell cell = table.getCellByPosition(column, row);		
		double widthMM = (cell.getTableColumn().getWidth()-2) * cell.getColumnSpannedNumber();
		double heightMM = (cell.getTableRow().getHeight()-2) * cell.getRowSpannedNumber();

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
	
	@Override
	public void setCellGraphic(int row, int column, String pictureName, byte[] picture, String pictureType)
	throws DocumentEngineException {
		OdfToolkitTextDocument doc = new OdfToolkitTextDocument(owner);
		doc.newPicture(pictureName, picture, pictureType);
		setCellGraphic(row, column, pictureName);
	}
	
	/**
	 * Adds a new graphics style.
	 *
	 * @param frame the new picture style wrap run through
	 * @throws DocumentEngineException the document engine exception
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
	
	/**
	 * Sets the barcode.
	 *
	 * @throws DocumentEngineException the document engine exception
	 */
	public void setBarcode() throws DocumentEngineException {
		Cell cell = table.getCellByPosition(0, 0);
		String value = cell.getStringValue();
		value = value.trim();
		if (!StringUtils.isNullOrBlank(value)) {
			String name = table.getTableName();
			BarCodeType type = BarcodeTableUtils.getBarcodeType(name,owner);
			HumanReadablePlacement hrp = BarcodeTableUtils.getHumanReadablePlacement(name, owner);		
			BarCodeFactory bcf = new BarCodeFactory();
			byte[] bytes = bcf.getBarCode(value, type, hrp);
			setCellGraphic(0, 0, table.getTableName(), bytes, bcf.getImageType());			
		}
	}
	
	
	
	

}
