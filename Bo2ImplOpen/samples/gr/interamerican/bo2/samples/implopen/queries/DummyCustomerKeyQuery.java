package gr.interamerican.bo2.samples.implopen.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;

/**
 * A {@link EntitiesQuery} of {@link CustomerKey} that is also {@link CriteriaDependent} of the {@link Customer}.
 */
public class DummyCustomerKeyQuery implements EntitiesQuery<CustomerKey>, CriteriaDependent<Customer> {

	@Override
	public boolean next() throws DataAccessException {
		return false;
	}

	@Override
	public int getRow() throws DataAccessException {
		return 0;
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		//empty
	}

	@Override
	public boolean isAvoidLock() {
		return false;
	}

	@Override
	public void open() throws DataException {
		//empty
	}

	@Override
	public void close() throws DataException {
		//empty
	}

	@Override
	public void init(Provider parent) throws InitializationException {
		//empty		
	}

	@Override
	public Provider getProvider() {
		return null;
	}

	@Override
	public void execute() throws DataException {
		//empty
	}

	@Override
	public void setCriteria(Customer criteria) {
		//empty
	}

	@Override
	public Customer getCriteria() {
		return null;
	}

	@Override
	public CustomerKey getEntity() throws DataAccessException {
		return null;
	}
}