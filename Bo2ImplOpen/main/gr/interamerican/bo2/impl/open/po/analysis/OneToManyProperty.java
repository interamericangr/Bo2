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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.impl.open.po.utils.PoMerger;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.functions.SerializableFunction;

/**
 * A {@link PoProperty} that is used for one to many entities.
 */
public abstract class OneToManyProperty
implements PoProperty, PoAnalysisOwner {

	/**
	 * Name of the Property
	 */
	@Property String name;

	/**
	 * {@link Class} of the Property
	 */
	@Property Class<?> type;

	/**
	 * {@link PoAnalysis} describing the entities included on this
	 */
	@Property PoAnalysis analysis;

	@Override
	public void merge(Object sourceValue, Object target, PoAnalyzer analyzer, MergeMode mode) {
		Object targetValue = ReflectionUtils.getProperty(name, target);
		if (Collection.class.isAssignableFrom(type)) {
			Collection<Object> one = Utils.cast(sourceValue);
			Collection<Object> targetCollection = Utils.cast(targetValue);
			mergePoCollection(one, targetCollection, analyzer, mode);
			return;
		}
		// Arrays of PersistentObject's that are also child
		if (type.isArray()) {
			Object[] casted = Utils.cast(sourceValue);
			Object[] targetArray = Utils.cast(targetValue);
			List<Object> sourceList = CollectionUtils.addAll(new ArrayList<Object>(), casted);
			List<Object> targetList = CollectionUtils.addAll(new ArrayList<Object>(), targetArray);
			mergePoCollection(sourceList, targetList, analyzer, mode);
			ReflectionUtils.setProperty(name, targetList.toArray(), target);
			return;
		}
		throw new RuntimeException("Unsupported Type " + type); //$NON-NLS-1$
	}

	/**
	 * Merges a source {@link Collection} into the target {@link Collection}.<br>
	 * 3 cases exist :
	 * <ul>
	 * <li>Entities that exist on both the source and target collection are
	 * merged.
	 * <li>Entities that exist on source collection but not on target collection
	 * are added on target collection
	 * <li>If the mode in use is {@link MergeMode#OVERWRITE}, then all entities
	 * that exist on target collection but not on source collection - will be
	 * removed from the target collection
	 * </ul>
	 * 
	 * @param sourceCollection
	 *            Collection of Entities to be merged from the source Entity
	 * @param targetCollection
	 *            Collection of Entities to be merged from the target Entity
	 * @param analyzer
	 *            The {@link PoAnalyzer} in use
	 * @param mode The {@link MergeMode}
	 */
	<T extends Object, K extends Comparable<? super K>> void mergePoCollection(Collection<T> sourceCollection, Collection<T> targetCollection,
			PoAnalyzer analyzer, MergeMode mode) {
		SerializableFunction<T, K> getter = ReflectionUtils.getFunction(analysis.getKeyProperty().getName());
		for (T po : sourceCollection) {
			K targetKey = getter.apply(po);
			T targetPo = SelectionUtils.selectFirstByProperty(getter, targetKey, targetCollection);
			if (targetPo != null) {
				PoMerger.merge(analyzer, mode, po, targetPo);
			} else {
				targetCollection.add(po);
			}
		}
		if (MergeMode.OVERWRITE.equals(mode)) {
			// removing target only entities
			List<T> toBeRemoved = new ArrayList<T>();
			for (T targetPo : targetCollection) {
				K targetKey = getter.apply(targetPo);
				if (!SelectionUtils.existsByProperty(getter, targetKey, sourceCollection)) {
					toBeRemoved.add(targetPo);
				}
			}
			targetCollection.removeAll(toBeRemoved);
		}
	}
}