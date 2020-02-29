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

import java.util.Collection;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;

/**
 * A Simple {@link PoProperty} without any special behavior.
 */
public abstract class SimpleProperty
implements PoProperty {

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
		if (MergeMode.FAVOR_TARGET.equals(mode)) {
			return;
		}
		// Remaining Cases
		if (Collection.class.isAssignableFrom(type)) {
			mergeNonPoCollection(sourceValue, target);
			return;
		}
		ReflectionUtils.setProperty(name, sourceValue, target);
	}

	/**
	 * Merges a simple property that is a collection.
	 * The special handling we need to do on this case is to maintain the target
	 * Collection if it exists.
	 * 
	 * @param sourceValue
	 *            Value this property has on the source entity
	 * @param target
	 *            Target Entity
	 */
	<T> void mergeNonPoCollection(Object sourceValue, Object target) {
		Collection<T> targetCollection = Utils.cast(ReflectionUtils.getProperty(name, target));
		if (targetCollection == null) {
			ReflectionUtils.setProperty(name, sourceValue, target);
			return;
		}
		targetCollection.clear();
		if (sourceValue != null) {
			targetCollection.addAll(Utils.<Collection<T>, Object> cast(sourceValue));
		}
	}
}