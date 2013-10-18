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
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.CustomerKey;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public class CustomerImpl 
extends AbstractModificationRecordPo<CustomerKey> 
implements Customer {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * name.
	 */
	private String customerName;
	
	/**
	 * afm
	 */
	private String taxId;
	
	/**
	 * addresses.
	 */
	@Child	
	private Set<CustomerAddress> addresses;
	
	/**
	 * Creates a new CustomerImpl object. 
	 *
	 */
	public CustomerImpl() {
		super();
		this.key = Factory.create(CustomerKey.class);
		Set<CustomerAddress> addressesSet = 
			new HashSet<CustomerAddress>(); 
		this.addresses = addressesSet;
		fixChild(addressesSet);
	}

	public String getCustomerNo() {
		return 	key.getCustomerNo();
	}
	
	public void setCustomerNo(String customerNo) {
		key.setCustomerNo(customerNo);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Set<CustomerAddress> getAddresses() {		
		return addresses;
	}

	public void setAddresses(Set<CustomerAddress> addresses) {
		this.addresses=addresses;
		fixChild(addresses);
	}
	
}
