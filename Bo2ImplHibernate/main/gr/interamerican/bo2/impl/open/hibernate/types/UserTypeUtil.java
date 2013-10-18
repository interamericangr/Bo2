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
package gr.interamerican.bo2.impl.open.hibernate.types;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Bo2 custom hibernate types
 */
public class UserTypeUtil {
	
	/**
	 * Maintains the associations between Bo2 defined custom hibernate user types
	 * and the types they represent.
	 */
	public static Map<Class<?>, Class<?>> userTypes = new HashMap<Class<?>, Class<?>>();
	
	/*
	 * static initializer block that fills the userTypes map with data.
	 * Anytime a new custom hibernate user type is created, this should
	 * be updated.
	 */
	static {
		userTypes.put(TypedSelectable.class, EntryUserType.class);
		userTypes.put(Enum.class, OneBasedEnumUserType.class);
		userTypes.put(Money.class, MonetaryAmountUserType.class);
	}
	
	/**
	 * Determines if an object is of a type that is handled with custom
	 * hibernate user types.
	 * 
	 * @param obj object 
	 * 
	 * @return Returns true if the object corresponds to a custom user type.
	 */
	public static boolean isUserType(Object obj) {
		if(obj==null) {
			return false;
		}
		for (Class<?> clazz : userTypes.keySet()) {
			if(clazz.isAssignableFrom(obj.getClass())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * hidden constructor 
	 */
	private UserTypeUtil() {/*empty */}

}
