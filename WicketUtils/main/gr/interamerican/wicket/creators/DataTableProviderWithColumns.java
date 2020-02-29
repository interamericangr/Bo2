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
package gr.interamerican.wicket.creators;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

/**
 * A {@link DataTableProvider} that also exposes to the api the columns it
 * contains.
 * 
 * @param <B>
 *            Type of entity presented in the data table.
 * @param <S>
 *            the type of the sorting parameter
 */
public interface DataTableProviderWithColumns<B extends Serializable, S> extends DataTableProvider<B, S> {

	/**
	 * Returns an un-modifiable {@link List} of the columns this contains.<br>
	 * It is the responsibility of the implementor to use
	 * {@link Collections#unmodifiableList(List)}.
	 * 
	 * @return List with columns
	 */
	List<IColumn<B, S>> getColumns();
}