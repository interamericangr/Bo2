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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.Named;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

/**
 * String utilities.
 */
public class Bo2ArchStringUtils {

	/**
	 * Hidden constructor of a utility class.
	 */
	private Bo2ArchStringUtils() {/* empty */}
	
	/**
	 * Creates a string representation of a {@link Codified} and
	 * {@link Named} object.
	 * 
	 * The string consists of thwe code and the name.
	 * 
	 * @param <T> Type of Codified and Named object.
	 * @param t Codified and Named object that will be presented as string.
	 * 
	 * @return Returns a string representation of the specified object.
	 */
	public static <T extends Codified<?> & Named>
	String formatCodifiedNamed(T t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		return StringUtils.concat(
				StringUtils.toString(t.getCode()),
				StringConstants.SPACE,
				StringConstants.COMMA,
				StringConstants.SPACE,
				t.getName());
	}
	
	

}
