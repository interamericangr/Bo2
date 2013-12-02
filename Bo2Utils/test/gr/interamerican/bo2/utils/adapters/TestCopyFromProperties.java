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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.samples.bean.BeanWithBoolean;
import gr.interamerican.bo2.samples.bean.BeanWithDate;
import gr.interamerican.bo2.utils.DateUtils;

import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CopyFromProperties}.
 */
public class TestCopyFromProperties {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Properties p = new Properties();
		CopyFromProperties<Object> copy = new CopyFromProperties<Object>(p);
		Assert.assertEquals(p, copy.properties);
	}
	
	/**
	 * Test for execute()
	 */
	public void testExecute() {
		Properties p = new Properties();
		String field1 = "f1"; //$NON-NLS-1$
		p.setProperty("field1", field1);		 //$NON-NLS-1$
		
		BeanWith2Fields bean = new BeanWith2Fields();
		CopyFromProperties<BeanWith2Fields> copy = new CopyFromProperties<BeanWith2Fields>(p);
		copy.execute(bean);
		Assert.assertEquals(field1, bean.getField1());		
	}
	
	/**
	 * Test for execute()
	 */
	@Test
	public void testExecute_withDates() {
		Properties p = new Properties();
		Date dt = new Date();
		DateUtils.removeTime(dt);
		
		String sdt = DateUtils.formatDateIso(dt);
		p.setProperty("date", sdt);		 //$NON-NLS-1$
		
		BeanWithDate bean = new BeanWithDate();
		CopyFromProperties<BeanWithDate> copy = new CopyFromProperties<BeanWithDate>(p);
		copy.execute(bean);
		Assert.assertEquals(dt, bean.getDate());		
	}
	
	/**
	 * Test for execute()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute_withNumbers() {
		Properties p = new Properties();
		String field1 = "X";
		Integer field2 = 5;
		Double field3 = 15.02;
		
		p.setProperty("field1", field1);
		p.setProperty("field2", field2.toString());
		p.setProperty("field3", field3.toString());
		
		BeanWith3Fields bean = new BeanWith3Fields();
		CopyFromProperties<BeanWith3Fields> copy = new CopyFromProperties<BeanWith3Fields>(p);
		copy.execute(bean);
		Assert.assertEquals(field1, bean.getField1());
		Assert.assertEquals(field2, bean.getField2());
		Assert.assertEquals(field3, bean.getField3());
	}
	
	/**
	 * Test for execute()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testExecute_withBool() {
		Properties p = new Properties();
		p.setProperty("bool", "true");
		p.setProperty("wrappedBool", "1");
		p.setProperty("hacky", "TRUE");
		p.setProperty("isLala", "1");
		
		BeanWithBoolean bean = new BeanWithBoolean();
		
		Assert.assertFalse(bean.isBool());
		Assert.assertFalse(bean.getHacky());
		Assert.assertNull(bean.getWrappedBool());
		Assert.assertFalse(bean.getIsLala());
		
		CopyFromProperties<BeanWithBoolean> copy = new CopyFromProperties<BeanWithBoolean>(p);
		copy.execute(bean);
		
		Assert.assertTrue(bean.isBool());
		Assert.assertTrue(bean.getWrappedBool());
		Assert.assertTrue(bean.getHacky());
		Assert.assertTrue(bean.getIsLala());
				
	}

}
