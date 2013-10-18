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
import gr.interamerican.bo2.utils.doc.DocumentTable;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Mock {@link BusinessDocument}.
 */
public class MockDocument implements BusinessDocument {

	
	@Override
	public DocumentEngine getEngine() {		
		return null;
	}

	
	@Override
	public List<String> getFields() throws DocumentEngineException {
		return null;
	}

	
	@Override
	public void setFields(Object model) throws DocumentEngineException {
		/* empty */		
	}

	
	@Override
	public void append(BusinessDocument doc) throws DocumentEngineException {
		/* empty */		
	}

	@Override
	public void pageBreak() throws DocumentEngineException {
		/* empty */		
	}
	
	@Override
	public byte[] asByteArray() throws DocumentEngineException {		
		return null;
	}
	
	@Override
	public ByteArrayOutputStream asByteStream() throws DocumentEngineException {
		return null;
	}

	
	@Override
	public DocumentTable getTable(String name) throws DocumentEngineException {
		return null;
	}

	
	@Override
	public void addText(String text) throws DocumentEngineException {
		/* empty */
	}

	
	@Override
	public void addParagraph(String text) throws DocumentEngineException {
		/* empty */	
	}

	
	@Override
	public void insertAt(String positionLabel, BusinessDocument document)
	throws DocumentEngineException {
		/* empty */		
	}
		
	

}
