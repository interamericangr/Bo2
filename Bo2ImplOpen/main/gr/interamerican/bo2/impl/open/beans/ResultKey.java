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
package gr.interamerican.bo2.impl.open.beans;

import gr.interamerican.bo2.impl.open.operations.util.CopyComplexEntityConfiguration;


/**
 * Setter for Optional key of the resulting entity that is a result of
 * {@link CopyComplexEntityConfiguration}.
 * 
 * @param <I>
 *            Input of the {@link CopyComplexEntityConfiguration}.
 */
public interface ResultKey<I> {

	/**
	 * Optional key of the resulting entity.
	 * 
	 * @param resultKey
	 *            Key of the Result
	 */
	void setResultKey(I resultKey);
}