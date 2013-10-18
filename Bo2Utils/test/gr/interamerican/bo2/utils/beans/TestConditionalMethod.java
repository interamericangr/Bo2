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
package gr.interamerican.bo2.utils.beans;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.IsNull;
import gr.interamerican.bo2.utils.conditions.PropertyEqualsTo;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ConditionalMethod}.
 */
public class TestConditionalMethod {
	/**
	 * Constant.
	 */
	private static final String ONE = "ONE"; //$NON-NLS-1$
	
	/**
	 * Indicator that field1 Is One.
	 */
	private boolean field1IsOneIndicator = false;
	
	/**
	 * Indicator that field1 Is One.
	 */
	private boolean field2IsZero = false;
	
	/**
	 * Indicator that field1 Is Null.
	 */
	private boolean field1IsNull = false;
	
	/**
	 * String variable.
	 */
	private String string = null;
	
	/**
	 * Sets field1IsOneIndicator to true.
	 */
	@SuppressWarnings("unused")
	private void setField1IsOneTrue() {
		field1IsOneIndicator = true;
	}
	
	/**
	 * Sets field1IsOneIndicator to true.
	 */
	@SuppressWarnings("unused")
	private void setField2IsZeroTrue() {
		field2IsZero = true;
	}
	
	/**
	 * Sets field1IsOneIndicator to true.
	 */
	@SuppressWarnings("unused")
	private void setField1IsNull(BeanWith2Fields bean) {
		field1IsNull = true;
		string = StringUtils.toString(bean.getField2());
	}
	
	/**
	 * Method with String argument.
	 */
	@SuppressWarnings("unused")
	private void methodWithStringArg(String arg) {
		/* empty */
	}

	/**
	 * Method with two arguments.
	 * @param bean
	 * @param arg
	 */
	@SuppressWarnings("unused")
	private void methodWithTwoArgs(BeanWith2Fields bean, String arg) {
		/* empty */
	}

	
	/**
	 * Tests setup.
	 */
	@Before
	public void setup() {
		this.field1IsNull = false;
		this.field1IsOneIndicator = false;
		this.field2IsZero = false;
		this.string = null;
	}
	
	/**
	 * Creates a ConditionalMethod to test.
	 * 
	 * @param onlyFirst
	 * 
	 * @return ConditionalMethod.
	 */
	@SuppressWarnings("nls")
	private ConditionalMethod<BeanWith2Fields, TestConditionalMethod> 
	getCM(boolean onlyFirst) {
		
		ConditionalMethod<BeanWith2Fields, TestConditionalMethod> cm = 
			new ConditionalMethod<BeanWith2Fields, TestConditionalMethod> 
			(onlyFirst,BeanWith2Fields.class, TestConditionalMethod.class);
		
		Condition<BeanWith2Fields> field1isOnE = 
			new PropertyEqualsTo<BeanWith2Fields>("field1", BeanWith2Fields.class, ONE);
		cm.register(field1isOnE, "setField1IsOneTrue");
				
		Condition<BeanWith2Fields> field2isZero = 
			new PropertyEqualsTo<BeanWith2Fields>("field2", BeanWith2Fields.class, 0);
		cm.register(field2isZero, "setField2IsZeroTrue");
		
		Condition<BeanWith2Fields> field1isNotNull = 
			new PropertyEqualsTo<BeanWith2Fields>("field2", BeanWith2Fields.class, 0);
		cm.register(field1isNotNull, "setField1IsNull");
		
		return cm;		
	}
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		ConditionalMethod<String, Date> cm = 
			new ConditionalMethod<String, Date>(true, String.class, Date.class);
		Assert.assertEquals(cm.invokeFirstTrueOnly, true);
		Assert.assertEquals(cm.clazzT, String.class);
		Assert.assertEquals(cm.clazzP, Date.class);
		Assert.assertEquals(0, cm.pairs.size());
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testRegister() {
		ConditionalMethod<String, Date> cm = 
			new ConditionalMethod<String, Date>(true, String.class, Date.class);
		cm.register(new IsNull<String>(), "getTime"); //$NON-NLS-1$
		Assert.assertEquals(1, cm.pairs.size());
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testRegister_withMethodThatHasTwoArgs() {
		ConditionalMethod<BeanWith2Fields, TestConditionalMethod> cm = 
			new ConditionalMethod<BeanWith2Fields, TestConditionalMethod> 
			(true,BeanWith2Fields.class, TestConditionalMethod.class);
		cm.register(new IsNull<BeanWith2Fields>(), "methodWithTwoArgs"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test(expected=RuntimeException.class)
	public void testRegister_withMethodThatHasOtherArgs() {
		ConditionalMethod<BeanWith2Fields, TestConditionalMethod> cm = 
			new ConditionalMethod<BeanWith2Fields, TestConditionalMethod> 
			(true,BeanWith2Fields.class, TestConditionalMethod.class);
		cm.register(new IsNull<BeanWith2Fields>(), "methodWithStringArg"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test()
	public void testExecute_onlyFirst() {
		ConditionalMethod<BeanWith2Fields, TestConditionalMethod> cm = 
			getCM(true);
		BeanWith2Fields bean = new BeanWith2Fields();
		bean.setField1(ONE);
		cm.execute(bean, this);
		Assert.assertTrue(this.field1IsOneIndicator);
		Assert.assertFalse(this.field2IsZero);
		Assert.assertFalse(this.field1IsNull);
		Assert.assertNull(this.string);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test()
	public void testExecute_all() {
		ConditionalMethod<BeanWith2Fields, TestConditionalMethod> cm = 
			getCM(false);
		BeanWith2Fields bean = new BeanWith2Fields();
		bean.setField1(null);
		bean.setField2(0);
		cm.execute(bean, this);
		Assert.assertFalse(this.field1IsOneIndicator);
		Assert.assertTrue(this.field2IsZero);
		Assert.assertTrue(this.field1IsNull);
		Assert.assertNotNull(this.string);
	}
	
	
	
	

}
