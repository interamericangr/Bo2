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

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.Collection;

/**
 * Validator for multiple cached Object Owners.
 * 
 * @param <T> 
 *        Type of entry Owner.
 * @param <C>
 *        Type of cache code.
 *  
 */
public class CachedEntriesOwnerValidator 
<T extends TranslatableEntryOwner<C,?,?>,
C extends Comparable<? super C>> 
implements Validator<Collection<T>> {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Validator of a single entry.
	 */
	private CachedEntryOwnerValidator<TranslatableEntryOwner<C,?,?>, C> delegate;
	
	/**
	 * Creates a new CachedEntriesValidator object. 
	 *
	 * @param cacheName the cache name
	 */
	public CachedEntriesOwnerValidator(String cacheName) {
		this.delegate = new CachedEntryOwnerValidator<TranslatableEntryOwner<C,?,?>, C>(cacheName);
	}

	@Override
	public void validate(Collection<T> value) throws ValidationException {
		String msg = StringConstants.EMPTY;
		for(TranslatableEntryOwner<C,?,?> entry : value) {
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