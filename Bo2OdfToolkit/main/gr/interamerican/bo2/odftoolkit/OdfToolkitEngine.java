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

import gr.interamerican.bo2.odftoolkit.pdf.JodPdfEngine;
import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.PdfEngine;
import gr.interamerican.bo2.utils.doc.PdfEngineException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.odftoolkit.simple.TextDocument;

/**
 * 
 */
public class OdfToolkitEngine implements DocumentEngine {
	/**
	 * PDF engine.
	 */
	private PdfEngine pdfEngine;
		
	/**
	 * Creates a new OdfToolkitEngine object. 
	 * 
	 * @param properties
	 *        Provider properties 
	 */
	public OdfToolkitEngine(Properties properties) {
		super();
		String engineClass = CollectionUtils.getMandatoryProperty(properties, "pdfEngineClass"); //$NON-NLS-1$
		try {
			Class<?> engineClazz = Class.forName(engineClass);
			pdfEngine = (PdfEngine) ReflectionUtils.newInstance(engineClazz, properties);
		}catch(ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}
	
	/**
	 * Creates a new OdfToolkitEngine object. 
	 *
	 */
	public OdfToolkitEngine() {
		super();
		pdfEngine = new JodPdfEngine(new Properties());
	}

	public String getEngineName() {		
		return "OdfToolkit"; //$NON-NLS-1$
	}
	
	public BusinessDocument newDocument() throws DocumentEngineException {		
		try {
			return new OdfToolkitTextDocument(TextDocument.newTextDocument(), this);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	public BusinessDocument openDocument(String path)
	throws DocumentEngineException {
		try {
			return new OdfToolkitTextDocument(TextDocument.loadDocument(path), this);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	public BusinessDocument openDocument(File file) throws DocumentEngineException {
		try {
			return new OdfToolkitTextDocument(TextDocument.loadDocument(file), this);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	public BusinessDocument openDocument(InputStream stream) throws DocumentEngineException {
		try {
			return new OdfToolkitTextDocument(TextDocument.loadDocument(stream), this);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	public void saveDocument(BusinessDocument doc, String path) throws DocumentEngineException {
		OdfToolkitTextDocument odfText = safeCast(doc);
		try {
			odfText.document.save(path);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	public void saveDocument(BusinessDocument doc, File file) throws DocumentEngineException {
		OdfToolkitTextDocument odfText = safeCast(doc);
		try {
			odfText.document.save(file);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}		
	}
	
	public void saveDocument(BusinessDocument doc, OutputStream stream)	throws DocumentEngineException {
		OdfToolkitTextDocument odfText = safeCast(doc);
		try {
			odfText.document.save(stream);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	public void saveDocument(BusinessDocument doc) throws DocumentEngineException {
		OdfToolkitTextDocument odfText = safeCast(doc);
		String path = OdfUtils.getDocumentPath(odfText.document);		
		if (path==null) {
			String msg = "The file is not saved"; //$NON-NLS-1$
			throw new DocumentEngineException(msg);
		}
		try {
			odfText.document.save(path);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}		
	}
	
	/**
	 * Safe cast.
	 * 
	 * @param doc
	 * @return Returns the same document.
	 * @throws DocumentEngineException 
	 */
	public static OdfToolkitTextDocument safeCast(BusinessDocument doc) 
	throws DocumentEngineException {
		if (!(doc instanceof OdfToolkitTextDocument)) {
			String msg = "Invalid document type."; //$NON-NLS-1$
			throw new DocumentEngineException(msg);
		}
		return (OdfToolkitTextDocument) doc;
	}
	
	public byte[] toPdf(BusinessDocument doc) throws DocumentEngineException {
		if(doc==null) {
			throw new DocumentEngineException("Input document was null."); //$NON-NLS-1$
		}
		try {
			OdfToolkitTextDocument odf = OdfToolkitEngine.safeCast(doc);
			byte[] odfAsBytes = odf.asByteArray();
			return pdfEngine.toPdf(odfAsBytes);
		} catch (PdfEngineException e) {
			throw new DocumentEngineException(e);
		}
	}
	
	

}
