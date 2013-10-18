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
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLineKey;

/**
 * Implementation of InvoiceSubLine.
 */
@SuppressWarnings("nls")
public class InvoiceSubLineImpl 
extends AbstractModificationRecordPo<InvoiceSubLineKey> 
implements InvoiceSubLine {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * subline name.
	 */
	private String name;
	
	/**
	 * rule.
	 */
	private InvoiceRule rule;
	
	/**
	 * rule cd. This is maintained separately, because the rule
	 * is mapped with inser="false" update="false" in hibernate.
	 * When setting the rule, the rulecd is set as well, see the
	 * setRule(Rule rule).
	 */
	private Long ruleCd;
	
	/**
	 * Creates a new InvoiceLineImpl object. 
	 *
	 */
	public InvoiceSubLineImpl() {
		super();
		key = Factory.create(InvoiceSubLineKey.class);
	}

	public String getInvoiceNo() {		
		return key.getInvoiceNo();
	}

	public void setInvoiceNo(String invoiceNo) {
		key.setInvoiceNo(invoiceNo);
		String[] properties = {"invoiceNo"};
		fixChildren(properties);
	}
	
	public void setLineNo(Integer lineNo) {
		key.setLineNo(lineNo);
		String[] properties = {"lineNo"};
		fixChildren(properties);	
	}
	
	public Integer getLineNo() {
		return key.getLineNo();
	}
	
	public Integer getSubLineNo() {
		return key.getSubLineNo();
	}
	
	public void setSubLineNo(Integer subLineNo) {
		key.setSubLineNo(subLineNo);
		String[] properties = {"subLineNo"};
		fixChildren(properties);	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InvoiceRule getRule() {
		return rule;
	}

	public void setRule(InvoiceRule rule) {
		this.rule = rule;
		if(rule!=null) {
			this.ruleCd = rule.getRuleCd();
		}
	}

	public Long getRuleCd() {
		return ruleCd;
	}

	public void setRuleCd(Long ruleCd) {
		this.ruleCd = ruleCd;
	}

}
