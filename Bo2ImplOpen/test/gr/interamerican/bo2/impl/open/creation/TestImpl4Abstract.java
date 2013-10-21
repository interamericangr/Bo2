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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.creation.creators.BaseTestForAbstractClassesImplementors;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.samples.enums.Sex;
import gr.interamerican.bo2.test.impl.posamples.InvoiceCustomerImpl;
import gr.interamerican.bo2.test.impl.posamples.InvoiceImpl;
import gr.interamerican.bo2.test.impl.samples.AbstractTs;
import gr.interamerican.bo2.test.impl.samples.AbstractTsWithWrongAnno;
import gr.interamerican.bo2.test.impl.samples.SamplePoForTestImpl;
import gr.interamerican.bo2.test.impl.samples.SamplePoImpl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Impl4Abstract}.
 */
public class TestImpl4Abstract extends BaseTestForAbstractClassesImplementors {
	

	/**
	 * Object to test.
	 * Must override the suffix, so the classes it creates don't mess
	 * with classes created by other tests.
	 */
	static Impl4Abstract impl = new Impl4Abstract() {
		@Override
		protected String getSuffix() {
			return "_forTestOfImpl4Abstract";		 //$NON-NLS-1$
		}
	};
	
	/**
	 * Creates a new TestImpl4Abstract object. 
	 */
	public TestImpl4Abstract() {
		super(impl);
	}
	
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_AbstractModificationRecordPo() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		String invoiceNo = "invoiceNo"; //$NON-NLS-1$
		Class<?> invoiceClass = creator.create(InvoiceImpl.class);
		InvoiceImpl po =  (InvoiceImpl) invoiceClass.newInstance();
		Assert.assertNotNull(po);
		InvoiceKey key = po.getKey();
		Assert.assertNotNull(key);		
		key.setInvoiceNo(invoiceNo);
		Assert.assertEquals(invoiceNo, po.getInvoiceNo());
		
		Set<InvoiceLine> lines = po.getLines();
		Assert.assertNotNull(lines);
		Assert.assertTrue(lines instanceof HashSet);
		
	}
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_AbstractModificationRecordPo_2() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> invoiceCustomerClass = creator.create(InvoiceCustomerImpl.class);
		InvoiceCustomerImpl po =  (InvoiceCustomerImpl) invoiceCustomerClass.newInstance();
		Assert.assertNotNull(po);
		InvoiceKey key = po.getKey();
		Assert.assertNotNull(key);		
	}

	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_PoIsTs() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {
		Class<?> poclass = creator.create(SamplePoImpl.class);
		SamplePoImpl po =  (SamplePoImpl) poclass.newInstance();
		Assert.assertNotNull(po);
		
		
		//test createKey (@KeyProperties)
		Integer id = 1;
		String name = "name"; //$NON-NLS-1$
		po.getKey().setId(id);
		po.getKey().setName(name);
		assertEquals(po.getKey().getId(), id);
		assertEquals(po.getKey().getName(), name);
		
		//test updatePo (@DelegateKeyProperties)
		po.setId(id);
		po.setName(name);
		assertEquals(po.getId(), id);
		assertEquals(po.getName(), name);
		
		//test updateType
		
		//@Property
		Long serialNo = 1L;
		po.setSerialNo(serialNo);
		assertEquals(po.getSerialNo(), serialNo);
		Long[] longs = {1L,2L};
		po.setLongs(longs);
		assertArrayEquals(longs, po.getLongs());
		byte[] bytes = {'c'};
		po.setBytes(bytes);
		assertArrayEquals(bytes, po.getBytes());
		int i=100;
		po.setI(i);
		assertEquals(po.getI(), i);
		
		//@DelegateProperties
		po.setBeanId(id);
		po.setBeanName(name);
		assertEquals(po.getBeanId(), id);
		assertEquals(po.getBeanName(), name);

		//@DelegateMethods
		assertEquals(po.add(1, 1), new Integer(2));
		assertEquals(po.subtract(1, 1), new Integer(0));
		
		//@TypedSelectableProperties
		serialNo = 2L;
		po.setCode(serialNo);
		assertEquals(po.getSerialNo(), po.getCode());
		assertEquals(po.getCode(), serialNo);
		
		//enum
		po.setSex(Sex.FEMALE);
		assertEquals(po.getSex(), Sex.FEMALE);
		
		po.setPicture(bytes);
		assertArrayEquals(bytes, po.getBytes());
		
		Integer[] integers = {1,2};
		po.setMeasurements(integers);
		assertArrayEquals(integers, po.getMeasurements());
	}
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_PoIsTs2() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {	
		Class<?> poclass = creator.create(SamplePoForTestImpl.class);
		SamplePoForTestImpl po =  (SamplePoForTestImpl) poclass.newInstance();
		Assert.assertNotNull(po);
	}
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testCreate_WithTs() 
	throws ClassCreationException, InstantiationException, IllegalAccessException {	
		Class<?> poclass = creator.create(AbstractTs.class);
		AbstractTs po =  (AbstractTs) poclass.newInstance();
		Assert.assertNotNull(po);
		po.setId(5);
		Assert.assertEquals(Integer.valueOf(5), po.getId());
		Assert.assertEquals(Integer.valueOf(5), po.getBeanId());
		Assert.assertEquals(Integer.valueOf(5), po.getCode());
		
		String name = "name"; //$NON-NLS-1$
		po.setName(name);
		Assert.assertEquals(name, po.getName());
		Assert.assertEquals(name, po.getBeanName());
		
		po.setName(null);
		po.setBeanName(name);
		Assert.assertEquals(name, po.getName());
		Assert.assertEquals(name, po.getBeanName());
		
		po.setTypeId(10L);
		Assert.assertEquals(Long.valueOf(10), po.getTypeId());
		
		po.setSubTypeId(100L);
		Assert.assertNull(po.getSubTypeId());
	}
	
	/**
	 * Test for create.
	 * 
	 * @throws ClassCreationException
	 */
	@Test(expected=ClassCreationException.class)
	public void testCreate_WithWrongTs() 
	throws ClassCreationException {	
		creator.create(AbstractTsWithWrongAnno.class);
	}
	

}
