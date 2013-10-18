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
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import org.junit.Test;

/**
 * Unit test for {@link CachedEntryOwnerValidator}.
 */
public class TestCachedEntryOwnerValidator {
	
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	/**
	 * CODE 1.
	 */
	private static final Long CODE_1 = 1L;

	/**
	 * Creates a CachedEntryOwnerValidator
	 * @return CachedEntryOwnerValidator
	 */
	private CachedEntryOwnerValidator<ObjectType, Long> createCachedEntryOwnerValidator(){
		
		Cache<Long> cache = new CacheImpl<Long>();
		EnumElement enumElement = new EnumElement(TYPE, ObjectType.OBJECT1);
		enumElement.setCode(CODE_1);
		enumElement.setSubTypeId(SUBTYPE);
		cache.put(enumElement);
		
		CachedEntryOwnerValidator<ObjectType, Long> cashedVal = 
			new CachedEntryOwnerValidator<ObjectType, Long>(cache);
		return cashedVal;
	}
	
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testValidateNoInCacheValue() throws ValidationException{
		 CachedEntryOwnerValidator<ObjectType, Long> cashedEntryOwnerVal = createCachedEntryOwnerValidator();
		 cashedEntryOwnerVal.validate(ObjectType.OBJECT2); 
	}
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		 CachedEntryOwnerValidator<ObjectType, Long> cashedEntryOwnerVal = createCachedEntryOwnerValidator();
		 cashedEntryOwnerVal.validate(ObjectType.OBJECT1); 
	}
}
