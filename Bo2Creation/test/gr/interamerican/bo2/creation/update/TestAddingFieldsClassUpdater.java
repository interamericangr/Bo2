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
import gr.interamerican.bo2.samples.empty.IEmpty1Impl1;
import gr.interamerican.bo2.utils.ReflectionUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AddingFieldsClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestAddingFieldsClassUpdater {	
	/**
	 * code for fields.
	 */	
	String[] code = {
		"public int field1;",
		"public Integer field2;"
	};	
	
	/**
	 * Unit test for update.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassCreationException 
	 */	
	@Test
	public void testUpdate() 
	throws InstantiationException, IllegalAccessException, ClassCreationException {
		String newName = "gr.interamerican.synth.AddingFieldsClassUpdaterTesterClass";		
		AddingFieldsClassUpdater updater = new AddingFieldsClassUpdater(code);
		Class<?> newType = updater.update(IEmpty1Impl1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();
		Integer f1 = (Integer) ReflectionUtils.get("field1", object);
		Assert.assertEquals(new Integer(0), f1);
		Integer f2 = (Integer) ReflectionUtils.get("field2", object);
		Assert.assertNull(f2);
	}
	
	

}
