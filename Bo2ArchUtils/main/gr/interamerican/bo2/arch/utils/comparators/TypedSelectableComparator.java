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
package gr.interamerican.bo2.arch.utils.comparators;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.Utils;

import java.util.Comparator;

/**
 * Compares the Code of Two TypeSelectable Values.
 */
public class TypedSelectableComparator implements Comparator<TypedSelectable<Long>>{

	/**
	 * Creates a new TypeSelectableComparator object. 
	 *
	 */
	public TypedSelectableComparator() {
		super();
	}

	@Override
	public int compare(TypedSelectable<Long> arg0, TypedSelectable<Long> arg1) {
		Long code_0 = arg0.getCode();
		Long code_1 = arg1.getCode();
		return Utils.nullSafeCompare(code_0, code_1);
	}

}
