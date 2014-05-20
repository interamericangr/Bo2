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
package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.dom.element.text.TextSequenceDeclsElement;
import org.odftoolkit.odfdom.dom.element.text.TextVariableDeclsElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.NotPublic;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Section;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utilities for ODF files.
 */
@SuppressWarnings("nls")
public class OdfUtils {
	/**
	 * OdfUtils is never instantiated.
	 */
	private OdfUtils() {/*empty*/}
	
	/**
	 * Gets the path where a document is saved.
	 * 
	 * @param document
	 *        Document.
	 *        
	 * @return Returns the path. If the document is not saved, then returns null.
	 */	
	public static String getDocumentPath(Document document) {
		String uri = document.getBaseURI();		
		if (uri==null) {
			return null;
		}
		uri = uri.replace("file:", ""); 
		uri = uri.replace("http:", ""); 
		if (uri.contains(":")) {
			uri = uri.substring(1);
		}
		return uri;
	}
	
	/**
	 * Saves the content of an ODF documents content in four unzipped XML files. <br/>
	 * 
	 * The files are saved in the same folder as the ODF document.
	 * The main part of the filename (without the extension) is the same as the
	 * ODF document's. The extension of each of the four XML files is:
	 * <li>.content.xml</li>
	 * <li>.styles.xml</li>
	 * <li>.meta.xml</li>
	 * <li>.settings.xml</li> 
	 * 
	 * @param document
	 *        Document to save.
	 *        
	 * @throws Exception
	 */
	public static void saveContentAsXml(Document document) throws Exception {
		String path = getDocumentPath(document);
		if (path==null) {
			throw new Exception("The document is not saved");
		}
		save(document.getContentDom(),path+".content.xml");
		save(document.getStylesDom(),path+".styles.xml");
		save(document.getMetaDom(),path+".meta.xml");
		save(document.getSettingsDom(),path+".settings.xml");
	}
	
	/**
	 * Saves the contents of an OdfFile as XML.
	 * 
	 * @param dom
	 * @param path
	 * @throws FileNotFoundException
	 */
	static void save(OdfFileDom dom, String path) throws FileNotFoundException {
		String xml = dom.toString();
		PrintWriter writer = new PrintWriter(new File(path));
		writer.print(xml);
		writer.close();		
	}
	
	
	
	/**
	 * Replaces an {@link OdfElement} with another.
	 * 
	 * @param newElement 
	 *        New element that replaces the old element.
	 * @param oldElement 
	 *        Element that is being replaced by the old element.
	 * 
	 */
	public static void replace(OdfElement newElement, OdfElement oldElement) {
		OdfElement parent = (OdfElement) oldElement.getParentNode();
		parent.replaceChild(newElement, oldElement);
	}
	
	/**
	 * Replaces an {@link OdfElement} with another.
	 * 
	 * @param element 
	 *        Element that is being removed.
	 * 
	 */
	public static void remove(OdfElement element) {
		OdfElement parent = (OdfElement) element.getParentNode();
		parent.removeChild(element);
	}	
	
	/**
	 * Add an {@link OdfElement} before a reference element.
	 * 
	 * @param newElement
	 *        New element that is added before the reference element. 
	 * @param refElement 
	 *        Reference element. 
	 */
	public static void insertBefore(OdfElement newElement, OdfElement refElement) {
		OdfElement parent = (OdfElement) refElement.getParentNode();
		parent.insertBefore(newElement, refElement);
	}
	
	/**
	 * Add an {@link OdfElement} before a reference element.
	 * 
	 * @param newElement
	 *        New element that is added before the reference element. 
	 * @param refElement 
	 *        Reference element. 
	 */
	public static void insertAfter(OdfElement newElement, OdfElement refElement) {		
		OdfElement nextElement = (OdfElement) refElement.getNextSibling();
		if (nextElement!=null) {
			insertBefore(newElement, nextElement);
		} else {
			OdfElement parent = (OdfElement) refElement.getParentNode();
			parent.appendChild(newElement);
		}
	}
	
