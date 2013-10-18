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
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import org.junit.Test;

/**
 * Unit test for {@link CachedEntryValidator}.
 */
public class TestCachedEntryValidator {

	
	/**
	 * Creates a CachedEntryValidator
	 * @return CachedEntryValidator
	 */
	private CachedEntryValidator<TypedSelectable<Long>, Long> createCachedObjectValidator(){
		
		Cache<Long> cache = new CacheImpl<Long>();
		TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		value.setCode(1L);
		value.setTypeId(1000L);
		value.setSubTypeId(1L);
		cache.put(value);
		CachedEntryValidator<TypedSelectable<Long>, Long> cashedVal = 
			new CachedEntryValidator<TypedSelectable<Long>, Long>(cache);
		return cashedVal;
	}
	
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testValidateNoInCacheValue() throws ValidationException{
		
		 CachedEntryValidator<TypedSelectable<Long>, Long> cashedVal = createCachedObjectValidator();
		 TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		 value.setCode(1L);
		 value.setTypeId(2000L);
		 value.setSubTypeId(1L);
		 cashedVal.validate(value);
		 
	}
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		
		 CachedEntryValidator<TypedSelectable<Long>, Long> cashedVal = createCachedObjectValidator();
		 TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		 value.setCode(1L);
		 value.setTypeId(1000L);
		 value.setSubTypeId(1L);
		 cashedVal.validate(value);
		 
	}
	
	
}
