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
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerSet;
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomerSetKey;

import java.util.Set;

/**
 * 
 */
public class InvoiceCustomerSetImpl 
extends AbstractModificationRecordPo<InvoiceCustomerSetKey> 
implements InvoiceCustomerSet {
	
	/**
	 * uid.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * set with invoice customers
	 */
	private Set<InvoiceCustomer> customers;

	
	/**
	 * ���������� customers.
	 *
	 * @return customers
	 */
	
	public Set<InvoiceCustomer> getCustomers() {
		return customers;
	}

	/**
	 * ��������� customers.
	 *
	 * @param customers 
	 */
	public void setCustomers(Set<InvoiceCustomer> customers) {
		this.customers = customers;
	}


	public String getInvoiceCustomerSetNo() {		
		return key.getInvoiceCustomerSetNo();
	}
	

	public void setInvoiceCustomerSetNo(String invoiceCustomerSetNo) {
		key.setInvoiceCustomerSetNo(invoiceCustomerSetNo);
	}

  
	/**
	 * Creates a new InvoiceImpl object. 
	 *
	 */
	public InvoiceCustomerSetImpl() {
		super();
		this.key = Factory.create(InvoiceCustomerSetKey.class);
	}
	

}
