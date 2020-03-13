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
import java.util.Set;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;

/**
 * {@link CallbackAction} that adds the edit element of a {@link BeanPanelDef}
 * to an input set.<br>
 * Before doing that it sets the indexProperty of that element with the max
 * Value. The indexProperty must be of type {@link Long}.<br>
 * In order to avoid problems that occur when we use the same index twice - this
 * {@link CallbackAction} keeps a state with the last index set by this and
 * tracks the initial state of the index as well.<br>
 * It will use the greater value of either the last index set by this last time,
 * or the maximum index present on the set.
 * 
 * @param <B>
 *            Type of elements in the set.
 * @deprecated Use {@link AddElementToCollectionAction}
 */
@Deprecated
public class AddElementToSet<B extends Serializable> implements ProcessAction<B> {

	/** Version UID. */
	private static final long serialVersionUID = 1L;

	/** Set To Add the Element To. */
	Set<B> set;

	/** Name of the property that defines the index. */
	String indexPropertyName;

	/** Last Used Index Property Value. */
	Long newIndex;

	/**
	 * Object that contains various properties that must be copied on the bean
	 * before adding it to the set (optional).
	 */
	Object otherProperties;

	/**
	 * Creates a new AddElementToSet object.
	 * 
	 * @param set
	 *            Set To Add the Element To
	 * @param indexPropertyName
	 *            Name of the property that defines the index
	 * @param otherProperties
	 *            Object that contains various properties that must be copied on
	 *            the bean before adding it to the set (optional)
	 */
	public AddElementToSet(Set<B> set, String indexPropertyName, Object otherProperties) {
		this.set = set;
		this.indexPropertyName = indexPropertyName;
		this.otherProperties = otherProperties;
		this.newIndex = getIndexFromSet();
	}

	/**
	 * Creates a new AddElementToSet object.
	 * 
	 * @param def
	 *            Definition to extract the item to be added
	 * @param set
	 *            Set To Add the Element To
	 * @param indexPropertyName
	 *            Name of the property that defines the index
	 * @param otherProperties
	 *            Object that contains various properties that must be copied on
	 *            the bean before adding it to the set (optional)
	 * @deprecated Use the other Constructor
	 */
	@Deprecated
	public AddElementToSet(@SuppressWarnings("unused") BeanPanelDef<B> def, Set<B> set, String indexPropertyName,
			Object otherProperties) {
		this(set, indexPropertyName, otherProperties);
	}

	@Override
	public B process(B bean) {
		if (otherProperties != null) {
			ReflectionUtils.copyProperties(otherProperties, bean);
		}
		newIndex = NumberUtils.max(getIndexFromSet(), newIndex).longValue();
		ReflectionUtils.setProperty(indexPropertyName, newIndex, bean);
		set.add(bean);
		newIndex = newIndex + 1;
		return bean;
	}

	/**
	 * Returns the next index to set according to the current set values.
	 * 
	 * @return Next Index to set
	 */
	Long getIndexFromSet() {
		B maxOwner = SelectionUtils.max(set, indexPropertyName);
		Long maxIndex = 1L;
		if (maxOwner != null) {
			maxIndex = (Long) ReflectionUtils.getProperty(indexPropertyName, maxOwner);
			maxIndex = Utils.notNull(maxIndex, 0L);
			maxIndex = maxIndex + 1;
		}
		return maxIndex;
	}
}