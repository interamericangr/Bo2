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
	
	public Date getInvoiceDate();
	
	public void setInvoiceDate(Date invoiceDate);
	
	public InvoiceCustomer getCustomer();
	
	public void setCustomer(InvoiceCustomer customer);
	
	public Set<InvoiceLine> getLines();
	
	public void setLines(Set<InvoiceLine> lines);
	
	public Set<InvoiceRule> getRules();
	
	public void setRules(Set<InvoiceRule> rules);
	
	public InvoiceInfo getInfo();

	public void setInfo(InvoiceInfo info);
	
	public InvoiceLine getLineByNo(Integer lineNo);
	

}
