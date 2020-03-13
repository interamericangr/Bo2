package gr.interamerican.bo2.impl.open.po.analysis;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.operations.util.CustomCustomerAnalyzer;
import gr.interamerican.bo2.impl.open.po.MergeMode;
import gr.interamerican.bo2.impl.open.po.utils.PoAnalyzer;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.SamplesFactory;

/**
 * Unit test of {@link OneToOneProperty}.
 */
public class TestOneToOneProperty {

	/**
	 * Test method for
	 * {@link OneToOneProperty#merge(Object, Object, PoAnalyzer, MergeMode)}.
	 */
	@Test
	public void testMerge() {
		// samples
		SamplesFactory factory = SamplesFactory.getBo2Factory();
		InvoiceCustomer iCustomer = factory.newInvoiceCustomer();
		Customer customer = factory.sampleCustomer("foo"); //$NON-NLS-1$
		iCustomer.setCustomer(customer);
		// 1st case - null source - mode is not overwrite
		OneToOneProperty tested = Factory.create(OneToOneProperty.class);
		tested.setName("customer"); //$NON-NLS-1$
		tested.merge(null, iCustomer, null, MergeMode.FAVOR_TARGET);
		assertNotNull(iCustomer.getCustomer());
		// 2nd case - null source - mode is overwrite
		tested.merge(null, iCustomer, null, MergeMode.OVERWRITE);
		assertNull(iCustomer.getCustomer());
		// 3rd case - not null source , but target property was null
		tested.merge(customer, iCustomer, null, MergeMode.OVERWRITE);
		assertSame(customer, iCustomer.getCustomer());
		// 4rd case - not null source and target property
		tested.merge(factory.sampleCustomer("another"), iCustomer, new CustomCustomerAnalyzer(), MergeMode.FAVOR_SOURCE); //$NON-NLS-1$
		assertEquals("another", iCustomer.getCustomer().getTaxId()); //$NON-NLS-1$
	}
}