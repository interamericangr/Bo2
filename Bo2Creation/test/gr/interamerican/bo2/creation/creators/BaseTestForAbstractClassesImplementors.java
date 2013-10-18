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

import gr.interamerican.bo2.creation.annotations.DelegateMethods;
import gr.interamerican.bo2.creation.annotations.DelegateProperties;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanUsingDelegation;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanUsingMaps;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWith2StringsIdAndName;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWith2StringsIdAndNameAndMocks;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithIdAndName;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithIdAndNameHavingNameField;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithMockMethodsAndProperties;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithMockProperties;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithPropertyOnStaticField;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithWrongDelegateProperty;
import gr.interamerican.bo2.samples.abstractimpl.AbstractBeanWithWrongDelegateToOtherAnno;
import gr.interamerican.bo2.samples.abstractimpl.AbstractClassWithOddProperties;
import gr.interamerican.bo2.samples.abstractimpl.AbstractComparableBeanWithIdAndName;
import gr.interamerican.bo2.samples.abstractimpl.AbstractSmartCalcImpl;
import gr.interamerican.bo2.samples.abstractimpl.MyCriteriaKeeper;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Base unit tests for {@link ImplementorForAbstractClasses} and
 * sub-types.
 */
public class BaseTestForAbstractClassesImplementors {
	/**
	 * object to test.
	 */
	protected ImplementorForAbstractClasses creator;
	
	
	
