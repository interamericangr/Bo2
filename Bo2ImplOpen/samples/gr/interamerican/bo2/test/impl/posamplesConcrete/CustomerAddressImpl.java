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
import gr.interamerican.bo2.test.def.posamples.CustomerAddress;
import gr.interamerican.bo2.test.def.posamples.CustomerAddressKey;

/**
 * 
 */
public class CustomerAddressImpl 
extends AbstractModificationRecordPo<CustomerAddressKey> 
implements CustomerAddress {
	
	
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CustomerAddressImpl object. 
	 *
	 */
	public CustomerAddressImpl() {
		super();
		this.key = Factory.create(CustomerAddressKey.class);		
	}

	/**
	 * street.
	 */
	private String street;
	
	/**
	 * number.
	 */
	private String streetNo;
	
	public String getCustomerNo() {
		return key.getCustomerNo();
	}
	
	public void setCustomerNo(String customerNo) {
		key.setCustomerNo(customerNo);
		fixKeysOfChildren();
	}
	
	public Integer getAddressNo() {
		return key.getAddressNo();
	}
	
	public void setAddressNo(Integer addressNo) {
		key.setAddressNo(addressNo);
		fixKeysOfChildren();
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getStreetNo() {
		return streetNo;
	}
	
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

}
