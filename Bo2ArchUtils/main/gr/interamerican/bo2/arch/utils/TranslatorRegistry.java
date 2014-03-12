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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.ext.Translator;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache registry keeps all registered caches.
 */
public class TranslatorRegistry {
	
	/**
	 * Hidden constructor.
	 */
	private TranslatorRegistry() {/* empty */}

	/**
	 * Map with all registered translators.
	 */
	private static Map<String, Translator<?,?>> registry = new HashMap<String, Translator<?,?>>();
	
	/**
	 * Gets a translator that has been registered with a specified name.
	 * 
	 * @param name Name of translator searched in registry.
	 * @return Gets the translator with the specified name.
	 */
	@SuppressWarnings("unchecked")
	public static <R, L> Translator<R, L> getRegisteredTranslator(String name) {
		return (Translator<R, L>) registry.get(name);
	}
	
	/**
	 * Registers a cache with a name.
	 * 
	 * If another cache with the same name exists, then a 
	 * RuntimeException will be thrown.
	 * 
	 * @param name Translator name.
	 * @param translator Translator to register.
	 */
	public static <R,L> void registerTranslator(String name, Translator<R, L> translator) {		
		Translator<?, ?> old = registry.get(name);
		if (old!=null) {			
			String message = "Already exists translator with the same name " //$NON-NLS-1$
				           + name;
			throw new RuntimeException(message);
		}
		registry.put(name, translator);
	}
	
}
