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
package gr.interamerican.wicket.callback;

import java.io.Serializable;
import java.util.Collection;

import gr.interamerican.bo2.utils.NumberCalculator;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.functions.SerializableBiConsumer;
import gr.interamerican.bo2.utils.functions.SerializableFunction;

/**
 * {@link ProcessAction} that adds the processed item to an input set.<br>
 * Before doing that it sets the indexProperty of that element with the max
 * Value.<br>
 * In order to avoid problems that occur when we use the same index twice - this
 * {@link ProcessAction} keeps a state with the last index set by this and
 * tracks the initial state of the index as well.<br>
 * It will use the greater value of either the last index set by this last time,
 * or the maximum index present on the set.
 * 
 * @param <B>
 *            Type of elements in the set.
 * @param <I>
 *            Type of Index
 */
public class AddElementToCollectionAction<B extends Serializable, I extends Number & Comparable<I>>
implements ProcessAction<B> {

	/** Version UID. */
	private static final long serialVersionUID = 1L;

	/** Set To Add the Element To. */
	final Collection<B> set;

	/** Getter of the property that defines the index. */
	final SerializableFunction<B, I> getter;

	/** Setter of the property that defines the index. */
	final SerializableBiConsumer<B, I> setter;

	/**
	 * Object that contains various properties that must be copied on the bean
	 * before adding it to the set (optional).
	 */
	final Object otherProperties;

	/** {@link NumberCalculator} used by this */
	final NumberCalculator<I> calculator;

	/** Last Used Index Property Value. */
	I newIndex;

	/**
	 * Public Constructor.
	 * 
	 * @param set
	 *            Set To Add the Element To
	 * @param getter
	 *            Getter of the property that defines the index
	 * @param setter
	 *            Setter of the property that defines the index
	 * @param otherProperties
	 *            Object that contains various properties that must be copied on
	 *            the bean before adding it to the set (optional)
	 * @param calculator
	 *            {@link NumberCalculator} used by this
	 */
	public AddElementToCollectionAction(Collection<B> set, SerializableFunction<B, I> getter, SerializableBiConsumer<B, I> setter,
			Object otherProperties, NumberCalculator<I> calculator) {
		this.set = set;
		this.getter = getter;
		this.setter = setter;
		this.otherProperties = otherProperties;
		this.calculator = calculator;
		this.newIndex = getIndexFromSet();
	}

	@Override
	public B process(B bean) {
		if (otherProperties != null) {
			ReflectionUtils.copyProperties(otherProperties, bean);
		}
		newIndex = NumberUtils.max(getIndexFromSet(), newIndex);
		setter.accept(bean, newIndex);
		set.add(bean);
		newIndex = calculator.add(newIndex, calculator.get(1));
		return bean;
	}

	/**
	 * Returns the next index to set according to the current set values.
	 * 
	 * @return Next Index to set
	 */
	I getIndexFromSet() {
		B maxOwner = SelectionUtils.max(set, getter);
		I maxIndex = calculator.get(1);
		if (maxOwner != null) {
			maxIndex = getter.apply(maxOwner);
			maxIndex = Utils.notNull(maxIndex, calculator.get(0));
			maxIndex = calculator.add(maxIndex, calculator.get(1));
		}
		return maxIndex;
	}
}