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
package gr.interamerican.bo2.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for dependency graphs. A Graph is represented, herein, by a {@literal Map<T, Set<T>}
 * that enforces that all elements that appear in the sets of the map entryset appear on the map 
 * keyset as well. Each key, T, is associated with the set of nodes it depends on, {@literal Set<T>}.
 */ 
public class DependencyGraphs {
	
	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(DependencyGraphs.class.getName());
	
	/**
	 * This method returns an ordered List of nodes in a fashion that no
	 * element in the list is preceded by any node it depends on.
	 * 
	 * @param graph
	 * 
	 * @param <T>
	 *        Type of graph node. Two different object instances that represent 
	 *        the same node, should be equal.
	 *        
	 * @return Ordered list.
	 * 
	 * @throws IllegalStateException if the graph is not solvable.
	 */
	@SuppressWarnings("nls")
	public <T> List<T> getNodesOrderWithDependencies(Map<T, Set<T>> graph) {
		if(!isSolvable(graph)) {
			throw new IllegalStateException("Input graph cannot be solved: " + graph);
		}
		List<T> ordered = new ArrayList<T>();
		
		Set<T> nodesToOrder = new HashSet<T>(graph.keySet());
		
		int ctr=0;
		
		while(true) { //since we know the graph can be solved this is safe
			ctr++;
			T orderedNode = null;
			for(T node : nodesToOrder) {
				boolean goodToOrder = true;
				for(T dependsOn : graph.get(node)) { //if all the node's dependencies are already ordered the node is good to order
					if(!ordered.contains(dependsOn)) {
						goodToOrder = false;
					}
				}
				if(goodToOrder) {
					ordered.add(node);
					orderedNode = node;
					break; //ordered a node, break to update nodesToOrder
				}
			}
			nodesToOrder.remove(orderedNode);
			orderedNode = null;
			
			LOG.debug("Ordered " + ctr + " out of " + graph.size() + ": " + ordered);
			
			if(ordered.size()==graph.size()) {
				break;
			}
		}
		
		return ordered;
	}
	
	/**
	 * @param graph
	 * @return True, if the input is valid, see javadoc.
	 */
	@SuppressWarnings("nls")
	private <T> boolean isInputValid(Map<T, Set<T>> graph) {
		Set<T> allNodes = graph.keySet();
		for(Set<T> nodes : graph.values()) {
			if(!allNodes.containsAll(nodes)) {
				LOG.error("The following nodes " + new HashSet<T>(nodes).removeAll(allNodes) + " do not appear as keys.");
				return false;
			}
		}
		return true;
	}
		
	/**
	 * A circular dependency (e.g. A depends on B and B depends on A) is detected here. 
	 * When this happens, there is no solution to this graph.
	 * 
	 * @param <T>
	 *        Type of graph node. Two different object instances that represent 
	 *        the same node, should be equal.
	 *        
	 * @param graph
	 * @return True, if the graph is solvable.
	 */
	public <T> boolean isSolvable(Map<T, Set<T>> graph) {
		if(!isInputValid(graph)) {
			throw new IllegalStateException("This graph does not follow the outlined conventions. See javadoc. " + graph); //$NON-NLS-1$
		}
		for(T node : graph.keySet()) {
			if(dependsOnSelf(node, null, graph, null)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true, if a node ultimately depends on itself.
	 * 
	 * @param originalNode
	 *        The node that is examined.
	 * @param currentNode
	 *        For recursive calls.
	 * @param graph
	 *        The dependency graph.
	 * @param explored
	 *        Nodes explored so far.
	 *        
	 * @return Returns true, if this node depends on itself.
	 */
	@SuppressWarnings("nls")
	<T> boolean dependsOnSelf(T originalNode, T currentNode, Map<T, Set<T>> graph, List<T> explored) {
		T currentNodeLocal = currentNode!=null ? currentNode : originalNode;
		List<T> exploredLocal = explored!=null ? explored : new ArrayList<T>();
		
		for(T node : graph.get(currentNodeLocal)) {
			if(node.equals(originalNode)) {
				exploredLocal.add(node);
				LOG.debug("Cycle detected: Node " + originalNode + " dependencies are: " + exploredLocal);
				return true;
			}
			if(exploredLocal.contains(node)) { //do not re-examine
				continue;
			}
			exploredLocal.add(node); //node is explored
			if(dependsOnSelf(originalNode, node, graph, exploredLocal)) { //recursively examine dependencies of current node
				return true;
			}
		}
		return false;
	}

	
	/**
	 * @return Factory method.
	 */
	public static DependencyGraphs get() {
		return new DependencyGraphs();
	}
	
	/**
	 * Hidden. 
	 */
	private DependencyGraphs() {
		super();
	}
	
}