	/**
	 * Finds if a section is on the top level.
	 * 
	 * A top level section, has the root text element as its parent.
	 * A lower level section will have another section or an element 
	 * of other type as its parent, but not the root text element.
	 * 
	 * @param section
	 *        Section to check.
	 *        
	 * @return Returns true if the specified section is on the top level. 
	 * @throws Exception 
	 */
	public static boolean isTopLevel(Section section) throws Exception {
		TextDocument doc = (TextDocument) section.getOwnerDocument();
		OfficeTextElement root = doc.getContentRoot();
		Node sectionParent = section.getOdfElement().getParentNode();
		return root.equals(sectionParent);
	}
	
	/**
	 * Gets the document's contents as a list of ODF nodes.
	 * 
	 * @param document
	 *        Document.
	 *        
	 * @return Returns a list of sections.
	 * 
	 * @throws Exception 
	 */
	public static List<OdfElement> getOdfElements(TextDocument document) 
	throws Exception {
		List<OdfElement> list = new ArrayList<OdfElement>();
		OfficeTextElement root = document.getContentRoot();
		NodeList nodes = root.getChildNodes();
		int nodesCount = nodes.getLength(); 
		for (int i = 0; i < nodesCount; i++) {
			Node node = nodes.item(i);
			OdfElement odfNode = (OdfElement) node;			
			list.add(odfNode);
		}		
		return list;
	}
	
	/**
	 * Gets the OdfElements of a document, for copying them to another document.
	 * 
	 * This method will return all elements of the document, except from the
	 * documents that belong to the following types.
	 * 
	 * @param document
	 *        Document.
	 *        
	 * @return Returns a list of sections.
	 * 
	 * @throws Exception 
	 */
	public static List<OdfElement> getOdfElementsForCopy(TextDocument document) 
	throws Exception {		
		Set<Class<?>> typesToExclude = new HashSet<Class<?>>();
		typesToExclude.add(TextSequenceDeclsElement.class);
		typesToExclude.add(TextVariableDeclsElement.class);		
		List<OdfElement> list = new ArrayList<OdfElement>();
		OfficeTextElement root = document.getContentRoot();
		NodeList nodes = root.getChildNodes();
		int nodesCount = nodes.getLength(); 
		for (int i = 0; i < nodesCount; i++) {
			Node node = nodes.item(i);
			OdfElement odfNode = (OdfElement) node;
			if (!typesToExclude.contains(odfNode.getClass())) {
				list.add(odfNode);
			}			
		}		
		return list;
	}
	
	/**
	 * Gets the first element of a document.
	 * 
	 * @param document
	 * 
	 * @return Returns the first element of a document.
	 * 
	 * @throws Exception 
	 */
	public static OdfElement getFirstElement(TextDocument document) throws Exception {
		OfficeTextElement root = document.getContentRoot();
		return (OdfElement) root.getFirstChild();		
	}
	
	/**
	 * Gets the last element of a document.
	 * 
	 * @param document
	 * 
	 * @return Returns the last element of a document.
	 * 
	 * @throws Exception 
	 */
	public static OdfElement getLastElement(TextDocument document) throws Exception {
		OfficeTextElement root = document.getContentRoot();
		return (OdfElement) root.getLastChild();		
	}
	
	/**
	 * Copies the specified element after the reference element.
	 * 
	 * @param refElement
	 *        Element used as a reference position.
	 * @param elementsToCopy
	 *        Elements being copied.
	 *        
	 * @throws Exception 
	 */
	public static void copyAfter(OdfElement refElement, OdfElement... elementsToCopy) 
	throws Exception {
		OdfElement referece = refElement;
		Document target = getOwnerDocument(refElement);
		for (OdfElement elementToCopy : elementsToCopy) {
			OdfElement newElement = copyOdfElement(target, elementToCopy);
			insertAfter(newElement, referece);
			referece = newElement;
		}	
	}
	
