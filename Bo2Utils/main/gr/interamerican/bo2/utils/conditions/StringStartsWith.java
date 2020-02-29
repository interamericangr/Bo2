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
package gr.interamerican.bo2.utils.conditions;

import java.util.Set;

import gr.interamerican.bo2.utils.StringUtils;

/**
 * Condition to check if a string starts with any of the given prefixes.
 */
public class StringStartsWith implements Condition<String> {

	/** Set of Prefixes to check against. */
	Set<String> prefixes;

	/**
	 * Creates a new StringStartsWith object.
	 * 
	 * @param prefixes
	 *            Set of Prefixes to check against
	 */
	public StringStartsWith(Set<String> prefixes) {
		super();
		this.prefixes = prefixes;
	}

	@Override
	public boolean check(String t) {
		return StringUtils.startsWith(t, prefixes);
	}
}