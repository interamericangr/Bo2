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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.transformations.ObjectPropertiesAnalyzer;
import gr.interamerican.bo2.utils.beans.Tree;
import gr.interamerican.bo2.utils.reflect.analyze.AllFieldsAnalyzer;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import org.slf4j.Logger;

/**
 * Utility class with specific logging functionality
 * for persistent objects.
 */
public class PoLogger {		
	
	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods. There is no need to
	 * create any instance of this class.
	 */
	private PoLogger() {/* empty */}
	
	/**
	 * Prints the keys of a {@link PersistentObject} and its children.
	 * 
	 * @param po Persistent object
	 * @param logger Logger used for logging.
	 */
	public static void printPoKeys(PersistentObject<?> po, Logger logger) {
		Tree<VariableDefinition<?>> keyTree = new PoChildrenAnalyzer().execute(po);
		logger.debug(keyTree.toString());		
	}
	
		
	/**
	 * Prints an object's properties.
	 * 
	 * @param o 
	 *        Object to print. 
	 * @param logger 
	 *        Logger to use.
	 */
	
	public static void printProperties(Object o, Logger logger) {	
		Tree<VariableDefinition<?>> keyTree = new ObjectPropertiesAnalyzer().execute(o);		
		logger.debug(keyTree.toString());	
	}

	/**
	 * Prints an object.
	 * 
	 * @param o 
	 *        Object to print. 
	 * @param logger 
	 *        Logger to use.
	 */
	
	public static void print(Object o, Logger logger) {	
		Tree<VariableDefinition<?>> keyTree = new AllFieldsAnalyzer().execute(o);		
		logger.debug(keyTree.toString());	
	}

}
