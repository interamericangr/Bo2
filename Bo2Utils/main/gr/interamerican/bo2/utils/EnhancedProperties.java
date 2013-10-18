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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * {@link Properties} extension that is able to import another 
 * properties file and also support property values that embed 
 * the values of other properties declared on the same or imported 
 * file. A property value may embed another property
 * value, by referencing its property key enclosed in ${}.
 * To import another properties file, declare its fully qualified
 * path as the value of the reserved property 'import'.
 *  
 * For example
 * 
 * If /foo/prop2.properties includes a single line: a=a
 * <br/><br/>
 * _import_=/foo/prop2.properties
 * <br/>
 * b=${a}b --> evaluates to ab.
 * <br/>
 * c=${b}c --> evaluates to abc.
 * <br/><br/>
 * If it is not possible to resolve the values of all properties
 * due to cyclic dependencies between them, a RuntimeException is
 * thrown.
 * 
 * @see ImportingProperties
 */
public class EnhancedProperties extends Properties {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prefix of the string referencing a property key.
	 */
	static final String PREFIX = "${"; //$NON-NLS-1$
	
	/**
	 * Suffix of the string referencing a property key.
	 */
	static final String SUFFIX = "}"; //$NON-NLS-1$
	
	/**
	 * Regular expression locating ${} occurrences.
	 */
	static final String REGEX = "\\$\\{.*?\\}"; //$NON-NLS-1$
	
	/**
	 * Compiled regex pattern.
	 */
	static final Pattern p = Pattern.compile(REGEX);
	
	/**
	 * Map associating properties with the properties they reference.
	 */
	private Map<String, List<String>> referencesGraph;
	
	/**
	 * Already evaluated properties.
	 */
	private Set<String> evaluated = new HashSet<String>();
	
	/**
	 * {@link ImportingProperties} instance that holds all properties
	 * before analyzing them and adding them to this.
	 */
	private Properties importingProperties = new ImportingProperties();
	
	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		importingProperties.clear();
		importingProperties.load(inStream);
		validate();
		doLoad();
	}
	
	/**
	 * Reference aware loading.
	 */
	private void doLoad() {
		for(Object key : importingProperties.keySet()) {
			if(referencesGraph.get(key)==null) {
				super.put(key, importingProperties.get(key));
			}
		}
		for(Object key : importingProperties.keySet()) {
			evaluateAndAddProperty((String) key);
		}
	}
	
	/**
	 * Validates that all referenced variables are reachable
	 * and that there are no cyclic dependencies.
	 */
	@SuppressWarnings("nls")
	void validate() {
		referencesGraph = getReferencesGraph();
		for(String k : referencesGraph.keySet()) {
			for(String v : referencesGraph.get(k)) {
				if(!importingProperties.keySet().contains(v)) {
					String msg = "Referenced variable " + v + " is not a reachable property.";
					throw new RuntimeException(msg);
				}
			}
		}
		if(isCyclic()) {
			throw new RuntimeException("Detected cyclic reference in properties: " + this); //$NON-NLS-1$
		}
	}
	
	/**
	 * Reference aware property evaluation.
	 * 
	 * @param key
	 */
	private void evaluateAndAddProperty(String key) {
		if(evaluated.contains(key)) {
			return;
		}
		if(referencesGraph.get(key)==null) {
			return;
		}
		for(String refKey : referencesGraph.get(key)) {
			evaluateAndAddProperty(refKey);
		}
		String value = importingProperties.getProperty(key);
		for(String refKey : referencesGraph.get(key)) {
			value = value.replace(PREFIX+refKey+SUFFIX, (String) super.get(refKey));
		}
		this.put(key, value);
		evaluated.add(key);
	}
	
	/**
	 * Creates a Map that associates each property with the list of properties
	 * it references directly.
	 * 
	 * @return the created Map.
	 */
	private Map<String, List<String>> getReferencesGraph() {
		Map<String, List<String>> graph = new HashMap<String, List<String>>();
		for(Map.Entry<Object, Object> entry : importingProperties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			List<String> referencedVars = RegexUtils.getMatches(value, p);
			if(!referencedVars.isEmpty()) {
				List<String> references = new ArrayList<String>();
				for(String var : referencedVars) {
					references.add(getKeyFromVar(var));
				}
				graph.put(key, references);
			}
		}
		return graph;
	}
	
	/**
	 * Converts a variable to the property it refers to.
	 * 
	 * @param var
	 * @return The property this variable refers to.
	 */
	private String getKeyFromVar(String var) {
		return var.substring(PREFIX.length(), var.length()-SUFFIX.length());
	}
	
	/**
	 * Are there any cyclic dependencies that disallow correct evaluation
	 * of all properties?
	 * 
	 * @return True, if yes.
	 */
	private boolean isCyclic() {
		for(String t : referencesGraph.keySet()) {
			Set<String> stack = new HashSet<String>();
			fillStack(t, stack, false);
			if(stack.contains(t)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper method for {@link #isCyclic()}.
	 * 
	 * @param node
	 * @param stack
	 * @param recursive
	 */
	private void fillStack(String node, Set<String> stack, boolean recursive) {
		if(recursive) {
			stack.add(node);
		}
		if(referencesGraph.get(node)==null) {
			return;
		}
		for(String s : referencesGraph.get(node)) {
			if(!stack.contains(s)) {
				fillStack(s, stack, true);
			}
		}
	}

}
