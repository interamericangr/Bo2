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

import java.util.List;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.utils.ReflectionUtils;

/**
 * A Composite {@link PoProperty}.<br>
 * This means that this {@link PoProperty} is actually an object that contains
 * other {@link PoProperty}-ies
 */
public abstract class CompositeProperty
implements PoProperty, PropertiesOwner {

	/**
	 * Name of the Property
	 */
	@Property String name;

	/**
	 * {@link Class} of the Property
	 */
	@Property Class<?> type;

	/**
	 * The properties that this consists of
	 */
	@Property List<PoProperty> properties;

	@Override
	public void merge(Object sourceValue, Object target, PoAnalyzer analyzer, MergeMode mode) {
		Object targetValue = ReflectionUtils.getProperty(name, target);
		if (targetValue == null) {
			if (!MergeMode.FAVOR_TARGET.equals(mode)) {
				ReflectionUtils.setProperty(name, sourceValue, target);
			}
			return;
		}
		for (PoProperty compProperty : properties) {
			Object sourceCompProperty = null;
			if (sourceValue != null) {
				sourceCompProperty = ReflectionUtils.getProperty(compProperty.getName(), sourceValue);
			}
			compProperty.merge(sourceCompProperty, targetValue, analyzer, mode);
		}
	}	
}