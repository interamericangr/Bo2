/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.test.impl.posamplesConcrete;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRule;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRuleKey;

/**
 * Implementation of InvoiceSubRule.
 */
@SuppressWarnings("nls")
public class InvoiceSubRuleImpl 
extends AbstractModificationRecordPo<InvoiceSubRuleKey> 
implements InvoiceSubRule{
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * name.
	 */
	private String subRuleName;
	
	/**
	 * Creates a new InvoiceSubRuleImpl object. 
	 *
	 */
	public InvoiceSubRuleImpl() {
		super();
		this.key = Factory.create(InvoiceSubRuleKey.class);
	}

	public Long getSubRuleCd() {
		return key.getSubRuleCd();
	}

	public void setSubRuleCd(Long subRuleCd) {		
		key.setSubRuleCd(subRuleCd);
		String[] properties = {"subRuleCd"};
		fixChildren(properties);
	}

	public Long getRuleCd() {
		return key.getRuleCd();
	}

	public void setRuleCd(Long ruleCd) {
		key.setRuleCd(ruleCd);
		String[] properties = {"ruleCd"};
		fixChildren(properties);
	}

	public String getInvoiceNo() {
		return this.key.getInvoiceNo();
	}

	public void setInvoiceNo(String invoiceNo) {
		key.setInvoiceNo(invoiceNo);
		String[] properties = {"invoiceNo"};
		fixChildren(properties);
	}

	public void setSubRuleName(String name) {
		this.subRuleName = name;
	}

	public String getSubRuleName() {
		return this.subRuleName;
	}

}
