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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AddingFieldsClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestMultipleClassUpdater {	
	/**
	 * code for fields.
	 */	
	String[] fields = {		
		"public Integer field1;"
	};	
	
	/**
	 * code for fields.
	 */	
	String[] methods = {		
		"public Integer getField1() {return field1;}",
		"public void setField1(Integer field1) {this.field1=field1;}"
			
	};
	
	
	/**
	 * Unit test for the constructor.
	 * 
	 */	
	@Test
	public void testConstructor_withList()  {		
		List<AbstractClassUpdater> list = new ArrayList<AbstractClassUpdater>();
		MultipleClassUpdater updater = new MultipleClassUpdater(list);
		Assert.assertEquals(list, updater.updaters);
	}
	
	/**
	 * Unit test for the constructor.
	 * 
	 */	
	@Test
	public void testConstructor_withArray()  {
		MakingConcreteClassUpdater u1 = new MakingConcreteClassUpdater();
		MakingConcreteClassUpdater u2 = new MakingConcreteClassUpdater();		
		MultipleClassUpdater updater = new MultipleClassUpdater(u1,u2);
		Assert.assertEquals(2, updater.updaters.size());
		Assert.assertTrue(updater.updaters.contains(u1));
		Assert.assertTrue(updater.updaters.contains(u2));
	}


	
	
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
		String newName = "gr.interamerican.synth.MultipleClassUpdaterTesterClass";		
		AddingFieldsClassUpdater f = new AddingFieldsClassUpdater(fields);
		AddingMethodsClassUpdater m = new AddingMethodsClassUpdater(methods);
		MultipleClassUpdater updater = new MultipleClassUpdater(f,m);		
		Class<?> newType = updater.update(IEmpty1Impl1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();		
		Integer val = 42;
		Object[] args = {val};
		ReflectionUtils.invokeMethodByUniqueName(object, "setField1", args);
		Integer actual1 = (Integer) ReflectionUtils.get("field1", object);
		Assert.assertEquals(val, actual1);
		Integer actual2 = ReflectionUtils.invokeMethodByUniqueName(object, "getField1");
		Assert.assertEquals(val, actual2);
	}
	
	

}
