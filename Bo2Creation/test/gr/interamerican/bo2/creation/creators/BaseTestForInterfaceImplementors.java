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
import gr.interamerican.bo2.samples.abstractimpl.ICriteriaDependent2;
import gr.interamerican.bo2.samples.abstractimpl.ICriteriaDependent3;
import gr.interamerican.bo2.samples.bean.IBeanWithIdAndNameImpl;
import gr.interamerican.bo2.samples.ibean.IBeanWith2Strings;
import gr.interamerican.bo2.samples.ibean.IBeanWithAllTypesOfPropertyNames;
import gr.interamerican.bo2.samples.ibean.IBeanWithIdAndName;
import gr.interamerican.bo2.samples.ibean.IBeanWithOddProperties;
import gr.interamerican.bo2.utils.DateUtils;

import java.io.Serializable;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CreatorForConcreteClasses}.
 */
public class BaseTestForInterfaceImplementors {
	/**
	 * object to test.
	 */
	protected ImplementorForInterfaces creator;
	
	
	
	/**
	 * Creates a new BaseTestForInterfaceImplementors object. 
	 *
	 * @param creator
	 */
	public BaseTestForInterfaceImplementors(ImplementorForInterfaces creator) {
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
	public void testCreate() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> clazz = creator.create(IBeanWithIdAndName.class);
		IBeanWithIdAndName bean = (IBeanWithIdAndName) clazz.newInstance();
		Integer id = 5;
		bean.setBeanId(id);
		Assert.assertEquals(id, bean.getBeanId());
		String name = "name"; //$NON-NLS-1$
		bean.setBeanName(name);
		Assert.assertEquals(name, bean.getBeanName());
		
		Assert.assertTrue(Serializable.class.isAssignableFrom(clazz)); 
	}
	
	/**
	 * Unit test for create.
	 * 
	 * @throws ClassCreationException 
	 */
	@Test
	public void testCreate_withInterfacethatIsNotSerializable() 
	throws ClassCreationException {
		Class<?> clazz = creator.create(IBeanWith2Strings.class);
		Assert.assertTrue(Serializable.class.isAssignableFrom(clazz)); 
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_withAllTypesOfPropertyNames() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> type = creator.create(IBeanWithAllTypesOfPropertyNames.class); 
		@SuppressWarnings("unchecked")		
		Class<IBeanWithAllTypesOfPropertyNames> clazz = (Class<IBeanWithAllTypesOfPropertyNames>) type;
			
		Assert.assertNotNull(clazz);
		IBeanWithAllTypesOfPropertyNames bean = clazz.newInstance();
		Object o = new Object();
		bean.setObject(o);
		Assert.assertEquals(o, bean.getObject());
		bean.setEnabled(true);
		Assert.assertTrue(bean.getEnabled());
		bean.setOld(true);
		Assert.assertTrue(bean.isOld());
		bean.setFine(true);
		Assert.assertTrue(bean.getFine());
		
	}
	
	/**
	 * Unit test for create.
	 * 
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withOddProperties() 
	throws ClassCreationException {
		creator.create(IBeanWithOddProperties.class);		
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withMethods() 
	throws ClassCreationException {
		creator.create(List.class);		
	}
	
	/**
	 * Unit test for create.
	 * @throws ClassCreationException 
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_withInvalidType() 
	throws ClassCreationException {
		creator.create(IBeanWithIdAndNameImpl.class);		
	}
	
	/**
	 * Unit test for create for covariant odd properties
	 *
	 * TODO: this is not yet supported correctly.
	 * 
	 * @throws ClassCreationException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	//@Test
	public void testCreate_withOddProperties_covariantGetter()
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> type = creator.create(ICriteriaDependent3.class);
		ICriteriaDependent3 impl = (ICriteriaDependent3) type.newInstance();
		impl.setCriteria(DateUtils.today());
		Assert.assertEquals(DateUtils.today(), impl.getCriteria());
		
		type = creator.create(ICriteriaDependent2.class);
		ICriteriaDependent2 impl2 = (ICriteriaDependent2) type.newInstance();
		impl2.setCriteria(DateUtils.today());
		Assert.assertEquals(DateUtils.today(), impl2.getCriteria());
	}
	
	
}
