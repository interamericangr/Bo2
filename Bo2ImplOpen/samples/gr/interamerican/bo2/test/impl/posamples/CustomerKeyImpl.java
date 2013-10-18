package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.CompanyUserKey;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;

/**
 * 
 */
public class CustomerKeyImpl extends AbstractKey implements CustomerKey {

	String customerNo;
	
	@Override
	protected Object[] getElements() {
		return new Object[]{customerNo};
	}

	@Override
	public String getCustomerNo() {
		return customerNo;
	}

	@Override
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

}
