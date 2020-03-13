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
package gr.interamerican.wicket.extensions.markup.html.repeater.util;

import static java.util.Comparator.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;

/**
 * Data provider for a sortable table.
 * 
 * @param <T>
 *            Type of objects presented by this data provider.
 */
public class FunctionalSortableDataProvider<T extends Serializable> 
extends org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider<T, SerializableComparableFunction<T,?>> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Rows of the table.
	 */
	private List<T> rows;

	/**
	 * Public Constructor.
	 *
	 * @param rows
	 *            Rows of the table.
	 */
	public FunctionalSortableDataProvider(List<T> rows) {
		this.rows = new ArrayList<T>(rows);
	}

	/**
	 * Creates a new SortableDataProvider object.
	 *
	 * @param rows
	 *            Rows of the table.
	 * @param defaultSort
	 *            Sort Criteria to use on initial Creation of List
	 */
	public FunctionalSortableDataProvider(List<T> rows, SerializableComparableFunction<T, ?> defaultSort) {
		if (defaultSort != null) {
			this.rows = CollectionUtils.sort(rows, defaultSort::apply);
		} else {
			this.rows = new ArrayList<T>(rows);
		}
	}

	/**
	 * Gets the rows of the table.
	 * 
	 * @return Returns a list containing the rows of the table.
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows
	 *            the new rows
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public IModel<T> model(T t) {
		return new Model<>(t);
	}

	@Override
	public Iterator<? extends T> iterator(long first, long count) {
		sortList();
		return rows.subList((int) first, (int) (first + count)).iterator();
	}

	/**
	 * Does the sorting of the list if necessary
	 */
	<C extends Comparable<C>> void sortList() {
		if (getSort() == null) {
			return;
		}
		SortParam<SerializableComparableFunction<T,?>> sortParam = getSort();
		SerializableComparableFunction<T,?> getter = sortParam.getProperty();
		Comparator<T> comparator = comparing(getter::apply, nullsFirst(naturalOrder()));
		if (sortParam.isAscending()) {
			comparator = comparator.reversed();
		}
		Collections.sort(rows, comparator);
	}

	@Override
	public long size() {
		return rows.size();
	}
}