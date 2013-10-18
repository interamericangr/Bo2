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

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTable;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.adapters.GetProperty;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utilities about styles.
 */
public class StyleUtils {
	
	
	/**
	 * Opens a document and sets the style of all cells of the specified row
	 * of the table with the specified name to be as the style of the first cell.
	 * Then the document is saved.
	 * 
	 * This method opens the specified document and then finds the table with
	 * the specified name. Then the method copies the style of the first cell
	 * to all other cells of the row.
	 * 
	 * @param path
	 *        Path to the document.
	 * @param tableName
	 *        Name of table in the document.
	 * @param row
	 *        Row index.
	 *        
	 * @throws DocumentEngineException 
	 */
	public static void setStyleAsFirstColumn(String path, String tableName, int row) throws DocumentEngineException {
		OdfToolkitEngine engine = new OdfToolkitEngine();		
		BusinessDocument doc = engine.openDocument(path);	
		OdfToolkitTextDocument odfDoc = OdfToolkitEngine.safeCast(doc);
		OdfToolkitTable table = (OdfToolkitTable) odfDoc.getTable(tableName);
		table.copyStyleOfFirstColumnToOthers(row);
		engine.saveDocument(doc);		
	}	
	
	/**
	 * Gets all style nodes.
	 * 
	 * @param doc
	 * @return Returns a list with {@link OdfElement}s.
	 * 
	 * @throws Exception
	 */
	static List<OdfStyle> getStyles(Document doc) throws Exception {
		String xpathExpr =styleNames();
		List<OdfElement> nodes = getXpath(doc,xpathExpr);
		List<OdfStyle> styles = new ArrayList<OdfStyle>();
		for (OdfElement node : nodes) {
			if (node instanceof OdfStyle) {
				styles.add((OdfStyle)node);
			}
		}
		return styles;
	}
	
	/**
	 * Gets all style nodes.
	 * 
	 * @param doc
	 * @param styleName 
	 * @return Returns a list with {@link OdfElement}s.
	 * 
	 * @throws Exception
	 */
	public static boolean styleExists(Document doc, String styleName) 
	throws Exception {	
		String xpathExpr =stylesWithName(styleName);
		List<OdfElement> nodes = getXpath(doc,xpathExpr);
		List<OdfStyle> styles = new ArrayList<OdfStyle>();
		for (OdfElement node : nodes) {
			if (node instanceof OdfStyle) {
				styles.add((OdfStyle)node);
			}
		}
		return !styles.isEmpty();
	}
	
	/**
	 * Gets all style nodes.
	 * 
	 * @param doc
	 * @return Returns a list with {@link OdfElement}s.
	 * 
	 * @throws Exception
	 */
	static Set<String> getStyleNames(Document doc) throws Exception {
		Set<String> names = new HashSet<String>();
		List<OdfStyle> styles = getStyles(doc);
		for (OdfStyle style : styles) {
			names.add(style.getStyleNameAttribute());
		}
		return names;
	}
	
	/**
	 * Fixes the styles of the specified document.
	 * 
	 * @param doc
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public static void fixStyles(Document doc) 
	throws Exception {
		List<OdfStyle> styles = getStyles(doc);
		AnyOperation<OdfStyle, String> getName = 
			new GetProperty<OdfStyle, String>("styleNameAttribute", OdfStyle.class); 
		List<String> styleNames = AdapterUtils.apply(styles, getName);
		
		for (OdfStyle style : styles) {
			String name = style.getStyleNameAttribute(); 
			String clean = cleanName(name);
			if (!clean.equals(name)) {
				if (styleNames.contains(clean)) {
					String family = style.getStyleFamilyAttribute();
					boolean isTable = "table".equals(family); //Fix only tables.
					if (isTable) {
						List<OdfElement> nodesToFix = 
							getNodesWithStyleName(doc, name, "table");
						setStyle(nodesToFix, clean);
					} else if ("standard".equalsIgnoreCase(clean)) {
						List<OdfElement> nodesToFix = 
							getNodesWithStyleName(doc, name, "text");
						setStyle(nodesToFix, clean);						
					}
				}
			}
		}
	}
	
	/**
	 * Sets the specified style name to all nodes.
	 * 
	 * @param nodesToFix
	 * @param name
	 */
	static void setStyle(List<OdfElement> nodesToFix, String name) {
		for (OdfElement node : nodesToFix) {
			if (node instanceof OdfStylableElement) {
				OdfStylableElement element = (OdfStylableElement) node;
				element.setStyleName(name);							
			}
		}
	}
	
	
	
	
	
