package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLineKey;

/**
 * The Class InvoiceSubLineKeyImpl.
 */
public class InvoiceSubLineKeyImpl extends AbstractKey implements InvoiceSubLineKey {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String invoiceNo;
	/**
	 * 
	 */
	Integer lineNo;
	/**
	 * 
	 */
	Integer subLineNo;
	/**
	 * Gets the subLineNo.
	 *
	 * @return Returns the subLineNo
	 */
	@Override
	public Integer getSubLineNo() {
		return subLineNo;
	}

	/**
	 * Assigns a new value to the subLineNo.
	 *
	 * @param subLineNo the subLineNo to set
	 */
	@Override
	public void setSubLineNo(Integer subLineNo) {
		this.subLineNo = subLineNo;
	}

	/**
	 * Gets the lineNo.
	 *
	 * @return Returns the lineNo
	 */
	@Override
	public Integer getLineNo() {
		return lineNo;
	}

	/**
	 * Assigns a new value to the lineNo.
	 *
	 * @param lineNo the lineNo to set
	 */
	@Override
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * Gets the invoiceNo.
	 *
	 * @return Returns the invoiceNo
	 */
	@Override
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * Assigns a new value to the invoiceNo.
	 *
	 * @param invoiceNo the invoiceNo to set
	 */
	@Override
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Override
	protected Object[] getElements() {
		return new Object[]{subLineNo, lineNo, invoiceNo};
	}


}
