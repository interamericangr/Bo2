package gr.interamerican.bo2.impl.open.po.analysis;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

/**
 * Unit test of {@link SimpleProperty}.
 */
public class TestSimpleProperty {

	/**
	 * Test method for {@link SimpleProperty#merge(Object, Object, PoAnalyzer, MergeMode)}.
	 */
	@Test
	public void testMerge() {
		// does nothing - merge mode is FAVOR_TARGET
		SimpleProperty tested = Factory.create(SimpleProperty.class);
		tested.merge(null, null, null, MergeMode.FAVOR_TARGET);
		// simple property merging
		tested.setName("taxId"); //$NON-NLS-1$
		tested.setType(String.class);
		Customer target = SamplesFactory.getBo2Factory().sampleCustomer("whatever", 2); //$NON-NLS-1$
		assertEquals("whatever", target.getTaxId()); //$NON-NLS-1$
		tested.merge("foo", target, null, MergeMode.FAVOR_SOURCE); //$NON-NLS-1$
		assertEquals("foo", target.getTaxId()); //$NON-NLS-1$
		// collection merging (handling CustomerAddress as not an entity for
		// this test scenario)
		tested.setName("addresses"); //$NON-NLS-1$
		tested.setType(Set.class);
		assertEquals(2, target.getAddresses().size());
		tested.merge(null, target, null, MergeMode.FAVOR_SOURCE);
		assertTrue(target.getAddresses().isEmpty());
	}

	/**
	 * Test method for {@link SimpleProperty#mergeNonPoCollection(Object, Object)}.
	 */
	@Test
	public void testMergeNonPoCollection() {
		// samples
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		Customer target = factory.sampleCustomer("whatever", 2); //$NON-NLS-1$
		Set<CustomerAddress> addresses = target.getAddresses();
		target.setAddresses(null);
		// null target collection case
		SimpleProperty tested = Factory.create(SimpleProperty.class);
		tested.setName("addresses"); //$NON-NLS-1$
		tested.mergeNonPoCollection(addresses, target);
		assertSame(addresses, target.getAddresses());
		// null source - not null target
		tested.mergeNonPoCollection(null, target);
		assertTrue(target.getAddresses().isEmpty());
		// not null source and target
		Set<CustomerAddress> newSource = new HashSet<CustomerAddress>();
		newSource.add(factory.sampleAddress(5));
		tested.mergeNonPoCollection(newSource, target);
		assertEquals(1, target.getAddresses().size());
		assertEquals(newSource,  target.getAddresses());
		assertNotSame(newSource,  target.getAddresses());
	}
}