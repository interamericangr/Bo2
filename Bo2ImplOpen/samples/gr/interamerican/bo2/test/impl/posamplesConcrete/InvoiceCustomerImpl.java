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
import gr.interamerican.bo2.test.def.posamples.InvoiceCustomer;
import gr.interamerican.bo2.test.def.posamples.InvoiceKey;

/**
 * The Class InvoiceCustomerImpl.
 */
public class InvoiceCustomerImpl 
extends AbstractModificationRecordPo<InvoiceKey> 
implements InvoiceCustomer {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Role id.
	 */
	private Integer roleId;
	
	/**
	 * Address no.
	 */
	private Integer addressNoForInvoice;
	
	/**
	 * Customer info.
	 */
	private Customer customer;
	
	
	/**
	 * Creates a new InvoiceCustomerImpl object. 
	 *
	 */
	public InvoiceCustomerImpl() {
		super();
		this.key = Factory.create(InvoiceKey.class);
	}

	@Override
	public void setInvoiceNo(String invoiceNo) {
		key.setInvoiceNo(invoiceNo);		
		fixKeysOfChildren();
	}
	
	@Override
	public String getInvoiceNo() {
		return key.getInvoiceNo();
	}

	@Override
	public Integer getRoleId() {
		return roleId;
	}

	@Override
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public Integer getAddressNoForInvoice() {
		return addressNoForInvoice;
	}

	@Override
	public void setAddressNoForInvoice(Integer addressNoForInvoice) {
		this.addressNoForInvoice = addressNoForInvoice;
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
