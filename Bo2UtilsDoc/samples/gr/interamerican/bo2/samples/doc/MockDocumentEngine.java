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
package gr.interamerican.bo2.samples.doc;

import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Mock {@link DocumentEngine}.
 */
public class MockDocumentEngine implements DocumentEngine {
	
	@Override
	public String getEngineName() {
		return null;
	}
	
	@Override
	public BusinessDocument newDocument() throws DocumentEngineException {
		return null;
	}
	
	@Override
	public BusinessDocument openDocument(String path) throws DocumentEngineException {
		return null;
	}
	
	@Override
	public BusinessDocument openDocument(File file)	throws DocumentEngineException {
		return null;
	}
	
	@Override
	public BusinessDocument openDocument(InputStream stream) throws DocumentEngineException {
		return null;
	}
	
	@Override
	public void saveDocument(BusinessDocument doc, String path)	throws DocumentEngineException {
		/* empty */
	}
	
	@Override
	public void saveDocument(BusinessDocument doc, File file) throws DocumentEngineException {
		/* empty */		
	}
	
	@Override
	public void saveDocument(BusinessDocument doc, OutputStream stream)	throws DocumentEngineException {
		/* empty */			
	}
	
	@Override
	public void saveDocument(BusinessDocument doc)	throws DocumentEngineException {
		/* empty */	
	}
	
	@Override
	public byte[] toPdf(BusinessDocument doc) throws DocumentEngineException {
		return null;
	}
	
	/**
	 * Creates a new MockDocumentEngine object. 
	 * @param properties 
	 */
	public MockDocumentEngine(Properties properties) {
		this();
	}
	
	/**
	 * Creates a new MockDocumentEngine object. 
	 *
	 */
	public MockDocumentEngine() {
		/* empty */
	}
	
}
