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
package gr.interamerican.bo2.impl.open.creation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Tests the creation of Throwable sub-classes
 */
public class ThrowableCreationTest extends AbstractTestClass {
	
	
	@Override
	public void testClass(Class<?> type) {
		@SuppressWarnings("unchecked")
		Class<? extends Throwable> throwableType = 
			(Class<? extends Throwable>) type;		
		try {
			
			testStringArgConstructor(throwableType);
			testNoArgConstructor(throwableType);
			testThrowableArgConstructor(throwableType);
			testStringThrowableArgConstructor(throwableType);

		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);			
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Creates a new ThrowableTester object. 
	 *
	 * @param className
	 * @throws ClassNotFoundException
	 */
	public ThrowableCreationTest(String className) throws ClassNotFoundException {
		super(className);		
	}

	/**
	 * Tests the no argument constructor.
	 * 
	 * @param <T>
	 * @param throwableType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private <T extends Throwable> void testNoArgConstructor(Class<T> throwableType) 
	throws InstantiationException, IllegalAccessException, InvocationTargetException {
		try {
			Class<?>[] parametersTypes = {};
			Constructor<T> noArgConstructor = throwableType.getConstructor(parametersTypes);
			Object[] parameters = {};
			T t = noArgConstructor.newInstance(parameters);
			assertNotNull(t);
		} catch (SecurityException se) {
			/* ignore this Exception */
		} catch (NoSuchMethodException nsme) {
			/*
			 * this constructor does not exist, so it is no problem.
			 */		 
		}		
	}
	
	/**
	 * Tests the constructor that takes a Throwable as argument.
	 * 
	 * @param <T>
	 * @param throwableType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private <T extends Throwable> void testThrowableArgConstructor(Class<T> throwableType) 
	throws InstantiationException, IllegalAccessException, InvocationTargetException {
		try {
			Class<?>[] parametersTypes = {Throwable.class};
			Constructor<T> noArgConstructor = throwableType.getConstructor(parametersTypes);
			Throwable cause = new Throwable();
			Object[] parameters = {cause};
			T t = noArgConstructor.newInstance(parameters);
			assertNotNull(t);
			assertSame(cause, t.getCause());
		} catch (SecurityException se) {
			/* ignore this Exception */
		} catch (NoSuchMethodException nsme) {
			/*
			 * this constructor does not exist, so it is no problem.
			 */		 
		}		
	}
	
	/**
	 * Tests the constructor that takes a String and a Throwable as arguments.
	 * 
	 * @param <T>
	 * @param throwableType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private <T extends Throwable> void testStringThrowableArgConstructor(Class<T> throwableType) 
	throws InstantiationException, IllegalAccessException, InvocationTargetException {
		try {
			Class<?>[] parametersTypes = {String.class, Throwable.class};
			Constructor<T> noArgConstructor = throwableType.getConstructor(parametersTypes);
			Throwable cause = new Throwable();
			String message = "message"; //$NON-NLS-1$
			Object[] parameters = {message,cause};
			T t = noArgConstructor.newInstance(parameters);
			assertNotNull(t);
			assertSame(cause, t.getCause());
			assertEquals(message, t.getMessage());
		} catch (SecurityException se) {
			/* ignore this Exception */
		} catch (NoSuchMethodException nsme) {
			/*
			 * this constructor does not exist, so it is no problem.
			 */		 
		}		
	}
	
	/**
	 * Tests the constructor that takes a String and a Throwable as arguments.
	 * 
	 * @param <T>
	 * @param throwableType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private <T extends Throwable> void testStringArgConstructor(Class<T> throwableType) 
	throws InstantiationException, IllegalAccessException, InvocationTargetException {
		try {
			Class<?>[] parametersTypes = {String.class};
			Constructor<T> noArgConstructor = throwableType.getConstructor(parametersTypes);
			String message = "message"; //$NON-NLS-1$
			Object[] parameters = {message};
			T t = noArgConstructor.newInstance(parameters);
			assertNotNull(t);			
			assertEquals(message, t.getMessage());
		} catch (SecurityException se) {
			/* ignore this Exception */
		} catch (NoSuchMethodException nsme) {
			/*
			 * this constructor does not exist, so it is no problem.
			 */		 
		}		
	}
	
}
