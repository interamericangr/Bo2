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
package gr.interamerican.bo2.creation.update;

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.empty.Empty1;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AddingCodeToDefaultConstructorClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestAddingCodeToDefaultConstructorClassUpdater {	
	
	/**
	 * Unit test for update.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassCreationException 
	 */	
	@Test
	public void testUpdate() throws InstantiationException, IllegalAccessException, ClassCreationException {
		String newName = "gr.interamerican.synth.AddingCodeToDefaultConstructorClassUpdaterTesterClass1";		
		String extraCode = " this.field2 = new java.lang.Long(78L); ";	
		AddingCodeToDefaultConstructorClassUpdater updater = new AddingCodeToDefaultConstructorClassUpdater(extraCode);
		Class<?> newType = updater.update(BeanWith1Field.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();
		Long f1 = (Long) ReflectionUtils.get("field2", object);
		Assert.assertEquals(new Long(78), f1);
	}
	
	/**
	 * Unit test for update.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassCreationException 
	 */	
	@Test
	public void testUpdate2() throws InstantiationException, IllegalAccessException, ClassCreationException {
		String newName = "gr.interamerican.synth.AddingCodeToDefaultConstructorClassUpdaterTesterClass2";
		String extraCode = " System.out.println(\"hello\"); ";
		AddingCodeToDefaultConstructorClassUpdater updater = new AddingCodeToDefaultConstructorClassUpdater(extraCode);
		Class<?> newType = updater.update(Empty1.class, newName);
		Assert.assertEquals(newName, newType.getName());		
		Object object = newType.newInstance();
		Assert.assertNotNull(object);
	}
	
	

}
