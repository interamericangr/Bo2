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
package gr.interamerican.bo2.impl.open.po.analysis;

import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.utils.attributes.Named;

/**
 * Interface for a Property of an object that can be persisted.
 */
public interface PoProperty extends Named {

	/**
	 * {@link Class} of the Property
	 * 
	 * @return {@link Class} of the Property
	 */
	Class<?> getType();

	/**
	 * {@link Class} of the Property
	 * 
	 * @param clz
	 *            {@link Class} of the Property
	 */
	void setType(Class<?> clz);

	/**
	 * Handles the merging of the property.
	 * 
	 * @param sourceValue
	 *            Value of the Property from the source
	 * @param target
	 *            Target Entity to merge to
	 * @param analyzer
	 *            {@link PoAnalyzer} to use
	 * @param mode
	 *            The {@link MergeMode}
	 */
	void merge(Object sourceValue, Object target, PoAnalyzer analyzer, MergeMode mode);
}