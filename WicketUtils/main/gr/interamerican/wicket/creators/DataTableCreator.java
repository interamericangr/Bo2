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

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

/**
 * Class that creates a {@link DataTable} with a {@link String} sorting parameter.
 * 
 * @param <B>
 *            Type of entity presented in the data table.
 */
public interface DataTableCreator<B extends Serializable> extends DataTableProvider<B, String> {
	// empty
}