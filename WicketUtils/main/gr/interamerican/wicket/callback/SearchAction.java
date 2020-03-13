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
import java.util.List;

/**
 * Action Performed when we search for something.
 * 
 * @param <C>
 *            Criteria to search with
 * @param <B>
 *            Type of resulting item
 */
@FunctionalInterface
public interface SearchAction<C, B> extends Serializable {

	/**
	 * The Action
	 * 
	 * @param criteria
	 *            The search criteria
	 * @return The result
	 * @throws Exception
	 */
	List<B> search(C criteria) throws Exception;
}