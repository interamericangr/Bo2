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

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Document engine.
 */
public interface DocumentEngine {
	
	/**
	 * Gets the name of this engine.
	 * 
	 * @return Returns the name of this engine.
	 */
	String getEngineName();
	
	/**
	 * Creates a new {@link BusinessDocument}.
	 * 
	 * @return Returns the new document.
	 * 
	 * @throws DocumentEngineException 
	 */
	BusinessDocument newDocument() throws DocumentEngineException;
	
	/**
	 * Opens a document from a specified path.
	 * 
	 * @param path
	 *        Path to get the document from.
	 *        
	 * @return Returns the document.
	 * 
	 * @throws DocumentEngineException 
	 */
	BusinessDocument openDocument (String path) throws DocumentEngineException;
	
	/**
	 * Opens a document from a specified file.
	 * 
	 * @param file
	 *        File.
	 *        
	 * @return Returns the document.
	 * 
	 * @throws DocumentEngineException 
	 */
	BusinessDocument openDocument(File file) throws DocumentEngineException;
	
	/**
	 * Opens a document from a specified stream.
	 * 
	 * @param stream
	 *        InputStream.
	 *        
	 * @return Returns the document.
	 * 
	 * @throws DocumentEngineException 
	 */
	BusinessDocument openDocument (InputStream stream) throws DocumentEngineException;
	
	/**
	 * Saves the document to the specified path.
	 * 
	 * @param doc
	 *        BusinessDocument to save.
	 * @param path
	 *        Path to save the document.
	 *        
	 * @throws DocumentEngineException
	 */
	void saveDocument(BusinessDocument doc, String path) throws DocumentEngineException;
	
	/**
	 * Saves the document to the specified file.
	 * 
	 * @param doc
	 *        BusinessDocument to save.
	 * @param file
	 *        File to save the document.
	 *        
	 * @throws DocumentEngineException
	 */
	void saveDocument(BusinessDocument doc, File file)  throws DocumentEngineException;
	
	/**
	 * Saves the document to the specified stream.
	 * 
	 * @param doc
	 *        BusinessDocument to save.
	 * @param stream
	 *        Stream to save the document.
	 *        
	 * @throws DocumentEngineException
	 */
	void saveDocument(BusinessDocument doc, OutputStream stream)  throws DocumentEngineException;
	
	/**
	 * Saves the document to the file it was last read or saved from.
	 * 
	 * @param doc
	 *        BusinessDocument to save.
	 *        
	 * @throws DocumentEngineException
	 */
	void saveDocument(BusinessDocument doc)  throws DocumentEngineException;
	
	/**
	 * Gets the business document in PDF format.
	 * 
	 * @param doc
	 *        Business document to convert to PDF.
	 *        
	 * @return Returns a byte array containing the specified business
	 *         document in PDF format.
	 *         
	 * @throws DocumentEngineException 
	 */
	byte[] toPdf(BusinessDocument doc) throws DocumentEngineException;
	
	/**
	 * Gets the business document in HTML format.
	 * 
	 * @param doc
	 *        Business document to convert to HTML.
	 *        
	 * @return Returns a String containing the specified business
	 *         document in HTML format.
	 *         
	 * @throws DocumentEngineException 
	 */
	String toHtml(BusinessDocument doc) throws DocumentEngineException;

}
