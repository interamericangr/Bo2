package gr.interamerican.bo2.samples.implopen.queries;

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.impl.open.runtime.EntitiesQueryCmd;
import gr.interamerican.bo2.impl.open.runtime.Execute;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKP;
import gr.interamerican.bo2.test.scenarios.DeleteInvoiceData;

import org.junit.Test;

/**
 * Integration test for {@link CustomersFromInvoiceCustomerQuery}.
 */
public class TestCustomersFromInvoiceCustomerQuery extends Bo2BaseTest {

	/**
	 * Test method for {@link CustomersFromInvoiceCustomerQuery#sql()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSql() throws Exception {
		Execute.transactional(new DeleteInvoiceData());
		Factory.registerPwFixture(Customer.class, MockUtils.<Customer> mockPw(null, null, null));
		InvoiceKP criteria1 = Factory.create(InvoiceKP.class);
		criteria1.setInvoiceNo("w00t"); //$NON-NLS-1$
		EntitiesQueryCmd<CustomersFromInvoiceCustomerQuery, Customer> cmd1 = new EntitiesQueryCmd<CustomersFromInvoiceCustomerQuery, Customer>(
				CustomersFromInvoiceCustomerQuery.class);
		cmd1.getQuery().setCriteria(criteria1);
		assertTrue(cmd1.getResults().isEmpty());
	}
}