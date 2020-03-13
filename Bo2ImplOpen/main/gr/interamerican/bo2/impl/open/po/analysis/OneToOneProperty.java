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

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.impl.open.po.utils.PoMerger;
import gr.interamerican.bo2.utils.ReflectionUtils;

/**
 * A {@link PoProperty} that is used for one to one entities.
 */
public abstract class OneToOneProperty implements PoProperty {

	/**
	 * Name of the Property
	 */
	@Property String name;

	/**
	 * {@link Class} of the Property
	 */
	@Property Class<?> type;

	@Override
	public void merge(Object sourceValue, Object target, PoAnalyzer analyzer, MergeMode mode) {
		// Child PO Entities
		if (sourceValue == null) {
			if (MergeMode.OVERWRITE.equals(mode)) {
				ReflectionUtils.setProperty(name, null, target);
			}
			return;
		}
		Object targetValue = ReflectionUtils.getProperty(name, target);
		if (targetValue == null) {
			ReflectionUtils.setProperty(name, sourceValue, target);
		} else {
			PoMerger.merge(analyzer, mode, sourceValue, targetValue);
		}
	}
}