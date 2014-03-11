package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRuleKey;

/**
 * 
 */
public class InvoiceSubRuleKeyImpl extends AbstractKey implements InvoiceSubRuleKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo;
	Long ruleCd;
	Long subRuleCd;
	

	/**
	 * Gets the subRuleCd.
	 *
	 * @return Returns the subRuleCd
	 */
	public Long getSubRuleCd() {
		return subRuleCd;
	}

	/**
	 * Assigns a new value to the subRuleCd.
	 *
	 * @param subRuleCd the subRuleCd to set
	 */
	public void setSubRuleCd(Long subRuleCd) {
		this.subRuleCd = subRuleCd;
	}

	/**
	 * Gets the ruleCd.
	 *
	 * @return Returns the ruleCd
	 */
	public Long getRuleCd() {
		return ruleCd;
	}

	/**
	 * Assigns a new value to the ruleCd.
	 *
	 * @param ruleCd the ruleCd to set
	 */
	public void setRuleCd(Long ruleCd) {
		this.ruleCd = ruleCd;
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
		return new Object[]{subRuleCd, ruleCd, invoiceNo};
	}


}
