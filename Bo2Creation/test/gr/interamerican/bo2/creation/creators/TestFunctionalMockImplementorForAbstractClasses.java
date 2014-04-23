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
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link FunctionalMockImplementorForAbstractClasses}.
 */
public class TestFunctionalMockImplementorForAbstractClasses 
extends BaseTestForAbstractClassesImplementors {

	/**
	 * Creates a new TestFunctionalMockImplementorForAbstractClasses object. 
	 */
	public TestFunctionalMockImplementorForAbstractClasses() {
		super(new FunctionalMockImplementorForAbstractClasses());
	}
	
	/**
	 * Unit test for create on abstract class that has fields to support
	 * the properties, but they are not annotated as Property.
	 * 
	 * @throws ClassCreationException 
	 */
	@Override
	@Test()
	public void testCreate_notAllFieldsMarkedAsProperty() 
	throws ClassCreationException {
		Class<?> clazz = creator.create(AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated.class);
		
		try {
			AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated bean = 
				(AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated) 
				clazz.newInstance();
			bean.setBeanId(5);
			bean.getBeanId();
			bean.getBeanName();
			bean.setBeanName("foo");			
		} catch (InstantiationException e) {
			Assert.fail(e.toString());
		} catch (IllegalAccessException e) {
			Assert.fail(e.toString());
		}
		
	}
	
	

}
