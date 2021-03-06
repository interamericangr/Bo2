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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.Document;

import gr.interamerican.bo2.odftoolkit.OdfToolkitEngine;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTable;
import gr.interamerican.bo2.odftoolkit.OdfToolkitTextDocument;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.doc.BusinessDocument;
import gr.interamerican.bo2.utils.doc.DocumentEngineException;

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
	 * @param path        Path to the document.
	 * @param tableName        Name of table in the document.
	 * @param row        Row index.
	 *        
	 * @throws DocumentEngineException the document engine exception
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
	 * @param doc the doc
	 * @return Returns a list with {@link OdfElement}s.
	 * @throws Exception the exception
	 */
	static List<OdfStyle> getStyles(Document doc) throws Exception {
		String xpathExpr =styleNames();
		List<OdfElement> nodes = OdfUtils.getXpath(doc.getContentDom(),xpathExpr);
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
	 * @param doc the doc
	 * @param styleName the style name
	 * @return Returns a list with {@link OdfElement}s.
	 * @throws Exception the exception
	 */
	public static boolean styleExists(Document doc, String styleName) 
	throws Exception {	
		String xpathExpr =stylesWithName(styleName);
		List<OdfElement> nodes = OdfUtils.getXpath(doc.getContentDom(),xpathExpr);
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
	 * @param doc the doc
	 * @return Returns a list with {@link OdfElement}s.
	 * @throws Exception the exception
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
	 * @param doc the doc
	 * @throws Exception the exception
	 */
	@SuppressWarnings("nls")
	public static void fixStyles(Document doc) 
	throws Exception {
		List<OdfStyle> styles = getStyles(doc);
		List<String> styleNames = AdapterUtils.apply(styles, OdfStyle::getStyleNameAttribute);
		
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
	 * @param nodesToFix the nodes to fix
	 * @param name the name
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
	 * @param doc        Document
	 * @param styleName        Name of style
	 * @param type        Type of style (text, table)
	 * @return Returns a list with the nodes that have the specified
	 *         style name.
	 *         
	 * @throws Exception the exception
	 */
	static List<OdfElement> getNodesWithStyleName(Document doc, String styleName, String type) 
	throws Exception {				
		String xpathExpr = nodesWithStyleName(styleName,type);
		return OdfUtils.getXpath(doc.getContentDom(),xpathExpr);
	}
	
	
	
	/**
	 * Gets the clean name of the specified name.
	 *
	 * @param name the name
	 * @return Returns the initial name.
	 */
	static String cleanName(String name) {
		return StringUtils.cutTo(name, StringConstants.MINUS);
	}
	
}
