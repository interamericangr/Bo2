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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.creation.ObjectFactory;
import gr.interamerican.bo2.creation.beans.FunctionalMocksObjectFactoryImpl;
import gr.interamerican.bo2.samples.almostEmpty.AlmostEmpty1;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.ibean.IBeanWith2Strings;
import gr.interamerican.bo2.samples.interfaces.SmartCalc;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;

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
	 * Tests compileJavaBean().
	 * 
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
		
		Class<?> secondCall = Factory.compileJavaBean(className, properties);
		assertSame(newType, secondCall);
	}
	
	/**
	 * Test for setCurrentFactory(of).
	 */
	@Test
	public void testSetCurrentFactory() {
		ObjectFactory factory = Factory.currentFactory;		
		ObjectFactory of = Mockito.mock(ObjectFactory.class);
		Factory.setCurrentFactory(of);
		assertEquals(of, Factory.currentFactory);
		Factory.currentFactory = factory;
	}
	
	/**
	 * Test for getCurrentFactory().
	 */
	@Test
	public void testGetCurrentFactory() {
		assertEquals(Factory.currentFactory, Factory.getCurrentFactory());
	}
	
	/**
	 * Test for registerFixture(clazz,factory).
	 */
	@Test
	public void testRegisterFixture_withFactory() {
		ObjectFactory of = new FunctionalMocksObjectFactoryImpl();
		Factory.registerFixture(SmartCalc.class, of);
		SmartCalc calc = Factory.create(SmartCalc.class);
		assertNotNull(calc);
		String name = "calc"; //$NON-NLS-1$
		calc.setBeanName(name);
		assertEquals(name, calc.getBeanName());
		Factory.resetFixtures();
	}
	
	/**
	 * Test for registerFixture(clazz,instance)
	 */
	@Test
	public void testRegisterFixture_withInstance() {
		SmartCalc calc = Mockito.mock(SmartCalc.class);
		Factory.registerFixture(SmartCalc.class, calc);
		SmartCalc[] actuals = {
				Factory.create(SmartCalc.class),
				Factory.create(SmartCalc.class),
				Factory.create(SmartCalc.class)
		};
		for (int i = 0; i < actuals.length; i++) {
			assertEquals(calc, actuals[i]);	
		}
		Factory.resetFixtures();		
	}
	
	/**
	 * Test for resetFixtures()
	 */
	@Test
	public void testResetFixtures() {
		IBeanWith2Strings mock = Mockito.mock(IBeanWith2Strings.class);
		Factory.registerFixture(IBeanWith2Strings.class, mock);
		IBeanWith2Strings created = Factory.create(IBeanWith2Strings.class);		
		assertEquals(mock, created);
		Factory.resetFixtures();
		IBeanWith2Strings bean = Factory.create(IBeanWith2Strings.class);
		assertNotSame(mock, bean);
	}
	
	/**
	 * Test for getType(c)
	 */
	@Test
	public void testGetType() {
		Class<?> clazz = Factory.getType(IBeanWith2Strings.class.getName());
		assertNotNull(clazz);
	}
	
	/**
	 * Test for declarationTypeName(c)
	 */
	@Test
	public void testDeclarationTypeName() {
		String name = Factory.declarationTypeName(IBeanWith2Strings.class);
		assertEquals(IBeanWith2Strings.class.getName(),name);
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
	

}
