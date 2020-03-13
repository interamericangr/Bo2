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
package gr.interamerican.bo2.impl.open.po.utils;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link PoAnalyzerResolver}.
 */
public class DefaultPoAnalyzerResolver implements PoAnalyzerResolver {

	/**
	 * Singleton Instance
	 */
	public final static DefaultPoAnalyzerResolver INSTANCE = new DefaultPoAnalyzerResolver();

	/**
	 * Registered analyzers
	 */
	private Map<Pair<Class<?>, String>, PoAnalyzer> analyzers = new HashMap<Pair<Class<?>, String>, PoAnalyzer>();

	/**
	 * Hidden Constructor.
	 */
	private DefaultPoAnalyzerResolver() {
		// empty
	}

	@Override
	public PoAnalyzer getAnalyzer(Class<?> clz, String manager) {
		return Utils.notNull(analyzers.get(new Pair<Class<?>, String>(clz, manager)), new DefaultPoAnalyzer());
	}

	/**
	 * Registers an analyzer.
	 * 
	 * @param clz
	 *            Class to Analyze
	 * @param manager
	 *            Used Manager
	 * @param analyzer
	 *            {@link PoAnalyzer}
	 */
	public void registerAnalyzer(Class<?> clz, String manager, PoAnalyzer analyzer) {
		analyzers.put(new Pair<Class<?>, String>(clz, manager), analyzer);
	}
}