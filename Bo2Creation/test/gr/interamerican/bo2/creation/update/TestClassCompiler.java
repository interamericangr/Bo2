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
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AddingFieldsClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestClassCompiler {	
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
	 * code for the constructor.
	 */
	String constructorCode = "field1=new Integer(0);";
	
	
	/**
	 * Unit test for the constructor.
	 * 
	 */	
	@Test
	public void testConstructor_withList()  {		
		List<AbstractClassUpdater> list = new ArrayList<AbstractClassUpdater>();
		ClassCompiler creator = new ClassCompiler(list);
		Assert.assertEquals(list, creator.updaters);
	}
	
	/**
	 * Unit test for the constructor.
	 * 
	 */	
	@Test
	public void testConstructor_withArray()  {
		MakingConcreteClassUpdater u1 = new MakingConcreteClassUpdater();
		MakingConcreteClassUpdater u2 = new MakingConcreteClassUpdater();		
		ClassCompiler creator = new ClassCompiler(u1,u2);
		Assert.assertEquals(2, creator.updaters.size());
		Assert.assertTrue(creator.updaters.contains(u1));
		Assert.assertTrue(creator.updaters.contains(u2));
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
		String newName = "gr.interamerican.synth.ClassCompilerTesterClass";		
		AddingFieldsClassUpdater f = new AddingFieldsClassUpdater(fields);
		AddingMethodsClassUpdater m = new AddingMethodsClassUpdater(methods);		
		ClassCompiler creator = new ClassCompiler(f,m);
		Class<?> newType = creator.createType(newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();		
		Integer val = 42;
		Object[] args = {val};
		ReflectionUtils.invokeMethodByUniqueName(object, "setField1", args);
		Integer actual1 = (Integer) ReflectionUtils.get("field1", object);
		Assert.assertEquals(val, actual1);
		Integer actual2 = ReflectionUtils.invokeMethodByUniqueName(object, "getField1");
		Assert.assertEquals(val, actual2);
		
		/*
		 * Second creation should fetch the existing class.
		 */
		creator.createType(newName);
	}
	
	

}
