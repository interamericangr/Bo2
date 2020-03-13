package gr.interamerican.bo2.samples.implopen.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.jdbc.JdbcQuery;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKP;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * {@link EntitiesQuery} to return {@link Customer}s related with a
 * {@link InvoiceKP}.<br>
 * In fact, this is one or zero {@link Customer}s, but for the sake of testing
 * we created this.
 */
public class CustomersFromInvoiceCustomerQuery
extends JdbcQuery
implements EntitiesQuery<Customer>, CriteriaDependent<InvoiceKP> {

	/**
	 * Criteria
	 */
	@Parameter(isBean=true) InvoiceKP criteria;

	/**
	 * {@link PersistenceWorker} of {@link Customer}.
	 */
	@Child PersistenceWorker<Customer> pw = Factory.createPw(Customer.class);

	@Override
	public void setCriteria(InvoiceKP criteria) {
		this.criteria  = criteria;
	}

	@Override
	public InvoiceKP getCriteria() {
		return criteria;
	}

	@Override
	public Customer getEntity() throws DataAccessException {
		Customer customer = Factory.create(Customer.class);
		customer.setCustomerNo(getString(1));
		try {
			return pw.read(customer);
		} catch (DataException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	protected String sql() {
		return "select CUSTOMER_NO from X__X.INVOICECUSTOMER where INVOICE_NO = :invoiceNo "; //$NON-NLS-1$
	}
}