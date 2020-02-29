package gr.interamerican.bo2.samples.implopen.queries;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.*;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.CustomerKP;

/**
 * 
 */
public class DummyCustomerQuery implements EntitiesQuery<Customer>,CriteriaDependent<CustomerKP> {
	
	@Override
	public void execute() throws DataException {
		return;
	}

	@Override
	public void open() throws DataException {
		// empty
	}

	@Override
	public void close() throws DataException {
		// empty
	}

	@Override
	public void init(Provider parent) throws InitializationException {
		// empty
	}

	@Override
	public Provider getProvider() {
		return null;
	}

	@Override
	public void setCriteria(CustomerKP criteria) {
		// empty
	}

	@Override
	public CustomerKP getCriteria() {
		return null;
	}

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
		// empty
	}

	@Override
	public boolean isAvoidLock() {
		return false;
	}

	@Override
	public Customer getEntity() throws DataAccessException {
		return null;
	}
}
