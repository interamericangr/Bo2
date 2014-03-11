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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * {@link Formatter} for {@link TypedSelectable} objects. <br/>
 * 
 * The formatter writes the TypeId,SubTypeId,Code separated by commas.
 * The String representations of these properties are created by the
 * {@link StringUtils}.toString(object) method.
 */
public class TypedSelectableFormatter 
implements Formatter<TypedSelectable<?>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String format(TypedSelectable<?> t) {			
		return StringUtils.concat(
				StringUtils.toString(t.getTypeId()),
				StringConstants.COMMA,
				StringUtils.toString(t.getSubTypeId()),
				StringConstants.COMMA,
				StringUtils.toString(t.getCode()));	
	}

}
