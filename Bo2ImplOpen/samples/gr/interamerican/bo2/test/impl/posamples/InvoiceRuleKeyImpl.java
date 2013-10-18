package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.impl.open.po.AbstractKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceRuleKey;

/**
 * 
 */
public class InvoiceRuleKeyImpl extends AbstractKey implements InvoiceRuleKey {

	String invoiceNo;
	Long ruleCd;
	

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
		return new Object[]{ruleCd, invoiceNo};
	}


}
