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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Since java.util still does not have a Tree implementation, here is one.
 * 
 * @param <T> 
 *        Type of element in the tree.
 * 
 */
public class Tree<T> {

	/**
	 * Tree root.
	 */
	T rootElement;
	
	/**
	 * Tree name.
	 */
	String name;
	
	/**
	 * children.
	 */
	List<Tree<T>> nodes = new ArrayList<Tree<T>>();
	
	/**
	 * Parent tree.
	 */
	Tree<T> parent;
	
	/**
	 * Creates a new Tree object. 
	 *
	 * @param rootElement
	 *        Root element.
	 * @param name
	 *        Tree name.
	 */
	public Tree(T rootElement, String name) {
		super();
		this.rootElement = rootElement;
		this.name = name;
	}
	
	/**
	 * Creates a new Tree object. 
	 *
	 * @param rootElement
	 *        Root element.
	 */
	public Tree(T rootElement) {
		super();
		this.rootElement = rootElement;
		this.name = rootElement.toString();
	}
	
	
	
	/**
	 * Adds a child node to this tree.
	 * 
	 * @param node
	 *        Node to add.
	 */
	public void add(Tree<T> node) {
		nodes.add(node);
		node.parent = this;
	}
	
	/**
	 * Creates and adds a node for the specified value.
	 * 
	 * @param t
	 *        Value to add.
	 */
	public void add(T t) {
		Tree<T> node = new Tree<T>(t);
		add(node);
	}
	
	/**
	 * Adds a child node to this tree, putting it to the specified index.
	 * 
	 * @param index
	 *        Index to put.
	 * @param node
	 *        Node to add.
	 */
	public void addAt(int index, Tree<T> node) {
		nodes.add(index, node);
		node.parent = this;
	}
	
	/**
	 * Removes a child node from this tree.
	 * 
	 * @param node
	 *        Node to remove.
	 *        
	 * @return Returns true if the tree contained the node.
	 */
	public boolean remove(Tree<T> node) {
		boolean b = nodes.remove(node);
		if (b) {
			node.parent = null;
		}
		return b; 
	}
	
	/**
	 * Removes the first node that has the specified value as root.
	 * 
	 * @param t
	 *        Value to remove.
	 *        
	 * @return Returns the part of the tree that was removed.
	 */
	public Tree<T> remove(T t) {
		Tree<T> removed = getAnyNodeOf(t);
		if (removed!=null) {
			remove(removed);
		}
		return removed;
	}
	
	/**
	 * Removes a child node from this tree.
	 * 
	 * @param index
	 *        Index of the node to be removed.
	 *        
	 * @return Returns the removed element.
	 */
	public Tree<T> removeAt(int index) {
		if (index<nodes.size()) {
			Tree<T> node = nodes.remove(index);
			node.parent = null;
			return node;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the first child node of this tree that has the specified
	 * object as its root element.
	 * 
	 * Only the direct children of this tree are searched. Their children
	 * are ignored.
	 *  
	 * @param t
	 *        Object being searched.
	 *        
	 * @return Returns the first direct child node of this tree that has 
	 *         the specified element as root. If there is no direct child
	 *         node of this tree with the specified root element, then 
	 *         returns null;
	 */
	public Tree<T> getChildNodeOf(T t) {
		for (Tree<T> node : nodes) {
			if (Utils.equals(node.rootElement, t)) {
				return node;
			}
		}
		return null;		
	}
	
	/**
	 * Gets the first child node of this tree that has the specified
	 * object as its root element.
	 * 
	 * Only the direct children of this tree are searched. Their children
	 * are ignored.
	 *  
	 * @param t
	 *        Object being searched.
	 *        
	 * @return Returns the first direct child node of this tree that has 
	 *         the specified element as root. If there is no direct child
	 *         node of this tree with the specified root element, then 
	 *         returns null;
	 */
	public Tree<T> getAnyNodeOf(T t) {
		for (Tree<T> child : nodes) {
			if (Utils.equals(child.rootElement, t)) {
				return child;
			} else {
				Tree<T> grandChild = child.getAnyNodeOf(t);
				if (grandChild!=null) {
					return grandChild;
				}
			}			
		}
		return null;		
	}

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Assigns a new value to the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the nodes.
	 *
	 * @return Returns the nodes
	 */
	public List<Tree<T>> getNodes() {
		return nodes;
	}

	/**
	 * Gets the rootElement.
	 *
	 * @return Returns the rootElement
	 */
	public T getRootElement() {
		return rootElement;
	}	
	
	/**
	 * Removes all elements that have the specified value as rootElement.
	 * 
	 * @param t
	 *        Value specified for removal.
	 */
	public void removeAll(T t) {
		List<Tree<T>> forRemoval = new ArrayList<Tree<T>>();
		
		for (Tree<T> node : nodes) {
			if (Utils.equals(node.rootElement, t)) {
				forRemoval.add(node);
			} else {
				node.removeAll(t);
			}
		}
		for (Tree<T> nodeToRemove : forRemoval) {
			remove(nodeToRemove);
		}
	}
	
	
	@Override
	public String toString() {	
		return StringConstants.NEWLINE + toString(this,0);
	}
	
	/**
	 * Prints a string for a node.
	 * @param node
	 * @param level
	 * @return returns a String for the node.
	 */
	String toString(Tree<T> node, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.spaces(4*level));
		sb.append(node.getName());
		sb.append(StringConstants.COLON);
		sb.append(StringConstants.SPACE);
		sb.append(StringUtils.toString(node.getRootElement()));
		sb.append(StringConstants.NEWLINE);		
		for (Tree<T> child : node.getNodes()) {
			sb.append(toString(child, level+1));
		}
		return sb.toString();
	}
	
	/**
	 * Gets the total count of nodes including all levels
	 * of the tree.
	 * 
	 * @return Returns the total count of nodes.
	 */
	public int getTotalNodesCount() {
		int count = 1;
		for (Tree<T> node : this.nodes) {
			count = count + node.getTotalNodesCount(); 
		}
		return count;
	}
	

}
