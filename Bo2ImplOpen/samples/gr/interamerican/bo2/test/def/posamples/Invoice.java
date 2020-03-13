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
package gr.interamerican.bo2.test.def.posamples;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;

import java.util.Date;
import java.util.Set;

/**
 * Invoice is the root persistent entity of the hibernate
 * test cases.
 */
@SuppressWarnings("all")
public interface Invoice 
extends PersistentObject<InvoiceKey>, InvoiceKP, ModificationRecord {
	
	/**
	 * Gets the invoice date.
	 *
	 * @return the invoice date
	 */
	public Date getInvoiceDate();
	
	/**
	 * Sets the invoice date.
	 *
	 * @param invoiceDate the new invoice date
	 */
	public void setInvoiceDate(Date invoiceDate);
	
	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public InvoiceCustomer getCustomer();
	
	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public void setCustomer(InvoiceCustomer customer);
	
	/**
	 * Gets the lines.
	 *
	 * @return the lines
	 */
	public Set<InvoiceLine> getLines();
	
	/**
	 * Sets the lines.
	 *
	 * @param lines the new lines
	 */
	public void setLines(Set<InvoiceLine> lines);
	
	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public Set<InvoiceRule> getRules();
	
	/**
	 * Sets the rules.
	 *
	 * @param rules the new rules
	 */
	public void setRules(Set<InvoiceRule> rules);
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public InvoiceInfo getInfo();

	/**
	 * Sets the info.
	 *
	 * @param info the new info
	 */
	public void setInfo(InvoiceInfo info);
	
	/**
	 * Gets the line by no.
	 *
	 * @param lineNo the line no
	 * @return the line by no
	 */
	public InvoiceLine getLineByNo(Integer lineNo);
	

}
