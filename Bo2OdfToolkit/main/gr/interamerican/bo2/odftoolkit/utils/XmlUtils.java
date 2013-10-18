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

import gr.interamerican.bo2.utils.adapters.VoidOperation;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Utilities.
 */
public class XmlUtils {
	
	/**
	 * Applies an operation on all children of a Node.
	 * 
	 * @param node
	 *        Node to apply the operation to its children.
	 * @param operation
	 *        Operation to apply.
	 */
	public static void applyOnChildren(Node node, VoidOperation<Node> operation) {
		if (node!=null) {
			NodeList children = node.getChildNodes();
			int childrenCount = children.getLength();
			for (int i = 0; i < childrenCount; i++) {
				operation.execute(children.item(i));
			}
		}
	}
	
	/**
	 * Convert a {@link NodeList} to List.
	 * 
	 * @param <T>
	 * @param nodeList
	 *        NodeList to convert.
	 *        
	 * @return Returns a list with all nodes. 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Node> List<T> asList(NodeList nodeList) {
		List<T> list = new ArrayList<T>();
		int count = nodeList.getLength();
		for (int i = 0; i < count; i++) {
			list.add((T) nodeList.item(i));
		}
		return list;		
	}
	
	/**
	 * Gets all nodes under a specified element, that are instances of a specified
	 * class.
	 * 
	 * @param <T>
	 * @param element
	 *        Element under which the nodes are checked.
	 * @param clazz
	 *        All elements are checked to be instances of this class.
	 * @param nodes
	 *        Nodes matching the criteria are added to this list.
	 *        The list is not cleared in the beginning of the method.
	 */
	static <T> void getAllNodesOfType(Node element, Class<T> clazz, List<T> nodes) {
		if (clazz.isInstance(element)) {
			@SuppressWarnings("unchecked") T t = (T) element;
			nodes.add(t);
		}
		NodeList children = element.getChildNodes();
		int childrenCount = children.getLength();
		for (int i = 0; i < childrenCount; i++) {
			Node node = children.item(i);
			getAllNodesOfType(node, clazz, nodes);			
		}
	}

}
