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
import gr.interamerican.bo2.test.def.posamples.InvoiceRule;
import gr.interamerican.bo2.test.def.posamples.InvoiceRuleKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubRule;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of InvoiceRule.
 */
@SuppressWarnings("nls")
public class InvoiceRuleImpl 
extends AbstractModificationRecordPo<InvoiceRuleKey> implements InvoiceRule {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sub rules.
	 */
	@Child
	private Set<InvoiceSubRule> subRules;
	
	/**
	 * name.
	 */
	private String ruleName;
	
	/**
	 * Creates a new InvoiceRuleImpl object. 
	 *
	 */
	public InvoiceRuleImpl() {
		super();
		key = Factory.create(InvoiceRuleKey.class);
		this.subRules = new HashSet<InvoiceSubRule>();
		fixChild(subRules);
	}

	public Long getRuleCd() {
		return this.key.getRuleCd();
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

	public void setRuleName(String name) {
		this.ruleName = name;
	}

	public String getRuleName() {
		return ruleName;
	}

	public Set<InvoiceSubRule> getSubRules() {
		return subRules;
	}

	public void setSubRules(Set<InvoiceSubRule> subRules) {
		this.subRules = subRules;
		fixChild(subRules);
	}

}
