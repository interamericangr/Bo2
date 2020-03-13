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

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.analysis.PoAnalysis;
import gr.interamerican.bo2.impl.open.po.analysis.PoProperty;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.Utils;

/**
 * A Utility responsible for merging an entity to another entity.
 */
public interface PoMerger {

	/**
	 * Merges a specific entity.<br>
	 * This is done by processing each {@link PoProperty} of the target class -
	 * that the {@link PoAnalyzer} returns.<br>
	 * The processing is done at
	 * {@link PoProperty#merge(Object, Object, PoAnalyzer, MergeMode)}.<br>
	 * Checks are done prior to checking to validate the 2
	 * {@link PersistentObject}'s are of same type (merging 2 entities that are
	 * of different sub-class is not allowed) and that they have the same key.
	 * 
	 * @param analyzer
	 *            {@link PoAnalyzer} to use
	 * @param mode
	 *            The {@link MergeMode} to use
	 * @param source
	 *            Source entity
	 * @param target
	 *            Target Entity
	 */
	public static <PO> void merge(PoAnalyzer analyzer, MergeMode mode, PO source, PO target) {
		if (!Utils.equals(source.getClass(), target.getClass())) {
			throw new RuntimeException("Merging was attempted between 2 entities of different type"); //$NON-NLS-1$
		}
		PoAnalysis analysis = analyzer.getAnalysis(target.getClass());
		if (!Utils.alike(source, target, analysis.getKeyProperty().getName())) {
			throw new RuntimeException("Merging was attempted between 2 entities that do not have the same key"); //$NON-NLS-1$
		}
		for (PoProperty property : analysis.getProperties()) {
			Object sourceValue = ReflectionUtils.getProperty(property.getName(), source);
			property.merge(sourceValue, target, analyzer, mode);
		}
	}
}