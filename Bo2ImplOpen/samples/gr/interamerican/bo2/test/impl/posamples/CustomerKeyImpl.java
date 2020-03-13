package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;

/**
 * The Class CustomerKeyImpl.
 */
public class CustomerKeyImpl extends AbstractKey implements CustomerKey {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Κωδικός Πελάτη
	 */
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
