package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceRuleKey;

/**
 * The Class InvoiceRuleKeyImpl.
 */
public class InvoiceRuleKeyImpl extends AbstractKey implements InvoiceRuleKey {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Κωδικός Invoice
	 */
	String invoiceNo;
	/**
	 * Κωδικός Κανόνα
	 */
	Long ruleCd;
	

	/**
	 * Gets the ruleCd.
	 *
	 * @return Returns the ruleCd
	 */
	@Override
	public Long getRuleCd() {
		return ruleCd;
	}

	/**
	 * Assigns a new value to the ruleCd.
	 *
	 * @param ruleCd the ruleCd to set
	 */
	@Override
	public void setRuleCd(Long ruleCd) {
		this.ruleCd = ruleCd;
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
		return new Object[]{ruleCd, invoiceNo};
	}


}
