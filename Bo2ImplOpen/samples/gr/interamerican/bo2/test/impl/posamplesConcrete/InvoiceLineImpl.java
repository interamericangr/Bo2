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
import gr.interamerican.bo2.test.def.posamples.Customer;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.test.def.posamples.InvoiceLineKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceSubLine;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of InvoiceLine.
 */
@SuppressWarnings("nls")
public class InvoiceLineImpl 
extends AbstractModificationRecordPo<InvoiceLineKey> 
implements InvoiceLine {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * type.
	 */
	private Integer type;
	
	/** amount. */
	private Double amount;
	
	/**
	 * Customer.
	 */
	private Customer customer;
	
	/**
	 * subLines.
	 */
	@Child
	private Set<InvoiceSubLine> subLines;
	
	/**
	 * Creates a new InvoiceLineImpl object. 
	 *
	 */
	public InvoiceLineImpl() {
		super();
		key = Factory.create(InvoiceLineKey.class);
		this.subLines = new HashSet<InvoiceSubLine>();
		fixChild(subLines);
	}

	@Override
	public String getInvoiceNo() {		
		return key.getInvoiceNo();
	}

	@Override
	public void setInvoiceNo(String invoiceNo) {
		key.setInvoiceNo(invoiceNo);
		String[] properties = {"invoiceNo"};
		fixChildren(properties);
	}
	
	@Override
	public void setLineNo(Integer lineNo) {
		key.setLineNo(lineNo);		
		String[] properties = {"lineNo"};
		fixChildren(properties);
	}
	
	@Override
	public Integer getLineNo() {
		return key.getLineNo();
	}

	@Override
	public Integer getType() {
		return type;
	}

	@Override
	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public Double getAmount() {
		return amount;
	}

	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public Set<InvoiceSubLine> getSubLines() {
		Debug.increaseCounter("Invoice.getSubLines()");	//$NON-NLS-1$
		return subLines;
	}

	@Override
	public void setSubLines(Set<InvoiceSubLine> subLines) {
		Debug.increaseCounter("Invoice.setSubLines()"); //$NON-NLS-1$
		this.subLines=subLines;
		fixChild(subLines);
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
