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
package gr.interamerican.bo2.impl.open.po.utils;

import static org.junit.Assert.*;
import gr.interamerican.bo2.impl.open.operations.util.CustomCustomerAnalyzer;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.impl.posamplesConcrete.CustomerAddressImpl;
import gr.interamerican.bo2.test.impl.posamplesConcrete.CustomerImpl;

import org.junit.Test;

/**
 * Unit Tests of {@link PoMerger}.
 */
public class TestPoMerger {

	/**
	 * {@link SamplesFactory} used in this.
	 */
	private SamplesFactory bo2Factory = SamplesFactory.getBo2Factory();

	/**
	 * Test method for {@link PoMerger#merge(PoAnalyzer, MergeMode, Object, Object)} with failure.
	 */
	@Test(expected = RuntimeException.class)
	public void testMergePo_Fail1() {
		PoMerger.merge(new CustomCustomerAnalyzer(), MergeMode.FAVOR_SOURCE, new CustomerImpl(), new CustomerAddressImpl());
	}

	/**
	 * Test method for {@link PoMerger#merge(PoAnalyzer, MergeMode, Object, Object)} with failure.
	 */
	@Test(expected = RuntimeException.class)
	public void testMergePo_Fail2() {
		PoMerger.merge(new CustomCustomerAnalyzer(), MergeMode.FAVOR_SOURCE, bo2Factory.sampleAddress(1), bo2Factory.sampleAddress(2));
	}

	/**
	 * Test method for {@link PoMerger#merge(PoAnalyzer, MergeMode, Object, Object)}.
	 */
	@Test
	public void testMergePo() {
		CustomerAddress from = bo2Factory.sampleAddress(2);
		CustomerAddress to = bo2Factory.sampleAddress(2);
		from.setStreet("street"); //$NON-NLS-1$
		from.setStreetNo("streetNo"); //$NON-NLS-1$
		assertNotEquals(from.getStreet(), to.getStreet());
		assertNotEquals(from.getStreetNo(), to.getStreetNo());
		PoMerger.merge(new CustomCustomerAnalyzer(), MergeMode.OVERWRITE, from, to);
		assertEquals(from.getStreet(), to.getStreet());
		assertEquals(from.getStreetNo(), to.getStreetNo());
	}
}