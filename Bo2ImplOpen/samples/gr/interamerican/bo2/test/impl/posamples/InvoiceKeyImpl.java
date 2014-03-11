package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;

/**
 * 
 */
public class InvoiceKeyImpl extends AbstractKey implements InvoiceKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo;
	
	/**
	 * Gets the invoiceNo.
	 *
	 * @return Returns the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * Assigns a new value to the invoiceNo.
	 *
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Override
	protected Object[] getElements() {
		return new Object[]{invoiceNo};
	}

}
