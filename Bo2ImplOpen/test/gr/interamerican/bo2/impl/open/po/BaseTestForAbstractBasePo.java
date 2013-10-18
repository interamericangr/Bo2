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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

import org.junit.Test;

/**
 * Tests functionality of {@link AbstractBasePo} using concrete 
 * implementations of the tested type.
 */
public class BaseTestForAbstractBasePo {
	
	/**
	 * SamplesFactory
	 */
	SamplesFactory samplesFactory;
	
	/**
	 * Creates a new TestAbstractBasePo object. 
	 * @param samplesFactory 
	 */
	public BaseTestForAbstractBasePo(SamplesFactory samplesFactory) {
		super();
		this.samplesFactory = samplesFactory;
		this.test1 = new AbstractBasePoTestBean(samplesFactory);
		this.test2 = new PoUtilsTestBean(samplesFactory);
	}
	
	/**
	 * Testing object.
	 */
	protected AbstractBasePoTestBean test1;
	
	/**
	 * Testing object.
	 */
	protected PoUtilsTestBean test2;
	
	

	/**
	 * See delegate.
	 */
	@Test
	public void testEqualForEqualInvoiceLines() {
		test2.testDeepEqualsForEqualInvoiceLines();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testEqualForNotEqualInvoiceLines() {
		test2.testDeepEqualsForNotEqualInvoiceLines();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testDeepEqualsForEqualInvoices() {
		test2.testDeepEqualsForEqualInvoices();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testEqualsForNonEqualInvoices() {
		test2.testDeepEqualsForNonEqualInvoices();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testCallingKeyDelegate() {
		test1.testCallingKeyDelegate();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testChangingTheKeyIsAppliedOnDelegate() {
		test1.testChangingTheKeyIsAppliedOnDelegate();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testSettingChild() {
		test1.testSettingChild();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testSettingTheKey() {
		test1.testSettingTheKey();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testModifyingTheKey() {
		test1.testModifyingTheKey();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testSettingChildCollection() {
		test1.testSettingChildCollection();
	}

	/**
	 * See delegate.
	 */
	@Test
	public void testInitializationOfChildCollection() {
		test1.testInitializationOfChildCollection();
	}
	
	/**
	 * 
	 * See delegate.
	 */
	@Test
	public void testSetterSetsTheReference() {
		test1.testSetterSetsTheReference();
	}
	
	/**
	 * Gets the sf.
	 *
	 * @return Returns the sf
	 */
	public SamplesFactory getSamplesFactory() {
		return samplesFactory;
	}

}
