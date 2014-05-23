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

import gr.interamerican.bo2.odftoolkit.utils.FieldUtils;
import gr.interamerican.bo2.odftoolkit.utils.MetaUtils;
import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.odftoolkit.utils.StyleUtils;
import gr.interamerican.bo2.odftoolkit.utils.VariableContainerUtils;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.adapters.GetProperty;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.odftoolkit.odfdom.dom.element.text.TextUserFieldDeclElement;
import org.odftoolkit.odfdom.dom.element.text.TextVariableDeclElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Footer;
import org.odftoolkit.simple.text.Header;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;

/**
 * Implementation of {@link BusinessDocument} based on OdfToolkit.
 * 
 * This solution is based on a basic assumption. Each document should
 * be organized in sections. The <code>insertAt(String,BusinessDocument)</code>
 * and <code>append(BusinessDocument)</code> methods are based on this 
 * assumption.
 */
public class OdfToolkitTextDocument implements BusinessDocument {
	/**
	 * Document.
	 */
	TextDocument document;
	
	/**
	 * engine.
	 */
	OdfToolkitEngine engine;

	/**
	 * Creates a new TextOdfDocument object. 
	 *
	 * @param document
	 *        Text document.
	 * @param engine 
	 *        Engine managing the document.
	 */
	OdfToolkitTextDocument(TextDocument document, OdfToolkitEngine engine) {
		super();
		this.document = document;
		this.engine = engine;
	}
	
	/**
	 * Creates a new TextOdfDocument object using a default
	 * OdfToolkitEngine. 
	 *
	 * @param document
	 *        Text document.
	 */
	OdfToolkitTextDocument(TextDocument document) {
		this(document,new OdfToolkitEngine());
	}
	
	public DocumentEngine getEngine() {		
		return engine;
	}
	
	public List<String> getFields() throws DocumentEngineException {
		List<TextUserFieldDeclElement> userFields = VariableContainerUtils.getUserFields(document);		
		Transformation<TextUserFieldDeclElement, String> getUserFieldName = 
			new GetProperty<TextUserFieldDeclElement, String>
			("textNameAttribute", TextUserFieldDeclElement.class); //$NON-NLS-1$
		List<String> fields = AdapterUtils.apply(userFields, getUserFieldName);
		
		List<TextVariableDeclElement> variables = VariableContainerUtils.getVariables(document);
		Transformation<TextVariableDeclElement, String> getVariableName = 
			new GetProperty<TextVariableDeclElement, String>
			("textNameAttribute", TextVariableDeclElement.class); //$NON-NLS-1$
		List<String> variableNames = AdapterUtils.apply(variables, getVariableName);
		
		fields.addAll(variableNames);
		
		return fields; 
	}
	
	public void setFields(Object model) throws DocumentEngineException {		
		try {
			FieldUtils.setFields(document, model);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}	
	}
	
	public void append(BusinessDocument doc) throws DocumentEngineException {
		try {
			OdfToolkitTextDocument other = OdfToolkitEngine.safeCast(doc);
			List<OdfElement> otherElementsList = OdfUtils.getOdfElementsForCopy(other.document);			
			OdfElement last = OdfUtils.getLastElement(this.document);
			OdfElement[] otherElements = otherElementsList.toArray(new OdfElement[0]);
			OdfUtils.copyAfter(last, otherElements);
			StyleUtils.fixStyles(this.document);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	public void pageBreak() {		
		document.addPageBreak();
	}

	public byte[] asByteArray() throws DocumentEngineException {
			return asByteStream().toByteArray();
	}
	
	public ByteArrayOutputStream asByteStream() throws DocumentEngineException {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			document.save(stream);
			return stream;
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	public DocumentTable getTable(String name) {
		Table table = document.getTableByName(name);
		if (table!=null) {
			return new OdfToolkitTable(document, table);			
		}
		Header header = document.getHeader(true);
		if (header!=null) {
			table = header.getTableByName(name);
			if (table!=null) {
				return new OdfToolkitTable(document, table);
			}
		}
		header = document.getHeader(false);
		if (header!=null) {
			table = header.getTableByName(name);
			if (table!=null) {
				return new OdfToolkitTable(document, table);
			}
		}
		Footer footer = document.getFooter(true);		
		if (footer!=null) {
			table = footer.getTableByName(name);
			if (table!=null) {
				return new OdfToolkitTable(document, table);
			}
		}
		footer = document.getFooter(false);
		if (footer!=null) {
			table = footer.getTableByName(name);
			if (table!=null) {
				return new OdfToolkitTable(document, table);
			}
		}
		return null;
	}
	
	
	public void addText(String text) throws DocumentEngineException {
		Paragraph paragraph = document.getParagraphByReverseIndex(0, false);
		paragraph.appendTextContent(text);
	}
	
	public void addParagraph(String text) throws DocumentEngineException {
		document.addParagraph(text);
	}
	
	public void insertAt(String positionLabel, BusinessDocument doc)
	throws DocumentEngineException {
		Section sectionToBeReplaced = this.document.getSectionByName(positionLabel);
		if (sectionToBeReplaced==null) {
			String msg = "Section with name " + positionLabel + " not found"; //$NON-NLS-1$ //$NON-NLS-2$
			throw new DocumentEngineException(msg);
		}
		OdfElement toBeReplaced = sectionToBeReplaced.getOdfElement();
		OdfToolkitTextDocument source = OdfToolkitEngine.safeCast(doc);		
		OdfUtils.replaceElementWithContent(toBeReplaced, this.document, source.document);
	}
	
	/**
	 * Puts a new picture in the document.
	 * 
	 * @param pictureName
	 * @param picture
	 * @param pictureType
	 * 
	 * @throws DocumentEngineException 
	 */
	public void newPicture(String pictureName, byte[] picture, String pictureType) 
	throws DocumentEngineException {		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(picture);			
			OdfPackage pkg = document.getPackage();
			String path = "Pictures/" + pictureName;			 //$NON-NLS-1$
	        pkg.insert(bis, path, pictureType);
	        bis.close();
		} catch (IOException ioe) {
			throw new DocumentEngineException(ioe);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	
	public String getProperty(String propertyName) 
	throws DocumentEngineException {
		try {
			return MetaUtils.getProperty(document, propertyName);			
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	
	
	
	
	
	

}
