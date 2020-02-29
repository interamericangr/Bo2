package gr.interamerican.bo2.impl.open.operations.util;

import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.utils.adapters.Modification;

/**
 * Dummy {@link Modification} of {@link Customer} for tests.
 */
public class CustomerModification implements Modification<Customer>{

	@Override
	public Customer execute(Customer a) {
		return a;
	}
}