	/**
	 * Copies the specified element after the reference element.
	 * 
	 * @param refElement
	 *        Element used as a reference position.
	 * @param elementsToCopy
	 *        Elements being copied.
	 *                
	 * @throws Exception 
	 */
	public static void copyBefore(OdfElement refElement, OdfElement... elementsToCopy) 
	throws Exception {
		Document target = getOwnerDocument(refElement);
		for (OdfElement elementToCopy : elementsToCopy) {
			OdfElement newElement = copyOdfElement(target, elementToCopy);
			insertBefore(newElement, refElement);			
		}
	}
	
	/**
	 * Creates a copy of an OdfElement.
	 * 
	 * The copy is done with intention to copy this element
	 * to the target document. 
	 * 
	 * @param target
	 *        Target document.
	 * @param elementToCopy
	 *        Element that is being copied. The element can belong
	 *        either to the target document or to another.
	 *        
	 * @return Returns the OdfElement ready to copy.
	 * 
	 * @throws Exception
	 */
	static OdfElement copyOdfElement(Document target, OdfElement elementToCopy) throws Exception {
		OdfElement newElement = (OdfElement) NotPublic.cloneForeignElement(target, elementToCopy, target.getContentDom(), true);
		Document otherDoc = getOwnerDocument(elementToCopy);
		NotPublic.copyLinkedRefInBatch(target, newElement, otherDoc);
		NotPublic.copyForeignStyleRef(target, newElement, otherDoc);
		NotPublic.updateNames(target, newElement);
		NotPublic.updateXMLIds(target, newElement);
		return newElement;
	}
	
	/**
	 * Gets the owner document of the specified ODF element.
	 * 
	 * @param element 
	 *        Element who's parent will be returned by the method.
	 * 
	 * @return Returns the parent document of the specified OdfElement.
	 */
	static Document getOwnerDocument(OdfElement element) {
		OdfFileDom odfFileDom = (OdfFileDom) element.getOwnerDocument();
		return (Document) odfFileDom.getDocument();
	}
	
	/**
	 * Replaces the specified OdfElement that belongs to the target
	 * TextDocument with the contents of the specified source 
	 * TextDocument.
	 * 
	 * @param toBeReplaced
	 *        OdfElement of the target TextDocument that will be replaced.
	 * @param target
	 *        Document being modified. Owner of the specified toBeReplaced
	 *        OdfElement.
	 * @param source
	 *        Source document who's content will be inserted in the specified
	 *        target document replacing the specified toBeReplaced OdfElement. 
	 * @throws DocumentEngineException 
	 */
	public static void replaceElementWithContent
	(OdfElement toBeReplaced, TextDocument target, TextDocument source) 
	throws DocumentEngineException {
		try {
			List<OdfElement> elementsToInsertList = OdfUtils.getOdfElementsForCopy(source);			
			OdfElement[] elementsToInsert = elementsToInsertList.toArray(new OdfElement[0]);
			OdfUtils.copyBefore(toBeReplaced, elementsToInsert);			
			OdfUtils.remove(toBeReplaced);
			StyleUtils.fixStyles(target);
		} catch (Exception e) {
			throw new DocumentEngineException(e);
		}
	}
	
	/**
	 * Evaluates an Xpath expression and fetches its results
	 * as a list of {@link OdfElement}s.
	 *  
	 * @param dom
	 * @param expression
	 * 
	 * @return Returns the list.
	 * 
	 * @throws Exception
	 */
	public static List<OdfElement> getXpath(OdfFileDom dom, String expression) 
	throws Exception {		
		XPath xpath = dom.getXPath();				
		NodeList nodeList = (NodeList) 
			xpath.evaluate(expression, dom, XPathConstants.NODESET);
		return XmlUtils.asList(nodeList);	
	}
	

}
