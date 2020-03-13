package gr.interamerican.bo2.samples.implopen.queries;

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.impl.open.runtime.EntitiesQueryCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKP;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import org.junit.Test;

/**
 * Integration test for {@link CustomersFromInvoiceLineQuery}.
 */
public class TestCustomersFromInvoiceLineQuery extends Bo2BaseTest {

	/**
	 * Test method for {@link CustomersFromInvoiceLineQuery#sql()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSql() throws Exception {
		Execute.transactional(new DeleteInvoiceData());
		Factory.registerPwFixture(Customer.class, MockUtils.<Customer> mockPw(null, null, null));
		InvoiceLineKP criteria2 = Factory.create(InvoiceLineKP.class);
		criteria2.setInvoiceNo("w00t"); //$NON-NLS-1$
		EntitiesQueryCmd<CustomersFromInvoiceLineQuery, CustomerKey> cmd2 = new EntitiesQueryCmd<CustomersFromInvoiceLineQuery, CustomerKey>(
				CustomersFromInvoiceLineQuery.class);
		cmd2.getQuery().setCriteria(criteria2);
		assertTrue(cmd2.getResults().isEmpty());
	}
}