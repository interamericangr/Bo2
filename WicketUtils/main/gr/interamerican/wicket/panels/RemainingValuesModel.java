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
package gr.interamerican.wicket.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

/**
 * An {@link AbstractReadOnlyModel} for a {@link List} that contains values from
 * an input {@link Collection} that are not currently selected (based on an
 * input {@link IModel} of a {@link List} ).
 * 
 * @param <T>
 *            Type of Objects on List
 */
public class RemainingValuesModel<T> extends AbstractReadOnlyModel<List<? extends T>> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All Available Values
	 */
	private final Collection<T> values;

	/**
	 * Current Selected Values
	 */
	private final ListModel<T> currentValues;

	/**
	 * Public Constructor.
	 *
	 * @param values
	 *            All Available Values
	 * @param currentValues
	 *            Current Selected Values
	 */
	public RemainingValuesModel(Collection<T> values, ListModel<T> currentValues) {
		this.values = values;
		this.currentValues = currentValues;
	}

	@Override
	public List<? extends T> getObject() {
		List<T> returned = new ArrayList<>(values);
		returned.removeAll(currentValues.getObject());
		return returned;
	}
}