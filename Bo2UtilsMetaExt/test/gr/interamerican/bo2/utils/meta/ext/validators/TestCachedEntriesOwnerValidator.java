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

import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.ext.AbstractCacheRelatedTest;

import java.util.Arrays;

import org.junit.Test;

/**
 * Unit test for {@link CachedEntriesOwnerValidator}.
 */
public class TestCachedEntriesOwnerValidator extends AbstractCacheRelatedTest {
	
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
	 * CODE 2.
	 */
	private static final Long CODE_2 = 2L;

	/**
	 * Creates a CachedEntryOwnerValidator
	 * @return CachedEntryOwnerValidator
	 */
	private CachedEntriesOwnerValidator<ObjectType, Long> createCachedEntriesOwnerValidator(){
		EnumElement enumElement = new EnumElement(TYPE, ObjectType.OBJECT1);
		enumElement.setCode(CODE_1);
		enumElement.setSubTypeId(SUBTYPE);
		cache().put(enumElement);
		
		EnumElement enumElement2 = new EnumElement(TYPE, ObjectType.OBJECT2);
		enumElement2.setCode(CODE_2);
		enumElement2.setSubTypeId(SUBTYPE);
		cache().put(enumElement2);
		
		CachedEntriesOwnerValidator<ObjectType, Long> cashedVal = 
			new CachedEntriesOwnerValidator<ObjectType, Long>(TEST_CACHE_NAME);
		return cashedVal;
	}
	
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testValidateNoInCacheValue() throws ValidationException{
		CachedEntriesOwnerValidator<ObjectType, Long>  validator= createCachedEntriesOwnerValidator();
		validator.validate(Arrays.asList(ObjectType.OBJECT1,ObjectType.OBJECT3)); 
	}
	
	/**
	 * Test Validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		CachedEntriesOwnerValidator<ObjectType, Long>  validator= createCachedEntriesOwnerValidator();
		validator.validate(Arrays.asList(ObjectType.OBJECT1,ObjectType.OBJECT2)); 
	}
}
