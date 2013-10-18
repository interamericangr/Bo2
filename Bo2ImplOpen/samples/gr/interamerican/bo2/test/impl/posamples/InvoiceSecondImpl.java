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
package gr.interamerican.bo2.test.impl.posamples;

import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.annotations.DelegateKeyProperties;
import gr.interamerican.bo2.impl.open.po.AbstractModificationRecordPo;
import gr.interamerican.bo2.test.def.posamples.Invoice;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;
import gr.interamerican.bo2.test.def.posamples.InvoiceLine;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Date;
import java.util.Set;

/**
 * This class exists only for running the test of TestObjectsFactory.
 * 
 * This test requires a class that has not been created before.
 */
@DelegateKeyProperties("")
public abstract class InvoiceSecondImpl 
extends AbstractModificationRecordPo<InvoiceKey> 
implements Invoice {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * invoice date
	 */
	@SuppressWarnings("unused")
	@Property	
	private Date invoiceDate;
	
	/**
	 * customer.
	 */
	@SuppressWarnings("unused")
	@Property
	@Child
	private InvoiceCustomer customer;
	
	/**
	 * lines.
	 */
	@SuppressWarnings("unused")
	@Property
	@Child
	private Set<InvoiceLine> lines;
	
	public InvoiceLine getLineByNo(Integer lineNo) {
		if (lines==null) {
			return null;
		}
		return SelectionUtils.selectFirstByProperty("lineNo", lineNo, lines, InvoiceLine.class);		
	}
	
}
