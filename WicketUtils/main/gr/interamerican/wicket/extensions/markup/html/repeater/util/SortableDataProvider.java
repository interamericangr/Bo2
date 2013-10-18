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

import gr.interamerican.bo2.utils.comparators.InvertedComparator;
import gr.interamerican.bo2.utils.comparators.PropertyBasedComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Data provider for a sortable table.
 * 
 * @param <T> Type of objects presented by this data provider.
 */
public class SortableDataProvider<T extends Serializable> 
extends org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Rows of the table.
	 */
	List<T> rows;
	
	/**
	 * Rows of the table.
	 */
	Class<T> clazz;
	
	/**
	 * Creates a new SortableDataProvider object. 
	 *
	 * @param rows
	 *        Rows of the table. 
	 * @param clazz 
	 *        Type of objects in the table.
	 */
	public SortableDataProvider(List<T> rows, Class<T> clazz) {
		this.rows = new ArrayList<T>(rows);
		this.clazz = clazz;
	}

	/**
	 * Creates a new SortableDataProvider object. 
	 *
	 * @param rows
	 *        Rows of the table. 
	 * @param clazz 
	 *        Type of objects in the table.
	 * @param defaultProperty 
	 * 		  Default sorting property.
	 */
	public SortableDataProvider(List<T> rows, Class<T> clazz, String defaultProperty) {
		if(defaultProperty != null){
			Comparator<T> comparator = new PropertyBasedComparator<T>(clazz, defaultProperty);
			Collections.sort(rows, comparator);
		}
		this.rows = new ArrayList<T>(rows);
		this.clazz = clazz;
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
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int size() {
		return rows.size();
	}

	public IModel<T> model(T t) {
		return new Model<T>(t);
	}

	public Iterator<? extends T> iterator(int first, int count) {
		final SortParam sortParam = getSort();
		if (sortParam != null) {
			String property = sortParam.getProperty();
			Comparator<T> comparator = new PropertyBasedComparator<T>(clazz, property);
			if (sortParam.isAscending()) {
				comparator = new InvertedComparator<T>(comparator); 
			}
			Collections.sort(rows,comparator);					
		}
		return rows.subList(first, first + count).iterator();		
	}
}


