package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRuleKey;

/**
 * The Class InvoiceSubRuleKeyImpl.
 */
public class InvoiceSubRuleKeyImpl extends AbstractKey implements InvoiceSubRuleKey {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String invoiceNo;
	/**
	 * 
	 */
	Long ruleCd;
	/**
	 * 
	 */
	Long subRuleCd;
	

	/**
	 * Gets the subRuleCd.
	 *
	 * @return Returns the subRuleCd
	 */
	@Override
	public Long getSubRuleCd() {
		return subRuleCd;
	}

	/**
	 * Assigns a new value to the subRuleCd.
	 *
	 * @param subRuleCd the subRuleCd to set
	 */
	@Override
	public void setSubRuleCd(Long subRuleCd) {
		this.subRuleCd = subRuleCd;
	}

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
		return new Object[]{subRuleCd, ruleCd, invoiceNo};
	}


}
