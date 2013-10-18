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

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link DynamicMethod}.
 */
public class TestDynamicMethod {
		
	/**
	 * Flag 1.
	 */
	private boolean flag1 = false;
	
	/**
	 * Flag 1.
	 */
	private boolean flag2 = false;
	
	/**
	 * Object to test.
	 */
	private DynamicMethod<Integer, TestDynamicMethod> dm = createTestObject();
	
	
	
	/**
	 * Set flag 1 to true.
	 */
	@SuppressWarnings("unused")
	private void lightFlag1() {
		flag1 = true;
	}
	
	/**
	 * Set flag 2 to true.
	 */
	@SuppressWarnings("unused")
	private void lightFlag2() {
		flag2 = true;
	}
	
	/**
	 * Tests setup.
	 */
	@Before
	public void setup() {
		this.flag1 = false;
		this.flag2 = false;
	}
	
	/**
	 * Creates a DynamicMethod to test.
	 * 
	 * @return DynamicMethod.
	 */
	@SuppressWarnings("nls")
	private DynamicMethod<Integer, TestDynamicMethod> createTestObject() {		
		DynamicMethod<Integer, TestDynamicMethod> dyna = 
			new DynamicMethod<Integer, TestDynamicMethod>(TestDynamicMethod.class);
		dyna.register(0, "lightFlag1");
		dyna.register(1, "lightFlag1");
		dyna.register(2, "lightFlag2");
		return dyna;		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		DynamicMethod<String, Date> cm = 
			new DynamicMethod<String, Date>(Date.class);
		Assert.assertEquals(cm.clazzP, Date.class);
		Assert.assertEquals(0, cm.methods.size());
	}
	
	/**
	 * Tests Register.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRegister() {
		DynamicMethod<String, Date> cm = 
			new DynamicMethod<String, Date>(Date.class);
		String key = "time"; 
		cm.register(key , "getTime"); 
		Assert.assertNotNull(cm.methods.get(key));
		Assert.assertEquals(1, cm.methods.size());
	}
	
	/**
	 * Tests Register.
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testRegister_withMethodThatHasArgs() {
		DynamicMethod<String, Date> m = 
			new DynamicMethod<String, Date>(Date.class);
		m.register("after", "after");
	}
	
	/**
	 * Tests Register.
	 */
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testRegister_withNotExistingMethod() {
		DynamicMethod<String, Date> m = 
			new DynamicMethod<String, Date>(Date.class);
		m.register("after", "nosuchmethod");
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test()
	public void testExecute() {
		dm.execute(1, this);
		Assert.assertTrue(this.flag1);
		Assert.assertFalse(this.flag2);
		dm.execute(2, this);
		Assert.assertTrue(this.flag2);
	}
}
