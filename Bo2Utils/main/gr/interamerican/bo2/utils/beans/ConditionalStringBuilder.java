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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.utils.StringConstants;

import java.util.ArrayList;


/**
 * Utility bean that for the creation of a string that is built
 * dynamically based on dynamically evaluated conditions.
 */
public class ConditionalStringBuilder {
	
	/**
	 * Separator used to separate the fragments. 
	 */
	String separator = StringConstants.EMPTY;
	
	/**
	 * List the strings to concatenate.
	 */
	ArrayList<String> fragments = new ArrayList<String>();
	
	/**
	 * Appends the specified string fragment to this bean if the specified condition
	 * is evaluated to <code>true</code>.
	 * 
	 * The condition is evaluated at the time of adding the fragment,
	 * so if the condition is false when adding the fragment, the
	 * fragment will not be included in the result of the <code>toString()</code>
	 * method.
	 * 
	 * @param fragment
	 *        String fragment that will be part of the output string if the
	 *        condition is true.
	 * @param condition
	 *        Condition that must be evaluated to true in order to include
	 *        the specified string fragment to the output string.
	 */
	public void appendIf(String fragment, boolean condition) {
		if (condition) {
			fragments.add(fragment);
		}
	}	
	
	/**
	 * Adds a conditional fragment to this conditional string builder.
	 * 
	 * The value of the object is evaluated at the time of adding the fragment,
	 * so if it is null at this time, the fragment will not be included in the 
	 * result of the <code>toString()</code> method.
	 * 
	 * @param fragment
	 *        String fragment that will be part of the output string if the
	 *        specified object is not null.
	 * @param object
	 *        Object that defines the condition for the specified fragment.
	 *        If the object is not null the condition is true, otherwise the
	 *        condition is false.
	 */
	public void appendIfNotNull(String fragment, Object object) {
		appendIf(fragment, object!=null);
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for (String fragment : fragments) {
			if (i!=0 && separator!=null) {
				sb.append(separator); 
			}
			sb.append(fragment);
			i++;
		}
		return sb.toString();
	}
	
	/**
	 * Sets the separator.
	 * 
	 * @return Returns the separator if it is not null. Otherwise
	 *         returns an empty string.
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * Sets the separator.
	 * 
	 * @param separator
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

}
