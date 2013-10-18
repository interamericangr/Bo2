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
package gr.interamerican.bo2.utils.meta;

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.meta.validators.Validator;

/**
 * Global store for validators.
 */
public class ValidatorRegistry {
	/**
	 * Validators.
	 */
	private static TypeBasedSelection<Validator<?>> validators =
		new TypeBasedSelection<Validator<?>>();

	/**
	 * Hidden constructor of a utility class.
	 */
	private ValidatorRegistry() {/* empty */}
	
	/**
	 * Registers the validator for the specified type.
	 * 
	 * @param validator
	 *        Validator to register
	 * @param type
	 *        Type being validated by the specified validator.
	 *     
	 * @param <T>
	 */
	public static <T> void register(Validator<T> validator, Class<T> type) {
		validators.registerSelection(type, validator);
	}
	
	/**
	 * Gets the appropriate validator for the specified type,
	 * if one is registered.
	 * 
	 * @param type
	 *        Type to find a validator.
	 * @param <T>
	 * 
	 * @return Returns the validator that has been registered for the 
	 *         specified type. If there is no validator registered for
	 *         the specified type, returns null.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Validator<T> getValidator(Class<T> type) {
		return (Validator<T>) validators.selectionForType(type);
	}
	
	
	

}
