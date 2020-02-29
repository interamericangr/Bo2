package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.CustomerAddressKey;

/**
 * The Class CustomerAddressKeyImpl.
 */
public class CustomerAddressKeyImpl extends AbstractKey implements CustomerAddressKey {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String customerNo;
	/**
	 * 
	 */
	Integer addressNo;
	
	@Override
	protected Object[] getElements() {
		return new Object[]{addressNo, customerNo};
	}

	@Override
	public String getCustomerNo() {
		return customerNo;
	}

	@Override
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Override
	public Integer getAddressNo() {
		return addressNo;
	}

	@Override
	public void setAddressNo(Integer addressNo) {
		this.addressNo = addressNo;
	}

}
