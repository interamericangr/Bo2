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

import java.util.Set;

/**
 * An {@link InvoiceCustomer} has a reference to an actual Customer.
 * This is a many-to-one relationship.
 */
@SuppressWarnings("all")
public interface Customer 
extends CustomerKP, PersistentObject<CustomerKey>, ModificationRecord {
	
	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getCustomerName();
	
	/**
	 * Sets the customer name.
	 *
	 * @param customerName the new customer name
	 */
	public void setCustomerName(String customerName);
	
	/**
	 * Gets the tax id.
	 *
	 * @return the tax id
	 */
	public String getTaxId();
	
	/**
	 * Sets the tax id.
	 *
	 * @param taxId the new tax id
	 */
	public void setTaxId(String taxId);
	
	/**
	 * Gets the addresses.
	 *
	 * @return the addresses
	 */
	public Set<CustomerAddress> getAddresses();
	
	/**
	 * Sets the addresses.
	 *
	 * @param addresses the new addresses
	 */
	public void setAddresses(Set<CustomerAddress> addresses); 
	
}
