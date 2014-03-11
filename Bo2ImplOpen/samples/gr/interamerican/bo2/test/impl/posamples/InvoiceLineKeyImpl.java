package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKey;

/**
 * 
 */
public class InvoiceLineKeyImpl extends AbstractKey implements InvoiceLineKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo;
	Integer lineNo;
	/**
	 * Gets the lineNo.
	 *
	 * @return Returns the lineNo
	 */
	public Integer getLineNo() {
		return lineNo;
	}

	/**
	 * Assigns a new value to the lineNo.
	 *
	 * @param lineNo the lineNo to set
	 */
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

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
		return new Object[]{lineNo, invoiceNo};
	}


}
