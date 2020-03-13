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
package gr.interamerican.bo2.impl.open.creation.test.conditions;

import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Condition to check if a String that represents an Interface.<br>
 * If {@link Class#forName(String)} throws an {@link ClassNotFoundException} -
 * then it will be wrapped to a {@link RuntimeException}.
 */
public class ClassIsInterface implements Condition<String> {

	@Override
	public boolean check(String clzName) {
		try {
			Class<?> clz = Class.forName(clzName);
			return clz.isInterface();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}