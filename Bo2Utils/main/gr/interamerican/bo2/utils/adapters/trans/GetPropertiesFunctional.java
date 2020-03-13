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
package gr.interamerican.bo2.utils.adapters.trans;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Extracts properties of an object to an array of objects.
 * 
 * @param <T>
 *            Type Of Object to export Properties From
 */
public class GetPropertiesFunctional<T>
implements Transformation<T, Object[]> {

	/**
	 * Functions of the Properties to extract.
	 */
	private Function<T, Object>[] functions;

	/**
	 * Public Constructor.
	 *
	 * @param functions
	 *            Functions of the Properties to extract.
	 */
	@SafeVarargs
	public GetPropertiesFunctional(Function<T, Object>... functions) {
		this.functions = functions;
	}

	@Override
	public Object[] execute(T t) {
		List<Object> result = new ArrayList<>(functions.length);
		for (Function<T, Object> function : functions) {
			result.add(function.apply(t));
		}
		return result.toArray();
	}
}