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
package gr.interamerican.bo2.impl.open.po.analysis;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.operations.util.CustomCustomerAnalyzer;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.test.def.posamples.ArrayWithAnnot;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;
import gr.interamerican.bo2.test.impl.posamplesConcrete.ArrayWithAnnotImpl;

/**
 * Unit test of {@link OneToManyProperty}.
 */
public class TestOneToManyProperty {

	/**
	 * Test method for
	 * {@link OneToManyProperty#merge(Object, Object, PoAnalyzer, MergeMode)}
	 * when the input is a Collection.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMerge_isCollection() {
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		Customer sampleCustomer = factory.sampleCustomer("foo"); //$NON-NLS-1$
		Customer targetCustomer = factory.sampleCustomer("bar"); //$NON-NLS-1$

		OneToManyProperty tested = spy(Factory.create(OneToManyProperty.class));
		tested.setType(ArrayList.class);
		tested.setName("addresses"); //$NON-NLS-1$

		doNothing().when(tested).mergePoCollection(any(Collection.class), any(Collection.class), any(PoAnalyzer.class),
				any(MergeMode.class));
		CustomCustomerAnalyzer analyzer = new CustomCustomerAnalyzer();
		tested.merge(sampleCustomer.getAddresses(), targetCustomer, analyzer, MergeMode.OVERWRITE);

		verify(tested).mergePoCollection(sampleCustomer.getAddresses(), targetCustomer.getAddresses(),
				analyzer, MergeMode.OVERWRITE);
	}

	/**
	 * Test method for
	 * {@link OneToManyProperty#merge(Object, Object, PoAnalyzer, MergeMode)}
	 * when the input is an Array.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMerge_isArray() {
		ArrayWithAnnot source = new ArrayWithAnnotImpl();
		ArrayWithAnnot target = new ArrayWithAnnotImpl();

		source.setArrayOfObjects(new Object[] {});
		target.setArrayOfObjects(new Object[] { "a" }); //$NON-NLS-1$

		OneToManyProperty tested = spy(Factory.create(OneToManyProperty.class));
		tested.setType(Object[].class);
		tested.setName("arrayOfObjects"); //$NON-NLS-1$

		doNothing().when(tested).mergePoCollection(any(Collection.class), any(Collection.class), any(PoAnalyzer.class),
				any(MergeMode.class));
		tested.merge(source.getArrayOfObjects(), target, new CustomCustomerAnalyzer(), MergeMode.OVERWRITE);

		assertEquals(target.getArrayOfObjects()[0], "a"); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link OneToManyProperty#mergePoCollection(Collection, Collection, PoAnalyzer, MergeMode)}
	 */
	@Test
	public void testMergePoCollection() {
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		CustomCustomerAnalyzer sampleAnalyzer = new CustomCustomerAnalyzer();

		List<Customer> source = new ArrayList<Customer>();
		List<Customer> target = new ArrayList<Customer>();
		Customer sampleCustomer = factory.sampleCustomer("foo"); //$NON-NLS-1$
		source.add(sampleCustomer);

		OneToManyProperty tested = Factory.create(OneToManyProperty.class);
		tested.setAnalysis(sampleAnalyzer.getAnalysis(Customer.class));
		tested.mergePoCollection(source, target, sampleAnalyzer, MergeMode.OVERWRITE);

		assertTrue(target.size() > 0);
		assertEquals(target.get(0), sampleCustomer);
	}

	/**
	 * Test method for
	 * {@link OneToManyProperty#mergePoCollection(Collection, Collection, PoAnalyzer, MergeMode)}
	 * where the target collection contains items that are not in the source
	 * collection.
	 */
	@Test
	public void testMergePoCollection_notInSource() {
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		CustomCustomerAnalyzer sampleAnalyzer = new CustomCustomerAnalyzer();

		List<Customer> source = new ArrayList<Customer>();
		List<Customer> target = new ArrayList<Customer>();
		Customer sampleCustomer = factory.sampleCustomer("foo"); //$NON-NLS-1$
		Customer existingCustomer = factory.sampleCustomer("bar"); //$NON-NLS-1$
		CustomerKey key = Factory.create(CustomerKey.class);
		key.setCustomerNo("foofoo"); //$NON-NLS-1$
		existingCustomer.setKey(key);

		source.add(sampleCustomer);
		target.add(sampleCustomer);
		target.add(existingCustomer);

		OneToManyProperty tested = Factory.create(OneToManyProperty.class);
		tested.setAnalysis(sampleAnalyzer.getAnalysis(Customer.class));
		tested.mergePoCollection(source, target, sampleAnalyzer, MergeMode.OVERWRITE);

		assertEquals(source, target);
		assertEquals(target.get(0), sampleCustomer);
	}
}