	/**
	 * XPath expression to fetch all style nodes.
	 * 
	 * It fetches all nodes with style:name attribute.
	 *        
	 * @return Returns the xpath expression.
	 */
	@SuppressWarnings("nls")
	static String styleNames() {
		return "*/office:automatic-styles//*[@style:name]";
	}
	
	/**
	 * XPath expression to fetch all nodes with specific style.
	 * 
	 * @param styleName 
	 *        Name of style.
	 *        
	 * @return Returns the xpath expression.
	 */
	@SuppressWarnings("nls")
	static String stylesWithName(String styleName) {
		return StringUtils.concat(
				"*/office:automatic-styles//*[@style:name=",
				StringUtils.surround(styleName, StringConstants.DOUBLE_QUOTE),
				"]");
	}
	
	/**
	 * XPath expression to fetch all nodes with specific style.

	 * @param styleName 
	 *        Name of style.
	 * @param type 
	 *        Style type (text, table)
	 *        
	 * @return Returns the xpath expression.
	 */
	@SuppressWarnings("nls")
	static String nodesWithStyleName(String styleName,String type) {
		return StringUtils.concat(
				"*/office:body//*[@",
				type, 
				":style-name=",
				StringUtils.surround(styleName, StringConstants.DOUBLE_QUOTE),
				"]");
	}
	
	/**
	 * Gets all nodes of the specified document that have 
	 * the specified style name.
	 * 
	 * @param doc
	 *        Document
	 * @param styleName
	 *        Name of style
	 * @param type 
	 *        Type of style (text, table)
	 * 
	 * @return Returns a list with the nodes that have the specified 
	 *         style name.
	 *         
	 * @throws Exception 
	 */
	static List<OdfElement> getNodesWithStyleName(Document doc, String styleName, String type) 
	throws Exception {				
		String xpathExpr = nodesWithStyleName(styleName,type);				
		return getXpath(doc,xpathExpr);
	}
	
	/**
	 * Converts a NodeList to ArrayList.
	 * 
	 * @param nodeList
	 * 
	 * @return Returns the ArrayList.
	 */
	static List<OdfElement> toList(NodeList nodeList) {
		List<OdfElement> list = new ArrayList<OdfElement>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			OdfElement odf = (OdfElement) node;
			list.add(odf);
		}		
		return list;
	}
	
	/**
	 * Evaluates an Xpath expression and fetches its results
	 * as a list of {@link OdfElement}s.
	 *  
	 * @param doc
	 * @param expression
	 * 
	 * @return Returns the list.
	 * 
	 * @throws Exception
	 */
	static List<OdfElement> getXpath(Document doc, String expression) 
	throws Exception {
		OdfFileDom dom = doc.getContentDom();
		XPath xpath = dom.getXPath();				
		NodeList nodeList = (NodeList) 
			xpath.evaluate(expression, dom, XPathConstants.NODESET);
		return toList(nodeList);	
	}
	
	/**
	 * Gets the clean name of the specified name.
	 * 
	 * @param name
	 * 
	 * @return Returns the initial name.
	 */
	static String cleanName(String name) {
		return StringUtils.cutTo(name, StringConstants.MINUS);
	}
	
}
