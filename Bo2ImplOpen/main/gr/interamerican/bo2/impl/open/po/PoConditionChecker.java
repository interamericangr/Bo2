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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.Or;

import java.util.ArrayList;
import java.util.List;

/**
 * Condition checker that checks conditions about objects.
 */
public class PoConditionChecker {
	
	
	
	/**
	 * List with conditions that are checked in order to omit a field from the
	 * modify children operation of an AbstractBasePo.
	 * 
	 * If any of the conditions is met, then the object will be omitted from
	 * the modify children operation.
	 */
	public static final List<Condition<Object>> CONDITIONS_TO_SKIP_FIXCHILD;
	
	/**
	 * Condition check for omitting an object from modify child. 
	 */
	private static final Condition<Object> IS_CHILD_NOT_FIXED;
	
	static {
		CONDITIONS_TO_SKIP_FIXCHILD = new ArrayList<Condition<Object>>();
		IS_CHILD_NOT_FIXED = new Or<Object>(CONDITIONS_TO_SKIP_FIXCHILD);
	}	
	
	/**
	 * Checks if the specified child object of an AbstractBasePo does not need
	 * to be fixed be the <code>fixChild(o)</code> method.
	 *
	 * @param value
	 *        Object to check.
	 *        
	 * @return Returns true if the specified object or field should be omitted.
	 */
	public static boolean isChildNotNeedFix(Object value) {
		return IS_CHILD_NOT_FIXED.check(value);
	}
	
	

}
