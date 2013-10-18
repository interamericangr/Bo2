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
package org.odftoolkit.simple;

import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.w3c.dom.Node;

/**
 * This utility class exposes methods of the package org.odftoolkit.simple
 * that are not public.
 */
public class NotPublic {
	
	/**
	 * Call to Document.copyLinkedRefInBatch(OdfElement, Document).
	 * 
	 * @param target
	 * @param sourceCloneEle
	 * @param source
	 */
	public static void copyLinkedRefInBatch(Document target, OdfElement sourceCloneEle, Document source) {
		target.copyLinkedRefInBatch(sourceCloneEle, source);
	}
	
	/**
	 * Call to Document.updateNames(OdfElement).
	 * 
	 * @param target
	 * @param element
	 */
	public static void updateNames(Document target, OdfElement element) {
		target.updateNames(element);
	}
	
	/**
	 * Call to Document.updateXMLIds(OdfElement).
	 * 
	 * @param target
	 * @param element
	 */
	public static void updateXMLIds(Document target, OdfElement element) {
		target.updateXMLIds(element);
	}
	
	/**
	 * Call to Document.copyForeignStyleRef(OdfElement, Document)
	 * 
	 * @param target
	 * @param sourceCloneEle
	 * @param srcDoc
	 */
	public static void copyForeignStyleRef(Document target, OdfElement sourceCloneEle, Document srcDoc)  {
		target.copyForeignStyleRef(sourceCloneEle, srcDoc);
	}
	
	/**
	 * Call to Document.cloneForeignElement(Node, OdfFileDom, boolean)
	 * 
	 * @param target
	 * @param element
	 * @param dom
	 * @param deep
	 * 
	 * @return Returns the cloned node.
	 */
	public static Node cloneForeignElement(Document target, Node element, OdfFileDom dom, boolean deep) {
		return target.cloneForeignElement(element, dom, deep);
	}

}
