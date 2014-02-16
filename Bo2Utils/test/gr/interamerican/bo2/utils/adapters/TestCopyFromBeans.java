package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.samples.bean.BeanWithDate;
import gr.interamerican.bo2.samples.bean.BeanWithString;
import gr.interamerican.bo2.samples.bean.BeanWithStringAndDate;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CopyFromBeans}.
 */
public class TestCopyFromBeans {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		BeanWithString bean1 = new BeanWithString();
		BeanWithDate bean2 = new BeanWithDate();
		Object[] objects = {bean1, bean2};
		CopyFromBeans<BeanWithStringAndDate> copy = 
			new CopyFromBeans<BeanWithStringAndDate>(objects);
		Assert.assertArrayEquals(objects, copy.beans);
	}
	
	/**
	 * Tests execute(o).
	 */
	@Test
	public void testExecute() {
		String string = "s"; //$NON-NLS-1$
		Date date = new Date();
		BeanWithString bean1 = new BeanWithString();
		bean1.setString(string);
		BeanWithDate bean2 = new BeanWithDate();
		bean2.setDate(date);
		CopyFromBeans<BeanWithStringAndDate> copy = 
			new CopyFromBeans<BeanWithStringAndDate>(bean1, bean2);
		
		BeanWithStringAndDate bean = new BeanWithStringAndDate();
		copy.execute(bean);
		Assert.assertEquals(string, bean.getString());
		Assert.assertEquals(date, bean.getDate());
		
	}

}