	/**
	 * Creates a new BaseTestForAbstractClassesImplementors object. 
	 *
	 * @param creator
	 */
	public BaseTestForAbstractClassesImplementors(ImplementorForAbstractClasses creator) {
		super();
		this.creator = creator;
	}

	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_onlyWithFields() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanWithIdAndName.class);
		AbstractBeanWithIdAndName bean = (AbstractBeanWithIdAndName) clazz.newInstance();
		Integer id = 5;
		bean.setBeanId(id);
		Assert.assertEquals(id, bean.getBeanId());
		String name = "name"; //$NON-NLS-1$
		bean.setBeanName(name);
		Assert.assertEquals(name, bean.getBeanName());
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_withPropertyAndDelegateProperties() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanUsingDelegation.class);
		AbstractBeanUsingDelegation bean = 
			(AbstractBeanUsingDelegation) clazz.newInstance();
		
		Integer id = 5;
		bean.setBeanId(id);
		Assert.assertEquals(id, bean.getBeanId());
		String name = "name"; //$NON-NLS-1$
		bean.setBeanName(name);
		Assert.assertEquals(name, bean.getBeanName());
		
		String string1 = "string1";
		bean.setString1(string1);
		Assert.assertEquals(string1, bean.getString1());
		
		String string2 = "string2";
		bean.setString2(string2);
		Assert.assertEquals(string2, bean.getString2());

	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_withPropertyAndDelegate2Map() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanUsingMaps.class);
		AbstractBeanUsingMaps bean = 
			(AbstractBeanUsingMaps) clazz.newInstance();
		
		Integer id = 5;
		bean.setBeanId(id);
		Assert.assertEquals(id, bean.getBeanId());
		String name = "name"; //$NON-NLS-1$
		bean.setBeanName(name);
		Assert.assertEquals(name, bean.getBeanName());
		
		String string1 = "string1";
		bean.setString1(string1);
		Assert.assertEquals(string1, bean.getString1());
		
		String string2 = "string2";
		bean.setString2(string2);
		Assert.assertEquals(string2, bean.getString2());

	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_withMockProperties() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanWithMockProperties.class);
		AbstractBeanWithMockProperties bean = 
			(AbstractBeanWithMockProperties) clazz.newInstance();
		
		bean.setBeanId(5);
		Assert.assertNull(bean.getBeanId());
				
		bean.setBeanName("X");
		Assert.assertNull(bean.getBeanName());
		
		String string1 = "string1";
		bean.setString1(string1);
		Assert.assertEquals(string1, bean.getString1());
		
		String string2 = "string2";
		bean.setString2(string2);
		Assert.assertEquals(string2, bean.getString2());

	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_withMockPropertiesAndMethods() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanWithMockMethodsAndProperties.class);
		AbstractBeanWithMockMethodsAndProperties bean = 
			(AbstractBeanWithMockMethodsAndProperties) clazz.newInstance();
		
		bean.setBeanId(5);
		Assert.assertNull(bean.getBeanId());
				
		bean.setBeanName("X");
		Assert.assertNull(bean.getBeanName());
		/*
		bean.add(null);
		Assert.assertNull(bean.getResult());
		
		bean.reset();*/

	}
	
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_withPropertyOnStaticField() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanWithPropertyOnStaticField.class);
		AbstractBeanWithPropertyOnStaticField bean = 
			(AbstractBeanWithPropertyOnStaticField) clazz.newInstance();
		Integer id = AbstractBeanWithPropertyOnStaticField.id;
		Assert.assertEquals(id, bean.getId());
		id = 10;
		bean.setId(id);
		Assert.assertEquals(id, bean.getId());
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_onlyWithFieldsAndInheritance() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractBeanWithIdAndNameHavingNameField.class);
		AbstractBeanWithIdAndNameHavingNameField bean = 
			(AbstractBeanWithIdAndNameHavingNameField) clazz.newInstance();
		Integer id = 5;
		bean.setBeanId(id);		
		Assert.assertEquals(id, bean.getBeanId());
		String name = "name"; //$NON-NLS-1$
		bean.setBeanName(name);
		Assert.assertEquals(name, bean.getBeanName());
	}
	
	/**
	 * Unit test for create on abstract class that has fields to support
	 * the properties, but they are not annotated as Property.
	 * 
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_notAllFieldsMarkedAsProperty() 
	throws ClassCreationException {
		creator.create(AbstractBeanWithIdAndNameHavingNameFieldNotAnnotated.class);		
	}
	
	/**
	 * Unit test for create on abstract class with odd properties.
	 * 
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withOddProperties() 
	throws ClassCreationException {
		creator.create(AbstractClassWithOddProperties.class);		
	}
	
	/**
	 * Unit test for create with interface.
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withInvalidType() 
	throws ClassCreationException {
		creator.create(List.class);		
	}
	
	/**
	 * Unit test for create with annotation that has wrong delegate
	 * properties annotation.
	 * 
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withWrongDelegatePropertiesAnno() 
	throws ClassCreationException {
		creator.create(AbstractBeanWithWrongDelegateProperty.class);		
	}
		
	/**
	 * Unit test for wrongDelegate.
	 */
	@Test()
	public void testWrongDelegate() {
		@SuppressWarnings("nls")
		ClassCreationException ex = creator.wrongDelegateToField("f", "property", "x");
		Assert.assertNotNull(ex);
		Assert.assertNotNull(ex.getMessage());
	}
	
	/**
	 * Unit test for create that includes {@link Property}, {@link DelegateProperties}
	 * and {@link DelegateMethods} annotations.
	 * 
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate_withAll() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(AbstractSmartCalcImpl.class);
		creator.logMethods();
		AbstractSmartCalcImpl calc = (AbstractSmartCalcImpl) clazz.newInstance();
		calc.setBeanId(1);
		Assert.assertEquals(Integer.valueOf(1), calc.getBeanId());
		calc.setBeanName("name");
		Assert.assertEquals("name", calc.getBeanName());
		calc.reset();
		Assert.assertEquals(BigDecimal.ZERO.setScale(2), calc.getResult());
		calc.add(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN.setScale(2), calc.getResult());
		calc.reset();
		Assert.assertEquals(BigDecimal.ZERO.setScale(2), calc.getResult());
		
		calc.setLeft(BigDecimal.ZERO);
		calc.setRight(BigDecimal.TEN);
				
		BigDecimal left = calc.getLeft();
		BigDecimal right = calc.getRight();
		
		Assert.assertEquals(BigDecimal.ZERO, left);		
		Assert.assertEquals(BigDecimal.TEN, right);
		
		Assert.assertTrue(calc.contains(BigDecimal.ONE));		
	}
	
	
	/**
	 * Unit test for wrongDelegate.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	@Test()
	public void testCreate_withComparable() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		
		Class<AbstractComparableBeanWithIdAndName> type1 = 
			(Class<AbstractComparableBeanWithIdAndName>) 
			creator.create(AbstractComparableBeanWithIdAndName.class);
		AbstractComparableBeanWithIdAndName bean1 = type1.newInstance();
		AbstractComparableBeanWithIdAndName bean2 = type1.newInstance();
		
		bean1.setBeanId(10);
		bean2.setBeanId(5);
		Assert.assertTrue(bean1.compareTo(bean2)>0);
	}
	
	/**
	 * Unit test for wrongDelegate.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test()
	public void testCreate_withPropertiesSupportedByOthers() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		
		Class<AbstractBeanWith2StringsIdAndName> type1 = 
			(Class<AbstractBeanWith2StringsIdAndName>) 
			creator.create(AbstractBeanWith2StringsIdAndName.class);
		AbstractBeanWith2StringsIdAndName bean1 = type1.newInstance();
		
		String string2 = "string2";
		bean1.setString2(string2);
		Assert.assertEquals(string2, bean1.getString2());
		
		bean1.setId(5);
		Assert.assertEquals(Integer.valueOf(5), bean1.getId());
		Assert.assertEquals(Integer.valueOf(5), bean1.getId());
		
		String name = "name";
		bean1.setString1(name);
		Assert.assertEquals(name, bean1.getBeanName());
		Assert.assertEquals(name, bean1.getString1());
	}
	
	/**
	 * Unit test for wrongDelegate.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test()
	public void testCreate_withPropertiesSupportedByOthersAndMocks() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		
		Class<AbstractBeanWith2StringsIdAndNameAndMocks> type1 = 
			(Class<AbstractBeanWith2StringsIdAndNameAndMocks>) 
			creator.create(AbstractBeanWith2StringsIdAndNameAndMocks.class);
		AbstractBeanWith2StringsIdAndNameAndMocks bean1 = type1.newInstance();
		
		bean1.setString2("not null");
		Assert.assertNull(bean1.getString2());
		
		bean1.setId(5);
		Assert.assertEquals(Integer.valueOf(5), bean1.getId());
		Assert.assertEquals(Integer.valueOf(5), bean1.getId());
		
		String name = "name";
		bean1.setString1(name);
		Assert.assertEquals(name, bean1.getBeanName());
		Assert.assertEquals(name, bean1.getString1());
	}
	
	/**
	 * Unit test for wrongDelegate.
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withWrongDelegateToOtherAnno() 
	throws ClassCreationException {
		creator.create(AbstractBeanWithWrongDelegateToOtherAnno.class);
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test()
	public void testCreate_withGenericInterface() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {		
		Class<?> type = creator.create(MyCriteriaKeeper.class);
		@SuppressWarnings("unchecked")
		Class<MyCriteriaKeeper> clazz = (Class<MyCriteriaKeeper>) type;
		MyCriteriaKeeper keeper = clazz.newInstance();
		keeper.setCriteria(5);
		Assert.assertEquals(Integer.valueOf(5), keeper.getCriteria());
	}
	
	
	
	
}
