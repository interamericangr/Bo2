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

/**
 * An {@link Invoice} owns one {@link InvoiceCustomer}.
 * This is a one-to-one relationship. The entities share
 * the same primary key.
 */
@SuppressWarnings("all")
public interface InvoiceCustomer 
extends InvoiceKP, PersistentObject<InvoiceKey>, ModificationRecord {
	
	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public Integer getRoleId();
	
	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(Integer roleId);
	
	/**
	 * Gets the address no for invoice.
	 *
	 * @return the address no for invoice
	 */
	public Integer getAddressNoForInvoice();
	
	/**
	 * Sets the address no for invoice.
	 *
	 * @param addressNo the new address no for invoice
	 */
	public void setAddressNoForInvoice(Integer addressNo);
	
	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer();
	
	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public void setCustomer(Customer customer);

}
