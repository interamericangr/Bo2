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
package gr.interamerican.bo2.impl.open.creation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.samples.almostEmpty.AlmostEmpty1;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Unit tests for {@link Factory}.
 */
public class TestFactory {
	
	/**
	 * unit test for nullSafe()
	 */
	@Test
	public void testNullSafe() {
		User user = null;
		user = Factory.nullSafe(user, User.class);
		assertNotNull(user);
		User us1 = user;
		user = Factory.nullSafe(user, User.class);
		assertSame(us1, user);
	}
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testCreate() {
		User user = Factory.create(User.class);
		assertNotNull(user);
	}
	
	/**
	 * Unit test for create throwing exception.
	 */
	@Test(expected=RuntimeException.class)
	public void testCreate_throwing() {
		Factory.create(AlmostEmpty1.class);
	}
	
	/**
	 * Unit test for getDetachStrategy.
	 */
	@Test
	public void testGetDefaultDetachStrategy() {
		DetachStrategy strategy = 
			Factory.getDefaultDetachStrategy(User.class);
		assertNotNull(strategy);
		strategy = Factory.getDefaultDetachStrategy(AlmostEmpty1.class);
		assertNull(strategy);
	}
	
	/**
	 * Tests compileJavaBean
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCompileJavaBean() throws SecurityException, NoSuchFieldException {
		VariableDefinition<?>[] properties = {
				new VariableDefinition<String>("name", String.class)
		};
		String className = "gr.interamerican.bo2.testpack.MySyntheticClass";
		Class<?> newType = Factory.compileJavaBean(className, properties);
		assertNotNull(newType);
		assertEquals(className, newType.getName());
		Field field = newType.getDeclaredField("name");
		assertNotNull(field);
		assertEquals(String.class, field.getType());		
		Object ob = Factory.create(className);
		assertNotNull(ob);
	}

}
