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
package gr.interamerican.bo2.utils.meta.ext.validators;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.Collection;

/**
 * Validator for multiple cached entries.
 * 
 * @param <T> 
 *        Type of entry.
 * @param <C>
 *        Type of cache code.
 */
public class CachedEntriesValidator
<T extends TypedSelectable<C>, C extends Comparable<? super C>>
implements Validator<Collection<T>> {
	
	/**
	 * Validator of a single entry.
	 */
	private CachedEntryValidator<TypedSelectable<C>, C> delegate;
	
	/**
	 * Creates a new CachedEntriesValidator object. 
	 * @param cache 
	 */
	public CachedEntriesValidator(Cache<C> cache) {
		this.delegate = new CachedEntryValidator<TypedSelectable<C>, C>(cache);
	}

	public void validate(Collection<T> value) throws ValidationException {
		String msg = StringConstants.EMPTY;
		for(TypedSelectable<C> entry : value) {
			try {
				delegate.validate(entry);
			} catch (ValidationException ve) {
				msg += ve.getMessage();
			}
		}
		if(!StringConstants.EMPTY.equals(msg)) {
			throw new ValidationException(msg);
		}
	}

}
