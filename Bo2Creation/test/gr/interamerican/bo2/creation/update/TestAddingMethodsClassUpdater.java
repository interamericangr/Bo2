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
 * Unit test for {@link AddingMethodsClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestAddingMethodsClassUpdater {	
	/**
	 * code for fields.
	 */	
	String[] code = {
		"public String getSome() {return \"some\";}",
		"public int getOne() {return 1;}",
		"public Integer getArg(Integer arg) {return arg;}"
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
		String newName = "gr.interamerican.synth.AddingMethodsClassUpdaterTesterClass";		
		AddingMethodsClassUpdater updater = new AddingMethodsClassUpdater(code);
		Class<?> newType = updater.update(IEmpty1Impl1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();		
		String some = ReflectionUtils.invokeMethodByUniqueName(object, "getSome");
		Assert.assertEquals("some", some);
		int one = ReflectionUtils.invokeMethodByUniqueName(object, "getOne");
		Assert.assertEquals(1, one);
		Object[] args = {4};
		int arg = ReflectionUtils.invokeMethodByUniqueName(object, "getArg", args);
		Assert.assertEquals(arg, args[0]);
	}
	
	

}
