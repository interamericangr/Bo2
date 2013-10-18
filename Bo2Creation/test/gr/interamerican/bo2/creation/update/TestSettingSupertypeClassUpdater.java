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
import gr.interamerican.bo2.utils.beans.PropertiesInitializedBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link SettingSuperTypeClassUpdater}.
 */
@SuppressWarnings("nls")
public class TestSettingSupertypeClassUpdater {	
	
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
		String newName = "gr.interamerican.synth.SettingSupertypeClassUpdaterTesterClass1";	
		SettingSuperTypeClassUpdater updater = 
			new SettingSuperTypeClassUpdater(BeanWith1Field.class.getCanonicalName());
		Class<?> newType = updater.update(Empty1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Object object = newType.newInstance();
		Assert.assertTrue(object instanceof BeanWith1Field);
	}
	
	/**
	 * Unit test for update.
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassCreationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */	
	@Test
	public void testUpdate_classHasNoDefaultConstructor() 
	throws InstantiationException, IllegalAccessException, 
	ClassCreationException, SecurityException, NoSuchMethodException, 
	IllegalArgumentException, InvocationTargetException {
		String newName = "gr.interamerican.synth.SettingSupertypeClassUpdaterTesterClass2";	
		SettingSuperTypeClassUpdater updater = 
			new SettingSuperTypeClassUpdater(PropertiesInitializedBean.class.getCanonicalName());
		Class<?> newType = updater.update(Empty1.class, newName);
		Assert.assertEquals(newName, newType.getName());
		Constructor<?> constructor = newType.getConstructor(Properties.class);
		Assert.assertNotNull(constructor);
		Properties p = new Properties();
		Object object = constructor.newInstance(p);
		Assert.assertTrue(object instanceof PropertiesInitializedBean);
	}
	
	
	

}
