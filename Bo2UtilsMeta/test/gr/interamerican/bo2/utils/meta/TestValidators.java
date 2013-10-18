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

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link ValidatorRegistry}.
 */
public class TestValidators {
	
	/**
	 * Unit test for register.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUtility() {		
		Validator<BeanWith1Field> v1 = Mockito.mock(Validator.class);
		Validator<BeanWith2Fields> v2 = Mockito.mock(Validator.class);
		ValidatorRegistry.register(v1, BeanWith1Field.class);
		ValidatorRegistry.register(v2, BeanWith2Fields.class);
		Validator<BeanWith1Field> actual1 = ValidatorRegistry.getValidator(BeanWith1Field.class);
		Assert.assertEquals(v1, actual1);
		Validator<BeanWith2Fields> actual2 = ValidatorRegistry.getValidator(BeanWith2Fields.class);
		Assert.assertEquals(v2, actual2);
		Validator<BeanWith3Fields> actual3 = ValidatorRegistry.getValidator(BeanWith3Fields.class);
		Assert.assertNull(actual3);
	}

}
