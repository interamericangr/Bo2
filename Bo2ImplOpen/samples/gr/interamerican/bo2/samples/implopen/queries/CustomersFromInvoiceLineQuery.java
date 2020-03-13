package gr.interamerican.bo2.samples.implopen.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.jdbc.parsed.DynamicJdbcQuery;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKP;

/**
 * {@link EntitiesQuery} to return {@link CustomerKey}s related with a
 * {@link InvoiceLineKP}.
 */
public class CustomersFromInvoiceLineQuery
extends DynamicJdbcQuery<InvoiceLineKP>
implements EntitiesQuery<CustomerKey> {

	/**
	 * Criteria
	 */
	@Parameter(isBean=true) InvoiceLineKP criteria;

	@Override
	public CustomerKey getEntity() throws DataAccessException {
		CustomerKey key = Factory.create(CustomerKey.class);
		key.setCustomerNo(getString(1));
		return key;
	}

	@Override
	public String baseSql() {
		return "select CUSTOMER_NO from X__X.INVOICELINE where LINE_NO = :lineNo and INVOICE_NO = :invoiceNo "; //$NON-NLS-1$
	}
}