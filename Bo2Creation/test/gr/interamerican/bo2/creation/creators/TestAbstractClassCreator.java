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
package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.samples.bean.BeanWithReadOnlyAndWriteOnly;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.samples.iempty.IEmpty1;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractClassCreator}.
 */
public class TestAbstractClassCreator {
	/**
	 * object to test.
	 */
	AbstractClassCreator creator = new AbstractClassCreator() {
		@Override
		protected String getSuffix() {			
			return "_xyz_suffix"; //$NON-NLS-1$
		}
		@Override
		protected void validatePossibleImplementation() throws ClassCreationException {
			/* empty */
		}
		@Override
		protected void supportType() throws ClassCreationException {
			/* empty */			
		}
	};	
		
	/**
	 * Unit test.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testNames() {
		String name = "com.foo.Bar"; 
		String rt = creator.runTimeClassName(name);
		String ct = creator.compileTimeClassName(rt);
		Assert.assertEquals(name, ct);		
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testImplementMethod() throws ClassCreationException {
		creator.initialize(IBeanWithIdAndName.class);
		Method getBeanName = ReflectionUtils.getMethodByUniqueName("getBeanName", IBeanWithIdAndName.class);
		String code = "public String getBeanName() {return null;}";
		creator.implementMethod(getBeanName, code);		
		Assert.assertEquals(code, creator.methodsAlreadyImplemented.get(getBeanName));
		Assert.assertTrue(creator.methodsToAdd.contains(code));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testIsNotImplementedMethod_withAbstractMethod() throws ClassCreationException {
		Class<?> type = IBeanWithIdAndName.class;
		creator.initialize(type);
		Method getBeanName = ReflectionUtils.getMethodByUniqueName("getBeanName", type);
		Assert.assertTrue(creator.isNotImplementedMethod(getBeanName));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testIsNotImplementedMethod_withAbstractMarkedMethod() throws ClassCreationException {
		Class<?> type = IBeanWithIdAndName.class;
		creator.initialize(type);
		Method getBeanName = ReflectionUtils.getMethodByUniqueName("getBeanName", type);
		creator.implementMethod(getBeanName, "dummy code");	
		Assert.assertFalse(creator.isNotImplementedMethod(getBeanName));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testIsNotImplementedMethod_withConcreteMarkedMethod() 
	throws ClassCreationException {
		Class<?> type = IBeanWithIdAndNameImpl.class;
		creator.initialize(type);
		Method getBeanName = ReflectionUtils.getMethodByUniqueName("getBeanName", type);
		creator.implementMethod(getBeanName, "dummy code");		
		Assert.assertFalse(creator.isNotImplementedMethod(getBeanName));
	}
	
		
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testIsNotImplementedMethod_withConcreteMethod() 
	throws ClassCreationException {
		creator.initialize(String.class);
		Method toString = ReflectionUtils.getMethodByUniqueName("toString", String.class);
		Assert.assertFalse(creator.isNotImplementedMethod(toString));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testIsImplementedMethod_withNull() 
	throws ClassCreationException {
		creator.initialize(String.class);
		Assert.assertFalse(creator.isNotImplementedMethod(null));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testInitialize() 
	throws ClassCreationException {
		creator.initialize(String.class);
		Assert.assertNotNull(creator.analysis);
		Assert.assertTrue(creator.fieldsToAdd.isEmpty());
		Assert.assertTrue(creator.methodsAlreadyImplemented.isEmpty());
		Assert.assertTrue(creator.methodsToAdd.isEmpty());
		Assert.assertTrue(creator.updaters.isEmpty());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testAddFieldCode() throws ClassCreationException {
		creator.initialize(String.class);
		String code = "code"; //$NON-NLS-1$
		creator.addFieldCode(code);
		Assert.assertTrue(creator.fieldsToAdd.contains(code));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testAddMethodCode() throws ClassCreationException {
		creator.initialize(String.class);
		String code = "code"; //$NON-NLS-1$
		creator.addMethodCode(code);
		Assert.assertTrue(creator.methodsToAdd.contains(code));
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testUpdaterForFields() throws ClassCreationException {
		creator.initialize(String.class);
		Assert.assertNull(creator.updaterForFields());
		String code = "code"; //$NON-NLS-1$
		creator.addFieldCode(code);
		Assert.assertNotNull(creator.updaterForFields());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testUpdaterForMethods() throws ClassCreationException {
		creator.initialize(String.class);
		Assert.assertNull(creator.updaterForMethods());
		String code = "code"; //$NON-NLS-1$
		creator.addMethodCode(code);
		Assert.assertNotNull(creator.updaterForMethods());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testGetClassCompiler() throws ClassCreationException {
		creator.initialize(String.class);
		Assert.assertNotNull(creator.getClassCompiler());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testCantImplementAllMethods() throws ClassCreationException {
		creator.initialize(List.class);
		ClassCreationException ex = creator.cantImplementAllMethods
			(creator.analysis.getAbstractMethods());
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testCouldntImplementAllMethods_returnException() throws ClassCreationException {
		creator.initialize(List.class);
		ClassCreationException ex = creator.couldntImplementAllMethods();
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testCouldntImplementAllMethods_returnNull() throws ClassCreationException {
		creator.initialize(IEmpty1.class);
		ClassCreationException ex = creator.couldntImplementAllMethods();
		Assert.assertNull(ex);
	}
	
	/**
	 * Unit test.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testCantSupportOddProperties() throws ClassCreationException {		
		creator.initialize(BeanWithReadOnlyAndWriteOnly.class);
		ClassCreationException ex = creator.cantSupportOddProperties();
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}
	
	/**
	 * Unit test for getMethodsNotYetImplemented.
	 * @throws ClassCreationException 
	 */		
	@Test
	public void testGetMethodsNotYetImplemented() throws ClassCreationException {
		creator.initialize(IBeanWithIdAndName.class);
		Set<Method> notImplemented = creator.getMethodsNotYetImplemented();
		Assert.assertEquals(4, notImplemented.size());
	}
	

}
