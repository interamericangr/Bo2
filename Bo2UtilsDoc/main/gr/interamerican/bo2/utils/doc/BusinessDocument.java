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

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * {@link BusinessDocument} is an abstraction for a document that is
 * created for business needs.
 * 
 * Documents of this type, usually are generated from predefined business 
 * templates by inserting to them dynamic material.
 */
public interface BusinessDocument {
	
	/**
	 * Gets the document engine of this business document.
	 * 
	 * @return Returns the document engine of the document.
	 */
	DocumentEngine getEngine();
	
	/**
	 * Fields of the template.
	 * 
	 * @return Returns a list with the fields of the template ordered
	 *         according to their occurrence in the document.
	 *         
	 * @throws DocumentEngineException 
	 */
	List<String> getFields() throws DocumentEngineException;
	
	/**
	 * Sets the values of the document's fields from an object. <br/>
	 * 
	 * The method uses the name of each field as an OGNL expression.
	 * This expression gets the value for the specified user defined field.
	 * 
	 * @param model
	 *        Model object.
	 *        
	 * @throws DocumentEngineException 
	 */
	void setFields(Object model) throws DocumentEngineException;	
	
	/**
	 * Appends another document.
	 * 
	 * @param doc
	 * 
	 * @throws DocumentEngineException 
	 */
	void append(BusinessDocument doc) throws DocumentEngineException;
	
	/**
	 * Adds a page break.
	 * 
	 * @throws DocumentEngineException 
	 */
	void pageBreak() throws DocumentEngineException;
	
	/**
	 * The document as a byte array.
	 * 
	 * @return Returns the document as a byte array.
	 * 
	 * @throws DocumentEngineException 
	 */
	byte[] asByteArray() throws DocumentEngineException;
	
	/**
	 * The document as a byte array stream.
	 * 
	 * @return Returns the document as a byte array stream.
	 * 
	 * @throws DocumentEngineException 
	 */
	ByteArrayOutputStream asByteStream() throws DocumentEngineException;
	
	/**
	 * Gets the table with the specified name.
	 * 
	 * @param name
	 *        Table name.
	 *        
	 * @return Returns the table with the specified name.
	 * 
	 * @throws DocumentEngineException 
	 */
	DocumentTable getTable(String name) throws DocumentEngineException;
	
	/**
	 * Adds text to the end of the document.
	 * 
	 * @param text
	 *        Text to add. 
	 *        
	 * @throws DocumentEngineException 
	 */
	void addText(String text) throws DocumentEngineException;
	
	/**
	 * Adds a paragraph to the end of the document.
	 * 
	 * @param text
	 *        Paragraph text.
	 *        
	 * @throws DocumentEngineException
	 */
	public void addParagraph(String text) throws DocumentEngineException;
	
	/**
	 * Inserts the contents of the specified document at the position
	 * identified by the specified label.
	 *  
	 * @param positionLabel
	 *        Labels the position on the document where the contents of the 
	 *        other document will be inserted.
	 *        
	 * @param document
	 *        Document to insert to the specified position.
	 *        
	 * @throws DocumentEngineException 
	 */
	public void insertAt(String positionLabel, BusinessDocument document) 
	throws DocumentEngineException;


}
