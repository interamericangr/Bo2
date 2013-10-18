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
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceRule;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Invoice.
 */
@SuppressWarnings("nls")
public class InvoiceImpl 
extends AbstractModificationRecordPo<InvoiceKey> 
implements Invoice {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * date.
	 */
	private Date invoiceDate;
	
	/**
	 * customer.
	 */
	@Child
	private InvoiceCustomer customer;
	
	/**
	 * lines.
	 */
	@Child
	private Set<InvoiceLine> lines;
	
	/**
	 * rules.
	 */
	@Child
	private Set<InvoiceRule> rules;
	
	/**
	 * Info - mapped as a component.
	 */
	private InvoiceInfo info;

	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public InvoiceImpl() {
		super();
		this.key = Factory.create(InvoiceKey.class);
		this.lines = new HashSet<InvoiceLine>();
		this.rules = new HashSet<InvoiceRule>();
		fixChild(lines);
		fixChild(rules);
	}

	public String getInvoiceNo() {		
		return key.getInvoiceNo();
	}

	
	public void setInvoiceNo(String invoiceNo) {
		key.setInvoiceNo(invoiceNo);
		String[] properties = {"invoiceNo"};
		fixChildren(properties);
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;		
	}

	public InvoiceCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(InvoiceCustomer customer) {
		this.customer = customer;
		fixChild(customer);
	}

	public Set<InvoiceLine> getLines() {
		return lines;
	}

	public void setLines(Set<InvoiceLine> lines) {
		this.lines=lines;
		fixChild(lines);
	}

	public Set<InvoiceRule> getRules() {
		return this.rules;
	}

	public void setRules(Set<InvoiceRule> rules) {
		this.rules = rules;
		fixChild(rules);
	}

	public InvoiceInfo getInfo() {
		return info;
	}

	public void setInfo(InvoiceInfo info) {
		this.info = info;
	}
	
	
	public InvoiceLine getLineByNo(Integer lineNo) {
		if (lines==null) {
			return null;
		}
		return SelectionUtils.selectFirstByProperty("lineNo", lineNo, lines, InvoiceLine.class);		
	}
	
}
