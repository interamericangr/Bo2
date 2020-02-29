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
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;

import gr.interamerican.bo2.odftoolkit.utils.FieldUtils;
import gr.interamerican.bo2.odftoolkit.utils.MetaUtils;
import gr.interamerican.bo2.odftoolkit.utils.OdfUtils;
import gr.interamerican.bo2.odftoolkit.utils.StyleUtils;
import gr.interamerican.bo2.odftoolkit.utils.TableUtils;
import gr.interamerican.bo2.odftoolkit.utils.VariableContainerUtils;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.IllegalCharacterFilter;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngine;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;
import gr.interamerican.bo2.utils.doc.DocumentTable;

/**
 * Implementation of {@link BusinessDocument} based on OdfToolkit.
 * 
 * This solution is based on a basic assumption. Each document should be
 * organized in sections. The <code>insertAt(String,BusinessDocument)</code> and
 * <code>append(BusinessDocument)</code> methods are based on this assumption.
 */
public class OdfToolkitTextDocument implements BusinessDocument {
	/**
	 * Document.
	 */
	public TextDocument document;

	/**
	 * engine.
	 */
	OdfToolkitEngine engine;

	/**
	 * Creates a new TextOdfDocument object.
	 *
	 * @param document
	 *            Text document.
	 * @param engine
	 *            Engine managing the document.
	 */
	OdfToolkitTextDocument(TextDocument document, OdfToolkitEngine engine) {
		super();
		this.document = document;
		this.engine = engine;
	}

	/**
	 * Creates a new TextOdfDocument object using a default OdfToolkitEngine.
	 *
	 * @param document
	 *            Text document.
	 */
	OdfToolkitTextDocument(TextDocument document) {
		this(document, new OdfToolkitEngine());
	}

	@Override
	public DocumentEngine getEngine() {
		return engine;
	}

	@Override
	public List<String> getFields() throws DocumentEngineException {
		List<TextUserFieldDeclElement> userFields = VariableContainerUtils.getUserFields(document);
		List<String> fields = AdapterUtils.apply(userFields, TextUserFieldDeclElement::getTextNameAttribute);
		List<TextVariableDeclElement> variables = VariableContainerUtils.getVariables(document);
		List<String> variableNames = AdapterUtils.apply(variables, TextVariableDeclElement::getTextNameAttribute);
		fields.addAll(variableNames);
		return fields;
	}

	@Override
	public void setFields(Object model) throws DocumentEngineException {
		try {
			FieldUtils.setFields(document, model);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
		setBarcodes();
	}

	@Override
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

	@Override
	public void pageBreak() {
		document.addPageBreak();
	}

	@Override
	public byte[] asByteArray() throws DocumentEngineException {
		return asByteStream().toByteArray();
	}

	@Override
	public ByteArrayOutputStream asByteStream() throws DocumentEngineException {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			document.save(stream);
			return stream;
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	@Override
	public DocumentTable getTable(String name) {
		Table table = TableUtils.getTable(document, name);
		if (table != null) {
			return new OdfToolkitTable(document, table);
		}
		return null;
	}

	@Override
	public void addText(String text) throws DocumentEngineException {
		String txt = IllegalCharacterFilter.SINGLETON.filter(text);
		Paragraph paragraph = document.getParagraphByReverseIndex(0, false);
		paragraph.appendTextContent(txt);
	}

	@Override
	public void addParagraph(String text) throws DocumentEngineException {
		document.addParagraph(text);
	}

	@Override
	public void insertAt(String positionLabel, BusinessDocument doc) throws DocumentEngineException {
		Section sectionToBeReplaced = this.document.getSectionByName(positionLabel);
		if (sectionToBeReplaced == null) {
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
	 *            the picture name
	 * @param picture
	 *            the picture
	 * @param pictureType
	 *            the picture type
	 * @throws DocumentEngineException
	 *             the document engine exception
	 */
	public void newPicture(String pictureName, byte[] picture, String pictureType) throws DocumentEngineException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(picture)) {
			OdfPackage pkg = document.getPackage();
			String path = "Pictures/" + pictureName; //$NON-NLS-1$
			pkg.insert(bis, path, pictureType);
		} catch (IOException ioe) {
			throw new DocumentEngineException(ioe);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	@Override
	public String getProperty(String propertyName) throws DocumentEngineException {
		try {
			return MetaUtils.getProperty(document, propertyName);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}

	/**
	 * Sets the barcodes to the relevant tables.
	 *
	 * @throws DocumentEngineException
	 *             the document engine exception
	 */
	void setBarcodes() throws DocumentEngineException {
		String prefix = OdfToolkitEnvironment.get().getBarcodeTablesNamePrefix();
		List<Table> tables = TableUtils.getTables(document);
		for (Table table : tables) {
			String name = table.getTableName().toUpperCase();
			if (name.startsWith(prefix)) {
				OdfToolkitTable odfTable = new OdfToolkitTable(document, table);
				odfTable.setBarcode();
			}
		}
	}
}