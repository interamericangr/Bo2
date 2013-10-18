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
import gr.interamerican.bo2.test.def.posamples.InvoiceRule;
import gr.interamerican.bo2.test.def.samples.InvoiceInfo;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.Date;
import java.util.Set;

/**
 * 
 */
@DelegateKeyProperties("")
public abstract class InvoiceImpl 
extends AbstractModificationRecordPo<InvoiceKey> 
implements Invoice {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * invoice date
	 */
	@Property Date invoiceDate;
	
	/**
	 * customer.
	 */
	@Child
	@Property InvoiceCustomer customer;
	
	/**
	 * lines.
	 */
	@Child
	@Property Set<InvoiceLine> lines;
	
	/**
	 * lines.
	 */
	@Child
	@Property Set<InvoiceRule> rules;
	
	/**
	 * info, mapped as a component.
	 */	
	@SuppressWarnings("unused")
	@Property private InvoiceInfo info;
	
	public InvoiceLine getLineByNo(Integer lineNo) {
		if (lines==null) {
			return null;
		}
		return SelectionUtils.selectFirstByProperty("lineNo", lineNo, lines, InvoiceLine.class);		 //$NON-NLS-1$
	}
	
}
